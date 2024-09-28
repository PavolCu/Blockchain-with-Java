package blockchain;

import java.util.List;

public class Block {
    private final int id;
    private final String prevHash;
    private final int minerId;
    private final int magicNumber;
    private final long timestamp;
    private final String hash;
    private final List<Transaction> transactions;
    private final int n;
    private final long generationTime = System.currentTimeMillis();

    public Block(int id, String prevHash, int minerId, int magicNumber, long timestamp, int n, List<Transaction> transactions) {
        this.id = id;
        this.prevHash = prevHash;
        this.minerId = minerId;
        this.magicNumber = magicNumber;
        this.timestamp = timestamp;
        this.n = n;
        this.transactions = List.copyOf(transactions);
        this.hash = StringUtil.applySha256(id + prevHash + minerId + magicNumber + timestamp + n + transactions);
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getHash() {
        return hash;
    }

    public int getN() {
        return n;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public long getGenerationTime() {
        return generationTime;
    }

    @Override
    public String toString() {
        long generationTime = System.currentTimeMillis() - timestamp;
        return "Block: \n" +
                "Created by: miner" + minerId + "\n" +
                "miner" + minerId + " gets 100 VC\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block: \n" + prevHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block data: " + (transactions.isEmpty() ? "No transactions" : transactions) + "\n" +
                "Block was generating for " + (generationTime / 1000) + " seconds\n" +
                "N was increased to " + n + "\n";
    }
}