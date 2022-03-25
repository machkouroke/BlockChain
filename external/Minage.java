package blockchain.external;

import blockchain.Internal.BlockChain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Minage implements Runnable{
    BlockChain blockchain;
    ExecutorService executor = Executors.newCachedThreadPool();
    final static int nb_miner = 16;
    public Minage(BlockChain blockchain) throws InterruptedException {
        this.blockchain = blockchain;

    }

    @Override
    public void run() {
        for (int i = 0; i < nb_miner; i++) {
            executor.submit(new Miner(blockchain));
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10000, TimeUnit.SECONDS)) {
                System.out.println("Le minage ne s'est pas terminé correctement");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
