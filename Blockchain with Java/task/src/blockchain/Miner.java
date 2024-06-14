package blockchain;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Miner implements Runnable {
    private final int id;
    private final Blockchain blockchain;
    private final long timeout; // Define timeout here
    private volatile boolean running = true;

    Miner(int id, Blockchain blockchain, long timeout) {
        this.id = id;
        this.blockchain = blockchain;
        this.timeout = timeout;
    }

    private int findMagicNumber(String prevHash, int N) {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        List<Future<Integer>> futures = new LinkedList<>();
        int range = Integer.MAX_VALUE / cores;
        for (int i = 0; i < cores; i++) {
            int start = i * range;
            int end = (i + 1) * range;
            futures.add(executor.submit(new MagicNumberFinder(prevHash, start, end, N)));
        }

        try {
            for (Future<Integer> future : futures) {
                int magicNumber = future.get();
                if (magicNumber != -1) {
                    executor.shutdownNow();
                    return magicNumber;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        return -1;
    }

    public void stop() {
        running = false;
    }
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        while (running) {
            Block lastBlock = blockchain.getLastBlock();
            int newId = lastBlock.getId() + 1;
            String prevHash = lastBlock.getHash();
            int magicNumber = findMagicNumber(prevHash, blockchain.getN());
            long generationTime = (System.currentTimeMillis() - startTime) / 1000;

            // Wait for a short time to allow messages to accumulate
            try {
                Thread.sleep(300); // Wait for 200 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Queue<Message> messages = blockchain.getAndClearNextBlockMessages();
            Block newBlock = new Block(newId, prevHash, id, magicNumber, generationTime, blockchain.getN(), new LinkedList<>(messages));

            if (blockchain.addBlockSynchronized(newBlock)) {
                System.out.println(newBlock);
                if (blockchain.blocks.size() >= 5) {
                    stop();
                }
            }

            if (System.currentTimeMillis() - startTime > timeout) {
                System.out.println("Timeout reached, stopping the miner.");
                stop();
            }
        }
    }
}
