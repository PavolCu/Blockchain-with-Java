package blockchain;

public class Main {
    public static void main(String[] args) {
        int minerId = 1;
        int magicNumber = 0;
        long generationTime = 0;
        int nValue = 4; // Increase the initial N value

        Blockchain blockchain = new Blockchain(minerId, magicNumber, generationTime, nValue);

        for (int i = 0; i < 10; i++) {
            new Thread(new Miner(i, blockchain)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new User("User" + i, blockchain)).start();
        }

        while (blockchain.blocks.size() < 5) {
            Thread.yield();
        }

        for (Block block : blockchain.blocks) {
            System.out.println(block);
        }
    }
}