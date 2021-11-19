import domini.classes.csv.EscriptorDeCSV;
import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import libs.consola;

public class DriverEscriptorDeCSV {
    public static void testEscriureCSV() throws Exception {
        String ubicacioIn = UtilitatsDeLectura.llegirUbicacioArxiu();
        String ubicacio = UtilitatsDeLectura.llegirUbicacioDestiArxiu();
        try {
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(ubicacioIn);

            EscriptorDeCSV escriptor = new EscriptorDeCSV();
            escriptor.escriureCSV(ubicacio, taula);
            System.out.println("S'ha escrit");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Driver de la clase EscriptorDeCSV");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test de escriureCSV\n";
        String err = "Valor invalid: introdueix un enter entre 0 i 1";
        while (true) {
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 1);
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
