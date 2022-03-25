package blockchain.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {
    public static synchronized List<String> read(File file) {
        List<String> lines = new ArrayList<>();
        try(Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                lines.add(reader.nextLine());
            }
        }
        catch(FileNotFoundException ignored) {

        }
        return lines;
    }
}
