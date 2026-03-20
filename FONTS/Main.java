import com.formdev.flatlaf.FlatDarkLaf;
import presentacio.controladors.ControladorPresentacio;

/**
 * @author maria.prat
 */
public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        try {
            ControladorPresentacio.obtenirInstancia();
        } catch (Exception e) {
            System.out.println("Hi ha hagut un error iniciant la interficie.");
            System.out.println(e.getMessage());
        }
    }
}
