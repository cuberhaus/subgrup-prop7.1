package presentacio.controladors;

import presentacio.EncarregatActualitzarVistes;
import presentacio.vistes.VistaMenuInici;

import java.util.ArrayList;

/**
 * Controlador de la vista d'inici
 * @author pol.casacuberta
 */
public class ControladorMenuInici implements EncarregatActualitzarVistes.Observador {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuInici instancia;
    private static VistaMenuInici vistaMenuInici;

    private ControladorMenuInici() {
    }

    public static ControladorMenuInici obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ControladorMenuInici();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuInici = VistaMenuInici.obtenirInstancia();
        }
        return instancia;
    }

    public int obtenirNombreUsuaris() {
        return controladorPresentacio.obtenirUsuaris().size();
    }

    public int obtenirNombreItems() {
        if (controladorPresentacio.obtenirNomTipusItemSeleccionat() != null) {
            return controladorPresentacio.obtenirNombreItems();
        }
        return 0;
    }

    public int obtenirNombreValoracions() {
        if (controladorPresentacio.obtenirNomTipusItemSeleccionat() != null) {
            return controladorPresentacio.obtenirNombreValoracions();
        }
        return 0;
    }

    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorPresentacio.obtenirNomsTipusItemsCarregats();
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    public boolean sessioIniciada() {
        return controladorPresentacio.sessioIniciada();
    }

    @Override
    public void actualitzar() {
        vistaMenuInici.actualitzar();
    }
}