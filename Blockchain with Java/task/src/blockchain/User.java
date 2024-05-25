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
        // Here you can add the logic for sending messages to the blockchain.

        while (true) {
            Message message = generateMessage();
            blockchain.addMessage(message);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
               break;
            }
        }
    }
}