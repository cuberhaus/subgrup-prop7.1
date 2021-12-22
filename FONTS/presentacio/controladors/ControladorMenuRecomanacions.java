package presentacio.controladors;

import excepcions.NoExisteixElementException;
import excepcions.SessioNoIniciadaException;
import excepcions.UsuariIncorrecteException;
import presentacio.vistes.VistaMenuRecomanacions;
import utilitats.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que representa el controlador del Menu de recomanacions
 * @author maria.prat
 */
public class ControladorMenuRecomanacions {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuRecomanacions instancia;
    private static VistaMenuRecomanacions vistaMenuRecomanacions;

    /**
     * Constructor per defecte del controlador
     */
    private ControladorMenuRecomanacions() {
    }

    /**
     * @return l'única instància del controlador, seguint el patró Singleton
     * @throws Exception si hi ha algun problema a l'hora de carregar les dades del programa
     */
    public static ControladorMenuRecomanacions obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ControladorMenuRecomanacions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuRecomanacions = VistaMenuRecomanacions.obtenirInstancia();
        }
        return instancia;
    }

    /**
     * @return cert si l'usuari ha iniciat sessio i, altrament, retorna fals
     */
    public static boolean sessioIniciada() {
        return controladorPresentacio.sessioIniciada();
    }

    /**
     * @return cert si hi ha un tipus d'item seleccionat i, altrament, retorna fals
     */
    public static boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    /**
     * @param valoracions Llista que conté parelles de identificadors d'items i valoracions numeriques guardades en una
     *                    String fetes per l'usuari sobre el items d'una recomanacio
     * @return String que conte el NDCG que avalua la recomanacio feta pel programa donades les valoracions de l'usuari.
     * Retorna una String buida si alguna de les valoracions no te el format esperat i emet un missatge en cas d'error.
     */
    public static String avaluarRecomanacio(ArrayList<Pair<String, String>> valoracions) throws NoExisteixElementException, SessioNoIniciadaException, UsuariIncorrecteException {
        ArrayList<Pair<Integer, Double>> valoracionsAmbFormat = new ArrayList<>();
        try {
            for (Pair<String, String> valoracio : valoracions) {
                if (valoracio.y != null && !valoracio.y.isEmpty()) {
                    double valor = Double.parseDouble(valoracio.y);
                    if (valor < 0) {
                        JOptionPane.showMessageDialog(vistaMenuRecomanacions, "Les valoracions han de ser nombres positius.");
                        return "";
                    }
                    valoracionsAmbFormat.add(new Pair<>(Integer.parseInt(valoracio.x), valor));
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "Les valoracions han de ser nombres.");
            return "";
        }
        return String.valueOf(controladorPresentacio.avaluarRecomanacio(valoracionsAmbFormat));
    }

    /**
     * @return Llista que conté els noms dels atributs del tipus d'item seleccionat. Emet un missatge si no hi ha cap
     * tipus d'item seleccionat.
     */
    public static ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No hi ha cap tipus d'ítem seleccionat.");
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirNomAtributsTipusItemSeleccionat();
    }

    /**
     * @return el nom del tipus d'item seleccionat o null si no hi ha cap tipus d'item seleccionat
     */
    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    /**
     * Emet un missatge si hi ha un error
     * @param descripcioMetode String que conte la descripcio del metode que es vol utilitzar per fer la recomanacio
     * @param nomsAtributsFiltre Llista que conte els noms dels atributs del tipus d'item seleccionat que es volen
     *                           incloure al filtre per fer la recomanacio
     * @return una llista amb els identificadors dels items recomanats
     */
    public ArrayList<String> obtenirRecomanacio(String descripcioMetode, Map<String, Boolean> nomsAtributsFiltre) {
        ArrayList<String> filtre = new ArrayList<>();
        nomsAtributsFiltre.forEach((nomAtribut, inclos) -> {
            if (inclos) {
                filtre.add(nomAtribut);
            }
        });
        try {
            switch (descripcioMetode) {
                case "Basat en els ítems que has valorat":
                    return controladorPresentacio.obtenirRecomanacioContentBased(filtre, true);
                case "Basat en usuaris amb gustos semblants als teus":
                    return controladorPresentacio.obtenirRecomanacioCollaborative(filtre, true);
                case "Basat en tot":
                    return controladorPresentacio.obtenirRecomanacioHibrida(filtre, true);
            }
        } catch (SessioNoIniciadaException e1) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No has iniciat la sessió.");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No s'ha pogut obtenir la recomanació. Error:\n" + e2.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Emet un missatge si hi ha un error
     * @param itemId identificador d'un item
     * @return un mapa que relaciona el nom dels atributs de l'item amb el seu valor en forma de String
     */
    public Map<String, String> obtenirItem(String itemId) {
        try {
            return controladorPresentacio.obtenirItem(itemId);
        } catch (NoExisteixElementException e1) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No existeix cap ítem amb identificador " + itemId);
            return new TreeMap<>();
        }
    }
}
