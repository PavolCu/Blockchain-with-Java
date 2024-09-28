package blockchain;

import java.util.List;

public class Miner extends BlockchainTask {
    private final int id;

    public Miner(int id, Blockchain blockchain, Object lock) {
        super(blockchain, lock);
        this.id = id;
    }

    @Override
    public void run() {
        while (shouldContinue()) {
            Block lastBlock = blockchain.getLastBlock();
            String prevHash = lastBlock == null ? "0" : lastBlock.getHash();
            List<Transaction> transactions = blockchain.getPendingTransactions();
            transactions.add(new Transaction("System", "Miner" + id, 100)); // Reward transaction
            int magicNumber = findMagicNumber(prevHash, blockchain.getN(), transactions);
            Block newBlock = new Block(blockchain.getSize() + 1, prevHash, id, magicNumber, System.currentTimeMillis(), blockchain.getN(), transactions);
            synchronized (lock) {
                if (blockchain.addBlock(newBlock)) {
                    lock.notifyAll();
                }
            }
            // Add a break condition to avoid infinite loop
            if (blockchain.getSize() >= 15) {
                break;
            }
        }
    }

    private int findMagicNumber(String prevHash, int N, List<Transaction> transactions) {
        int magicNumber = 0;
        String targetPrefix = "0".repeat(N);
        StringBuilder baseData = new StringBuilder(prevHash).append(transactions.toString());
        while (!StringUtil.applySha256(baseData.toString() + magicNumber).startsWith(targetPrefix)) {
            magicNumber++;
            if (!blockchain.isMining()) {
                break;
            }
        }
        return magicNumber;
    }
}