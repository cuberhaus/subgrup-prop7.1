package presentacio.controladors;

import utilitats.Pair;
import excepcions.*;
import presentacio.vistes.VistaDialegCrearTipusItem;
import presentacio.vistes.VistaDialegEditarTipusItem;
import presentacio.vistes.VistaDialegMostrarTipusItem;
import presentacio.vistes.VistaMenuTipusItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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

    public static ControladorMenuTipusItem obtenirInstancia() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        if (instancia == null) {
            instancia = new ControladorMenuTipusItem();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }

    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorPresentacio.obtenirNomsTipusItemsCarregats();
    }

    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IOException, NomInternIncorrecteException, JaExisteixElementException, DistanciaNoCompatibleAmbValorException {
        Map<String, Pair<String, String>> nomAValorAtributAmbFormat = new TreeMap<>();
        for (Map.Entry<String, Pair<String, String>> atribut : nomAValorAtribut.entrySet()) {
            String valorAtribut;
            String distanciaAtribut;
            switch (atribut.getValue().x) {
                case "Booleà":
                    valorAtribut = "ValorBoolea";
                    break;
                case "Categòric":
                    valorAtribut = "ValorCategoric";
                    break;
                case "Numèric":
                    valorAtribut = "ValorNumeric";
                    break;
                case "Textual":
                    valorAtribut = "ValorTextual";
                    break;
                case "Conjunt booleà":
                    valorAtribut = "ValorConjuntBoolea";
                    break;
                case "Conjunt categòric":
                    valorAtribut = "ValorConjuntCategoric";
                    break;
                case "Conjunt numèric":
                    valorAtribut = "ValorConjuntNumeric";
                    break;
                case "Conjunt textual":
                    valorAtribut = "ValorConjuntTextual";
                    break;
                default:
                    valorAtribut = "";
            }
            switch (atribut.getValue().y) {
                case "Diferència de conjunts":
                    distanciaAtribut = "DistanciaDiferenciaDeConjunts";
                    break;
                case "Discreta":
                    distanciaAtribut = "DistanciaDiscreta";
                    break;
                case "Euclidiana":
                    distanciaAtribut = "DistanciaEuclidiana";
                    break;
                case "Levenshtein":
                    distanciaAtribut = "DistanciaLevenshtein";
                    break;
                case "Zero":
                    distanciaAtribut = "DistanciaZero";
                    break;
                default:
                    distanciaAtribut = "";
            }
            nomAValorAtributAmbFormat.put(atribut.getKey(), new Pair<>(valorAtribut, distanciaAtribut));
        }
        controladorPresentacio.crearTipusItem(nom, nomAValorAtributAmbFormat);
    }

    public void carregarTipusItem(String nom, String rutaAbsoluta) throws IOException, JaExisteixElementException, FormatIncorrecteException {
        controladorPresentacio.carregarTipusItem(nom, rutaAbsoluta);
    }

    public Map<String, Pair<String, String>> obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat() throws DistanciaNoCompatibleAmbValorException {
        Map<String, Pair<String, String>> atributsAmbFormat = controladorPresentacio.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat();
        Map<String, Pair<String, String>> atributs = new TreeMap<>();
        for (Map.Entry<String, Pair<String, String>> atribut : atributsAmbFormat.entrySet()) {
            String valorAtribut;
            String distanciaAtribut;
            switch (atribut.getValue().x) {
                case "ValorBoolea":
                    valorAtribut = "Booleà";
                    break;
                case "ValorCategoric":
                    valorAtribut = "Categòric";
                    break;
                case "ValorNumeric":
                    valorAtribut = "Numèric";
                    break;
                case "ValorTextual":
                    valorAtribut = "Textual";
                    break;
                case "ValorConjuntBoolea":
                    valorAtribut = "Conjunt booleà";
                    break;
                case "ValorConjuntCategoric":
                    valorAtribut = "Conjunt categòric";
                    break;
                case "ValorConjuntNumeric":
                    valorAtribut = "Conjunt numèric";
                    break;
                case "ValorConjuntTextual":
                    valorAtribut = "Conjunt textual";
                    break;
                default:
                    valorAtribut = "";
            }
            switch (atribut.getValue().y) {
                case "DistanciaDiferenciaDeConjunts":
                    distanciaAtribut = "Diferència de conjunts";
                    break;
                case "DistanciaDiscreta":
                    distanciaAtribut = "Discreta";
                    break;
                case "DistanciaEuclidiana":
                    distanciaAtribut = "Euclidiana";
                    break;
                case "DistanciaLevenshtein":
                    distanciaAtribut = "Levenshtein";
                    break;
                case "DistanciaZero":
                    distanciaAtribut = "Zero";
                    break;
                default:
                    distanciaAtribut = "";
            }
            atributs.put(atribut.getKey(), new Pair<>(valorAtribut, distanciaAtribut));
        }
        return atributs;
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    public void esborrarTipusItemSeleccionat() throws IOException {
        controladorPresentacio.esborrarTipusItemSeleccionat();
    }

    public void seleccionarTipusItem(String nomTipusItem) throws NoExisteixElementException, IOException, AccesAEstatIncorrecteException, UsuariIncorrecteException {
        controladorPresentacio.seleccionarTipusItem(nomTipusItem);
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    public void editarTipusItem(String nouNom) throws IOException, FormatIncorrecteException, JaExisteixElementException {
        controladorPresentacio.editarTipusItem(nouNom);
    }

    public void desseleccionarTipusItem() throws IOException {
        controladorPresentacio.deseleccionarTipusItem();
    }

    public void actualitzarVistesTipusItemSeleccionat() {
        controladorPresentacio.actualitzarVistesTipusItemSeleccionat();
    }
}
