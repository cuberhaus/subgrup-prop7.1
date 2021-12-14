package presentacio.controladors;

import presentacio.vistes.MenuTipusItem;

import javax.swing.*;
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

    public boolean afegirTipusItem(String nom, Map<String, String> valorsAtributs, Map<String, String> distanciesAtributs) {
        // TODO: esborrar sortida
        System.out.println("Afegint TipusItem");
        System.out.println(nom);
        System.out.println(valorsAtributs.toString());
        System.out.println(distanciesAtributs.toString());
        System.out.println("******************");
        return controladorPresentacio.afegirTipusItem(nom, valorsAtributs, distanciesAtributs);
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
}
