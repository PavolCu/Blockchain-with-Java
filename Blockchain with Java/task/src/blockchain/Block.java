package blockchain;

import java.util.Date;

public class Block {
    private int id;
    private long timestamp;
    private String prevHash;
    private String hash;
    private int minerId;
    private int magicNumber;
    private long generationTime;
    private int nValue;

    public Block(int id, String prevHash, int minerId, int magicNumber, long generationTime, int nValue) {
        this.id = id;
        this.prevHash = prevHash;
        this.timestamp = new Date().getTime();
        this.minerId = minerId;
        this.magicNumber = magicNumber;
        this.generationTime = generationTime;
        this.nValue = nValue;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(id + prevHash + timestamp + minerId + magicNumber + generationTime + nValue);
        // Implementation of hash calculation...
    }


    public String getHash() {
        return this.hash;
    }

    public String getPrevHash() {
        return this.prevHash;
    }

    public long getGenerationTime() {
        return this.generationTime;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Block:\n" +
                "Created by miner # " + minerId + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" +
                prevHash + "\n" +
                "Hash of the block:\n" +
                hash + "\n" +
                "Block was generating for " + generationTime + " seconds\n" +
                "N was increased to " + nValue + "\n";
    }
}