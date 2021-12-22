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
import utilitats.Pair;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Classe que representa el controlador de domini
 * @author edgar.moreno and pablo.vega
 */
public class ControladorDomini {
    private static ControladorDomini instancia;
    private final ControladorPersistencia controladorPersistencia;

    private final Programa estatPrograma;
    private int ultimIdUsat = 1000000;
    private int ultimIdUsatItem = 1000000;
    private String nomTipusItemActual = null;
    private ConjuntValoracions valoracionsTipusItemActual = null;
    private ConjuntItems itemsActuals = null;
    private Recomanador recomanador;
    private ConjuntRecomanacions recomanacions;

    /**
     * Contructor privat de la classe per defecte
     * @throws IOException si no existeix algun fitxer de la càrrega de fitxers per defecte
     * @throws NomInternIncorrecteException si hi ha algun problema amb els noms dels fitxers dels metodes interns
     * @throws DistanciaNoCompatibleAmbValorException no es pot calcular la distancia
     */
    private ControladorDomini() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        controladorPersistencia = ControladorPersistencia.obtenirInstancia();
        estatPrograma = Programa.obtenirInstancia();
        ArrayList<String> llistaTipusItems = controladorPersistencia.obtenirNomsDeTotsElsTipusItems();
        for (String tipusItem : llistaTipusItems) {
            this.carregarTipusItem(tipusItem);
        }
        estatPrograma.afegeixConjuntUsuaris(new ConjuntUsuaris(controladorPersistencia.obtenirConjuntUsuaris("basic")));
    }

    /**
     * Retorna la instancia de Controlador Domini
     * @return La instancia de controlador domini
     * @throws IOException si no existeix algun fitxer de la càrrega de fitxers per defecte
     * @throws NomInternIncorrecteException si hi ha algun problema amb els noms dels fitxers dels metodes interns
     * @throws DistanciaNoCompatibleAmbValorException La distancia no es compatible amb el valor
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
     * @throws SessioNoIniciadaException Si la sessió no està iniciada
     */
    public int obtenirSessio() throws SessioNoIniciadaException {
        if (this.estatPrograma.isSessioIniciada()) return 0;
        else {
            Usuari usuari = this.estatPrograma.obtenirUsuariSessioIniciada();
            Id id = usuari.obtenirId();
            return id.obtenirValor();
        }
    }

    /**
     * Inicia sessió amb l'usuari d'id idSessio si la contrasenya és correcte
     * @param idSessio id de l'usuari que inicia la sessió
     * @param contrasenya contrasenya de l'usuari
     * @throws SessioIniciadaException La sessió no està iniciada
     * @throws ContrasenyaIncorrectaException la contrasenya no és correcte
     * @throws NoExisteixElementException No existeix element
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
     * Comprova si existeix un usuari al conjunt d'usuaris del programa
     * @param id <code>int</code> l'id de l'usuari
     * @return retorna si existeix l'usuari al conjunt o no
     * @throws NoExisteixElementException si l'usuari ja existeix
     */
    public boolean existeixUsuari(int id) throws NoExisteixElementException {
        Id idBo = new Id(id, true);
        return estatPrograma.conteUsuari(idBo) && estatPrograma.obtenirUsuari(idBo).isActiu();
    }


    /**
     * @return retorna un identificador disponible per un usuari
     */
    private Id obtenirIdUsuariDisponible() {
        while (estatPrograma.conteUsuari(new Id(ultimIdUsat, true))) {
            ultimIdUsat++;
        }
        return new Id(ultimIdUsat, true);
    }

    /**
     * @return retorna un identificador disponible per un item
     */
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
     * @return Retorna l'identificador de l'usuari creat
     * @throws JaExisteixElementException l'element ja existeix
     */
    public int afegirUsuari(String nom, String contrasenya) throws JaExisteixElementException {
        Id id = obtenirIdUsuariDisponible();
        try {
            if (estatPrograma.conteUsuari(id) && estatPrograma.obtenirUsuari(id).isActiu()) {
                throw new JaExisteixElementException("L'usuari " + nom + " ja existeix");
            }
            else {
                estatPrograma.afegirUsuari(new Usuari(id, nom, contrasenya));
                return id.obtenirValor();
            }
        } catch (Exception ignored) {
            return -1;
        }
    }

    /**
     * Esborra usuari, falta parametre del conjunt
     * @param id id del usuari
     * @throws NoExisteixElementException no existeix element
     */
    public void esborrarUsuari(int id) throws NoExisteixElementException {
        Id id1 = new Id(id, true);
        if (!estatPrograma.conteUsuari(id1) || !estatPrograma.obtenirUsuari(id1).isActiu()) {
            throw new NoExisteixElementException("L'usuari no existeix");
        }
        else {
            estatPrograma.esborraUsuari(new Id(id, true));
        }
    }

    /**
     * Tanca la sessio de programa
     * @throws SessioNoIniciadaException La sessió no està iniciada
     */
    public void tancarSessio() throws SessioNoIniciadaException {
        this.estatPrograma.tancarSessio();
    }

    /**
     * Afegeix la una valoració a la llista de valoracions
     * @param usuariId <code>String</code> l'id de l'usuari
     * @param itemId <code>String</code> l'id de l'item
     * @param valor <code>String</code> valor de la valoracio
     * @throws NoExisteixElementException no existeis l'item
     * @throws UsuariIncorrecteException si no existeix l'usuari
     */
    public void afegirValoracio(String usuariId, String itemId, String valor) throws NoExisteixElementException, UsuariIncorrecteException {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        valoracionsTipusItemActual.afegir(new Valoracio(Double.parseDouble(valor), us, item));
    }

    /**
     * Comproba si al conjunt existeix una valoracio d'un usuari cap a un item
     * @param usuariId <code>String</code> l'id de l'usuari
     * @param itemId <code>String</code> l'id de l'item
     * @return retorna si existeix la valoracio de l'usuari cap a un item
     * @throws NoExisteixElementException si no existeix l'usuari o no existeix l'item
     */
    public boolean existeixValoracio(String usuariId, String itemId) throws NoExisteixElementException {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        return valoracionsTipusItemActual.conte(us, item);
    }

    /**
     * S'esborra la valoracio de un usuari cap a un item
     * @param usuariId <code>String</code> l'id de l'usuari
     * @param itemId <code>String</code> l'id id de l'item
     * @throws NoExisteixElementException si no existeix l'usuari o no existeix l'item
     */
    public void esborraValoracio(String usuariId, String itemId) throws NoExisteixElementException {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        valoracionsTipusItemActual.esborrar(valoracionsTipusItemActual.obte(us, item));
    }

    /**
     * Edita la valoracio
     * @param usuariId <code>String</code> l'id de l'usuari
     * @param itemId <code>String</code> l'id de l'item
     * @param valor <code>String</code> el valor a escriure a la recomanacio
     * @throws NoExisteixElementException l'item no existeixen
     * @throws UsuariIncorrecteException si no s'ha pogut modificar la valoracio perque l'usuari no existeix
     */
    public void editarValoracio(String usuariId, String itemId, String valor) throws NoExisteixElementException, UsuariIncorrecteException {
        esborraValoracio(usuariId, itemId);
        afegirValoracio(usuariId, itemId, valor);
    }

    /**
     * Llegeix el contingut del fitxer de les valoracions
     * @param rutaAbsolut <code>String</code> ubicacio de l'arxiu a llegir
     * @throws IOException si no s'ha pogut afegir la valoracio al contenidor
     * @throws AccesAEstatIncorrecteException a
     * @throws NoExisteixElementException b
     * @throws UsuariIncorrecteException c
     */
    public void carregaConjuntValoracions(String rutaAbsolut) throws IOException, AccesAEstatIncorrecteException, NoExisteixElementException, UsuariIncorrecteException {
        if (nomTipusItemActual == null) {
            throw new AccesAEstatIncorrecteException("S'ha de seleccionar un tipus d'item abans");
        }
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
     * @throws IOException si el fitxer no existeix o no te format correcte
     * @throws JaExisteixElementException l'element ja existeix al conjunt
     * @throws FormatIncorrecteException el fitxer no te el format correcte
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
     * @throws  DistanciaNoCompatibleAmbValorException la distància no és compatible amb el valor
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
     * @throws IOException si no existeix el fitxer i/o no es pot obrir
     * @throws NomInternIncorrecteException el fitxer amb el nom del tipus d'item no existeix
     * @throws JaExisteixElementException ja existeix un tipus d'item amb aquest nom
     * @throws DistanciaNoCompatibleAmbValorException la distancia no és compatible amb el valor
     */
    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IOException, NomInternIncorrecteException, JaExisteixElementException, DistanciaNoCompatibleAmbValorException {
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
     * @return <code>ArrayList&lt;String&gt;</code> llista de noms
     */
    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return estatPrograma.obteTipusItem();
    }

    /**
     * Obtenir les distancies
     * @return <code>Map&lt;String, Pair&lt;String, String&gt;&gt;</code> amb els valors
     * @throws DistanciaNoCompatibleAmbValorException el metode distancia no es pot calcular amb l'atribut seleccionat
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
    public boolean sessioIniciada() {
        return estatPrograma.isSessioIniciada();
    }

    /**
     * Exporta el conjunt d'usuaris a un fitxer
     * @throws IOException Error en l'entrada/sortida
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
     * Obté el nom del tipus d'ítem seleccionat
     * @return null si no hi ha cap seleccionat
     */
    public String obtenirNomTipusItemSeleccionat() {
        return nomTipusItemActual;
    }

    /**
     * Esborra el tipus d'item seleccionat
     * @throws IOException el fitxer no es pot obrir
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
     * @throws IOException a
     * @throws AccesAEstatIncorrecteException b
     * @throws NoExisteixElementException c
     * @throws UsuariIncorrecteException d
     */
    public void seleccionarTipusItem(String nomTipusItem) throws IOException, AccesAEstatIncorrecteException, NoExisteixElementException, UsuariIncorrecteException {
        if (nomTipusItemActual != null) {
            desseleccionarTipusItem();
        }
        nomTipusItemActual = nomTipusItem;
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
        ArrayList<ArrayList<String>> res = itemsActuals.convertirAArrayList();
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
     * @return retorna l'identificador que s'ha assignat a l'item
     * @throws IllegalArgumentException si falta algun atribut
     * @throws FormatIncorrecteException si hi ha atributs amb valor incorrecte
     * @throws NoSuchMethodException si no s'ha pogut crear l'item del tipus d'item seleccionat
     * @throws InvocationTargetException si l'item no es correspon amb el tipus d'item seleccionat
     * @throws InstantiationException si l'item no es correspon amb el tipus d'item seleccionat
     * @throws IllegalAccessException si no s'ha pogut crear l'item del tipus d'item seleccionat
     */
    public int afegirItem(Map<String, String> valorsAtributs) throws IllegalArgumentException, FormatIncorrecteException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        TipusItem tipusItem = estatPrograma.obteTipusItem(nomTipusItemActual);
        TreeMap<String, ValorAtribut<?>> atributs = new TreeMap<>();
        for(Map.Entry<String, TipusAtribut> tipusAtribut : tipusItem.obtenirTipusAtributs().entrySet()) {
            String valor = valorsAtributs.get(tipusAtribut.getKey());
            if (valor == null) {
                throw new IllegalArgumentException("No hi ha cap atribut amb nom " + tipusAtribut.getKey());
            }
            if (valor.contains(",") || valor.contains("\"")) {
                throw new FormatIncorrecteException("Els atributs no poden tenir el caràcter , ni \"");

            }
            Class<? extends ValorAtribut> classe = tipusAtribut.getValue().obtenirValorAtribut().getClass();
            Constructor<?> constructor = classe.getConstructor(String.class);
            Object object = constructor.newInstance(valor);
            atributs.put(tipusAtribut.getKey(), classe.cast(object));
        }
        Item item = new Item(obteIdItemDisponible(), tipusItem, atributs, new HashMap<>());
        itemsActuals.afegir(item);
        return item.obtenirId().obtenirValor();
    }

    /**
     * Esborra l'item amb l'id desitjat
     * @param id <code>String</code> l'id de l'item a eesborrar
     * @return <code>boolean</code> si s'ha pogut esborrar o no
     * @throws NoExisteixElementException no existeix l'item
     */
    public boolean esborrarItem(String id) throws NoExisteixElementException {
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
     * @throws NoExisteixElementException no existeix item
     */
    public Map<String, String> obtenirItem(String id) throws NoExisteixElementException {
        TreeMap<String, String> res = new TreeMap<>();
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(id)));
        for (var x : item.obtenirAtributs().entrySet()) {
            res.put(x.getKey(), x.getValue().toString());
        }
        return res;
    }

    /**
     * @param id identificador de l'item que es vol editar
     * @param valorsAtributs mapa que relaciona el nom dels atributs de l'item amb el seu nou valor
     * @throws IllegalArgumentException si falta algun atribut
     * @throws FormatIncorrecteException si hi ha atributs amb valor incorrecte
     * @throws NoSuchMethodException si no s'ha pogut crear l'item del tipus d'item seleccionat
     * @throws InvocationTargetException si l'item donat no es correspon amb el tipus d'item seleccionat
     * @throws InstantiationException si els valors de l'item no es corresponen amb el tipus d'item seleccionat
     * @throws IllegalAccessException si no s'ha pogut editar l'item del tipus d'item seleccionat
     * @throws NoExisteixElementException si no existeix un item amb l'identificador donat
     */
    public void editarItem(String id, Map<String, String> valorsAtributs) throws IllegalArgumentException,
            NoExisteixElementException, FormatIncorrecteException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        int idItemABuscar;
        try {
            idItemABuscar = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new FormatIncorrecteException("L'identificador " + id + " no és vàlid.");
        }
        Item item = itemsActuals.obtenir(new Id(idItemABuscar));
        for (var atribut : item.obtenirAtributs().entrySet()) {
            String valor = valorsAtributs.get(atribut.getKey());
            if (valor == null) {
                throw new IllegalArgumentException("No hi ha cap atribut amb nom " + atribut.getKey());
            }
            if (valor.contains(",") || valor.contains("\"")) {
                throw new FormatIncorrecteException("Els atributs no poden tenir el caràcter , ni \"");

            }
            Class<? extends ValorAtribut> classe = atribut.getValue().getClass();
            Constructor<?> constructor = classe.getConstructor(String.class);
            Object object = constructor.newInstance(valor);
            item.modificarAtribut(atribut.getKey(), classe.cast(object));
        }
    }

    /**
     * Carrega un conjunt d'items amb un nou nom a partir d'un arxiu extern.
     * @param nomTipusItem El nom del tipusItem que es creara
     * @param rutaAbsoluta La localitzacio del fitxer d'on es carregara.
     * @throws IOException Algun problema obrint el fitxer
     * @throws AccesAEstatIncorrecteException Error creant el nou tipus item.
     * @throws DistanciaNoCompatibleAmbValorException Error creant el nou tipus item.
     * @throws NoExisteixElementException Error creant el nou tipus item.
     * @throws JaExisteixElementException Ja existeix un tipus item amb aquest nom.
     * @throws FormatIncorrecteException El fitxer no segueix el format esperat.
     */
    public void carregarConjuntItems(String nomTipusItem, String rutaAbsoluta) throws IOException, AccesAEstatIncorrecteException, DistanciaNoCompatibleAmbValorException, NoExisteixElementException, JaExisteixElementException, FormatIncorrecteException {
        if (estatPrograma.conteTipusItem(nomTipusItem)) {
            throw new JaExisteixElementException("Ja existeix un tipus d'item amb nom " + nomTipusItem);
        }
        ArrayList<ArrayList<String>> items = controladorPersistencia.llegirCSVQualsevol(rutaAbsoluta);
        TaulaCSV taulaItems = new TaulaCSV(items);
        itemsActuals = new ConjuntItems(nomTipusItem, taulaItems);
        estatPrograma.afegirTipusItem(nomTipusItem, itemsActuals.obteTipusItem());
        valoracionsTipusItemActual = new ConjuntValoracions();
        nomTipusItemActual = nomTipusItem;
        controladorPersistencia.guardarTipusItem(itemsActuals.obteTipusItem().convertirAArrayList(), nomTipusItem);
        controladorPersistencia.guardarConjuntValoracions(valoracionsTipusItemActual.convertirAArrayList(), nomTipusItem);
    }

    /**
     * Carrega un conjunt d'items a partir d'un fitxer
     *
     * @param rutaAbsoluta <code>String</code> ruta del fitxer
     * @throws IOException si no s'ha pogut obrir el fitxer
     * @throws AccesAEstatIncorrecteException taula no inicialitzada
     */
    public void carregarConjuntItems(String rutaAbsoluta) throws IOException, AccesAEstatIncorrecteException {
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

    /**
     * Canvia el nom del tipus item seleccionat.
     * @param nouNom nou nom pel tipus item.
     * @throws IOException Problema canviant el nom del tipus item a la persistencia.
     */
    public void editarTipusItem(String nouNom) throws IOException, FormatIncorrecteException, JaExisteixElementException {
        if (nomTipusItemActual == null) return;
        if (nouNom.isEmpty()) {
            throw new FormatIncorrecteException("El nom d'un tipus d'ítem no pot ser buit.");
        }
        if (existeixTipusItem(nouNom)) {
            throw new JaExisteixElementException("Ja existeix un tipus d'ítem amb nom: \"" + nouNom + "\"");
        }
        controladorPersistencia.borrarTipusItem(nomTipusItemActual);
        controladorPersistencia.borrarConjuntValoracions(nomTipusItemActual);
        TipusItem tipusItem = estatPrograma.obteTipusItem(nomTipusItemActual).copiar();
        for (var item : itemsActuals.obtenirTotsElsElements().entrySet()) {
            item.getValue().canviaNomTipusItem(nouNom);
        }
        estatPrograma.esborraTipusItem(nomTipusItemActual);
        tipusItem.canviaElNom(nouNom);
        nomTipusItemActual = nouNom;
        estatPrograma.afegirTipusItem(nouNom, tipusItem);
        controladorPersistencia.guardarTipusItem(tipusItem.convertirAArrayList(), nouNom);
        controladorPersistencia.guardarConjuntItems(itemsActuals.convertirAArrayList(), nouNom, "basic");
        controladorPersistencia.guardarConjuntValoracions(valoracionsTipusItemActual.convertirAArrayList(), nouNom);
    }


    /**
     * @param nom nom del tipus d'item
     * @return retorna cert si existeix un tipus d'item carregat amb el nom donat
     */
    private boolean existeixTipusItem(String nom) {
        return estatPrograma.conteTipusItem(nom);
    }

    /**
     * Obté una recomanació amb el mètode Recomanador Collaborative per a l'usuari que està actiu.
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu true si el filtre és de tipus inclusiu, false si és exclusiu.
     * @return El conjunt d'id's dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException hi ha un problema per crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioCollaborative(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws SessioNoIniciadaException, NoExisteixElementException {
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

    /**
     * Obté una recomanació amb el mètode Recomanador ContentBased per al usuari que esta actiu.
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu true si el filtre és de tipus inclusiu, false si és exclusiu.
     * @return El conjunt de id's dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException hi ha un problema per crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioContentBased(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws SessioNoIniciadaException, NoExisteixElementException {
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

    /**
     * Obté una recomanació amb el mètode Recomanador Híbrid per a l'usuari que està actiu.
     * @param nomAtributs atributs considerats pel filtre
     * @param filtreInclusiu true si el filtre és de tipus inclusiu, false si és exclusiu.
     * @return El conjunt d'id's dels items recomanats.
     * @throws SessioNoIniciadaException si no hi ha cap sessió iniciada.
     * @throws NoExisteixElementException hi ha un problema per crear la recomanació.
     */
    public ArrayList<String> obtenirRecomanacioHibrida(ArrayList<String> nomAtributs, boolean filtreInclusiu) throws SessioNoIniciadaException, NoExisteixElementException {
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

    /**
     * Retorna una avaluacio de la ultima recomanacio feta.
     * @return El NDGC de la ultima recomanacio.
     */
    public double avaluarRecomanacio(ArrayList<Pair<Integer,Double>> valoracions) {
        // TODO (edgar): afegir les valoracions al conjunt de valoracions
        return recomanacions.calculaDiscountedCumulativeGain(valoracions) /
                recomanacions.calculaIdealDiscountedCumulativeGain(valoracions, recomanacions.obtenirConjuntRecomanacions().size());
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
     * pre: Hi ha un tipusItem seleccionat
     * Desselecciona el tipus item actual i esborra la relacio del programa actual amb aquest.
     * @throws IOException hi ha un problema guardant el tipus item actual.
     */
    public void desseleccionarTipusItem() throws IOException {
        controladorPersistencia.borrarTipusItem(nomTipusItemActual);
        controladorPersistencia.guardarTipusItem(estatPrograma.obteTipusItem(nomTipusItemActual).convertirAArrayList(), nomTipusItemActual);
        controladorPersistencia.guardarConjuntValoracions(valoracionsTipusItemActual.convertirAArrayList(), nomTipusItemActual);
        controladorPersistencia.guardarConjuntItems(itemsActuals.convertirAArrayList(), nomTipusItemActual, "basic");
        nomTipusItemActual = null;
    }

    /**
     * Retorna la llista de tots els usuaris actius i no actius del programa
     * @return retorna la llista d'usuaris
     */
    public ArrayList<ArrayList<String>> obtenirUsuaris() {
        return estatPrograma.obtenirTotsElsUsuaris().obtenirLlistaUsuarisActius();
    }

    /**
     * Carrega un conjunt d'usuaris a partir d'un path
     * @param ubicacioFitxer la direccio del fitxer a llegir
     * @return retorna els usuaris que no s'han pogut afegir
     * @throws IOException si l'usuari ha posat una direccio de fitxer no valida
     */
    public ArrayList<String> importarUsuaris(String ubicacioFitxer) throws IOException {
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
     * @throws NoExisteixElementException si l'usuari no existeix, retorna excepcio
     */
    public void canviaContrasenyaUsuari(String id, char[] novaContrasenyaArray) throws NoExisteixElementException {
        String novaContrasenya = String.valueOf(novaContrasenyaArray);
        Id idUsuari = new Id(Integer.parseInt(id), true);
        if (!estatPrograma.conteUsuari(idUsuari) || !estatPrograma.obtenirUsuari(idUsuari).isActiu()) {
            throw new NoExisteixElementException("Canvi de contrasenya: L'id d'usuari seleccionat no existeix");
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
     * @throws NoExisteixElementException Si l'usuari no existeix es retorna excepcio
     */
    public void canviaNomUsuari(String id, String nouNom) throws NoExisteixElementException {
        Id idUsuari = new Id(Integer.parseInt(id), true);
        if (!estatPrograma.conteUsuari(idUsuari) || !estatPrograma.obtenirUsuari(idUsuari).isActiu()) {
            throw new NoExisteixElementException("Canvi de nom: L'id d'usuari seleccionat no existeix");
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
     * @throws IOException el fitxer no es pot obrir
     */
    public void exportaValoracions(String absolutePath) throws IOException {
        Date today = Calendar.getInstance().getTime();
        controladorPersistencia.escriureCSVQualsevol(absolutePath, valoracionsTipusItemActual.convertirAArrayList(), "Valoracions" + today);
    }

    /**
     * Obre el Manual d'usuari.
     * @throws IOException Hi ha algun error.
     */
    public void obrirManual() throws IOException {
        controladorPersistencia.obreManual();
    }

    /**
     * Guarda les modificacions que hagin patit les dades de l'aplicacio abans de tancar-la.
     * @throws IOException Hi ha algun error.
     */
    public void guardarPrograma() throws IOException {
        controladorPersistencia.guardarConjuntUsuaris(estatPrograma.obtenirTotsElsUsuaris().obtenirUsuarisCSV(), "basic");
        if (nomTipusItemActual != null) {
            desseleccionarTipusItem();
        }
    }

    /**
     * @return llista amb els identificadors dels items del tipus d'item seleccionat
     */
    public ArrayList<String> obtenirIdsItems() {
        ArrayList<String> idsItems = new ArrayList<>();
        itemsActuals.obtenirTotsElsElements().keySet().forEach((id) -> idsItems.add(String.valueOf(id.obtenirValor())));
        return idsItems;
    }

    /**
     * Exporta els items a la ruta donada
     * @param rutaAbsoluta ruta on volem guardar l'arxiu
     * @throws IOException si hi ha hagut un error en la lectura o escriptura de fitxers
     */
    public void exportarItems(String rutaAbsoluta) throws IOException {
        Date avui = Calendar.getInstance().getTime();
        controladorPersistencia.escriureCSVQualsevol(rutaAbsoluta, itemsActuals.convertirAArrayList(), "Items" + avui);
    }
}
