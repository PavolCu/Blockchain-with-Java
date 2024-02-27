package blockchain;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        for (int i = 0; i < 4; i++) {
            blockchain.addBlock();
        }

        for (Block block : blockchain.blocks) {
            System.out.println(block);
        }

        //System.out.println("Blockchain is valid: " + blockchain.isValid());
    }
}