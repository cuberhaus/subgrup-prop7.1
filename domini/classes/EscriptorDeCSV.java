package domini.classes;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe que s'encarrega de la escriptura de CSV.
 * @author pablo.vega
 */
public class EscriptorDeCSV extends EscriptorDeFitxers{
    public EscriptorDeCSV() {}

    @Override
    public void escriptorFitxers(String ubicacio, Contenidor taula) throws IOException {
        escriureCSV(ubicacio, (TaulaCSV) taula);
    }

    /**
     * Funció que donat un pathaname i una taula, emmagatzema el contingut en un fitxer.
     * @param ubicacio <code>String</code> pathname que conté la ubicació de l'arxiu.
     * @param tabla <code>Taula</code> tabla que és un conjunt de valors
     * @throws IOException
     */
    public void escriureCSV(String ubicacio, TaulaCSV tabla) throws IOException {
        if (!tabla.estaInicialitzat()) {
            System.out.println("La taula que intentes escriure, no està inicialitzada.");
        }

        else {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ubicacio));
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
