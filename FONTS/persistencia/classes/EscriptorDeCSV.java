package persistencia.classes;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe que s'encarrega de la escriptura de CSV.
 * @author pablo.vega
 */
public class EscriptorDeCSV {
    public EscriptorDeCSV() {}

    /**
     * Funció que donat un pathaname i una taula, emmagatzema el contingut en un fitxer.
     * @param ubicacio <code>String</code> pathname que conté la ubicació de l'arxiu.
     * @param tauleta <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> tabla que és un conjunt de valors
     * @throws IOException si no existeix el fitxer, llença exepcio
     */
    public void escriureCSV(String ubicacio, ArrayList<ArrayList<String>> tauleta) throws IOException {
        File comprobacio = new File(ubicacio);
        if (comprobacio.isDirectory()) {
            System.out.println("Marico");
            ubicacio = ubicacio + "/temporal.csv";
        }
        BufferedWriter fitxer = new BufferedWriter(new FileWriter(ubicacio));
        for (ArrayList<String> fila : tauleta) {
            boolean primer = true;
            for (String contingut : fila) {
                if (!primer) fitxer.write(',');
                fitxer.write(contingut);
                primer = false;
            }
            fitxer.write('\n');
        }
        fitxer.close();
    }
}
