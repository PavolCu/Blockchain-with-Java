package blockchain;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Blockchain {
    private final List<Transaction> pendingTransactions = new ArrayList<>(MAX_TRANSACTIONS);
    private final List<Block> blocks = new ArrayList<>();
    private final Lock blockLock = new ReentrantLock();
    private int N = 0;
    private volatile boolean isMining = true;
    private static final int MAX_TRANSACTIONS = 10; // Limit the number of transactions

    public Blockchain() {
        // The first block, with no transactions and a previous hash of "0"
        blocks.add(new Block(1, "0", 0, 0, System.currentTimeMillis(), 0, new ArrayList<>()));
    }

    public void addTransaction(Transaction transaction) {
        synchronized (pendingTransactions) {
            if (pendingTransactions.size() < MAX_TRANSACTIONS) {
                pendingTransactions.add(transaction);
            }
        }
    }

    public boolean addBlock(Block block) {
        blockLock.lock();
        try {
            if (blocks.size() >= 15) {
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
        return newBlock.getPrevHash().equals(lastBlock.getHash()) && newBlock.getHash().startsWith("0".repeat(newBlock.getN()));
    }

    private void adjustN(long generationTime) {
        if (generationTime < 1000) {
            N = Math.min(N + 1, 5); // Limit the number of zeros to 5
        } else if (generationTime > 2000) {
            N = Math.max(N - 1, 0);
        }
    }

    public List<Transaction> getPendingTransactions() {
        synchronized (pendingTransactions) {
            return new ArrayList<>(pendingTransactions);
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

    public int getBalance(String user) {
        int balance = 100; // Everyone starts with 100 virtual coins
        for (Block block : blocks) {
            for (Transaction transaction : block.getTransactions()) {
                if (transaction.sender().equals(user)) {
                    balance -= transaction.amount();
                }
                if (transaction.receiver().equals(user)) {
                    balance += transaction.amount();
                }
            }
        }
        return balance;
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