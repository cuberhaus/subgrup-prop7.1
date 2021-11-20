import domini.classes.csv.EscriptorDeCSV;
import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import libs.consola;

public class DriverEscriptorDeCSV {
    public static void testEscriureCSV() {
        String ubicacioEntrada = consola.llegirString("Introdueix la ruta del fitxer d'entrada");
        String ubicacioSortida = consola.llegirString("Introdueix la ruta del fitxer de sortida");
        try {
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(ubicacioEntrada);

            EscriptorDeCSV escriptor = new EscriptorDeCSV();
            escriptor.escriureCSV(ubicacioSortida, taula);
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
