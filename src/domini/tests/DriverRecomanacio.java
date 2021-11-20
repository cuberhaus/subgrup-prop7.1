import domini.classes.Id;
import domini.classes.recomanador.metode_recomanador.Recomanacio;
import libs.consola;

public class DriverRecomanacio {

    public static void testConstructora() {
        try {
            Id id = UtilitatsDeLectura.llegirId();
            double seguretat = UtilitatsDeLectura.llegirDouble();
            Recomanacio rec = new Recomanacio(id, seguretat);
            System.out.println("S'ha creat la recomanacio");
            rec.imprimir();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObteId() {
        try {
            Recomanacio rec = UtilitatsDeLectura.llegirRecomanacio();
            int id = rec.obtenirId();
            System.out.println("Id: " + id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObteSeguretat() {
        try {
            Recomanacio rec = UtilitatsDeLectura.llegirRecomanacio();
            double seguretat = rec.obtenirRate();
            System.out.println("Seguretat: " + seguretat);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Driver per a la classe Recomanacio");
        String consulta = "\n0 -Sortir\n" +
                "1 - Test Constructora\n" +
                "2 - Test Obtenir Id\n" +
                "3 - Test Obtenir Rate\n";
        String err = "Valor invalid: introdueix un enter entre 0 i 3";

        while (true) {
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 3);
                switch (i) {
                    case 0:
                        return;

                    case 1:
                        testConstructora();
                        break;

                    case 2:
                        testObteId();
                        break;

                    case 3:
                        testObteSeguretat();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Torna-ho a probar");
            }
        }
    }
}
