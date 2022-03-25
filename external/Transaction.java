package blockchain.external;

public class Transaction {
    User sender;
    User receiver;
    int amount;

    public Transaction(User sender, User receiver, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        if (this.receiver == null) System.out.println("ok");
        return sender.name + " sent " + amount + " VC to " + receiver.name;
    }
}
