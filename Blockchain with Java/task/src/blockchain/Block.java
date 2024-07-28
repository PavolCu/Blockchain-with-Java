package blockchain;

import java.util.List;

public class Block {
    private final int id;
    private final String prevHash;
    private final int minerId;
    private final int magicNumber;
    private final long timestamp;
    private final String hash;
    private final List<String> messages;
    private final int n;
    private final long generationTime;

    public Block(int id, String prevHash, int minerId, int magicNumber, long timestamp, int n, List<String> messages) {
        this.id = id;
        this.prevHash = prevHash;
        this.minerId = minerId;
        this.magicNumber = magicNumber;
        this.timestamp = timestamp;
        this.n = n;
        this.messages = List.copyOf(messages);
        this.generationTime = System.currentTimeMillis() - timestamp;
        this.hash = StringUtil.applySha256(id + prevHash + minerId + magicNumber + timestamp + n + messages.toString()); // MODIFIKÁCIA
    }

    public int getId() {
        return id;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public int getMinerId() {
        return minerId;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public int getN() {
        return n;
    }

    public List<String> getMessages() {
        return messages;
    }


    public long getGenerationTime() {
        return System.currentTimeMillis() - timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block: \n");
        sb.append("Created by miner #").append(minerId).append("\n");
        sb.append("Id: ").append(id).append("\n");
        sb.append("Timestamp: ").append(timestamp).append("\n");
        sb.append("Magic number: ").append(magicNumber).append("\n");
        sb.append("Hash of the previous block: \n").append(prevHash).append("\n");
        sb.append("Hash of the block: \n").append(hash).append("\n");
        sb.append("Block data: ").append(messages.isEmpty() ? "no messages" : String.join("\n", messages)).append("\n");
        sb.append("Block was generating for ").append(getGenerationTime() / 1000).append(" seconds\n");
        sb.append("N was increased to ").append(n).append("\n");
        return sb.toString();
    }
}
