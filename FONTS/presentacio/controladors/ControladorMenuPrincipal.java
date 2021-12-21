package presentacio.controladors;

import excepcions.NomInternIncorrecteException;
import presentacio.vistes.VistaMenuPrincipal;

import java.io.IOException;

/**
 * Classe que representa el controlador del Menu principal
 * @author pol.casacuberta i maria.prat
 */

public class ControladorMenuPrincipal {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuPrincipal instancia;
    private static VistaMenuPrincipal vistaMenuPrincipal;

    private ControladorMenuPrincipal () {
    }

    public static ControladorMenuPrincipal obtenirInstancia() throws IOException, NomInternIncorrecteException {
        if (instancia == null) {
            instancia = new ControladorMenuPrincipal();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuPrincipal = VistaMenuPrincipal.obtenirInstancia();
            vistaMenuPrincipal.setVisible(true);
        }
        return instancia;
    }
}
