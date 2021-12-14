package presentacio.controladors;

import presentacio.vistes.MenuPrincipal;

import java.awt.*;

/**
 * Classe que representa el controlador del Menu principal
 * @author pol.casacuberta i maria.prat
 */

public class ControladorMenuPrincipal {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuPrincipal instancia;
    private static MenuPrincipal menuPrincipal;

    private ControladorMenuPrincipal () {
    }

    public static ControladorMenuPrincipal obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuPrincipal();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            menuPrincipal = new MenuPrincipal();
            menuPrincipal.setVisible(true);
        }
        return instancia;
    }
}
