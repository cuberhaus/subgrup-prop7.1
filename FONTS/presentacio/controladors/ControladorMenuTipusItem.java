package presentacio.controladors;

import domini.classes.TipusItem;
import presentacio.vistes.MenuPrincipal;

/**
 * @author maria.prat
 */
public class ControladorMenuTipusItem {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuTipusItem instancia;

    private ControladorMenuTipusItem () {
    }

    public static ControladorMenuTipusItem obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuTipusItem();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }

    public TipusItem[] obtenirTipusItemsCarregats() {
        return controladorPresentacio.obtenirTipusItemsCarregats();
    }
}
