package blockchain;

import java.util.List;

public class User implements Runnable {
    private String name;
    private Blockchain blockchain;
    private final int id;
    private final List<String> messages;

    public User(String name, Blockchain blockchain, int id, List<String> messages) {
        this.name = name;
        this.blockchain = blockchain;
        this.id = id;
        this.messages = messages;
    }

    @Override
    public void run() {
        for (String messageText : messages) {
            Message message = new Message(name, messageText);
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