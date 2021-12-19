package presentacio.controladors;

import domini.classes.Pair;
import presentacio.vistes.VistaDialegCrearTipusItem;
import presentacio.vistes.VistaDialegEditarTipusItem;
import presentacio.vistes.VistaDialegMostrarTipusItem;
import presentacio.vistes.VistaMenuTipusItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author maria.prat
 */
public class ControladorMenuTipusItem {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuTipusItem instancia;
    private static VistaMenuTipusItem vistaMenuTipusItem;
    private static VistaDialegCrearTipusItem vistaDialegCrearTipusItem;
    private static VistaDialegEditarTipusItem vistaDialegEditarTipusItem;
    private static VistaDialegMostrarTipusItem vistaDialegMostrarTipusItem;

    private ControladorMenuTipusItem () {
    }

    public static ControladorMenuTipusItem obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuTipusItem();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }

    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorPresentacio.obtenirNomsTipusItemsCarregats();
    }

    public void afegirTipusItem(String nom, Map<String, Pair<String, String>> nomValorAAtribut) throws IOException {
        // TODO: esborrar sortida
        System.out.println("Afegint TipusItem");
        System.out.println(nom);
        System.out.println(nomValorAAtribut.toString());
        System.out.println("******************");
        // TODO (maria): catch exception
        controladorPresentacio.afegirTipusItem(nom, nomValorAAtribut);
    }

    public void carregarTipusItem(String rutaAbsoluta, String nom) throws IOException {
        // TODO: esborrar sortida
        System.out.println("Carregant TipusItem");
        System.out.println(rutaAbsoluta);
        System.out.println("******************");
        // TODO (maria): catch exception
        controladorPresentacio.carregarTipusItem(rutaAbsoluta, nom);
    }

    public Map<String, Pair<String, String>> obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat() {
        return controladorPresentacio.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat();
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    public void esborrarTipusItemSeleccionat() throws IOException {
        controladorPresentacio.esborrarTipusItemSeleccionat();
    }

    public void seleccionarTipusItem(String nomTipusItem) throws Exception {
        controladorPresentacio.seleccionarTipusItem(nomTipusItem);
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    public void editarTipusItem(Map<String, String> relacioNomsTipusAtributs) {
        controladorPresentacio.editarTipusItem(relacioNomsTipusAtributs);
    }

    public void deseleccionarTipusItem() throws IOException {
        controladorPresentacio.deseleccionarTipusItem();
    }
}
