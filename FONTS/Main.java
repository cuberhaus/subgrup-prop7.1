import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorPresentacio;

import java.io.IOException;

/**
 * @author maria.prat
 */
public class Main {
    public static void main(String[] args) {
        try {
            ControladorPresentacio.obtenirInstancia();
        } catch (Exception e) {
            System.out.println("Hi ha hagut un error iniciant la interficie.");
            System.out.println(e.getMessage());
        }
    }
}
