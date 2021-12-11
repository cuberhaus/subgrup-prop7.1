package presentacio.controladors;

import presentacio.vistes.MenuPrincipal;

/**
 * @author pol.casacuberta
 */

public class ControladorMenuPrincipal {
    private static ControladorMenuPrincipal instanciaUnica = null;
    private MenuPrincipal menuPrincipal;
    private static ControladorGestioUsuari controladorGestioUsuari;

    private ControladorMenuPrincipal () {
    }

    public static ControladorMenuPrincipal obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorMenuPrincipal();
            controladorGestioUsuari = ControladorGestioUsuari.obtenirInstancia();
        }
        return instanciaUnica;
    }
    public void inicialitzarControladorMenuPrincipal() {
        menuPrincipal = new MenuPrincipal("Menu Principal",controladorGestioUsuari);
        menuPrincipal.setVisible(true);
    }
}
