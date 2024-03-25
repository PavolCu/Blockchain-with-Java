package blockchain;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class Block {
    int id;
    long timestamp;
    String prevHash;
    String hash;
    int magicNumber;
    long generationTime;

    public Block(int id, String prevHash) {
        this.id = id;
        this.timestamp = new Date().getTime();
        this.prevHash = prevHash;
        this.magicNumber = new Random().nextInt();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(id + Long.toString(timestamp) + prevHash + magicNumber);
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        long startTime = System.currentTimeMillis();
        while (!hash.substring(0, difficulty).equals(target)) {
            magicNumber++;
            hash = calculateHash();
        }
        long endTime = System.currentTimeMillis();
        generationTime = (endTime - startTime) / 1000; // convert to seconds
    }

    @Override
    public String toString() {
        return String.format("""
                Block:
                Id: %d
                Timestamp: %d
                Magic number: %d
                Hash of the previous block:\u0020
                %s
                Hash of the block:\u0020
                %s
                Block was generating for %d seconds
                """, id, timestamp, magicNumber, prevHash, hash, generationTime);
    }
}