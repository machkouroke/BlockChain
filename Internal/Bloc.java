package blockchain.Internal;

import java.io.Serializable;

public class Bloc implements Serializable {
    private final int id;
    private final String previous;
    private final long timeStamp;
    public final String hash;
    private final long magicNumber;
    private final long timeOfCreation;
    private String message;
    private String minerName;

    public Bloc(int id, String previous, long timeStamp, String hash, long magicNumber,
                long timeOfCreation) {
        this.id = id;
        this.previous = previous;
        this.timeStamp = timeStamp;
        this.hash = hash;
        this.magicNumber = magicNumber;
        this.timeOfCreation = timeOfCreation;
        this.message = "";
    }

    public int getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getPrevious() {
        return previous;
    }

    public long getTimeOfCreation() {
        return timeOfCreation;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void print() {
        System.out.println("Block:");
        printGeneral();
    }



    public void printGeneral() {
        System.out.println("Id: " + this.id);
        System.out.println("Timestamp: " + this.timeStamp);
        System.out.println("Magic number: " + this.magicNumber);
        System.out.println("Hash of the previous block:");
        System.out.println(this.previous);
        System.out.println("Hash of the block:");
        System.out.println(this.hash);
        System.out.print("Block data:");
        System.out.println("".equals(message) ? " no messages" : "\n" + message);
        System.out.println("Block was generating for " + this.timeOfCreation + " seconds");
    }

    public void print(String minerName) {
        this.minerName = minerName;
        System.out.println("Block:");
        System.out.println("Created by " + minerName);
        System.out.println(minerName + " gets 100 VC");
        printGeneral();
    }
}
