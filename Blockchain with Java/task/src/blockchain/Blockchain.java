package blockchain;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Blockchain {
    private final List<String> messages = new ArrayList<>();
    private final List<Block> blocks = new ArrayList<>();
    private final Lock blockLock = new ReentrantLock();
    private int N = 0;
    private volatile boolean isMining = true;
    public final long miningTimeout = 12000;
    private static final int MAX_MESSAGES = 4; // Limit the number of messages

    public Blockchain() {
        // The first block, with no messages and a previous hash of "0"
        blocks.add(new Block(1, "0", 0, 0, System.currentTimeMillis(), 0, new ArrayList<>()));
    }

    public void addMessage(String message) {
        synchronized (messages) {
            if (messages.size() < MAX_MESSAGES) {
                messages.add(message);
            }
        }
    }

    public boolean addBlock(Block block) {
        blockLock.lock();
        try {
            if (blocks.size() >= 5) {
                stopMining();
                return false;
            }
            if (isValidNewBlock(block)) {
                blocks.add(block);
                adjustN(block.getGenerationTime());
                return true;
            }
            return false;
        } finally {
            blockLock.unlock();
        }
    }

    private boolean isValidNewBlock(Block newBlock) {
        if (blocks.isEmpty()) {
            return newBlock.getPrevHash().equals("0");
        }
        Block lastBlock = blocks.get(blocks.size() - 1);
        return newBlock.getPrevHash().equals(lastBlock.getHash()) && newBlock.getHash().startsWith("0".repeat(N));
    }

    private void adjustN(long generationTime) {
        if (generationTime < 1000) {
            N++;
        } else if (generationTime > 2000) {
            N--;
        }
    }

    public List<String> getMessages() {
        synchronized (messages) {
            return new ArrayList<>(messages);
        }
    }

    public Block getLastBlock() {
        return blocks.isEmpty() ? null : blocks.get(blocks.size() - 1);
    }

    public void stopMining() {
        isMining = false;
    }

    public boolean isMining() {
        return isMining;
    }

    public int getSize() {
        return blocks.size();
    }

    public int getN() {
        return N;
    }

    public long getMiningTimeout() {
        return miningTimeout;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Block block : blocks) {
            sb.append(block).append("\n");
        }
        return sb.toString();
    }
}