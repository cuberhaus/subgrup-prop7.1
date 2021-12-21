package presentacio.controladors;

import utilitats.Pair;
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

    /**
     * Constructora per defecte de ControladorPresentacio
     */
    private ControladorPresentacio() {
    }

    /**
     * Constructora de ControladorPresentacio
     * Crea una instància única de ControladorPresentacio
     *
     * @return <code> ControladorPresentacio </code>
     * @throws IOException Hi ha hagut algun error en l'entrada/sortida
     * @throws NomInternIncorrecteException Algun valor demanat no existeix
     */
    public static ControladorPresentacio obtenirInstancia() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        if (instancia == null) {
            instancia = new ControladorPresentacio();
            controladorDomini = ControladorDomini.obtenirInstancia();
            controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
            controladorMenuUsuaris = ControladorMenuUsuaris.obtenirInstancia();
            controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        }
        return instancia;
    }

    public Map<String, Pair<String, String>> obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat() throws DistanciaNoCompatibleAmbValorException {
        return controladorDomini.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat();
    }

    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorDomini.obtenirNomsTipusItemsCarregats();
    }

    /**
     * Obté l'identificador de l'usuari que ha iniciat sessió
     * @return identificador de l'usuari
     * @throws SessioNoIniciadaException La sessió no està iniciada
     */
    public int obtenirSessio() throws SessioNoIniciadaException {
        return controladorDomini.obtenirSessio();
    }

    /**
     * Inicia sessió amb l'identificador donat si la contrasenya és correcte
     * @param idSessio identificador d'usuari
     * @param contrasenya contrasenya
     * @throws NoExisteixElementException no existeix l'identificador d'usuari
     * @throws ContrasenyaIncorrectaException La contrasenya no és correcte
     * @throws SessioIniciadaException La sessió ja està iniciada
     */
    public void iniciarSessio(int idSessio, String contrasenya) throws NoExisteixElementException, ContrasenyaIncorrectaException, SessioIniciadaException {
        controladorDomini.iniciarSessio(idSessio, contrasenya);
    }

    /**
     * Retorna true si existeix l'usuari amb l'identificador donat
     * @param id identificador d'usuari
     * @return true si l'usuari existeix, altrament, retorna false
     * @throws NoExisteixElementException No existeix element
     */
    public boolean existeixUsuari(int id) throws NoExisteixElementException {
        return controladorDomini.existeixUsuari(id);
    }

    /**
     * Afegeix un usuari al conjunt d'usuaris
     * @param nom nom de l'usuari
     * @param contrasenya contrasenya de l'usuari
     * @return retorna l'identificador que s'ha assignat a l'usuari
     * @throws NoExisteixElementException No existeix element
     * @throws JaExisteixElementException L'element ja existeix
     */
    public int afegirUsuari(String nom, String contrasenya) throws NoExisteixElementException, JaExisteixElementException {
        return controladorDomini.afegirUsuari(nom,contrasenya);
    }

    /**
     * Esborra un usuari del conjunt
     * @param id identificador de l'usuari
     * @throws NoExisteixElementException L'element no existeix
     */
    public void esborrarUsuari(int id) throws NoExisteixElementException {
        controladorDomini.esborrarUsuari(id);
    }

    /**
     * Tanca la sessió
     * @throws SessioNoIniciadaException La sessió no està iniciada
     */
    public void tancarSessio() throws SessioNoIniciadaException {
        controladorDomini.tancarSessio();
    }

    /**
     * Afegeix una valoració
     * @param usuariId identificador d'usuari
     * @param itemId identificador d'ítem
     * @param valor valor de la valoració
     * @throws Exception no s'ha pogut crear la valoració
     */
    public void afegirValoracio(String usuariId, String itemId, String valor) throws Exception {
        controladorDomini.afegirValoracio(usuariId,itemId,valor);
    }

    /**
     * Consultora de si la valoració amb els identificadors donats existeix
     * @param usuariId identificador d'usuari
     * @param itemId identificador d'ítem
     * @return retorna true si la valoració existeix, altrament retorna false
     * @throws NoExisteixElementException No existeix l'element
     */
    public boolean existeixValoracio(String usuariId, String itemId) throws NoExisteixElementException {
        return controladorDomini.existeixValoracio(usuariId,itemId);
    }

    /**
     * Esborra una valoració amb els identificadors donats
     * @param usuariId identificador d'usuari
     * @param itemId identificador d'ítem
     * @throws Exception no s'ha pogut crear la valoració
     */
    public void esborrarValoracio(String usuariId, String itemId) throws Exception {
        controladorDomini.esborraValoracio(usuariId,itemId);
    }

    /**
     * Permet canviar el valor d'una valoració donats els identificadors i el nou valor
     * @param usuariId identificador d'usuari
     * @param itemId identificador d'ítem
     * @param valor valor de la valoració
     * @throws NoExisteixElementException no existeix l'element
     * @throws UsuariIncorrecteException l'usuari no és correcte
     */
    public void editarValoracio(String usuariId, String itemId, String valor) throws NoExisteixElementException, UsuariIncorrecteException {
        controladorDomini.editarValoracio(usuariId,itemId,valor);
    }

    /**
     * Carrega un conjunt de valoracions a memòria des d'un arxiu extern
     * @param pathAbsolut path on es troba l'arxiu
     * @throws NoExisteixElementException no existeix l'element
     * @throws IOException error en l'entrada/sortida
     * @throws AccesAEstatIncorrecteException
     * @throws UsuariIncorrecteException L'usuari no és correcte
     */
    public void carregarConjuntValoracions(String pathAbsolut) throws NoExisteixElementException, IOException, AccesAEstatIncorrecteException, UsuariIncorrecteException {
        controladorDomini.carregaConjuntValoracions(pathAbsolut);
    }

    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorDomini.obtenirLlistaConjunts();
    }

    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IllegalArgumentException, IOException, NomInternIncorrecteException, JaExisteixElementException, DistanciaNoCompatibleAmbValorException {
        controladorDomini.crearTipusItem(nom, nomAValorAtribut);
    }

    public void carregarTipusItem(String nom, String rutaAbsoluta) throws IOException, JaExisteixElementException, FormatIncorrecteException {
        controladorDomini.carregarTipusItem(nom, rutaAbsoluta);
    }

    /**
     * Consultora de si la sessió està iniciada o no
     * @return retorna true si la sessió està iniciada, altrament retorna false
     */
    public boolean esSessioIniciada() {
        return controladorDomini.esSessioIniciada();
    }

    /**
     * Exporta el conjunt d'usuaris a un arxiu extern en la localització donada
     * @param absolutePath path de la carpeta on volem crear l'arxiu
     * @throws IOException Error en l'entrada/sortida
     */
    public void exportarConjuntUsuaris(String absolutePath) throws IOException {
        controladorDomini.exportarConjuntDadesUsuari(absolutePath);
    }

    /**
     * Esborra el conjunt d'usuaris
     */
    public void esborrarConjuntUsuaris() {
        controladorDomini.esborraConjuntUsuaris();
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorDomini.obtenirNomTipusItemSeleccionat();
    }

    public void esborrarTipusItemSeleccionat() throws IOException {
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

    public boolean editarItem(String id, Map<String, String> valorsAtributs) throws NoExisteixElementException {
        return controladorDomini.editarItem(id, valorsAtributs);
    }

    public void carregarConjuntItems(String rutaAbsoluta) throws IOException, AccesAEstatIncorrecteException {
        controladorDomini.carregarConjuntItems(rutaAbsoluta);
    }

    public void esborrarTotsElsItems() {
        controladorDomini.esborrarTotsElsItems();
    }

    public void editarTipusItem(String nouNom) throws IOException {
        controladorDomini.editarTipusItem(nouNom);
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

    /**
     * Esborra totes les valoracions
     */
    public void esborrarTotesLesValoracions() {
        controladorDomini.esborrarTotesLesValoracions();
    }

    /**
     * Obté les valoracions del tipus d'ítem seleccionat
     * @return ArrayList amb el conjunt de valoracions
     */
    public ArrayList<ArrayList<String>> obtenirValoracions() {
        if (!controladorDomini.existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorDomini.obtenirValoracions();
    }

    public void deseleccionarTipusItem() throws IOException {
        controladorDomini.deseleccionarTipusItem();
    }

    /**
     * Obté el conjunt d'usuaris
     * @return retorna un ArrayList amb el conjunt d'usuaris
     */
    public ArrayList<ArrayList<String>> obteUsuaris() {
        return controladorDomini.obteUsuaris();
    }

    /**
     * Importa el conjunt d'usuaris des de l'arxiu en la localització donada
     * @param absolutePath path a l'arxiu amb el conjunt d'usuaris
     * @return ArrayList amb el conjunt d'usuaris
     * @throws Exception no s'ha pogut importar els usuaris
     */
    public ArrayList<String> importarUsuaris(String absolutePath) throws Exception{
        return controladorDomini.importarUsuaris(absolutePath);
    }

    /**
     * Modificadora de la contrasenya de l'usuari amb l'identificador donat
     * @param id identificador de l'usuari
     * @param novaContrasenya contrasenya nova
     * @throws NoExisteixElementException no existeix l'element
     */
    public void canviaContrasenyaUsuari(String id, char[] novaContrasenya) throws NoExisteixElementException {
        controladorDomini.canviaContrasenyaUsuari(id,novaContrasenya);
    }

    /**
     * Modificadora del nom del usuari amb l'identificador donat
     * @param id identificadora del usuari
     * @param nouNom nou nom de l'usuari
     * @throws NoExisteixElementException
     */
    public void canviaNomUsuari(String id, String nouNom) throws NoExisteixElementException {
        controladorDomini.canviaNomUsuari(id,nouNom);
    }

    /**
     * Exporta les valoracions a la localització donada
     * @param absolutePath path on volem guardar l'arxiu
     * @throws IOException Error en l'entrada/sortida
     */
    public void exportaValoracions(String absolutePath) throws IOException {
        controladorDomini.exportaValoracions(absolutePath);
    }

    /**
     * Obre el manual d'usuari.
     * @throws IOException si hi ha cap problema.
     */
    public void obreManual() throws IOException{
        controladorDomini.obreManual();
    }
}
