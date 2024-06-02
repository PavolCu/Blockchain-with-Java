package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Blockchain {
    List<Block> blocks;
    int N = 0;
    private BlockingDeque<Message> pendingMessages = new LinkedBlockingDeque<>();

    public Blockchain(int minerId, int magicNumber, long generationTime, int nValue) {
        blocks = new ArrayList<>();
        blocks.add(new Block(1, "0", minerId, magicNumber, generationTime, nValue, new ArrayList<>()));
    }

   public synchronized boolean addBlockSynchronized(Block block) {
    if (isValidNewBlock(block)) {
        adjustN(block.getGenerationTime());
        blocks.add(block);
        pendingMessages.clear();
        return true;
    }
    return false;
}

    public synchronized void addMessage(Message message) {

        pendingMessages.add(message);
    }

    public synchronized List<Message> getAndClearPendingMessages() {
        List<Message> messages = new ArrayList<>(pendingMessages);
        pendingMessages.clear();
        return messages;
    }

    public List<Message> getPendingMessages() {
        // Return a new list containing all elements from pendingMessages
        return new ArrayList<>(pendingMessages);
    }
    public boolean isValidNewBlock(Block newBlock) {
        Block lastBlock = blocks.get(blocks.size() - 1);
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