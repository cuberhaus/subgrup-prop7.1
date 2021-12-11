package presentacio.controladors;

import domini.classes.TipusItem;
import domini.controladors.ControladorDomini;
import presentacio.vistes.MenuPrincipal;

/**
 * @author maria.prat
 */
public class ControladorPresentacio {

    private final ControladorDomini controladorDomini;

    public ControladorPresentacio() {
        controladorDomini = new ControladorDomini();
        inicialitzarControladorPresentacio();
    }

    public void inicialitzarControladorPresentacio() {
        MenuPrincipal menuPrincipal = new MenuPrincipal("Menu Principal", this);
        menuPrincipal.setVisible(true);
    }

    public TipusItem[] obtenirTipusItemsCarregats() {
        // TODO: implementar
        return new TipusItem[]{new TipusItem("Llibres"), new TipusItem("Música"), new TipusItem("Tipus d'item amb un nom molt llarg")};
        //return new TipusItem[]{};
    }

    public int obtenirSessio() {
        return controladorDomini.obtenirSessio();
    }

    public void iniciarSessio(int idSessio, String contrasenya) {
        controladorDomini.iniciarSessio(idSessio, contrasenya);
    }
}
