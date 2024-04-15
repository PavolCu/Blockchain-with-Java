package blockchain;

public class Miner implements Runnable {
    private int id;
    private Blockchain blockchain;

    Miner(int id, Blockchain blockchain) {
        this.id = id;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        while (true) {
            Block lastBlock = blockchain.getLastBlock();
            int newId = lastBlock.getId() + 1;
            String prevHash = lastBlock.getHash();
            long startTime = System.currentTimeMillis();
            int magicNumber = findMagicNumber(prevHash, blockchain.getN());
            long generationTime = (System.currentTimeMillis() - startTime) / 1000;
            Block newBlock = new Block(newId, prevHash, id, magicNumber, generationTime, blockchain.getN());
            blockchain.addBlockSynchronized(newBlock);
            if (blockchain.blocks.size() >= 5) {
                break;
            }
        }
    }

    private int findMagicNumber(String prevHash, int N) {
        int magicNumber = 0;
        while (true) {
            String hash = StringUtil.applySha256(prevHash + magicNumber);
            if (hash.startsWith("0".repeat(N))) {
                break;
            }
            magicNumber++;
        }
        return magicNumber;
    }
}