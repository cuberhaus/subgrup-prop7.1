import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import libs.consola;

import java.io.IOException;

public class DriverLectorDeCSV {
    public static void testLectorCSV() throws Exception {
        String ubicacio = UtilitatsDeLectura.llegirUbicacioArxiu();
        try {
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(ubicacio);
            taula.imprimir();
        } catch (IOException e1) {
            System.out.println("Ubicacio no valida");
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Driver per la classe LectorDeCSV");
        String consulta = """

                0 - Sortir
                1 - Llegir CSV
                """;
        String err = "Valor invàlid: Introdueix un enter entre 0 i 1";
        while(true) {
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 1);
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
