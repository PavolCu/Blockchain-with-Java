package blockchain;

import java.util.Random;

public class MessageGenerator extends BlockchainTask {
    private final Random random = new Random();

    public MessageGenerator(Blockchain blockchain, Object lock) {
        super(blockchain, lock);
    }

    @Override
    public void run() {
        int messageCount = 0;
        while (shouldContinue()) {
            synchronized (lock) {
                blockchain.addMessage("Random message " + (++messageCount) + " from user " + random.nextInt(1000));
                lock.notifyAll();
                try {
                    lock.wait(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}