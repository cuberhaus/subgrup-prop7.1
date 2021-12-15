package presentacio.controladors;

import presentacio.vistes.VistaDialegCrearTipusItem;
import presentacio.vistes.VistaDialegEditarTipusItem;
import presentacio.vistes.VistaDialegMostrarTipusItem;
import presentacio.vistes.VistaMenuTipusItem;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
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

    public boolean afegirTipusItem(String nom, Map<String, String> valorsTipusAtributs,
                                   Map<String, String> distanciesTipusAtributs) {
        // TODO: esborrar sortida
        System.out.println("Afegint TipusItem");
        System.out.println(nom);
        System.out.println(valorsTipusAtributs.toString());
        System.out.println(distanciesTipusAtributs.toString());
        System.out.println("******************");
        return controladorPresentacio.afegirTipusItem(nom, valorsTipusAtributs, distanciesTipusAtributs);
    }

    public void carregaTipusItem(String rutaAbsoluta) {
        // TODO: esborrar sortida
        System.out.println("Carregant TipusItem");
        System.out.println(rutaAbsoluta);
        System.out.println("******************");
        if (!controladorPresentacio.carregarTipusItem(rutaAbsoluta)) {
            JOptionPane.showMessageDialog(vistaMenuTipusItem,
                    "No es pot llegir un tipus d'ítem del fitxer seleccionat.");
        }
    }

    public void esborrarTipusItem(String nomTipusItem) {
        System.out.println("Esborrant TipusItem");
        System.out.println(nomTipusItem);
        System.out.println("******************");
        controladorPresentacio.esborrarTipusItem(nomTipusItem);
    }

    public Map<String, String> obtenirValorsTipusAtributs(String nomTipusItem) {
        Map<String, String> test = new HashMap<>();
        test.put("Autor", "Booleà");
        return test;
        //return controladorPresentacio.obtenirValorsTipusAtributs(nomTipusItem);
    }

    public Map<String, String> obtenirDistanciesTipusAtributs(String nomTipusItem) {
        Map<String, String> test = new HashMap<>();
        test.put("Autor", "Discreta");
        return test;
        //return controladorPresentacio.obtenirDistanciesTipusAtributs(nomTipusItem);
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    public void esborrarTipusItemSeleccionat() {
        controladorPresentacio.esborrarTipusItemSeleccionat();
    }

    public void seleccionarTipusItem(String nomTipusItem) {
        controladorPresentacio.seleccionarTipusItem(nomTipusItem);
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }
}
