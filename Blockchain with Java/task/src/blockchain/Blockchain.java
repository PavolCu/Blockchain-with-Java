package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    List<Block> blocks;
    int difficulty;

    public Blockchain(int difficulty) {
        this.difficulty = difficulty;
        blocks = new ArrayList<>();
        Block genesisBlock = new Block(1, "0");
        genesisBlock.mineBlock(difficulty);
        blocks.add(genesisBlock);
    }

    public void addBlock() {
        int newId = blocks.size() + 1;
        String prevHash = blocks.get(blocks.size() - 1).hash;
        Block newBlock = new Block(newId, prevHash);
        newBlock.mineBlock(difficulty);
        blocks.add(newBlock);
    }

    public boolean isValid() {
        for (int i = 1; i < blocks.size(); i++) {
            Block currentBlock = blocks.get(i);
            Block previousBlock = blocks.get(i - 1);

            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                return false;
            }

            if (!currentBlock.prevHash.equals(previousBlock.hash)) {
                return false;
            }
        }
        return true;
    }
}

