package blockchain;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int minerId = 1;
        int magicNumber = 0;
        long generationTime = 0;
        int nValue = 2;

        Blockchain blockchain = new Blockchain(minerId, magicNumber, generationTime, nValue);

        List<String> tomMessages = Arrays.asList("Hey, I'm first!", "You're welcome :)");
        List<String> sarahMessages = Arrays.asList("It's not fair!", "You always will be first because it is your blockchain!", "Anyway, thank you for this amazing chat.");
        List<String> nickMessages = Arrays.asList("Hey Tom, nice chat");

        new Thread(new User("Tom", blockchain, 0, tomMessages)).start();
        new Thread(new User("Sarah", blockchain, 1, sarahMessages)).start();
        new Thread(new User("Nick", blockchain, 2, nickMessages)).start();

        for (int i = 0; i < 5; i++) { // Change this line
            new Thread(new Miner(i, blockchain)).start();
        }

        long startTime = System.currentTimeMillis();
        long timeout = 15000;

        synchronized (blockchain) {
            while (blockchain.blocks.size() < 5) { // Change this line
                try {
                    blockchain.wait(timeout);
                    if (System.currentTimeMillis() - startTime > timeout) {
                        System.out.println("Timeout reached, stopping the program.");
                        break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        for (Block block : blockchain.blocks) {
            System.out.println(block);
        }
    }
}