package domini.classes;

import java.io.*;
import java.util.ArrayList;

/** Classe que s'encarrega de la gestió entrada i sortida de CSV. */
public class CSVWriter {
    public CSVWriter() {}
    public void writeCSV(String pathname, CSVTable tabla) throws IOException {
        if (!tabla.estaInicialitzat()) {
            System.out.println("La taula que intentes escriure, no està inicialitzada.");
        }

        else {
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathname));
            ArrayList<ArrayList<String>> tablita = tabla.obtenirTaula();
            for (ArrayList<String> elem1 : tablita) {
                boolean primero = true;
                for (String elem2 : elem1) {
                    if (!primero) bw.write(',');
                    bw.write(elem2);
                    primero = false;
                }
                bw.write('\n');
            }
            bw.close();
        }
    }
}
