package blockchain;

import java.util.List;

public class Miner implements Runnable {
    private final int id;
    private final Blockchain blockchain;
    private final Object lock;

    public Miner(int id, Blockchain blockchain, Object lock) {
        this.id = id;
        this.blockchain = blockchain;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (blockchain.isMining() && blockchain.getSize() < 5) {
            Block lastBlock = blockchain.getLastBlock();
            List<String> messages = blockchain.getMessages();
            int magicNumber = findMagicNumber(lastBlock.getHash(), blockchain.getN(), messages);
            if (!blockchain.isMining()) {
                break;
            }
            long generationTime = System.currentTimeMillis();
            Block newBlock = new Block(lastBlock.getId() + 1, lastBlock.getHash(), id, magicNumber, generationTime, blockchain.getN(), messages);
            if (blockchain.addBlock(newBlock)) {
                System.out.println(newBlock);
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        }
        synchronized (lock) {
            if (!blockchain.isMining() || blockchain.getSize() == 5) {
                blockchain.stopMining();
                lock.notifyAll();
            }
        }
    }

    private int findMagicNumber(String prevHash, int N, List<String> messages) {
        int magicNumber = 0;
        String targetPrefix = "0".repeat(N);
        String baseData = prevHash + messages.toString();
        while (!StringUtil.applySha256(baseData + magicNumber).startsWith(targetPrefix)) {
            magicNumber++;
            if (!blockchain.isMining()) {
                break;
            }
        }
        return magicNumber;
    }
}
