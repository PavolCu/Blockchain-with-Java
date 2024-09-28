package blockchain;

import java.util.Random;

public class MessageGenerator extends BlockchainTask {
    private final Random random = new Random();

    public MessageGenerator(Blockchain blockchain, Object lock) {
        super(blockchain, lock);
    }

    @Override
    public void run() {
        while (shouldContinue()) {
            synchronized (lock) {
                String sender = "User" + random.nextInt(1000);
                String receiver = "User" + random.nextInt(1000);
                int amount = random.nextInt(50) + 1;
                if (blockchain.getBalance(sender) >= amount) {
                    blockchain.addTransaction(new Transaction(sender, receiver, amount));
                }
                lock.notifyAll();
                try {
                    lock.wait(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // Add a break condition to avoid infinite loop
            if (blockchain.getSize() >= 15) {
                break;
            }
        }
    }
}