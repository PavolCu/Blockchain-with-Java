package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    List<Block> blocks;
    int N = 0;

    public Blockchain(int minerId, int magicNumber, long generationTime, int nValue) {
        blocks = new ArrayList<>();
        blocks.add(new Block(1, "0", minerId, magicNumber, generationTime, nValue));
    }

    public synchronized void addBlockSynchronized(Block block) {
        if (isValidNewBlock(block)) {
            blocks.add(block);
            adjustN(block.getGenerationTime());
        }
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