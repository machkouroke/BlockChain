package blockchain.external;

import blockchain.Internal.BlockChain;

import java.io.File;

import blockchain.utility.Reader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Chat implements Runnable {
    BlockChain blockchain;
    ExecutorService executor = Executors.newCachedThreadPool();
    List<String> users = List.of("Déodat", "Hiba", "Machkour", "Candy");
    List<String> listMessage;

    public Chat(BlockChain blockchain) {
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        listMessage = Reader.read(new File("Blockchain/task/src/blockchain/utility/message.txt"));
        for (String user : users) {
            executor.submit(new UsersLambda(user, listMessage, blockchain));
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10000, TimeUnit.SECONDS)) {
                System.out.println("Le chat ne s'est pas terminé correctement");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}

