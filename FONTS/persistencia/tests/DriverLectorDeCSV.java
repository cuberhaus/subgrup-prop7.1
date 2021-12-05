package persistencia.tests;

import persistencia.classes.LectorDeCSV;
import libs.consola;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Driver per la classe LectorDeCSV
 * @author pablo.vega
 */
public class DriverLectorDeCSV {
    public static void testLectorCSV() {
        try {
            LectorDeCSV lector = new LectorDeCSV();
            ArrayList<ArrayList<String>> mockTaula = lector.llegirCSV(
                    consola.llegirString("Introdueix la ubicacio del csv (i.e. ../EXE/dades_tests/items.csv):"));
            for (ArrayList<String> fila : mockTaula) {
                for (String valor : fila) {
                    System.out.print(" | " + valor + " | ");
                }
                System.out.println();
            }
        } catch (IOException e1) {
            System.out.println("Ubicacio no valida");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Driver per la classe LectorDeCSV");
        String consulta = "\n0 - Sortir\n" +
                "1 - Llegir CSV\n";
        String err = "Valor invàlid: Introdueix un enter entre 0 i 1";
        while(true) {
            try {
                int i = consola.llegirInt(consulta, err, 0, 1);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testLectorCSV();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}
