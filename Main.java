package blockchain;

import blockchain.Internal.BlockChain;
import blockchain.external.Chat;
import blockchain.external.Minage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockChain blockchain = BlockChain.getInstance();
        ExecutorService main = Executors.newFixedThreadPool(2);
        main.submit(new Minage(blockchain));
        main.submit(new Chat(blockchain));
        main.shutdown();

        if (!main.awaitTermination(100, TimeUnit.SECONDS)) {
            System.out.println("Le programme ne s'est pas terminé correctement");
        }
    }
}
