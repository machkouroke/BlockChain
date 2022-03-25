package blockchain.external;

import blockchain.Internal.Bloc;
import blockchain.Internal.BlockChain;
import blockchain.utility.StringUtil;

import java.util.Date;
import java.util.Random;

public class Miner extends User implements Runnable {

    Random rand = new Random();


    public Miner(BlockChain blockchain) {
        super(blockchain);
    }

    private Bloc blocGenerator() {
        /*Générer un bloc en fonction du bloc précédent*/
        Bloc bloc = blockChain.lastBlock();
        long timeOfCreation = System.currentTimeMillis(), timeStamp =
                new Date().getTime();
        int n = blockChain.getNumberOfZero(), id =
                bloc == null ? 1 : bloc.getId() + 1;
        String previous = bloc == null ? "0" : bloc.getHash(), hash;
        long magicNumber;
        /* Recherche du nombre magic*/
        do {
            magicNumber = rand.nextLong();
            hash = StringUtil.applySha256(
                    id + previous + timeStamp + magicNumber);
        } while (!hash.startsWith("0".repeat(n)));

        /*Calcul du temps de création*/
        timeOfCreation = (System.currentTimeMillis() - timeOfCreation);
        return new Bloc(id, previous, timeStamp, hash, magicNumber,
                timeOfCreation);
    }

    @Override
    public void run() {
        /*Tant qu'un bloc n'est pas trouvé ou le nombre maximum de
        block n'est pas atteint*/
        String threadName = Thread.currentThread().getName();
        super.name = "miner" + threadName.charAt(threadName.length() - 1);
        this.blockChain.addUser(this);
        boolean find;
        do {
            find = blockChain.validateBloc(blocGenerator(), name);
            transactionSimulation();
        } while (!find && Boolean.TRUE.equals(!blockChain.getMaxBloc()));
    }
}
