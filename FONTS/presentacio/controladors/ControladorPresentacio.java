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

    private static ControladorPresentacio instancia;
    private static ControladorDomini controladorDomini;

    private static ControladorMenuItems controladorMenuItems;
    private static ControladorMenuPrincipal controladorMenuPrincipal;
    private static ControladorMenuRecomanacions controladorMenuRecomanacions;
    private static ControladorMenuTipusItem controladorMenuTipusItem;
    private static ControladorMenuUsuaris controladorMenuUsuaris;
    private static ControladorMenuValoracions controladorMenuValoracions;

    /**
     * Constructor per defecte del Controlador de la capa de presentacio
     */
    private ControladorPresentacio() {
    }

    /**
     * Constructor del controlador
     * @return instància única de ControladorPresentacio seguint el patró Singleton
     * @throws Exception si hi ha hagut algun error carregant les dades del programa
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
     * @return identificador de l'usuari que ha iniciat la sessio
     * @throws SessioNoIniciadaException si la sessio no esta iniciada
     */
    public int obtenirSessio() throws SessioNoIniciadaException {
        return controladorDomini.obtenirSessio();
    }

    /**
     * Inicia sessio amb l'usuari amb l'identificador donat si la contrasenya es correcta
     * @param id identificador d'usuari
     * @param contrasenya contrasenya de l'usuari
     * @throws NoExisteixElementException si no existeix un usuari amb l'identificador donat
     * @throws ContrasenyaIncorrectaException si la contrasenya no es correcta
     * @throws SessioIniciadaException si la sessio ja estava iniciada previament
     */
    public void iniciarSessio(int id, String contrasenya) throws NoExisteixElementException, ContrasenyaIncorrectaException, SessioIniciadaException {
        controladorDomini.iniciarSessio(id, contrasenya);
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
     * @return identificador que s'ha assignat a l'usuari
     * @throws Exception si hi ha hagut algun problema afegint l'usuari
     */
    public int afegirUsuari(String nom, String contrasenya) throws Exception {
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
     * Tanca la sessio
     * @throws SessioNoIniciadaException si la sessio no esta iniciada
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
     * @param nomAValorAtribut <code>Map&lt;String, Pair&lt;String, String&gt;&gt;</code> que relaciona el nom de cada
     *                         atribut amb ua parella formada pel tipus del valor de l'atribut i la distancia
     * @throws IOException si hi ha un problema escrivint el fitxer del tipus d'item
     * @throws NomInternIncorrecteException si el fitxer del tipus d'item no s'ha creat amb el format correcte
     * @throws JaExisteixElementException si ja existeix un tipus d'item amb aquest nom
     * @throws DistanciaNoCompatibleAmbValorException si alguna parella de distancia i valor de l'atribut no es
     * compatible
     */
    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IllegalArgumentException, IOException, NomInternIncorrecteException, JaExisteixElementException, DistanciaNoCompatibleAmbValorException {
        controladorDomini.crearTipusItem(nom, nomAValorAtribut);
    }

    /**
     * Carrega tipus d'item a partir d'un fitxer
     * @param nom <code>String</code> nom del tipus d'item
     * @param rutaAbsoluta <code>String</code> ruta absoluta del fitxer
     * @throws IOException si el fitxer no s'ha pogut llegir
     * @throws JaExisteixElementException si ja existeix un tipus d'item amb el nom donat
     * @throws FormatIncorrecteException el fitxer no te el format correcte
     */
    public void carregarTipusItem(String nom, String rutaAbsoluta) throws IOException, JaExisteixElementException, FormatIncorrecteException {
        controladorDomini.carregarTipusItem(nom, rutaAbsoluta);
    }

    /**
     * Consultora de si la sessió està iniciada o no
     * @return retorna true si la sessió està iniciada, altrament retorna false
     */
    public boolean sessioIniciada() {
        return controladorDomini.sessioIniciada();
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
     * @return retorna el nom del tipus d'item seleccionat o null si no n'hi ha cap de seleccionat
     */
    public String obtenirNomTipusItemSeleccionat() {
        return controladorDomini.obtenirNomTipusItemSeleccionat();
    }

    /**
     * Esborra totes les dades relacionades amb el tipus d'item seleccionat
     * @throws IOException si no s'han pogut esborrar els fitxers
     */
    public void esborrarTipusItemSeleccionat() throws IOException {
        controladorDomini.esborrarTipusItemSeleccionat();
    }

    /**
     * Selecciona el tipus item carregat amb el nom donat
     * @param nomTipusItem <code> String </code> el nom del tipus d'item
     * @throws IOException si no s'han pogut llegir els fitxers del tipus d'item
     * @throws AccesAEstatIncorrecteException si hi ha hagut algun error carregant els fitxers del tipus d'item
     * @throws NoExisteixElementException si no existeix cap tipus d'item amb el nom donat
     * @throws UsuariIncorrecteException si hi ha hagut algun error en els fitxers del tipus d'item
     */
    public void seleccionarTipusItem(String nomTipusItem) throws NoExisteixElementException, IOException, AccesAEstatIncorrecteException, UsuariIncorrecteException {
        controladorDomini.seleccionarTipusItem(nomTipusItem);
    }

    /**
     * Obte la llista d'items del tipus d'item seleccionat
     * @return <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> conjunt de items
     */
    public ArrayList<ArrayList<String>> obtenirItems() {
        return controladorDomini.obtenirItems();
    }

    /**
     * @return llista amb els noms dels atributs de l'item seleccionat
     */
    public ArrayList<String> obtenirNomAtributsTipusItemSeleccionat() {
        return controladorDomini.obtenirNomsAtributsTipusItemSeleccionat();
    }

    /**
     * @return cert si hi ha un tipus d'item selecccionat i, altrament, retorna fals
     */
    public boolean existeixTipusItemSeleccionat() {
        return controladorDomini.existeixTipusItemSeleccionat();
    }

    /**
     * @param valorsAtributs <code>Map&lt;String, String&gt;</code> que relaciona el nom dels atributs de l'item del
     *                       tipus d'item seleccionat amb els seus valors guardats en una String
     * @return cert si s'ha pogut afegir un item amb els parametres donats
     * @throws FormatIncorrecteException si el format dels atributs no es correcte
     * @throws InvocationTargetException si no s'han pogut obtenir els valors dels atributs dels parametres donats
     * @throws NoSuchMethodException si no s'han pogut obtenir els valors dels atributs dels parametres donats
     * @throws InstantiationException si no s'han pogut obtenir els valors dels atributs dels parametres donats
     * @throws IllegalAccessException si no s'han pogut obtenir els valors dels atributs dels parametres donats
     */
    public String afegirItem(Map<String, String> valorsAtributs) throws FormatIncorrecteException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return String.valueOf(controladorDomini.afegirItem(valorsAtributs));
    }

    /**
     * @param id identificador de l'item que es vol esborrar
     * @return cert si s'ha pogut esborrar l'item amb l'identificador donat, altrament retorna fals
     * @throws NoExisteixElementException si no existeix un item amb l'identificador donat
     */
    public boolean esborrarItem(String id) throws NoExisteixElementException {
        return controladorDomini.esborrarItem(id);
    }

    /**
     * @param id identificador de l'item que es vol obtenir
     * @return <code>Map&lt;String, String&gt;</code> amb el contingut de l'item amb l'identificador donat
     * @throws IllegalArgumentException si l'identificador no es valid
     * @throws NoExisteixElementException si no existeix un item amb l'identificador donat
     */
    public Map<String, String> obtenirItem(String id) throws NoExisteixElementException {
        return controladorDomini.obtenirItem(id);
    }

    /**
     * @param id identificador de l'item que es vol editar
     * @param valorsAtributs mapa que relaciona el nom dels atributs del l'item amb el nou valor en forma de String
     * @throws NoExisteixElementException si no existeix un item amb l'identificador donat
     * @throws FormatIncorrecteException si el format dels atributs no es correcte
     * @throws InvocationTargetException si no s'han pogut obtenir els valors dels atributs dels parametres donats
     * @throws NoSuchMethodException si no s'han pogut obtenir els valors dels atributs dels parametres donats
     * @throws InstantiationException si no s'han pogut obtenir els valors dels atributs dels parametres donats
     * @throws IllegalAccessException si no s'han pogut obtenir els valors dels atributs dels parametres donats
     */
    public void editarItem(String id, Map<String, String> valorsAtributs) throws NoExisteixElementException,
            FormatIncorrecteException, InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {
        controladorDomini.editarItem(id, valorsAtributs);
    }


    /**
     * @param deduirTipusItem si es cert, es deduira el tipus item del conjunt i se li assignara el nom donat, altrament
     *                        s'utilitzara el tipus d'item seleccionat
     * @param nomTipusItem nom del tipus d'item que es creara si deduirTipusItem es cert
     * @param rutaAbsoluta String que conte la ruta absoluta al fitxer
     * @throws IOException si hi ha un error llegint el fitxer o creant els fitxers
     * @throws AccesAEstatIncorrecteException si hi ha algun error llegint els items del fitxer
     * @throws DistanciaNoCompatibleAmbValorException si es crea un tipus d'atribut amb valor i distancia incompatibles
     * @throws NoExisteixElementException si hi ha algun error creant els fitxers del tipus d'item
     * @throws JaExisteixElementException si ja existeix un tipus d'item amb el nom donat, en el cas que deduirTipusItem
     * sigui cert
     * @throws FormatIncorrecteException si el format del fitxer no es correcte i no es poden llegir items del fitxer
     */
    public void carregarConjuntItems(boolean deduirTipusItem, String nomTipusItem, String rutaAbsoluta) throws IOException,
            AccesAEstatIncorrecteException, DistanciaNoCompatibleAmbValorException, NoExisteixElementException,
            JaExisteixElementException, FormatIncorrecteException {
        if (deduirTipusItem) {
            controladorDomini.carregarConjuntItems(nomTipusItem, rutaAbsoluta);
        } else {
            controladorDomini.carregarConjuntItems(rutaAbsoluta);
        }
    }

    /**
     * Esborra tots els items del tipus d'item seleccionat
     */
    public void esborrarTotsElsItems() {
        controladorDomini.esborrarTotsElsItems();
    }

    /**
     * Canvia el nom del tipus item seleccionat.
     * @param nouNom nou nom pel tipus item.
     * @throws IOException si hi ha un problema editant els fitxers del tipus d'item
     * @throws FormatIncorrecteException el format no és correcte
     * @throws JaExisteixElementException l'element ja existeix
     */
    public void editarTipusItem(String nouNom) throws IOException, FormatIncorrecteException, JaExisteixElementException {
        controladorDomini.editarTipusItem(nouNom);
    }

    /**
     * Obté una recomanació amb el mètode Recomanador Collaborative per l'usuari que està actiu.
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu cert si el filtre és de tipus inclusiu, fals si és exclusiu.
     * @return El conjunt d'identificadors dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException si hi ha un error a l'hora de crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioCollaborative(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioCollaborative(nomAtributs, filtreInclusiu);
    }

    /**
     * Obté una recomanació amb el mètode Recomanador ContentBased per l'usuari que te la sessio iniciada
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu cert si el filtre és de tipus inclusiu, fals si és exclusiu.
     * @return El conjunt d'identificadors dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException si hi ha un error a l'hora de crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioContentBased(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioContentBased(nomAtributs, filtreInclusiu);
    }

    /**
     * Obté una recomanació amb el mètode Recomanador Híbrid per l'usuari que te la sessio iniciada
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu cert si el filtre és de tipus inclusiu, fals si és exclusiu.
     * @return El conjunt d'identificadors dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException si hi ha un error a l'hora de crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioHibrida(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws NoExisteixElementException, SessioNoIniciadaException {
        return controladorDomini.obtenirRecomanacioHibrida(nomAtributs, filtreInclusiu);
    }

    /**
     * @param valoracions valoracions fetes per l'usuari dels items recomanats en l'ultima recomanacio
     * @return valoració de la recomanacio amb el valor del NDCG de la recomanacio
     * @throws NoExisteixElementException No existeix l'element
     * @throws SessioNoIniciadaException la sessio no està iniciada
     * @throws UsuariIncorrecteException l'usuari és incorrecte
     */
    public double avaluarRecomanacio(ArrayList<Pair<Integer,Double>> valoracions) throws NoExisteixElementException, SessioNoIniciadaException, UsuariIncorrecteException {
        return controladorDomini.avaluarRecomanacio(valoracions);
    }

    /**
     * Esborra totes les valoracions
     */
    public void esborrarTotesLesValoracions() {
        controladorDomini.esborrarTotesLesValoracions();
    }

    /**
     * @return ArrayList amb el conjunt de valoracions del tipus d'item seleccionat, retorna una llista buida si no hi
     * ha cap tipus d'item seleccionat
     */
    public ArrayList<ArrayList<String>> obtenirValoracions() {
        if (!controladorDomini.existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorDomini.obtenirValoracions();
    }

    /**
     * Desselecciona el tipus d'item
     * @throws IOException si no s'ha pogut desseleccionar el tipus d'item
     */
    public void desseleccionarTipusItem() throws IOException {
        controladorDomini.desseleccionarTipusItem();
    }

    /**
     * @return retorna una matriu amb el conjunt d'usuaris. Cada fila representa un usuari.
     */
    public ArrayList<ArrayList<String>> obtenirUsuaris() {
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
    public void canviarContrasenyaUsuari(String id, char[] novaContrasenya) throws NoExisteixElementException {
        controladorDomini.canviaContrasenyaUsuari(id, novaContrasenya);
    }

    /**
     * Modificadora del nom del usuari amb l'identificador donat
     * @param id identificadora del usuari
     * @param nouNom nou nom de l'usuari
     * @throws NoExisteixElementException a
     */
    public void canviarNomUsuari(String id, String nouNom) throws NoExisteixElementException {
        controladorDomini.canviaNomUsuari(id,nouNom);
    }

    /**
     * Exporta les valoracions a la localització donada
     * @param absolutePath path on volem guardar l'arxiu
     * @throws IOException Error en l'entrada/sortida
     */
    public void exportarValoracions(String absolutePath) throws IOException {
        controladorDomini.exportaValoracions(absolutePath);
    }

    /**
     * Obre el manual d'usuari.
     * @throws IOException si hi ha algun error obrint el manual d'usuari
     */
    public void obrirManual() throws IOException{
        controladorDomini.obrirManual();
    }

    /**
     * Guarda tots els fitxers relacionats amb el tipus d'item seleccionat
     * @throws IOException si hi ha algun error llegint o escrivint fitxers
     */
    public void guardarPrograma() throws IOException {
        controladorDomini.guardarPrograma();
    }

    /**
     * @return Llista amb els identificadors de tots els items del tipus d'item seleccionat
     */
    public ArrayList<String> obtenirIdsItems() {
        return controladorDomini.obtenirIdsItems();
    }

    /**
     * Exporta els items a la ruta donada
     * @param rutaAbsoluta ruta on volem guardar l'arxiu
     * @throws IOException si hi ha hagut un error en la lectura o escriptura de fitxers
     */
    public void exportarItems(String rutaAbsoluta) throws IOException {
        controladorDomini.exportarItems(rutaAbsoluta);
    }

    /**
     * @param nomAtribut nom de l'atribut
     * @return nom en format intern del valor atribut de l'atribut amb el nom donat del tipus d'ítem seleccionat
     */
    public String obtenirValorAtributTipusItemSeleccionat(String nomAtribut) {
        return controladorDomini.obtenirValorAtributTipusItemSeleccionat(nomAtribut);
    }

    /**
     * @param nomValorAtributIntern String que conte el nom d'un valor atribut en un format intern
     * @return String que conte el nom extern del valor atribut donat o una String buida si no el reconeix
     */
    public String obtenirNomValorAtributExtern(String nomValorAtributIntern) {
        switch (nomValorAtributIntern) {
            case "ValorBoolea":
                return "Booleà";
            case "ValorCategoric":
                return "Categòric";
            case "ValorNumeric":
                return "Numèric";
            case "ValorTextual":
                return "Textual";
            case "ValorConjuntBoolea":
                return "Conjunt booleà";
            case "ValorConjuntCategoric":
                return "Conjunt categòric";
            case "ValorConjuntNumeric":
                return "Conjunt numèric";
            case "ValorConjuntTextual":
                return "Conjunt textual";
            default:
                return "";
        }
    }
}
