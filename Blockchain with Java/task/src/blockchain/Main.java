package blockchain;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        final Object lock = new Object();
        Thread[] minerThreads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            minerThreads[i] = new Thread(new Miner(i, blockchain, lock));
            minerThreads[i].start();
        }

        Thread messageGenerator = new Thread(() -> {
            int messageCount = 0;
            while (blockchain.isMining() && blockchain.getSize() < 5) {
                synchronized (lock) {
                    blockchain.addMessage("Message " + (++messageCount) + " from user");
                    lock.notifyAll();
                    try {
                        lock.wait(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        });

        messageGenerator.start();

        try {
            for (Thread minerThread : minerThreads) {
                minerThread.join();
            }
            messageGenerator.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        blockchain.printMinerStats();
        System.out.println(blockchain);
    }
}