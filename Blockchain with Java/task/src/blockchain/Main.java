package blockchain;

public class Main {
    public static void main(String[] args) {
        int minerId = 1;
        int magicNumber = 0;
        long generationTime = 0;
        int nValue = 2; // Reduce the initial N value

        Blockchain blockchain = new Blockchain(minerId, magicNumber, generationTime, nValue);


        // Add messages to the blockchain
        blockchain.addMessage(new Message("Tom", "Hey, I'm first!"));
        blockchain.addMessage(new Message("Sarah", "It's not fair!"));
        blockchain.addMessage(new Message("Sarah", "You always will be first because it is your blockchain!"));
        blockchain.addMessage(new Message("Sarah", "Anyway, thank you for this amazing chat."));
        blockchain.addMessage(new Message("Tom", "You're welcome :)"));
        blockchain.addMessage(new Message("Nick", "Hey Tom, nice chat"));

        for (int i = 0; i < 5; i++) {
            new Thread(new Miner(i, blockchain)).start();
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