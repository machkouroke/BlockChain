package blockchain.Internal;

import blockchain.external.Transaction;
import blockchain.external.User;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BlockChain implements Serializable {
    private static BlockChain instance;
    private final List<Bloc> listBloc = new ArrayList<>();
    private final Map<String, String> listMessage = new HashMap<>();
    private final Map<User, Integer> listUsersWithSold = new HashMap<>();
    private Boolean maxBloc = false;
    private static final int NUMBEROFBLOCKS = 15;
    private int messageId = 1;
    private int numberOfZero = 0;
    private static final long serialVersionUID = 7L;

    private BlockChain() {
    }

    public static BlockChain getInstance() {
        if (instance == null) {
            instance = new BlockChain();
        }
        return instance;
    }
    public int upZero() {
        return ++numberOfZero;
    }
    public int downZero() {
        return numberOfZero == 0 ? 0 : --numberOfZero;
    }
    public int getNumberOfZero() {
        return numberOfZero;
    }

    private int messageIdGenerator() {
        return messageId++;
    }

    public void difficulty(long time) {
        if (time < 30) {
            System.out.printf("N was increased to %d\n", upZero());
        } else if (time > 30) {
            System.out.printf("N was decreased to %d\n", downZero());
        } else {
            System.out.println("N stays the same");
        }
    }

    public synchronized void addUser(User user) {
        listUsersWithSold.put(user, 100);
    }

    public synchronized void sendMessage(String nom, String message) {

        listMessage.put(nom, message);
    }
    public synchronized User[] getUserList() {
        return listUsersWithSold.keySet().toArray(new User[0]);
    }
    public String currentMessage() {
        String message = listMessage.entrySet().stream().
                map(x -> x.getKey() + x.getValue())
                .collect(Collectors.joining("\n"));
        listMessage.clear();
        return message;
    }

    public synchronized void sendMoney(Transaction transaction) {
        User sender = transaction.getSender();

        User receiver = transaction.getReceiver();
        Integer senderSold = listUsersWithSold.get(sender);

        Integer receiverSold = listUsersWithSold.get(receiver);
        Integer transactionAmount = transaction.getAmount();

        if (senderSold >= transactionAmount) {
            listUsersWithSold.put(receiver, receiverSold + transactionAmount);
            listUsersWithSold.put(sender, receiverSold - transactionAmount);

            sendMessage(transaction.toString(), "");
        }
    }

    public synchronized boolean validateBloc(Bloc bloc, String nameMiner) {
        /*Vérifie si un bloc fourni est valide si oui il l'ajoute à la blockChain*/
        if (n() < NUMBEROFBLOCKS && bloc.getHash().startsWith("0".repeat(numberOfZero))) {
            /* Pour s'assurer que les Id fournis par les mineurs sont corrects*/
            if ((n() == 0 && bloc.getId() == 1) ||
                    Objects.equals(bloc.getPrevious(), listBloc.get(n() - 1).getHash()) &&
                            bloc.getId() == n() + 1) {
                if (n() != 0) {
                    bloc.setMessage(currentMessage());
                }
                listBloc.add(bloc);
                bloc.print(nameMiner);
                difficulty(bloc.getTimeOfCreation());
                System.out.println();
                if (n() >= NUMBEROFBLOCKS) {
                    maxBloc = true;
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // This method return the size of listBloc just for have a short line
    public int n() {
        return listBloc.size();
    }

    public Boolean getMaxBloc() {
        return maxBloc;
    }

    public static void serialize(Object obj, String fileName){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(obj);
            }
        } catch (IOException ignored) {
        }
    }

    public static BlockChain deserialize(String fileName)
            throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (BlockChain) ois.readObject();
        }

    }

    public Bloc lastBlock() {
        try {
            return n() == 0 ? null : listBloc.get(n() - 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
