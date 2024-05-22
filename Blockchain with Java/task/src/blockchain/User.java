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
    private String generateMessage() {
        return name + ": " + random.nextInt(1000);
    }

    @Override
    public void run() {
        // Here you can add the logic for sending messages to the blockchain.

        while (true) {
            String message = generateMessage();
            blockchain.addMessage(message);

            try {
                Thread.sleep(1000);// Sleep for a second before sending the next message
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            // For example, you can use a loop to send a message every second.
            // You can use the `addMessage` method of the `Blockchain` class to send a message.
        }
    }
}