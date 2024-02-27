package blockchain;

import java.security.MessageDigest;
import java.util.Date;

public class Block {
    int id;
    long timestamp;
    String prevHash;
    String hash;

    public Block(int id, String prevHash) {
        this.id = id;
        this.timestamp = new Date().getTime();
        this.prevHash = prevHash;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(id + Long.toString(timestamp) + prevHash);
    }

    @Override
    public String toString() {
        return String.format("""
                Block:
                Id: %d
                Timestamp: %d
                Hash of the previous block:\u0020
                %s
                Hash of the block:\u0020
                %s
                """, id, timestamp, prevHash, hash);
    }
}