package blockchain;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        final Object lock = new Object();
        Thread[] minerThreads = new Thread[15];
        Thread[] messageGenerators = new Thread[15];

        for (int i = 0; i < 15; i++) {
            minerThreads[i] = new Thread(new Miner(i, blockchain, lock));
            minerThreads[i].start();
        }

        for (int i = 0; i < 15; i++) {
            messageGenerators[i] = new Thread(new MessageGenerator(blockchain, lock));
            messageGenerators[i].start();
        }

        try {
            for (Thread minerThread : minerThreads) {
                minerThread.join();
            }
            for (Thread messageGenerator : messageGenerators) {
                messageGenerator.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        System.out.println(blockchain);
    }
}