package domini.controladors;

import domini.classes.*;
import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.csv.TaulaCSV;
import domini.classes.recomanador.*;
import domini.classes.recomanador.filtre.Filtre;
import domini.classes.recomanador.filtre.FiltreExclusiu;
import domini.classes.recomanador.filtre.FiltreInclusiu;
import excepcions.*;
import persistencia.controladors.ControladorPersistencia;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Classe que representa el controlador de domini
 * @author edgar.moreno && pablo.vega
 */
public class ControladorDomini {
    private static ControladorDomini instancia;
    private final ControladorPersistencia controladorPersistencia;

    private final Programa estatPrograma;
    private int ultimIdUsat = 1000000;
    private int ultimIdUsatItem = 0;
    private String nomTipusItemActual = null;
    private ConjuntValoracions valoracionsTipusItemActual = null;
    private ConjuntItems itemsActuals = null;
    private Recomanador recomanador;
    private ConjuntRecomanacions recomanacions;

    /**
     * Contructor privat de la classe per defecte
     * @throws IOException si no existeix algun fitxer de la càrrega de fitxers per defecte
     * @throws NomInternIncorrecteException si hi ha algun problema amb els noms dels fitxers dels metodes interns
     */
    private ControladorDomini() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        controladorPersistencia = ControladorPersistencia.obtenirInstancia();
        estatPrograma = Programa.obtenirInstancia();
        ArrayList<String> llistaTipusItems = controladorPersistencia.obtenirNomsDeTotsElsTipusItems();
        for (String tipusItem : llistaTipusItems) {
            this.carregarTipusItem(tipusItem);
        }
    }

    /**
     * Retorna la instancia de Controlador Domini
     * @return La instancia de controlador domini
     * @throws IOException si no existeix algun fitxer de la càrrega de fitxers per defecte
     * @throws NomInternIncorrecteException si hi ha algun problema amb els noms dels fitxers dels metodes interns
     */
    public static ControladorDomini obtenirInstancia() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        if (instancia == null) {
            instancia = new ControladorDomini();
        }
        return instancia;
    }

    /**
     * Obté l'id de l'usuari que ha iniciat la sessió
     * @return retorna 0 en cas que no hi hagi sessió iniciada, altrament retorna l'id de l'usuari
     */
    public int obtenirSessio() throws Exception {
        if (this.estatPrograma.isSessioIniciada()) return 0;
        else {
            Usuari usuari = this.estatPrograma.obtenirUsuariSessioIniciada();
            Id id = usuari.obtenirId();
            return id.obtenirValor();
        }
    }

    /**
     * Inicia sessió amb l'usuari de id idSessio si la contrasenya és correcte
     * @param idSessio id de l'usuari que inicia la sessió
     * @param contrasenya contrasenya de l'usuari
     */
    public void iniciarSessio(int idSessio, String contrasenya) throws SessioIniciadaException, ContrasenyaIncorrectaException, NoExisteixElementException {
        Id idUsuariBuscat = new Id(idSessio, true);
        if (this.estatPrograma.conteUsuari(idUsuariBuscat)) {
            Usuari usuariCercat = this.estatPrograma.obtenirUsuari(idUsuariBuscat);
            Id idUsuariCercat = usuariCercat.obtenirId();

            if (!idUsuariCercat.esActiu()) {
                throw new NoExisteixElementException("L'usuari existeix pero no es actiu");
            }

            else if (usuariCercat.isContrasenya(contrasenya)) {
                this.estatPrograma.iniciarSessio(usuariCercat);
            }

            else {
                throw new ContrasenyaIncorrectaException("La contrasenya es incorrecta");
            }
        }

        else {
            throw new NoExisteixElementException("L'usuari no existeix");
        }
    }

    /**
     * Comproba si existeix un usuari al conjunt d'usuaris del programa
     * @param id <code>int</code> l'id de l'usuari
     * @return retorna si existeix l'usuari al conjunt o no
     * @throws Exception si l'usuari ja existeix
     */
    public boolean existeixUsuari(int id) throws Exception {
        Id idBo = new Id(id, true);
        return estatPrograma.conteUsuari(idBo) && estatPrograma.obtenirUsuari(idBo).isActiu();
    }


    private Id obteIdUsuariDisponible() {
        while (estatPrograma.conteUsuari(new Id(ultimIdUsat, true))) {
            ultimIdUsat++;
        }
        return new Id(ultimIdUsat, true);
    }
    private Id obteIdItemDisponible() {
        while (itemsActuals.conte(new Id(ultimIdUsatItem, true))) {
            ultimIdUsatItem++;
        }
        return new Id(ultimIdUsatItem, true);
    }
    /**
     * Afegeix un Usuari que encara no existeix.
     * @param nom nom del usuari
     * @param contrasenya contrasenya del usuari
     */
    public int afegirUsuari(String nom, String contrasenya) throws Exception {
        Id id = obteIdUsuariDisponible();
        if (estatPrograma.conteUsuari(id) && estatPrograma.obtenirUsuari(id).isActiu()) {
            throw new Exception("L'usuari ja existeix");
        }
        else {
            estatPrograma.afegirUsuari(new Usuari(id, nom, contrasenya));
            return id.obtenirValor();
        }
    }

    /**
     * Esborra usuari, falta parametre del conjunt
     * @param id id del usuari
     */
    public void esborrarUsuari(int id) throws Exception {
        Id id1 = new Id(id, true);
        if (!estatPrograma.conteUsuari(id1) || !estatPrograma.obtenirUsuari(id1).isActiu()) {
            throw new Exception("L'usuari no existeix");
        }
        else {
            estatPrograma.esborraUsuari(new Id(id, true));
        }
    }

    /**
     * Tanca la sessio de programa
     */
    public void tancarSessio() throws SessioNoIniciadaException {
        this.estatPrograma.tancarSessio();
    }

    /**
     * Afegeix la una valoració a la llista de valoracions
     * @param usuariId <code>String</code> l'id de l'usuari
     * @param itemId <code>String</code> l'id de l'item
     * @param valor <code>String</code> valor de la valoracio
     * @throws Exception si no existeix l'usuari o no existeis l'item
     */
    public void afegirValoracio(String usuariId, String itemId, String valor) throws Exception {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        valoracionsTipusItemActual.afegir(new Valoracio(Double.parseDouble(valor), us, item));
    }

    /**
     * Comproba si al conjunt existeix una valoracio d'un usuari cap a un item
     * @param usuariId <code>String</code> l'id de l'usuari
     * @param itemId <code>String</code> l'id de l'item
     * @return retorna si existeix la valoracio de l'usuari cap a un item
     * @throws Exception si no existeix l'usuari o no existeix l'item
     */
    public boolean existeixValoracio(String usuariId, String itemId) throws Exception {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        return valoracionsTipusItemActual.conte(us, item);
    }

    /**
     * S'esborra la valoracio de un usuari cap a un item
     * @param usuariId <code>String</code> l'id de l'usuari
     * @param itemId <code>String</code> l'id id de l'item
     * @throws Exception si no existeix l'usuari o no existeix l'item
     */
    public void esborraValoracio(String usuariId, String itemId) throws Exception {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        valoracionsTipusItemActual.esborrar(valoracionsTipusItemActual.obte(us, item));
    }

    /**
     * Edita la valoracio
     * @param usuariId <code>String</code> l'id de l'usuari
     * @param itemId <code>String</code> l'id de l'item
     * @param valor <code>String</code> el valor a escriure a la recomanacio
     * @throws Exception si no s'ha pogut modificar la valoracio perque l'usuari i/o l'item no existeixen
     */
    public void editarValoracio(String usuariId, String itemId, String valor) throws Exception {
        esborraValoracio(usuariId, itemId);
        afegirValoracio(usuariId, itemId, valor);
    }

    /**
     * Llegeix el contingut del fitxer de les valoracions
     * @param rutaAbsolut <code>String</code> ubicacio de l'arxiu a llegir
     * @throws Exception si no s'ha pogut afegir la valoracio al contenidor
     */
    public void carregaConjuntValoracions(String rutaAbsolut) throws Exception {
        ArrayList<ArrayList<String>> valoracions = controladorPersistencia.llegirCSVQualsevol(rutaAbsolut);
        valoracionsTipusItemActual.afegir(new TaulaCSV(valoracions), itemsActuals, estatPrograma.obtenirTotsElsUsuaris());
    }

    /**
     * Obtenir el conjunt d'items actuals
     * @return <code>ArraList&lt;String&gt;</code> conjunt d'items del tipus item seleccionar
     */
    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorPersistencia.obtenirConjuntsItem(nomTipusItemActual);
    }

    /**
     * Carrega tipus d'item a partir d'un fitxer
     * @param nom <code>String</code> nom del tipus d'item
     * @param rutaAbsoluta <code>String</code> ubicacio del fitxer
     * @throws Exception si el fitxer no existeix o no te format correcte
     */
    public void carregarTipusItem(String nom, String rutaAbsoluta) throws IOException, JaExisteixElementException, FormatIncorrecteException {
        if (estatPrograma.conteTipusItem(nom)) {
            throw new JaExisteixElementException("Ja existeix un tipus item amb aquest nom.");
        }
        ArrayList<ArrayList<String>> definicio = controladorPersistencia.llegirCSVQualsevol(rutaAbsoluta);
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : definicio) {
            try {
                tipusAtributs.put(fila.get(0), new TipusAtribut(fila.get(1), fila.get(2)));
            } catch (Exception e) {
                throw new FormatIncorrecteException("El format del fitxer donat no s'adequa.");
            }
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(definicio, nom);
        ConjuntValoracions buit = new ConjuntValoracions();
        controladorPersistencia.guardarConjuntValoracions(buit.convertirAArrayList(), nom);
    }

    /**
     * Carrega el tipus item amb el nom existent
     * @param nom <code>String</code> nom del tipus d'item
     * @throws IOException si no s'ha pogut obrir el fitxer
     * @throws NomInternIncorrecteException si no existeix un fitxer amb el tipus d'item dessitjat
     */
    public void carregarTipusItem(String nom) throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        ArrayList<ArrayList<String>> definicio = controladorPersistencia.obtenirTipusItem(nom);
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : definicio) {
            tipusAtributs.put(fila.get(0), new TipusAtribut(fila.get(1), fila.get(2)));
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(definicio, nom);
    }

    /**
     * Crea el tipus d'item amb el nom i els seus atributs
     * @param nom <code>String</code> nom del tipus d'item
     * @param nomAValorAtribut <code>Map&lt;String, Pair&lt;String, String&gt;&gt;</code> que conté els atributs amb el tipus
     * @throws IllegalArgumentException si ja existeix el tipus d'item
     * @throws IOException si no existeix el fitxer i/o no es pot obrir
     * @throws NomInternIncorrecteException el fitxer amb el nom del tipus d'item no existeix
     */
    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IllegalArgumentException, IOException, NomInternIncorrecteException, JaExisteixElementException, DistanciaNoCompatibleAmbValorException {
        if (estatPrograma.conteTipusItem(nom)) {
            throw new JaExisteixElementException("Ja existeix aquest tipus item.");
        }
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : nomAValorAtribut.entrySet()) {
            tipusAtributs.put(fila.getKey(), new TipusAtribut(fila.getValue().x, fila.getValue().y));
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(tipus.convertirAArrayList(), nom);
        ConjuntValoracions buit = new ConjuntValoracions();
        controladorPersistencia.guardarConjuntValoracions(buit.convertirAArrayList(), nom);
    }

    /**
     * Retorna el nom dels items carregats
     * @return <code>ArrayList&lt;String&gt;</code> llista del noms
     */
    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return estatPrograma.obteTipusItem();
    }

    /**
     * Obtenir les distancies
     * @return <code>Map&lt;String, Pair&lt;String, String&gt;&gt;</code> amb els valors
     */
    public Map<String, Pair<String, String>> obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat() throws DistanciaNoCompatibleAmbValorException {
        TipusItem tipus = estatPrograma.obteTipusItem(nomTipusItemActual);
        TreeMap<String, Pair<String, String>> valors = new TreeMap<>();
        for(var x : tipus.obtenirTipusAtributs().entrySet()) {
            valors.put(x.getKey(), new Pair<>(x.getValue().obtenirValorAtribut().obteNomValor(), x.getValue().obtenirDistancia().obteNomDistancia()));
        }
        return valors;
    }

    /**
     * Veure si la sessio esta iniciada o no
     * @return <code>boolean</code> si esta inicicada o no
     */
    public boolean esSessioIniciada() {
        return estatPrograma.isSessioIniciada();
    }

    /**
     * Exporta el conjunt d'usuaris a un fitxer
     * @param absolutePath <code>String</code> ubicacio del fitxer
     */
    public void exportarConjuntDadesUsuari(String absolutePath) throws IOException {
        Date today = Calendar.getInstance().getTime();
        controladorPersistencia.escriureCSVQualsevol(absolutePath, estatPrograma.obtenirTotsElsUsuaris().obtenirUsuarisCSV(), "Usuari" + today);
    }

    /**
     * Esborra tot el conjunt d'usuaris
     */
    public void esborraConjuntUsuaris() {
        estatPrograma.esborraTotsUsuaris();
    }

    /**
     * @return null si no hi ha cap seleccionat
     */
    public String obtenirNomTipusItemSeleccionat() {
        return nomTipusItemActual;
    }

    /**
     * Esborra el tipus d'item seleccionat
     */
    public void esborrarTipusItemSeleccionat() throws IOException {
        controladorPersistencia.borrarTipusItem(nomTipusItemActual);
        controladorPersistencia.borrarConjuntValoracions(nomTipusItemActual);
        itemsActuals = null;
        estatPrograma.esborraTipusItem(nomTipusItemActual);
        valoracionsTipusItemActual = null;
        nomTipusItemActual = null;
    }

    /**
     * Selecciona el tipus item
     * @param nomTipusItem <code>String</code> el nom del tipus d'item
     * @throws Exception si no s'ha pogut seleccionar el tipus d'item
     */
    public void seleccionarTipusItem(String nomTipusItem) throws IOException, AccesAEstatIncorrecteException, NoExisteixElementException, UsuariIncorrecteException {
        if (nomTipusItemActual != null) {
            deseleccionarTipusItem();
        }
        nomTipusItemActual = nomTipusItem;
        // TODO (edgar): treure barra baixes! i revisar si n'hi ha més
        // TODO (edgar): no funciona però l'ítem existeix
        ArrayList<ArrayList<String>> valoracionsEnBrut = controladorPersistencia.obtenirConjuntValoracions(nomTipusItem);
        ArrayList<ArrayList<String>> itemsEnBrut = controladorPersistencia.obtenirConjuntItems(nomTipusItemActual, "basic");
        TaulaCSV taulaItems = new TaulaCSV(itemsEnBrut);
        itemsActuals = new ConjuntItems(taulaItems, estatPrograma.obteTipusItem(nomTipusItemActual));
        valoracionsTipusItemActual = new ConjuntValoracions();
        TaulaCSV taulaValoracions = new TaulaCSV(valoracionsEnBrut);
        valoracionsTipusItemActual.afegir(taulaValoracions, itemsActuals, estatPrograma.obtenirTotsElsUsuaris());
    }

    /**
     * Obte la llista d'items
     * @return <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> conjunt de items
     */
    public ArrayList<ArrayList<String>> obtenirItems() {
        ArrayList<ArrayList<String>> res = itemsActuals.converteixAArray();
        res.remove(0);
        return res;
    }

    /**
     * Obte el nom dels atributs de l'item seleccionat
     * @return <code>ArrayList&lt;String&gt;</code> conjunt del nom dels atributs
     */
    public ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        return new ArrayList<>(estatPrograma.obteTipusItem(nomTipusItemActual).obtenirTipusAtributs().keySet());
    }

    /**
     * Retorna la existencia del tipus item sleccionat
     * @return <code>boolean</code> si existeix o no
     */
    public boolean existeixTipusItemSeleccionat() {
        return nomTipusItemActual != null;
    }

    /**
     * Afegeix un item al conjunt
     * @param valorsAtributs <code>Map&lt;String, String&gt;</code> els atributs i el seu valor
     * @return <code>boolean</code> true si 'sha afegit
     * @throws Exception si no s'ha pogut afegir l'item
     */
    public boolean afegirItem(Map<String, String> valorsAtributs) throws Exception {
        // Crea un item amb els valors donats i del tipus de l'ítem seleccionat
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // retorna false si no s'ha pogut fer i cert si tot esta be
        TipusItem tipusItem = estatPrograma.obteTipusItem(nomTipusItemActual);
        TreeMap<String, ValorAtribut<?>> atributs = new TreeMap<>();
        for(var x : tipusItem.obtenirTipusAtributs().entrySet()) {
            String valor = valorsAtributs.get(x.getKey());
            Class<? extends ValorAtribut> clase = x.getValue().obtenirValorAtribut().getClass();
            Constructor<?> ctor = clase.getConstructor(String.class);
            Object object = ctor.newInstance(valor);
            atributs.put(x.getKey(), clase.cast(object));
        }
        Item item = new Item(obteIdItemDisponible(), tipusItem, atributs, new HashMap<>());

        itemsActuals.afegir(item);
        return true;
    }

    // TODO: Pablo, s'han de borrar les seves valoracions!!!!
    // TODO falta por acabar pero tengo hambre
    // Esborra l'ítem amb aquest id
    // hi ha un tipus d'ítem seleccionat pero millor comprovar
    // l'item es del tipus d'ítem seleccionat
    // retorna fals si es invalid o no s'ha pogut esborrar
    // pot ser una paraula, un numero, estar buit, etc

    /**
     * Esborra l'item amb l'id dessitjat
     * @param id <code>String</code> l'id de l'item a eesborrar
     * @return <code>boolean</code> si s'ha pogut esborrar o no
     */
    public boolean esborrarItem(String id) throws NoExisteixElementException {
        //Comprobacio si id es valid nomes de transformar
        int idItemABuscar;
        try {
            idItemABuscar = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return false;
        }

        Id idItem = new Id(idItemABuscar, true);
        if (itemsActuals.conte(idItem)) {
            itemsActuals.esborrar(idItem);
            valoracionsTipusItemActual.esborraValoracionsItem(idItem);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtenir el item amb l'id seleccionat
     * @param id <code>String</code> l'id de l'item a obtenir
     * @return <code>Map&lt;String, String&gt;</code> amb el contingut de l'item
     * @throws IllegalArgumentException si l'identificador no es valid
     */
    public Map<String, String> obtenirItem(String id) throws IllegalArgumentException, NoExisteixElementException {
        // Retorna un mapa amb els noms del atributs i el valor dels atributs de l'ítem amb aquest id
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // l'item es del tipus d'ítem seleccionat
        // retorna null si l'id no es valid
        TreeMap<String, String> res = new TreeMap<>();
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(id)));
        for (var x : item.obtenirAtributs().entrySet()) {
            res.put(x.getKey(), x.getValue().obtenirValor().toString());
        }
        return res;
    }

    public boolean editarItem(String id, Map<String, String> valorsAtributs) {
        // TODO
        // edita l'item amb l'id donat amb els valors donats
        // valorsAtribut es un mapa del nom de l'atribut al nou valor
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // l'item es del tipus d'ítem seleccionat
        // existeix un item amb aquest id pero millor comprovar
        // retorna true si tot be i retorna fals si alguna cosa no funcioa
        return false;
    }

    /**
     * Carrega un conjunt d'items a partir d'un fitxer
     * @param rutaAbsoluta <code>String</code> ruta del fitxer
     * @throws Exception si no s'ha pogut obrir el fitxer
     */
    public void carregarConjuntItems(String rutaAbsoluta) throws Exception {
        ArrayList<ArrayList<String>> items = controladorPersistencia.llegirCSVQualsevol(rutaAbsoluta);
        TaulaCSV taulaItems = new TaulaCSV(items);
        ConjuntItems nousItems = new ConjuntItems(taulaItems, estatPrograma.obteTipusItem(nomTipusItemActual));
        for (var x : nousItems.obtenirTotsElsElements().entrySet()) {
            itemsActuals.afegir(x.getValue());
        }
    }

    /**
     * Esborra tots els items del tipus d'item seleccionat
     */
    public void esborrarTotsElsItems() {
        itemsActuals = new ConjuntItems(estatPrograma.obteTipusItem(nomTipusItemActual));
    }

    public void editarTipusItem(Map<String, String> relacioNomsTipusAtributs) {
        // TODO
        // relaciona el nom de l'atribut anterior amb el nou (es a dir permet canviar el nom del tipus d'atribut)
        // es una mica horrible perque ha de passar per tots els items per canviar, crec
        // si el nou atribut no té nom ("") vol dir que s'ha eliminat
    }

    //TODO: filtros
    /**
     * Obte una recomanacio amb el metode Collaborative
     * @param nomAtributs
     * @param filtreInclusiu
     * @return
     * @throws Exception
     */
    public ArrayList<String> obtenirRecomanacioCollaborative(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws Exception {
        // retorna conjunt d'ids d'items recomanats
        // utilitza l'usuari que ha iniciat sessio, el tipus d'item seleccionat, els conjunts del tipus d'item seleccionat
        // i el filtre que li passa
        Filtre filtre;
        if (filtreInclusiu) {
            filtre = new FiltreInclusiu(nomAtributs);
        }
        else filtre = new FiltreExclusiu(nomAtributs);
        recomanador = new RecomanadorCollaborative(estatPrograma.obtenirTotsElsUsuaris(), itemsActuals, valoracionsTipusItemActual, filtre);
        recomanacions = recomanador.obteRecomanacions(estatPrograma.obtenirUsuariSessioIniciada(), 20);
        ArrayList<String> res = new ArrayList<>();
        for (var x : recomanacions.obtenirConjuntRecomanacions()) {
            res.add(Integer.toString(x.obtenirId().obtenirValor()));
        }
        return res;
    }

    //TODO: filtros
    /**
     *
     * @param nomAtributs
     * @param filtreInclusiu
     * @return
     * @throws Exception
     */
    public ArrayList<String> obtenirRecomanacioContentBased(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws Exception {
        // retorna conjunt d'ids d'items recomanats
        // utilitza l'usuari que ha iniciat sessio, el tipus d'item seleccionat, els conjunts del tipus d'item seleccionat
        // i el filtre que li passa
        Filtre filtre;
        if (filtreInclusiu) {
            filtre = new FiltreInclusiu(nomAtributs);
        }
        else filtre = new FiltreExclusiu(nomAtributs);
        recomanador = new RecomanadorContentBased(estatPrograma.obtenirTotsElsUsuaris(), itemsActuals, valoracionsTipusItemActual, filtre);
        recomanacions = recomanador.obteRecomanacions(estatPrograma.obtenirUsuariSessioIniciada(), 20);
        ArrayList<String> res = new ArrayList<>();
        for (var x : recomanacions.obtenirConjuntRecomanacions()) {
            res.add(Integer.toString(x.obtenirId().obtenirValor()));
        }
        return res;
    }

    //TODO: filtros
    /**
     *
     * @param nomAtributs
     * @param filtreInclusiu
     * @return
     * @throws Exception
     */
    public ArrayList<String> obtenirRecomanacioHibrida(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws Exception {
        // retorna conjunt d'ids d'items recomanats
        // utilitza l'usuari que ha iniciat sessio, el tipus d'item seleccionat, els conjunts del tipus d'item seleccionat
        // i el filtre que li passa
        Filtre filtre;
        if (filtreInclusiu) {
            filtre = new FiltreInclusiu(nomAtributs);
        }
        else filtre = new FiltreExclusiu(nomAtributs);
        recomanador = new RecomanadorHibrid(estatPrograma.obtenirTotsElsUsuaris(), itemsActuals, valoracionsTipusItemActual, filtre);
        recomanacions = recomanador.obteRecomanacions(estatPrograma.obtenirUsuariSessioIniciada(), 20);
        ArrayList<String> res = new ArrayList<>();
        for (var x : recomanacions.obtenirConjuntRecomanacions()) {
            res.add(Integer.toString(x.obtenirId().obtenirValor()));
        }
        return res;
    }

    //TODO: filtros

    /**
     *
     * @return
     */
    public double avaluarRecomanacio() {
        return recomanacions.obteDiscountedCumulativeGain()/recomanacions.obteIdealDiscountedCumulativeGain();
    }

    /**
     * Esborra totes les valoracions
     */
    public void esborrarTotesLesValoracions() {
        valoracionsTipusItemActual.esborraTotesLesValoracions();
    }

    /**
     * Obte les valoracions
     * @return <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> llista de les valoracions
     */
    public ArrayList<ArrayList<String>> obtenirValoracions() {
        ArrayList<ArrayList<String>> retornaValoracions =  valoracionsTipusItemActual.convertirAArrayList();
        retornaValoracions.remove(0);
        return retornaValoracions;
    }

    /**
     * Desseleciona el tipus item actual i esborra la relacio del porgrama actual amb aquest.
     * @throws IOException
     */
    public void deseleccionarTipusItem() throws IOException {
        // TODO: si no n'hi ha cap de seleccionat retornar una excepció personalitzada per distingir entre
        //  les dues excepcions i posar-me un todo (maria)
        controladorPersistencia.borrarTipusItem(nomTipusItemActual);
        controladorPersistencia.guardarTipusItem(estatPrograma.obteTipusItem(nomTipusItemActual).convertirAArrayList(), nomTipusItemActual);
        controladorPersistencia.guardarConjuntValoracions(valoracionsTipusItemActual.convertirAArrayList(), nomTipusItemActual);
        controladorPersistencia.guardarConjuntItems(itemsActuals.converteixAArray(), nomTipusItemActual, "basic");
        nomTipusItemActual = null;
    }

    /**
     * Retorna la llista de tots els usuaris actius i no actius del programa
     * @return retorna la llista d'usuaris
     */
    public ArrayList<ArrayList<String>> obteUsuaris() {
        return estatPrograma.obtenirTotsElsUsuaris().obtenirLlistaUsuaris();
    }

    /**
     * Carrega un conjunt d'usuaris a partir d'un path
     * @param ubicacioFitxer la direccio del fitxer a llegir
     * @return retorna els usuaris que no s'han pogut afegir
     * @throws Exception si l'usuari ha posat una direccio de fitxer no valida
     */
    public ArrayList<String> importarUsuaris(String ubicacioFitxer) throws Exception {
        ArrayList<ArrayList<String>> llistaUsuaris = controladorPersistencia.llegirCSVQualsevol(ubicacioFitxer);
        ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris(llistaUsuaris);
        ArrayList<Usuari> llista = conjuntUsuaris.obtenirUsuaris();

        ArrayList<String> idsNoInclosos = new ArrayList<>();
        for (Usuari usuari : llista) {
            if (!estatPrograma.conteUsuari(usuari.obtenirId())) {
                estatPrograma.afegirUsuari(usuari);
            }

            else {
                idsNoInclosos.add(String.valueOf(usuari.obtenirId().obtenirValor()));
            }
        }

        return idsNoInclosos;
    }

    /**
     * Funcio que canvia la contrasenya d'un usuari
     * @param id es l'id de l'usuari a editar
     * @param novaContrasenyaArray es la contrasenya a la que es vol canviar
     * @throws Exception si l'usuari no existeix, retorna excepcio
     */
    public void canviaContrasenyaUsuari(String id, char[] novaContrasenyaArray) throws Exception {
        String novaContrasenya = String.valueOf(novaContrasenyaArray);
        Id idUsuari = new Id(Integer.parseInt(id), true);
        if (!estatPrograma.conteUsuari(idUsuari) || !estatPrograma.obtenirUsuari(idUsuari).isActiu()) {
            throw new Exception("Canvi de contrasenya: L'id d'usuari seleccionat no existeix");
        }

        else {
            if (!novaContrasenya.isBlank()) {
                estatPrograma.obtenirUsuari(idUsuari).setContrasenya(novaContrasenya);
            }
        }
    }

    /**
     * Funcio que canvia el nom d'un usuari
     * @param id id de l'usuari
     * @param nouNom nom a canviar
     * @throws Exception Si l'usuari no existeix es retorna excepcio
     */
    public void canviaNomUsuari(String id, String nouNom) throws Exception {
        Id idUsuari = new Id(Integer.parseInt(id), true);
        if (!estatPrograma.conteUsuari(idUsuari) || !estatPrograma.obtenirUsuari(idUsuari).isActiu()) {
            throw new Exception("Canvi de nom: L'id d'usuari seleccionat no existeix");
        }

        else {
            if (!nouNom.isBlank()) {
                estatPrograma.obtenirUsuari(idUsuari).setNom(nouNom);
            }
        }
    }

    /**
     *
     * @param absolutePath path a la carpeta on exportem les valoracions
     */
    public void exportaValoracions(String absolutePath) throws IOException {
        Date today = Calendar.getInstance().getTime();
        controladorPersistencia.escriureCSVQualsevol(absolutePath, valoracionsTipusItemActual.convertirAArrayList(), "Valoracions" + today);
    }
}
