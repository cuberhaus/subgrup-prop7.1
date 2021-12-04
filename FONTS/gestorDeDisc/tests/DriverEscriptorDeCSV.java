package gestorDeDisc.tests;

import domini.tests.UtilitatsDeLectura;
import gestorDeDisc.classes.EscriptorDeCSV;
import libs.consola;

import java.util.ArrayList;

/**
 * Driver de la classe EscriptorDeCSV
 * @author pablo.vega
 */

public class DriverEscriptorDeCSV {
    public static void testEscriureCSV() {
        String ubicacioSortida = consola.llegirString("Introdueix la ruta del fitxer de sortida");
        try {
            ArrayList<ArrayList<String>> mockTaula = UtilitatsDeLectura.llegirTaulaCSV("MockTaulaCSV1");
            EscriptorDeCSV escriptor = new EscriptorDeCSV();
            escriptor.escriureCSV(ubicacioSortida, mockTaula);
            System.out.println("S'ha escrit");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Driver de la clase EscriptorDeCSV");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test de escriureCSV\n";
        String err = "Valor invalid: introdueix un enter entre 0 i 1";
        while (true) {
            try {
                int i = consola.llegirInt(consulta, err, 0, 1);
                switch (i) {
                    case 0:
                        return;

                    case 1:
                        testEscriureCSV();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Torna a provar");
            }
        }
    }
}
