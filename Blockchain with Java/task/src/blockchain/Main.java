package blockchain;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        int minerId = 1;
        int magicNumber = 0;
        long generationTime = 0;
        int nValue = 2;
        long timeout = 15000;

        Blockchain blockchain = new Blockchain(minerId, magicNumber, generationTime, nValue, lock);

        List<String> tomMessages = new LinkedList<>(List.of("Hey, I'm first!"));
        List<String> sarahMessages = new LinkedList<>(List.of("It's not fair!", "You always will be first because it is your blockchain!", "Anyway, thank you for this amazing chat."));
        List<String> nickMessages = new LinkedList<>(List.of("You're welcome :)", "Hey Tom, nice chat"));
        CountDownLatch latch = new CountDownLatch(3); // Initialize the latch with the number of User threads

        new Thread(new User("Tom", blockchain, 0, tomMessages, latch)).start();
        new Thread(new User("Sarah", blockchain, 1, sarahMessages, latch)).start();
        new Thread(new User("Nick", blockchain, 2, nickMessages, latch)).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ExecutorService minerExecutor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            minerExecutor.submit(new Miner(i, blockchain, timeout));
        }

        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        minerExecutor.shutdownNow();

        try {
            if (!minerExecutor.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
                System.err.println("Miner executor did not terminate in the specified time.");
                List<Runnable> droppedTasks = minerExecutor.shutdownNow();
                System.err.println("Miner executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed.");
            }
        } catch (InterruptedException e) {
            System.err.println("Miner executor termination interrupted.");
            Thread.currentThread().interrupt();
        }

        for (Block block : blockchain.blocks) {
            System.out.println(block);
        }
    }
}
