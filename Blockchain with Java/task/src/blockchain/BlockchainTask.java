package blockchain;

public abstract class BlockchainTask implements Runnable {
    protected final Blockchain blockchain;
    protected final Object lock;

    public BlockchainTask(Blockchain blockchain, Object lock) {
        this.blockchain = blockchain;
        this.lock = lock;
    }

    protected boolean shouldContinue() {
        return blockchain.isMining() && blockchain.getSize() < 15;
    }
}