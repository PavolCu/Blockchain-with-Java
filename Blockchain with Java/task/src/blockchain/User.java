package blockchain;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class User implements Runnable {
    private final String name;
    private final Blockchain blockchain;
    private final int id;
    private final List<String> messages;
    private final CountDownLatch latch;

    public User(String name, Blockchain blockchain, int id, List<String> messages, CountDownLatch latch) {
        this.name = name;
        this.blockchain = blockchain;
        this.id = id;
        this.messages = messages;
        this.latch = latch;
    }

    @Override
    public void run() {
        for (String messageText : messages) {
            Message message = new Message(name, messageText);
            blockchain.addMessage(message);
            try {
                Thread.sleep(1000); // Delay between adding each message
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        latch.countDown();
    }
}
