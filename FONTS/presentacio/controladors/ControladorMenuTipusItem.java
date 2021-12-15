package presentacio.controladors;

import presentacio.vistes.MenuTipusItem;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maria.prat
 */
public class ControladorMenuTipusItem {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuTipusItem instancia;
    private static MenuTipusItem menuTipusItem;

    private ControladorMenuTipusItem () {
    }

    public static ControladorMenuTipusItem obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuTipusItem();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }

    public String[] obtenirNomsTipusItemsCarregats() {
        return new String[]{"a", "b"};
        //return controladorPresentacio.obtenirNomsTipusItemsCarregats();
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
            JOptionPane.showMessageDialog(menuTipusItem,
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
}
