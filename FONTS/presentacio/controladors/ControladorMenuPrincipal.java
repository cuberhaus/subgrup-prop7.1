package presentacio.controladors;

import presentacio.vistes.MenuPrincipal;

/**
 * Classe que representa el controlador del Menu principal
 * @author pol.casacuberta i maria.prat
 */

public class ControladorMenuPrincipal {
    private ControladorPresentacio controladorPresentacio;
    private static ControladorMenuPrincipal instancia;
    private MenuPrincipal menuPrincipal;

    private ControladorMenuPrincipal () {
        controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        menuPrincipal = MenuPrincipal.obtenirInstancia();
        menuPrincipal.setVisible(true);
    }

    public static ControladorMenuPrincipal obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuPrincipal();
        }
        return instancia;
    }
}
