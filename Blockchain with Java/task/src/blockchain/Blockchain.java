package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    List<Block> blocks;

    public Blockchain() {
        blocks = new ArrayList<>();
        blocks.add(new Block(1, "0"));
    }

    public void addBlock() {
        int newId = blocks.size() + 1;
        String prevHash = blocks.get(blocks.size() - 1).hash;
        blocks.add(new Block(newId, prevHash));
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