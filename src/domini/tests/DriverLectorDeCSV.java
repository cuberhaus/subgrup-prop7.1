import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import libs.consola;

/**
 * Driver de la classe LectorDeCSV
 * @author pablo.vega
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DriverLectorDeCSV {
    public static void testLectorCSV() {
        try {
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(consola.llegirString("Introdueix la ubicacio del csv"));
            taula.imprimir();
        } catch (IOException e1) {
            System.out.println("Ubicacio no valida");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testLectorCSVContingut() {
        try {
            String ubicacio = consola.llegirString("Introduir ubicacio de la taula");
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(ubicacio);
            ArrayList<ArrayList<String>> contingutTaula = taula.obtenirTaula();

            FileReader lectorFitxer = new FileReader(ubicacio);
            BufferedReader fitxer = new BufferedReader(lectorFitxer);

            String row;
            ArrayList<ArrayList<String>> comparar = new ArrayList<>();
            while ((row = fitxer.readLine()) != null) {
                String[] fila = row.split(",", 0);
                ArrayList<String> afila = new ArrayList<>();
                for (String elem : fila) {
                    afila.add(elem);
                }
                comparar.add(new ArrayList<>(afila));
                afila.clear();
            }

            if(comparar.equals(contingutTaula)) {
                System.out.println("Contingut original: " + comparar);
                System.out.println("Contingut de la taula " + contingutTaula);
                System.out.println("El contingut de la taula es el mateix al dessitjat");
            }

            else {
                System.out.println("mal Pablo");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Driver per la classe LectorDeCSV");
        String consulta = "\n0 - Sortir\n" +
                "1 - Llegir CSV\n" +
                "2 - Llegir i contingut taula\n";
        String err = "Valor invàlid: Introdueix un enter entre 0 i 2";
        while(true) {
            try {
                int i = consola.llegirInt(consulta, err, 0, 2);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testLectorCSV();
                        break;

                    case 2:
                        testLectorCSVContingut();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}
