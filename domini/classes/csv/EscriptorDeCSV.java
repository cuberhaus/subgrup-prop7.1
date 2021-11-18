package domini.classes.csv;

import domini.classes.Contenidor;
import domini.classes.EscriptorDeFitxers;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe que s'encarrega de la escriptura de CSV.
 * @author pablo.vega
 */
public class EscriptorDeCSV extends EscriptorDeFitxers {
    public EscriptorDeCSV() {}

    @Override
    public void escriptorFitxers(String ubicacio, Contenidor taula) throws IOException, InterruptedException {
        escriureCSV(ubicacio, (TaulaCSV) taula);
    }

    /**
     * Funció que donat un pathaname i una taula, emmagatzema el contingut en un fitxer.
     * @param ubicacio <code>String</code> pathname que conté la ubicació de l'arxiu.
     * @param taula <code>Taula</code> tabla que és un conjunt de valors
     * @throws IOException
     */
    public void escriureCSV(String ubicacio, TaulaCSV taula) throws IOException, InterruptedException {
        if (!taula.estaInicialitzat()) {
            System.out.println("La taula que intentes escriure, no està inicialitzada.");
        }

        else {
            BufferedWriter bw;
            try {
                bw = new BufferedWriter(new FileWriter(ubicacio));
            } catch(IOException e1) {
                throw e1;
            }
            ArrayList<ArrayList<String>> tauleta = taula.obtenirTaula();
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
}
