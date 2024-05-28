package blockchain;
import java.util.Random;

public class User implements Runnable {
    private String name;
    private Blockchain blockchain;
    private Random random = new Random();

    public User(String name, Blockchain blockchain) {
        this.name = name;
        this.blockchain = blockchain;
    }
   private Message generateMessage() {

        return new Message(name, "Message " + random.nextInt(1000));
   }

 @Override
public void run() {
    long startTime = System.currentTimeMillis();
    while (blockchain.blocks.size() < 5) {
        // If more than 15 seconds have passed, break the loop
        if (System.currentTimeMillis() - startTime > 15000) {
            break;
        }
        Message message = generateMessage();
        blockchain.addMessage(message);

        try {
            Thread.sleep(100); // Reduce sleep time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            break;
        }
    }
}
}