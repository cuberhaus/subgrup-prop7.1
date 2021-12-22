package presentacio.controladors;

import presentacio.EncarregatActualitzarVistes;
import utilitats.Pair;
import domini.controladors.ControladorDomini;
import excepcions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Classe que representa el controlador de presentació
 * @author maria.prat i pol.casacuberta
 */
public class ControladorPresentacio {

    private static ControladorDomini controladorDomini;
    private static ControladorPresentacio instancia;

    private static ControladorMenuItems controladorMenuItems;
    private static ControladorMenuPrincipal controladorMenuPrincipal;
    private static ControladorMenuRecomanacions controladorMenuRecomanacions;
    private static ControladorMenuTipusItem controladorMenuTipusItem;
    private static ControladorMenuUsuaris controladorMenuUsuaris;
    private static ControladorMenuValoracions controladorMenuValoracions;

    /**
     * Constructora per defecte de ControladorPresentacio
     */
    private ControladorPresentacio() {
    }

    /**
     * Constructora de ControladorPresentacio
     * Crea una instància única de ControladorPresentacio seguint el patró Singleton
     *
     * @return <code> ControladorPresentacio </code>
     * @throws IOException si hi ha hagut algun error en l'entrada/sortida
     * @throws NomInternIncorrecteException si algun valor demanat no existeix
     * @throws DistanciaNoCompatibleAmbValorException si el valor de l'atribut no es compatible amb la distancia seleccionada
     */
    public static ControladorPresentacio obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ControladorPresentacio();
            controladorDomini = ControladorDomini.obtenirInstancia();
            controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
            controladorMenuUsuaris = ControladorMenuUsuaris.obtenirInstancia();
            controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        }
        return instancia;
    }

    /**
     * Retorna un mapa que relaciona el nom de cada atribut del tipus d'item seleccionat amb una parella formada pel
     * valor de l'atribut i la seva distancia
     * @return <code>Map&lt;String, Pair&lt;String, String&gt;&gt;</code> amb els valors
     * @throws DistanciaNoCompatibleAmbValorException si alguna distancia no es compatible amb el valor de l'atribut
     */
    public Map<String, Pair<String, String>> obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat() throws DistanciaNoCompatibleAmbValorException {
        return controladorDomini.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat();
    }

    /**
     * Retorna el nom dels tipus d'items carregats
     * @return <code>ArrayList&lt;String&gt;</code> llista dels noms
     */
    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorDomini.obtenirNomsTipusItemsCarregats();
    }

    /**
     * Obte l'identificador de l'usuari que ha iniciat sessio
     * @return identificador de l'usuari
     * @throws SessioNoIniciadaException si la sessio no està iniciada
     */
    public int obtenirSessio() throws SessioNoIniciadaException {
        return controladorDomini.obtenirSessio();
    }

    /**
     * Inicia sessio amb l'identificador donat si la contrasenya és correcte
     * @param idSessio identificador d'usuari
     * @param contrasenya contrasenya
     * @throws NoExisteixElementException si no existeix un usuari amb l'identificador donat
     * @throws ContrasenyaIncorrectaException si la contrasenya no és correcta
     * @throws SessioIniciadaException si la sessió ja està iniciada
     */
    public void iniciarSessio(int idSessio, String contrasenya) throws NoExisteixElementException, ContrasenyaIncorrectaException, SessioIniciadaException {
        controladorDomini.iniciarSessio(idSessio, contrasenya);
    }

    /**
     * Retorna cert si existeix l'usuari amb l'identificador donat
     * @param id identificador d'usuari
     * @return cert si l'usuari existeix, altrament, retorna fals
     * @throws NoExisteixElementException si no existeix un usuari amb l'identificador donat
     */
    public boolean existeixUsuari(int id) throws NoExisteixElementException {
        return controladorDomini.existeixUsuari(id);
    }

    /**
     * Afegeix un usuari al conjunt d'usuaris
     * @param nom nom de l'usuari
     * @param contrasenya contrasenya de l'usuari
     * @return l'identificador que s'ha assignat a l'usuari
     * @throws NoExisteixElementException si no existeix un usuari amb l'identificador que se li assigna
     * @throws JaExisteixElementException L'element ja existeix
     */
    public int afegirUsuari(String nom, String contrasenya) throws NoExisteixElementException, JaExisteixElementException {
        return controladorDomini.afegirUsuari(nom, contrasenya);
    }

    /**
     * Esborra un usuari del conjunt
     * @param id identificador de l'usuari
     * @throws NoExisteixElementException si no existeix un usuari amb l'identificador donat
     */
    public void esborrarUsuari(int id) throws NoExisteixElementException {
        controladorDomini.esborrarUsuari(id);
    }

    /**
     * Tanca la sessió
     * @throws SessioNoIniciadaException si la sessió no està iniciada
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
     * @throws AccesAEstatIncorrecteException a
     * @throws UsuariIncorrecteException L'usuari no és correcte
     */
    public void carregarConjuntValoracions(String pathAbsolut) throws NoExisteixElementException, IOException, AccesAEstatIncorrecteException, UsuariIncorrecteException {
        controladorDomini.carregaConjuntValoracions(pathAbsolut);
        EncarregatActualitzarVistes.notificarObservadors();
    }

    /**
     * Obtenir el conjunt d'items actuals
     * @return <code>ArraList&lt;String&gt;</code> conjunt d'items del tipus item seleccionar
     */
    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorDomini.obtenirLlistaConjunts();
    }

    /**
     * Crea el tipus d'item amb el nom i els seus atributs
     * @param nom <code>String</code> nom del tipus d'item
     * @param nomAValorAtribut <code>Map&lt;String, Pair&lt;String, String&gt;&gt;</code> que conté els atributs amb el tipus
     * @throws IllegalArgumentException si ja existeix el tipus d'item
     * @throws IOException si no existeix el fitxer i/o no es pot obrir
     * @throws NomInternIncorrecteException el fitxer amb el nom del tipus d'item no existeix
     * @throws JaExisteixElementException ja existeix l'element
     * @throws DistanciaNoCompatibleAmbValorException la distancia no és compatible amb el valor
     */
    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IllegalArgumentException, IOException, NomInternIncorrecteException, JaExisteixElementException, DistanciaNoCompatibleAmbValorException {
        controladorDomini.crearTipusItem(nom, nomAValorAtribut);
    }

    /**
     * Carrega tipus d'item a partir d'un fitxer
     * @param nom <code>String</code> nom del tipus d'item
     * @param rutaAbsoluta <code>String</code> ubicacio del fitxer
     * @throws IOException si el fitxer no existeix o no te format correcte
     * @throws JaExisteixElementException l'element ja existeix al conjunt
     * @throws FormatIncorrecteException el fitxer no te el format correcte
     */
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

    /**
     * Obté el nom del tipus d'ítem seleccionat
     * @return null si no hi ha cap seleccionat
     */
    public String obtenirNomTipusItemSeleccionat() {
        return controladorDomini.obtenirNomTipusItemSeleccionat();
    }

    /**
     * Esborra el tipus d'item seleccionat
     * @throws IOException S'ha produit un error en l'entrada/sortida
     */
    public void esborrarTipusItemSeleccionat() throws IOException {
        controladorDomini.esborrarTipusItemSeleccionat();
    }

    /**
     * Selecciona el tipus item
     * @param nomTipusItem <code>String</code> el nom del tipus d'item
     * @throws IOException Error en l'entrada/sortida
     * @throws AccesAEstatIncorrecteException S'accedeix a estat incorrecte
     * @throws NoExisteixElementException No existeix l'element
     * @throws UsuariIncorrecteException L'usuari no és correcte
     */
    public void seleccionarTipusItem(String nomTipusItem) throws NoExisteixElementException, IOException, AccesAEstatIncorrecteException, UsuariIncorrecteException {
        controladorDomini.seleccionarTipusItem(nomTipusItem);
    }

    /**
     * Obte la llista d'items
     * @return <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> conjunt de items
     */
    public ArrayList<ArrayList<String>> obtenirItems() {
        return controladorDomini.obtenirItems();
    }

    /**
     * Obte el nom dels atributs de l'item seleccionat
     * @return <code>ArrayList&lt;String&gt;</code> conjunt del nom dels atributs
     */
    public ArrayList<String> obtenirNomAtributsTipusItemSeleccionat() {
        return controladorDomini.obtenirNomsAtributsTipusItemSeleccionat();
    }

    /**
     * Retorna la existencia del tipus item sleccionat
     * @return <code>boolean</code> si existeix o no
     */
    public boolean existeixTipusItemSeleccionat() {
        return controladorDomini.existeixTipusItemSeleccionat();
    }

    /**
     * Afegeix un item al conjunt
     * @param valorsAtributs <code>Map&lt;String, String&gt;</code> els atributs i el seu valor
     * @return <code>boolean</code> true si 'sha afegit
     *
     */
    // TODO: arreglar javadoc
    public String afegirItem(Map<String, String> valorsAtributs) throws FormatIncorrecteException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoExisteixElementException {
        return String.valueOf(controladorDomini.afegirItem(valorsAtributs));
    }

    /**
     * Esborra l'item amb l'id dessitjat
     * @param id <code>String</code> l'id de l'item a eesborrar
     * @return <code>boolean</code> si s'ha pogut esborrar o no
     * @throws NoExisteixElementException No existeix l'element
     */
    public boolean esborrarItem(String id) throws NoExisteixElementException {
        return controladorDomini.esborrarItem(id);
    }

    /**
     * Obtenir el item amb l'id seleccionat
     * @param id <code>String</code> l'id de l'item a obtenir
     * @return <code>Map&lt;String, String&gt;</code> amb el contingut de l'item
     * @throws IllegalArgumentException si l'identificador no es valid
     * @throws NoExisteixElementException No existeix l'element
     */
    public Map<String, String> obtenirItem(String id) throws NoExisteixElementException {
        return controladorDomini.obtenirItem(id);
    }

    public void editarItem(String id, Map<String, String> valorsAtributs) throws NoExisteixElementException, FormatIncorrecteException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        controladorDomini.editarItem(id, valorsAtributs);
    }

    /**
     * Carrega un conjunt d'items a partir d'un fitxer
     *
     * @param deduirTipusItem
     * @param nomTipusItem
     * @param rutaAbsoluta <code>String</code> ruta del fitxer
     * @throws IOException si no s'ha pogut obrir el fitxer
     * @throws AccesAEstatIncorrecteException Accedeix a estat incorrecte
     */
    // TODO: arreglar javadoc
    public void carregarConjuntItems(boolean deduirTipusItem, String nomTipusItem, String rutaAbsoluta) throws IOException, AccesAEstatIncorrecteException, DistanciaNoCompatibleAmbValorException, NoExisteixElementException, JaExisteixElementException, FormatIncorrecteException {
        if (deduirTipusItem) {
            controladorDomini.carregarConjuntItems(nomTipusItem, rutaAbsoluta);
        } else {
            controladorDomini.carregarConjuntItems(rutaAbsoluta);
        }
    }

    /**
     * Esborra tots els items del tipus d'ítem seleccionat
     */
    public void esborrarTotsElsItems() {
        controladorDomini.esborrarTotsElsItems();
    }

    /**
     * Canvia el nom del tipus item seleccionat.
     * @param nouNom nou nom pel tipus item.
     * @throws IOException Problema canviant el nom del tipus item a la persistència.
     */
    public void editarTipusItem(String nouNom) throws IOException, FormatIncorrecteException, JaExisteixElementException {
        controladorDomini.editarTipusItem(nouNom);
    }

    /**
     * Obté una recomanació amb el mètode Recomanador Collaborative per a l'usuari que està actiu.
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu true si el filtre és de tipus inclusiu, false si és exclusiu.
     * @return El conjunt d'id's dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException hi ha un problema per crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioCollaborative(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioCollaborative(nomAtributs, filtreInclusiu);
    }

    /**
     * Obté una recomanació amb el mètode Recomanador ContentBased per al usuari que esta actiu.
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu true si el filtre és de tipus inclusiu, false si és exclusiu.
     * @return El conjunt de id's dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException hi ha un problema per crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioContentBased(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioContentBased(nomAtributs, filtreInclusiu);
    }

    /**
     * Obté una recomanació amb el mètode Recomanador Híbrid per a l'usuari que està actiu.
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu true si el filtre és de tipus inclusiu, false si és exclusiu.
     * @return El conjunt d'id's dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException hi ha un problema per crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioHibrida(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioHibrida(nomAtributs, filtreInclusiu);
    }

    /**
     * Obté l'avaluació de la recomanació
     * @return avaluació de la recomanació
     */
    public double avaluarRecomanacio(ArrayList<Pair<Integer,Double>> valoracions) {
        return controladorDomini.avaluarRecomanacio(valoracions);
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

    /**
     * Desselecciona el tipus d'ítem
     * @throws IOException no s'ha pogut desseleccionar l'ítem
     */
    public void deseleccionarTipusItem() throws IOException {
        controladorDomini.desseleccionarTipusItem();
    }

    /**
     * Obté el conjunt d'usuaris
     * @return retorna un ArrayList amb el conjunt d'usuaris
     */
    public ArrayList<ArrayList<String>> obteUsuaris() {
        return controladorDomini.obtenirUsuaris();
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
     * @throws NoExisteixElementException a
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
        controladorDomini.obrirManual();
    }

    public void guardarPrograma() throws IOException {
        controladorDomini.guardarPrograma();
    }

    public ArrayList<String> obtenirIdsItems() {
        return controladorDomini.obtenirIdsItems();
    }

}
