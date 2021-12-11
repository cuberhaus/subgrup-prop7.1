package presentacio.controladors;

import domini.classes.TipusItem;
import domini.controladors.ControladorDomini;
import presentacio.vistes.MenuPrincipal;

/**
 * @author maria.prat i pol.casacuberta
 */
public class ControladorPresentacio {

    private static ControladorDomini controladorDomini;
    private static ControladorPresentacio instanciaUnica = null;

    private ControladorPresentacio() {
    }

    public static ControladorPresentacio obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorPresentacio();
            controladorDomini = new ControladorDomini();
            inicialitzarControladorPresentacio();
        }
        return instanciaUnica;
    }

    public static void inicialitzarControladorPresentacio() {
        MenuPrincipal menuPrincipal = new MenuPrincipal("Menu Principal");
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
