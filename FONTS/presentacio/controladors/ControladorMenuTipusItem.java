package presentacio.controladors;

import presentacio.EncarregatActualitzarVistes;
import utilitats.Pair;
import excepcions.*;
import presentacio.vistes.VistaMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que representa el controlador del Menu del tipus d'item
 * @author maria.prat
 */
public class ControladorMenuTipusItem implements EncarregatActualitzarVistes.Observador {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuTipusItem instancia;
    private static VistaMenuTipusItem vistaMenuTipusItem;

    /**
     * Constructor per defecte del controlador
     */
    private ControladorMenuTipusItem () {
    }

    /**
     * @return l'única instància del controlador, seguint el patró Singleton
     * @throws Exception si hi ha algun problema a l'hora de carregar les dades del programa
     */
    public static ControladorMenuTipusItem obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ControladorMenuTipusItem();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuTipusItem = VistaMenuTipusItem.obtenirInstancia();
        }
        return instancia;
    }

    /**
     * @return Llista amb els noms del tipus d'items carregats al programa
     */
    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorPresentacio.obtenirNomsTipusItemsCarregats();
    }

    /**
     * @param nomValorAtributExtern String que conte el nom d'un valor atribut en un format extern
     * @return String que conte el nom intern del valor atribut donat o una String buida si no el reconeix
     */
    private String obtenirNomValorAtributIntern(String nomValorAtributExtern) {
        switch (nomValorAtributExtern) {
            case "Booleà":
                return "ValorBoolea";
            case "Categòric":
                return "ValorCategoric";
            case "Numèric":
                return "ValorNumeric";
            case "Textual":
                return "ValorTextual";
            case "Conjunt booleà":
                return "ValorConjuntBoolea";
            case "Conjunt categòric":
                return "ValorConjuntCategoric";
            case "Conjunt numèric":
                return "ValorConjuntNumeric";
            case "Conjunt textual":
                return "ValorConjuntTextual";
            default:
                return "";
        }
    }

    /**
     * @param nomDistanciaExtern String que conte el nom d'una distancia en un format extern
     * @return String que conte el nom intern de la distancia donat o una String buida si no el reconeix
     */
    private String obtenirNomDistanciaIntern(String nomDistanciaExtern) {
        switch (nomDistanciaExtern) {
            case "Diferència de conjunts":
                return "DistanciaDiferenciaDeConjunts";
            case "Discreta":
                return "DistanciaDiscreta";
            case "Euclidiana":
                return "DistanciaEuclidiana";
            case "Levenshtein":
                return "DistanciaLevenshtein";
            case "Zero":
                return "DistanciaZero";
            default:
                return "";
        }
    }

    /**
     * @param nomDistanciaIntern String que conte el nom d'una distancia en un format intern
     * @return String que conte el nom extern de la distancia donat o una String buida si no el reconeix
     */
    private String obtenirNomDistanciaExtern(String nomDistanciaIntern) {
        switch (nomDistanciaIntern) {
            case "DistanciaDiferenciaDeConjunts":
                return "Diferència de conjunts";
            case "DistanciaDiscreta":
                return "Discreta";
            case "DistanciaEuclidiana":
                return "Euclidiana";
            case "DistanciaLevenshtein":
                return "Levenshtein";
            case "DistanciaZero":
                return "Zero";
            default:
                return "";
        }
    }

    /**
     * @param component Component on s'emetran els missatges
     * @param nom Nom del nou tipus d'item
     * @param nomAValorAtribut Mapa que relaciona el nom de cada atribut a una parella formada pel nom en format extern
     *                         del tipus de valor de l'atribut i el nom en format extern de la distancia de l'atribut
     * @return cert si s'ha pogut crear un nou tipus d'item amb els parametres donats i, altrament, fals
     */
    public boolean crearTipusItem(Component component, String nom, Map<String, Pair<String, String>> nomAValorAtribut) {
        try {
            Map<String, Pair<String, String>> nomAValorAtributAmbFormat = new TreeMap<>();
            for (Map.Entry<String, Pair<String, String>> atribut : nomAValorAtribut.entrySet()) {
                nomAValorAtributAmbFormat.put(atribut.getKey(), new Pair<>(
                        obtenirNomValorAtributIntern(atribut.getValue().x),
                        obtenirNomDistanciaIntern(atribut.getValue().y)));
            }
            controladorPresentacio.crearTipusItem(nom, nomAValorAtributAmbFormat);
            JOptionPane.showMessageDialog(component,
                    "Tipus d'ítem creat amb èxit.");
            return true;
        } catch (JaExisteixElementException e1) {
            JOptionPane.showMessageDialog(component,
                    "Ja existeix un tipus d'ítem amb aquest nom.");
        } catch (DistanciaNoCompatibleAmbValorException distanciaNoCompatibleAmbValorException) {
            JOptionPane.showMessageDialog(component,
                    "Hi ha una distància que no és compatible amb el valor escollit");
        } catch (Exception e3) {
            JOptionPane.showMessageDialog(component,
                    "No s'ha pogut crear el tipus d'ítem. Torna-ho a intentar.");
        }
        return false;
    }

    /**
     * Crea un nou tipus d'item amb el nom donat des d'un fitxer
     * @param nom String que conte el nom del nou tipus d'item
     * @param rutaAbsoluta String que conte la ruta absoluta on trobar el fitxer
     */
    public void carregarTipusItem(String nom, String rutaAbsoluta) {
        try {
            controladorPresentacio.carregarTipusItem(nom, rutaAbsoluta);
            JOptionPane.showMessageDialog(vistaMenuTipusItem, "Tipus d'ítem carregat amb èxit.");
            EncarregatActualitzarVistes.notificarObservadors();
        } catch (JaExisteixElementException e1) {
            JOptionPane.showMessageDialog(vistaMenuTipusItem,
                    "Ja existeix un tipus d'ítem amb el nom indicat.");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(vistaMenuTipusItem,
                    "No s'ha pogut carregar un tipus d'ítem d'aquest arxiu.");
        }
    }

    /**
     * @return un mapa que relaciona els noms dels atributs del tipus item seleccionat amb una parella formada pels
     * noms en format extern del tipus de valor i la distancia de cada tipus d'atribut
     * @throws DistanciaNoCompatibleAmbValorException si alguna de les parelles que defineixen el tipus d'atribut no
     * es compatible
     */
    public Map<String, Pair<String, String>> obtenirTipusAtributsTipusItemSeleccionat()
            throws DistanciaNoCompatibleAmbValorException {
        Map<String, Pair<String, String>> atributsAmbFormat =
                controladorPresentacio.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat();
        Map<String, Pair<String, String>> atributs = new TreeMap<>();
        for (Map.Entry<String, Pair<String, String>> atribut : atributsAmbFormat.entrySet()) {
            atributs.put(atribut.getKey(), new Pair<>(controladorPresentacio.obtenirNomValorAtributExtern(atribut.getValue().x),
                    obtenirNomDistanciaExtern(atribut.getValue().y)));
        }
        return atributs;
    }

    /**
     * @return String que conte el nom del tipus d'item seleccionat o null si hi ha un tipus d'item seleccionat
     */
    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    /**
     * Esborra totes les dades relacionades amb el tipus d'item seleccionat
     */
    public void esborrarTipusItemSeleccionat() {
        try {
            controladorPresentacio.esborrarTipusItemSeleccionat();
            EncarregatActualitzarVistes.notificarObservadors();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(vistaMenuTipusItem,
                    "No s'ha pogut esborrar el tipus d'ítem seleccionat.");
        }
    }

    /**
     * Selecciona el tipus d'item amb el nom donat. Emet un missatge si hi ha un error.
     * @param nomTipusItem String nom del tipus d'item
     */
    public void seleccionarTipusItem(String nomTipusItem) {
        try {
            controladorPresentacio.seleccionarTipusItem(nomTipusItem);
            EncarregatActualitzarVistes.notificarObservadors();
        } catch (NoExisteixElementException e) {
            JOptionPane.showMessageDialog(vistaMenuTipusItem,
                    "No exiseix un tipus d'item amb aquest nou carregat.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vistaMenuTipusItem,
                    "No s'han pogut llegir els fitxers d'aquest tipus d'item.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuTipusItem,
                    "Hi ha algun error en els fitxers d'aquest tipus d'item.");
        }
    }

    /**
     * @return cert si hi ha un tipus d'item seleccionat i, altrament, retorna fals
     */
    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    /**
     * Edita el tipus d'item seleccionat substituint el seu nom pel nom donat. Emet un missatge si hi ha algun error.
     * @param component Component on es s'emetran els missatges
     * @param nouNom nou nom del tipus d'item
     * @return cert si s'ha pogut editar i fals altrament
     */
    public boolean editarTipusItem(Component component, String nouNom) {
        try {
            controladorPresentacio.editarTipusItem(nouNom);
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(component,
                    "No es pot editar aquest tipus d'ítem. Torna-ho a intentar.");
        } catch (FormatIncorrecteException | JaExisteixElementException ex) {
            JOptionPane.showMessageDialog(component, ex.getMessage());
        }
        return false;
    }

    /**
     * Desselecciona el tipus d'item seleccionat, si n'hi ha un. Guarda totes les dades relacionades amb ell als fixers
     * corresponents. Emet un missatge si hi ha un error.
     */
    public void desseleccionarTipusItem() {
        if (existeixTipusItemSeleccionat()) {
            try {
                controladorPresentacio.desseleccionarTipusItem();
                EncarregatActualitzarVistes.notificarObservadors();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(vistaMenuTipusItem,
                        "No s'ha pogut deseleccionar el tipus d'ítem seleccionat.");
            }
        }
    }

    @Override
    public void actualitzar() {
        VistaMenuTipusItem.actualitzarTipusItems();
    }
}
