package domini.controladors;

import domini.classes.*;
import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.csv.TaulaCSV;
import domini.classes.recomanador.*;
import domini.classes.recomanador.filtre.Filtre;
import domini.classes.recomanador.filtre.FiltreExclusiu;
import domini.classes.recomanador.filtre.FiltreInclusiu;
import excepcions.NomInternIncorrecteException;
import excepcions.SessioNoIniciadaException;
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

    private ControladorDomini() throws IOException, NomInternIncorrecteException {
        controladorPersistencia = ControladorPersistencia.obtenirInstancia();
        estatPrograma = Programa.obtenirInstancia();
        ArrayList<String> llistaTipusItems = controladorPersistencia.obtenirNomsDeTotsElsTipusItems();
        for (String tipusItem : llistaTipusItems) {
            this.carregarTipusItem(tipusItem);
        }
    }

    public static ControladorDomini obtenirInstancia() throws IOException, NomInternIncorrecteException {
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
    public void iniciarSessio(int idSessio, String contrasenya) throws Exception {
        Id idUsuariBuscat = new Id(idSessio, true);
        if (this.estatPrograma.conteUsuari(idUsuariBuscat)) {
            Usuari usuariCercat = this.estatPrograma.obtenirUsuari(idUsuariBuscat);
            Id idUsuariCercat = usuariCercat.obtenirId();

            if (!idUsuariCercat.esActiu()) {
                throw new Exception("L'usuari existeix pero no es actiu");
            }

            else if (usuariCercat.isContrasenya(contrasenya)) {
                this.estatPrograma.iniciarSessio(usuariCercat);
            }

            else {
                throw new Exception("La contrasenya es incorrecta");
            }
        }

        else {
            throw new Exception("L'usuari no existeix");
        }
    }

    public boolean existeixUsuari(int id) throws Exception {
        Id id_bo = new Id(id, true);
        return estatPrograma.conteUsuari(id_bo) && estatPrograma.obtenirUsuari(id_bo).isActiu();
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

    public void afegirValoracio(String usuariId, String itemId, String valor) throws Exception {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        valoracionsTipusItemActual.afegir(new Valoracio(Double.parseDouble(valor), us, item));
    }

    public boolean existeixValoracio(String usuariId, String itemId) throws Exception {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        return valoracionsTipusItemActual.conte(us, item);
    }

    public void esborraValoracio(String usuariId, String itemId) throws Exception {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        valoracionsTipusItemActual.esborrar(valoracionsTipusItemActual.obte(us, item));
    }

    public void editarValoracio(String usuariId, String itemId, String valor) throws Exception {
        esborraValoracio(usuariId, itemId);
        afegirValoracio(usuariId, itemId, valor);
    }

    public void carregaConjuntValoracions(String rutaAbsolut) throws Exception {
        ArrayList<ArrayList<String>> valoracions = controladorPersistencia.llegirCSVQualsevol(rutaAbsolut);
        valoracionsTipusItemActual.afegir(new TaulaCSV(valoracions), itemsActuals, estatPrograma.obtenirTotsElsUsuaris());
    }

    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorPersistencia.obtenirConjuntsItem(nomTipusItemActual);
    }

    public void carregarTipusItem(String nom, String rutaAbsoluta) throws Exception {
        ArrayList<ArrayList<String>> definicio = controladorPersistencia.llegirCSVQualsevol(rutaAbsoluta);
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : definicio) {
            try {
                tipusAtributs.put(fila.get(0), new TipusAtribut(fila.get(1), fila.get(2)));
            } catch (Exception e) {
                throw new Exception();
            }
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(definicio, nom);
        // TODO (edgar): afegir exception quan ja existeix tipus item amb aquest nom i que sigui una exception explicativa
    }


    public void carregarTipusItem(String nom) throws IOException, NomInternIncorrecteException {
        ArrayList<ArrayList<String>> definicio = controladorPersistencia.obtenirTipusItem(nom);
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : definicio) {
            tipusAtributs.put(fila.get(0), new TipusAtribut(fila.get(1), fila.get(2)));
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(definicio, nom);
    }

    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IllegalArgumentException, IOException, NomInternIncorrecteException {
        if (estatPrograma.conteTipusItem(nom)) {
            // TODO: crear excepcio
            throw new IllegalArgumentException("Ja existeix aquest tipus item.");
        }
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : nomAValorAtribut.entrySet()) {
            tipusAtributs.put(fila.getKey(), new TipusAtribut(fila.getValue().x, fila.getValue().y));
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(tipus.convertirAArrayList(), nom);
    }

    /** Retorna els noms dels conjunts d'items coneguts**/
    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return estatPrograma.obteTipusItem();
    }

    public Map<String, Pair<String, String>> obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat() {
        TipusItem tipus = estatPrograma.obteTipusItem(nomTipusItemActual);
        TreeMap<String, Pair<String, String>> valors = new TreeMap<>();
        for(var x : tipus.obtenirTipusAtributs().entrySet()) {
            valors.put(x.getKey(), new Pair<>(x.getValue().obtenirValorAtribut().obteNomValor(), x.getValue().obtenirDistancia().obteNomDistancia()));
        }
        return valors;
    }

    public boolean esSessioIniciada() {
        return estatPrograma.isSessioIniciada();
    }

    /**
     *
     * @param absolutePath Absolute path to folder where the new file will be created
     */
    public void exportarConjuntDadesUsuari(String absolutePath) throws IOException {
        Date today = Calendar.getInstance().getTime();
        controladorPersistencia.escriureCSVQualsevol(absolutePath, estatPrograma.obtenirTotsElsUsuaris().obtenirUsuarisCSV(), "Usuari" + today);
    }

    public void esborraConjuntUsuaris() {
        estatPrograma.esborraTotsUsuaris();
    }

    /**
     * @return null si no hi ha cap seleccionat
     */
    public String obtenirNomTipusItemSeleccionat() {
        return nomTipusItemActual;
    }

    public void esborrarTipusItemSeleccionat() {
        controladorPersistencia.borrarTipusItem(nomTipusItemActual);
        itemsActuals = null;
        estatPrograma.esborraTipusItem(nomTipusItemActual);
        valoracionsTipusItemActual = null;
        nomTipusItemActual = null;
    }

    public void seleccionarTipusItem(String nomTipusItem) throws Exception {
        if (nomTipusItemActual != null) {
            deseleccionarTipusItem();
        }
        nomTipusItemActual = nomTipusItem;
        // TODO (edgar): treure barra baixes! i revisar si n'hi ha més
        // TODO (edgar): no funciona però l'ítem existeix
        ArrayList<ArrayList<String>> valoracions_raw = controladorPersistencia.obtenirConjuntValoracions(nomTipusItem);
        ArrayList<ArrayList<String>> items_raw = controladorPersistencia.obtenirConjuntItems(nomTipusItemActual, "basic");
        TaulaCSV taulaItems = new TaulaCSV(items_raw);
        itemsActuals = new ConjuntItems(taulaItems, estatPrograma.obteTipusItem(nomTipusItemActual));
        valoracionsTipusItemActual = new ConjuntValoracions();
        TaulaCSV taula_valoracions = new TaulaCSV(valoracions_raw);
        valoracionsTipusItemActual.afegir(taula_valoracions, itemsActuals, estatPrograma.obtenirTotsElsUsuaris());
    }

    public ArrayList<ArrayList<String>> obtenirItems() {
        ArrayList<ArrayList<String>> res = itemsActuals.converteixAArray();
        res.remove(0);
        return res;
    }

    public ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        return new ArrayList<>(estatPrograma.obteTipusItem(nomTipusItemActual).obtenirTipusAtributs().keySet());
    }

    public boolean existeixTipusItemSeleccionat() {
        return nomTipusItemActual != null;
    }

    public boolean afegirItem(Map<String, String> valorsAtributs) throws Exception {
        // Crea un item amb els valors donats i del tipus de l'ítem seleccionat
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // retorna false si no s'ha pogut fer i cert si tot esta be
        TipusItem tipusItem = estatPrograma.obteTipusItem(nomTipusItemActual);
        TreeMap<String, ValorAtribut<?>> atributs = new TreeMap<>();
        for(var x : tipusItem.obtenirTipusAtributs().entrySet()) {
            String valor = valorsAtributs.get(x.getKey());
            // TODO (edgar): solucionar aquest warning
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
    public boolean esborrarItem(String id) {
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

    public Map<String, String> obtenirItem(String id) throws IllegalArgumentException {
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

    public void carregarConjuntItems(String rutaAbsoluta) throws Exception {
        ArrayList<ArrayList<String>> items = controladorPersistencia.llegirCSVQualsevol(rutaAbsoluta);
        TaulaCSV taulaItems = new TaulaCSV(items);
        ConjuntItems nousItems = new ConjuntItems(taulaItems, estatPrograma.obteTipusItem(nomTipusItemActual));
        for (var x : nousItems.obtenirTotsElsElements().entrySet()) {
            itemsActuals.afegir(x.getValue());
        }
    }

    public void esborrarTotsElsItems() {
        itemsActuals = new ConjuntItems(estatPrograma.obteTipusItem(nomTipusItemActual));
    }

    public void editarTipusItem(Map<String, String> relacioNomsTipusAtributs) {
        // TODO
        // relaciona el nom de l'atribut anterior amb el nou (es a dir permet canviar el nom del tipus d'atribut)
        // es una mica horrible perque ha de passar per tots els items per canviar, crec
        // si el nou atribut no té nom ("") vol dir que s'ha eliminat
    }

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

    public double avaluarRecomanacio() {
        return recomanacions.obteDiscountedCumulativeGain()/recomanacions.obteIdealDiscountedCumulativeGain();
    }

    //Esborrar tal cual
    public void esborrarTotesLesValoracions() {
        valoracionsTipusItemActual.esborraTotesLesValoracions();
    }

    //Necessita IDUsuari, IDItem, Rating
    public ArrayList<ArrayList<String>> obtenirValoracions() {
        ArrayList<ArrayList<String>> retornaValoracions =  valoracionsTipusItemActual.convertirAArrayList();
        retornaValoracions.remove(0);
        return retornaValoracions;
    }

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
    public void exportaValoracions(String absolutePath) {

    }
}
