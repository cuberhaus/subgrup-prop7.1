import domini.classes.LectorDeCSV;
import domini.classes.TaulaCSV;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV tablita = lector.llegirCSV("C:/Users/pable/Desktop/items.csv");
    }
}
