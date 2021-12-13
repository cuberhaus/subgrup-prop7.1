package presentacio.controladors;

import presentacio.vistes.MenuPrincipal;

/**
 * @author pol.casacuberta
 */

public class ControladorMenuPrincipal {
    private static ControladorMenuPrincipal instanciaUnica = null;
    private MenuPrincipal menuPrincipal;

    private ControladorMenuPrincipal () {
    }

    public static ControladorMenuPrincipal obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorMenuPrincipal();
        }
        return instanciaUnica;
    }
    public void inicialitzarControladorMenuPrincipal() {
        menuPrincipal = new MenuPrincipal("Menu Principal");
        menuPrincipal.setVisible(true);
    }
}
