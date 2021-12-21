import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorPresentacio;

import java.io.IOException;

/**
 * @author maria.prat
 */
public class Main {
    public static void main(String[] args) throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        ControladorPresentacio.obtenirInstancia();
    }
}
