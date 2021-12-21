package presentacio.controladors;

import domini.classes.Pair;
import domini.controladors.ControladorDomini;
import excepcions.*;

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

    public static ControladorPresentacio obtenirInstancia() throws IOException, NomInternIncorrecteException {
        if (instancia == null) {
            instancia = new ControladorPresentacio();
            controladorDomini = ControladorDomini.obtenirInstancia();
            controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
            controladorMenuUsuaris = ControladorMenuUsuaris.obtenirInstancia();
            controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        }
        return instancia;
    }

    public Map<String, Pair<String, String>> obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat() {
        return controladorDomini.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat();
    }

    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorDomini.obtenirNomsTipusItemsCarregats();
    }

    public int obtenirSessio() throws SessioNoIniciadaException {
        return controladorDomini.obtenirSessio();
    }

    public void iniciarSessio(int idSessio, String contrasenya) throws NoExisteixElementException, ContrasenyaIncorrectaException, SessioIniciadaException {
        controladorDomini.iniciarSessio(idSessio, contrasenya);
    }

    public boolean existeixUsuari(int id) throws NoExisteixElementException {
        return controladorDomini.existeixUsuari(id);
    }

    public int afegirUsuari(String nom, String contrasenya) throws NoExisteixElementException, JaExisteixElementException {
        return controladorDomini.afegirUsuari(nom,contrasenya);
    }

    public void esborrarUsuari(int id) throws NoExisteixElementException {
        controladorDomini.esborrarUsuari(id);
    }

    public void tancarSessio() throws SessioNoIniciadaException {
        controladorDomini.tancarSessio();
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) throws Exception {
        controladorDomini.afegirValoracio(usuariId,itemId,valor);
    }

    public boolean existeixValoracio(String usuariId, String itemId) throws NoExisteixElementException {
        return controladorDomini.existeixValoracio(usuariId,itemId);
    }

    public void esborrarValoracio(String usuariId, String itemId) throws Exception {
        controladorDomini.esborraValoracio(usuariId,itemId);
    }

    public void editarValoracio(String usuariId, String itemId, String valor) throws NoExisteixElementException, UsuariIncorrecteException {
        controladorDomini.editarValoracio(usuariId,itemId,valor);
    }

    public void carregarConjuntValoracions(String pathAbsolut) throws NoExisteixElementException, IOException, AccesAEstatIncorrecteException, UsuariIncorrecteException {
        controladorDomini.carregaConjuntValoracions(pathAbsolut);
    }

    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorDomini.obtenirLlistaConjunts();
    }

    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IllegalArgumentException, IOException, NomInternIncorrecteException, JaExisteixElementException {
        controladorDomini.crearTipusItem(nom, nomAValorAtribut);
    }

    public void carregarTipusItem(String nom, String rutaAbsoluta) throws IOException, JaExisteixElementException, FormatIncorrecteException {
        controladorDomini.carregarTipusItem(nom, rutaAbsoluta);
    }

    public boolean esSessioIniciada() {
        return controladorDomini.esSessioIniciada();
    }

    public void exportarConjuntUsuaris(String absolutePath) throws IOException {
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

    public void seleccionarTipusItem(String nomTipusItem) throws NoExisteixElementException, IOException, AccesAEstatIncorrecteException, UsuariIncorrecteException {
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

    public boolean afegirItem(Map<String, String> valorsAtributs) throws Exception {
        return controladorDomini.afegirItem(valorsAtributs);
    }

    public boolean esborrarItem(String id) throws NoExisteixElementException {
        return controladorDomini.esborrarItem(id);
    }

    public Map<String, String> obtenirItem(String id) throws NoExisteixElementException {
        return controladorDomini.obtenirItem(id);
    }

    public boolean editarItem(String id, Map<String, String> valorsAtributs) {
        return controladorDomini.editarItem(id, valorsAtributs);
    }

    public void carregarConjuntItems(String rutaAbsoluta) throws IOException, AccesAEstatIncorrecteException {
        controladorDomini.carregarConjuntItems(rutaAbsoluta);
    }

    public void esborrarTotsElsItems() {
        controladorDomini.esborrarTotsElsItems();
    }

    public void editarTipusItem(Map<String, String> relacioNomsTipusAtributs) {
        controladorDomini.editarTipusItem(relacioNomsTipusAtributs);
    }

    public ArrayList<String> obtenirRecomanacioCollaborative(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioCollaborative(nomAtributs, filtreInclusiu);
    }

    public ArrayList<String> obtenirRecomanacioContentBased(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioContentBased(nomAtributs, filtreInclusiu);
    }

    public ArrayList<String> obtenirRecomanacioHibrida(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioHibrida(nomAtributs, filtreInclusiu);
    }

    public double avaluarRecomanacio() {
        return controladorDomini.avaluarRecomanacio();
    }

    public void esborrarTotesLesValoracions() {
        controladorDomini.esborrarTotesLesValoracions();
    }

    public ArrayList<ArrayList<String>> obtenirValoracions() {
        if (!controladorDomini.existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorDomini.obtenirValoracions();
    }

    public void deseleccionarTipusItem() throws IOException {
        controladorDomini.deseleccionarTipusItem();
    }

    public ArrayList<ArrayList<String>> obteUsuaris() {
        return controladorDomini.obteUsuaris();
    }

    public ArrayList<String> importarUsuaris(String absolutePath) throws Exception{
        return controladorDomini.importarUsuaris(absolutePath);
    }

    public void canviaContrasenyaUsuari(String id, char[] novaContrasenya) throws NoExisteixElementException {
        controladorDomini.canviaContrasenyaUsuari(id,novaContrasenya);
    }

    public void canviaNomUsuari(String id, String nouNom) throws NoExisteixElementException {
        controladorDomini.canviaNomUsuari(id,nouNom);
    }

    public void exportaValoracions(String absolutePath) throws IOException {
        controladorDomini.exportaValoracions(absolutePath);
    }
}
