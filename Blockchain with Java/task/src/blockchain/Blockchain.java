package blockchain;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Blockchain {
    private final Set<String> messages = new HashSet<>();
    private final Map<Integer, Integer> minerStats = new HashMap<>();
    private final List<Block> blocks = new ArrayList<>();
    private final Lock blockLock = new ReentrantLock();
    private int N = 0;
    private volatile boolean isMining = true;

    public Blockchain() {
        // The first block, with no messages and a previous hash of "0"
        blocks.add(new Block(1, "0", 0, 0, System.currentTimeMillis(), 0, new ArrayList<>()));
    }

    public void addMessage(String message) {
        synchronized (messages) {
            if (messages.size() < 4) {
                messages.add(message);
            }
        }
    }

    public boolean addBlock(Block block) {
        blockLock.lock();
        try {
            if (!isMining || blocks.size() == 5) {
                isMining = false;
                return false;
            }
            if (isValidNewBlock(block)) {
                blocks.add(block);
                adjustN(block.getGenerationTime());
                synchronized (messages) {
                    messages.clear();
                }
                minerStats.merge(block.getMinerId(), 1, Integer::sum);
                if (blocks.size() == 5) {
                    isMining = false;
                }
                return true;
            }
            return false;
        } finally {
            blockLock.unlock();
        }
    }

    private boolean isValidNewBlock(Block newBlock) {
        Block lastBlock = blocks.get(blocks.size() - 1);
        return newBlock.getPrevHash().equals(lastBlock.getHash()) && newBlock.getHash().startsWith("0".repeat(N));
    }

    private void adjustN(long generationTime) {
        if (generationTime < 1000) {
            N++;
        } else if (generationTime > 2000 && N > 0) {
            N--;
        }
    }

    public List<String> getMessages() {
        synchronized (messages) {
            return new ArrayList<>(messages);
        }
    }

    public Block getLastBlock() {
        blockLock.lock();
        try {
            return blocks.get(blocks.size() - 1);
        } finally {
            blockLock.unlock();
        }
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

    public void printMinerStats() {
        minerStats.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("Miner " + entry.getKey() + " mined " + entry.getValue() + " blocks"));
    }

    @Override
    public String toString() {
        return blocks.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}