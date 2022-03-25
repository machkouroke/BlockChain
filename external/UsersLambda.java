package blockchain.external;

import blockchain.Internal.BlockChain;

import java.util.List;
import java.util.Random;

public class UsersLambda extends User implements Runnable {

    List<String> listMessage;
    private String signature;

    public UsersLambda(String name, List<String> listMessage, BlockChain blockchain) {
        super(blockchain);
        super.name = name;
        this.listMessage = listMessage;
    }

    public String messageGenerator() {
        return listMessage.get(r.nextInt(listMessage.size()));
    }

    @Override
    public void run() {
        this.blockChain.addUser(this);
        while (Boolean.TRUE.equals(!blockChain.getMaxBloc())) {
            //blockChain.sendMessage(name + ": ", messageGenerator());
            transactionSimulation();
        }
    }
}
