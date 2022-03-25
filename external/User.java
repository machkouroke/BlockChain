package blockchain.external;

import blockchain.Internal.BlockChain;

import java.util.Arrays;
import java.util.Random;

public class User {
    protected String name;
    Random r = new Random();
    protected final BlockChain blockChain;

    public User(BlockChain blockchain) {
        this.blockChain = blockchain;
    }

    protected void sendCoins(User user, int amount) {
        blockChain.sendMoney(new Transaction(this, user, amount));
    }
    protected void transactionSimulation() {
        User[] userBlocChain = blockChain.getUserList();

        /* On envoie de l'argent à un utilisateur aléatoire de la blockChain*/
        User receiver = userBlocChain[r.nextInt(userBlocChain.length)];
        if (!this.equals(receiver)) {
            int amount = r.nextInt(100);
            sendCoins(receiver, amount);
        }

    }

    public String getName() {
        return name;
    }
}
