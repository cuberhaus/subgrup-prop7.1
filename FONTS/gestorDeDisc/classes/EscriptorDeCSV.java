package gestorDeDisc.classes;

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
     * @param taula <code>Taula</code> tabla que és un conjunt de valors
     * @throws IOException si no existeix el fitxer, llença exepcio
     */
    public void escriureCSV(String ubicacio, ArrayList<ArrayList<String>> tauleta) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ubicacio));
        for (ArrayList<String> elem1 : tauleta) {
            boolean primer = true;
            for (String elem2 : elem1) {
                if (!primer) bw.write(',');
                bw.write(elem2);
                primer = false;
            }
            bw.write('\n');
        }
        bw.close();
    }
}
