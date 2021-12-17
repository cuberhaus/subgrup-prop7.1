package presentacio.controladors;

import domini.controladors.ControladorDomini;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe que representa el controlador de presentació
 * @author maria.prat i pol.casacuberta
 */
public class ControladorPresentacio {

    private static ControladorDomini controladorDomini;
    private static ControladorPresentacio instancia;
    private static ControladorMenuUsuaris controladorMenuUsuaris;
    private static ControladorMenuPrincipal controladorMenuPrincipal;
    private static ControladorMenuTipusItem controladorMenuTipusItem;

    private ControladorPresentacio() {
    }

    public static ControladorPresentacio obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorPresentacio();
            controladorDomini = ControladorDomini.obtenirInstancia();
            controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
            controladorMenuUsuaris = ControladorMenuUsuaris.obtenirInstancia();
            controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        }
        return instancia;
    }

    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorDomini.obtenirNomsTipusItemsCarregats();
    }

    public int obtenirSessio() {
        return controladorDomini.obtenirSessio();
    }

    public void iniciarSessio(int idSessio, String contrasenya) {
        controladorDomini.iniciarSessio(idSessio, contrasenya);
    }

    public boolean existeixUsuari(int id) {
        return controladorDomini.existeixUsuari(id);
    }

    public void afegirUsuari(String nom, String contrasenya) {
        controladorDomini.afegirUsuari(nom,contrasenya);
    }

    public void esborrarUsuari(int id) {
        controladorDomini.esborrarUsuari(id);
    }

    public void tancarSessio() {
        controladorDomini.tancarSessio();
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) {
        controladorDomini.afegirValoracio(usuariId,itemId,valor);
    }

    public boolean existeixValoracio(String usuariId, String itemId) {
        return controladorDomini.existeixValoracio(usuariId,itemId);
    }

    public void esborrarValoracio(String usuariId, String itemId) {
        controladorDomini.esborraValoracio(usuariId,itemId);
    }

    public void editarValoracio(String usuariId, String itemId, String valor) {
        controladorDomini.editarValoracio(usuariId,itemId,valor);
    }

    public void carregarConjuntValoracions(String pathAbsolut) throws Exception {
        controladorDomini.carregaConjuntValoracions(pathAbsolut);
    }

    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorDomini.obtenirLlistaConjunts();
    }

    public boolean afegirTipusItem(String nom, Map<String, String> valorsTipusAtributs, Map<String, String> distanciesTipusAtributs) {
        return controladorDomini.crearTipusItem(nom, valorsTipusAtributs, distanciesTipusAtributs);
    }

    public boolean carregarTipusItem(String rutaAbsoluta) {
        return controladorDomini.carregarTipusItem(rutaAbsoluta);
    }

    public void esborrarTipusItem(String nomTipusItem) {
        controladorDomini.esborrarTipusItem(nomTipusItem);
    }

    public Map<String, String> obtenirValorsTipusAtributs(String nomTipusItem) {
        return controladorDomini.obtenirValorsTipusAtributs(nomTipusItem);
    }

    public Map<String, String> obtenirDistanciesTipusAtributs(String nomTipusItem) {
        return controladorDomini.obtenirDistanciesTipusAtributs(nomTipusItem);
    }

    public boolean sessioIniciada() {
        return controladorDomini.sessioIniciada();
    }

    public void exportarConjuntUsuaris(String absolutePath) {
        controladorDomini.exportarConjuntDadesUsuari(absolutePath);
    }

    public void esborrarConjuntUsuaris() {
        controladorDomini.esborraConjuntUsuaris();
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorDomini.obtenirNomTipusItemSeleccionat();
    }

    public void esborrarTipusItemSeleccionat() {
        controladorDomini.esborrarTipusItemSeleccionat();
    }

    public void seleccionarTipusItem(String nomTipusItem) throws IOException {
        controladorDomini.seleccionarTipusItem(nomTipusItem);
    }

    public ArrayList<ArrayList<String>> obtenirItems() {
        return controladorDomini.obtenirItems();
    }

    public ArrayList<String> obtenirNomAtributsTipusItemSeleccionat() {
        return controladorDomini.obtenirNomsAtributsTipusItemSeleccionat();
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorDomini.existeixTipusItemSeleccionat();
    }

    public boolean afegirItem(Map<String, String> valorsAtributs) {
        return controladorDomini.afegirItem(valorsAtributs);
    }

    public boolean esborrarItem(String id) {
        return controladorDomini.esborrarItem(id);
    }

    public Map<String, String> obtenirItem(String id) {
        return controladorDomini.obtenirItem(id);
    }

    public boolean editarItem(String id, Map<String, String> valorsAtributs) {
        return controladorDomini.editarItem(id, valorsAtributs);
    }

    public void carregarConjuntItems(String rutaAbsoluta) {
        controladorDomini.carregarConjuntItems(rutaAbsoluta);
    }

    public void esborrarTotsElsItems() {
        controladorDomini.esborrarTotsElsItems();
    }

    public void editarTipusItem(Map<String, String> relacioNomsTipusAtributs) {
        controladorDomini.editarTipusItem(relacioNomsTipusAtributs);
    }

    public ArrayList<String> obtenirRecomanacioCollaborative(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        return controladorDomini.obtenirRecomanacioCollaborative(nomAtributs, filtreInclusiu);
    }

    public ArrayList<String> obtenirRecomanacioContentBased(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        return controladorDomini.obtenirRecomanacioContentBased(nomAtributs, filtreInclusiu);
    }

    public ArrayList<String> obtenirRecomanacioHibrida(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        return controladorDomini.obtenirRecomanacioHibrida(nomAtributs, filtreInclusiu);
    }

    public double avaluarRecomanacio() {
        return controladorDomini.avaluarRecomanacio();
    }

    public void esborrarTotesLesValoracions() {
        controladorDomini.esborrarTotesLesValoracions();
    }

    public ArrayList<ArrayList<String>> obtenirValoracions() {
        return controladorDomini.obtenirValoracions();
    }
}
