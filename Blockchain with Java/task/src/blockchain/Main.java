package blockchain;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter how many zeros the hash must start with: ");
        int difficulty = scanner.nextInt();
        Blockchain blockchain = new Blockchain(difficulty);
        for (int i = 0; i < 4; i++) {
            blockchain.addBlock();
        }

        for (Block block : blockchain.blocks) {
            System.out.println(block);
        }

        //System.out.println("Blockchain is valid: " + blockchain.isValid());
    }
}