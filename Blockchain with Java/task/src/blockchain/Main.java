package blockchain;

public class Main {
    public static void main(String[] args) {
        int minerId = 1;
        int magicNumber = 0;
        long generationTime = 0;
        int nValue = 0;

        Blockchain blockchain = new Blockchain(minerId, magicNumber, generationTime, nValue);

        for (int i = 0; i < 10; i++) {
            new Thread(new Miner(i, blockchain)).start();
        }

        while (blockchain.blocks.size() < 5) {
            Thread.yield();
        }

        for (Block block : blockchain.blocks) {
            System.out.println(block);
        }
    }
}

