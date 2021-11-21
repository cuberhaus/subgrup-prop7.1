import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import libs.consola;

import java.io.IOException;

public class DriverLectorDeCSV {
    public static void testLectorCSV() {
        try {
            TaulaCSV taula = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            taula.imprimir();
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
