package blockchain;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Blockchain {
    List<Block> blocks;
    int N = 0;
    private final ConcurrentLinkedQueue<Message> nextBlockMessages = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Message> currentBlockMessages = new ConcurrentLinkedQueue<>();

    private final Object lock;
    private Block lastBlock;

    public Blockchain(int minerId, int magicNumber, long generationTime, int nValue, Object lock) {
        this.lock = lock;
        blocks = new LinkedList<>();
        lastBlock = new Block(0, "0", minerId, magicNumber, generationTime, nValue, new LinkedList<>());
        blocks.add(lastBlock);
    }

    public synchronized boolean addBlockSynchronized(Block block) {
        if (isValidNewBlock(block)) {
            adjustN(block.getGenerationTime());
            lastBlock = block;
            blocks.add(block);
            nextBlockMessages.addAll(currentBlockMessages);
            currentBlockMessages.clear();
            lock.notifyAll();
            return true;
        }
        return false;
    }

    public synchronized Queue<Message> getAndClearNextBlockMessages() {
        Queue<Message> messages = new LinkedList<>(nextBlockMessages);
        nextBlockMessages.clear();
        return messages;
    }

    public synchronized void addMessage(Message message) {
        currentBlockMessages.add(message);
    }

    public boolean isValidNewBlock(Block newBlock) {
        return newBlock.getPrevHash().equals(lastBlock.getHash()) && newBlock.getHash().startsWith("0".repeat(N));
    }

    public void adjustN(long generationTime) {
        if (generationTime < 1) {
            N++;
        } else if (generationTime > 2) {
            if (N > 0) {
                N--;
            }
        }
    }

    public int getN() {
        return N;
    }

    public Block getLastBlock() {
        return blocks.get(blocks.size() - 1);
    }
}
