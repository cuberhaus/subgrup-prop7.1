package domini.controladors;

import domini.classes.*;
import domini.classes.atributs.TipusAtribut;
import domini.classes.csv.TaulaCSV;
import persistencia.controladors.ControladorPersistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe que representa el controlador de domini
 * @author edgar.moreno && pablo.vega
 */
public class ControladorDomini {

    private static ControladorDomini instancia;
    private final ControladorPersistencia controladorPersistencia;

    private final Programa estatPrograma;
    private int ultimIdUsat = 1000000;
    private String nomTipusItemActual = null;
    private ConjuntValoracions valoracionsTipusItemActual = null;
    private ConjuntItems itemsActuals = null;

    private ControladorDomini() {
        controladorPersistencia = ControladorPersistencia.obtenirInstancia();
        estatPrograma = Programa.obtenirInstancia();
    }

    public static ControladorDomini obtenirInstancia() {
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
            if (contrasenya.isBlank()) throw new Exception("La contrasenya es buida o nomes conte espais en buit");
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
    public void tancarSessio() throws Exception {
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

    // TODO: MARIA prerequisit no hi ha tipusitem seleccionat
    public void carregarTipusItem(String nom, String rutaAbsoluta) throws IOException {
        // TODO (edgar): aixo no hauria de seleccionar el tipus d'ítem, només carregar-lo, per tant no hauria d'importar
        // si hi ha un tipus d'ítem seleccionat o no
        // TODO (edgar): no funciona perquè li dono qualsevol arxiu i me l'accepta sense throw exception
        // l'exception que llança si l'arxiu no es correcte hauria de ser una altra excepció que creem nosaltres
        // (illegal argument no esta be i io diria que tampco)
        ArrayList<ArrayList<String>> definicio = controladorPersistencia.llegirCSVQualsevol(rutaAbsoluta);
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : definicio) {
            tipusAtributs.put(fila.get(0), new TipusAtribut(fila.get(1), fila.get(2)));
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        nomTipusItemActual = nom;
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(definicio, nom);
    }

    // TODO: MARIA prerequisit no hi ha tipusitem seleccionat
    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomAValorAtribut) throws IllegalArgumentException, IOException {
        // TODO (edgar): aixo no hauria de seleccionar el tipus d'ítem, només crear-lo, per tant no hauria d'importar
        // si hi ha un tipus d'ítem seleccionat o no
        if (estatPrograma.conteTipusItem(nom)) {
            // TODO: crear excepcio
            throw new IllegalArgumentException("Ja existeix aquest tipus item.");
        }
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : nomAValorAtribut.entrySet()) {
            tipusAtributs.put(fila.getKey(), new TipusAtribut(fila.getValue().x, fila.getValue().y));
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        nomTipusItemActual = nom;
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(tipus.convertirAArrayList(), nom);
    }

    /** Retorna els noms dels conjunts d'items coneguts**/
    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorPersistencia.obtenirNomsDeTotsElsTipusItems();
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

    // TODO: Pablo

    /**
     *
     * @param absolutePath Absolute path to folder where the new file will be created
     */
    public void exportarConjuntDadesUsuari(String absolutePath) throws IOException {
        controladorPersistencia.escriureCSVQualsevol(absolutePath, estatPrograma.obtenirTotsElsUsuaris().obtenirUsuarisCSV());
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
        ArrayList<ArrayList<String>> valoracions_raw = controladorPersistencia.obtenirConjuntValoracions(nomTipusItem);
        ArrayList<ArrayList<String>> items_raw = controladorPersistencia.obtenirConjuntItems(nomTipusItemActual, "basic");
        TaulaCSV taulaItems = new TaulaCSV(items_raw);
        itemsActuals = new ConjuntItems(taulaItems, estatPrograma.obteTipusItem(nomTipusItemActual));
        valoracionsTipusItemActual = new ConjuntValoracions();
        TaulaCSV taula_valoracions = new TaulaCSV(valoracions_raw);
        valoracionsTipusItemActual.afegir(taula_valoracions, itemsActuals, estatPrograma.obtenirTotsElsUsuaris());
    }

    // TODO: Pablo
    public ArrayList<ArrayList<String>> obtenirItems() {
        // TODO
        // retorna una llista d'items
        // cada item es una arraylist amb els atributs
        // la primera columna ha de tenir l'id de l'item
        return new ArrayList<>();
    }

    // TODO: Pablo
    public ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        // TODO
        return new ArrayList<>();
    }

    public boolean existeixTipusItemSeleccionat() {
        return nomTipusItemActual != null;
    }

    // TODO: Pablo
    public boolean afegirItem(Map<String, String> valorsAtributs) {
        // TODO
        // Crea un item amb els valors donats i del tipus de l'ítem seleccionat
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // retorna false si no s'ha pogut fer i cert si tot esta be
        return false;
    }

    // TODO: Pablo, s'han de borrar les seves valoracions!!!!
    public boolean esborrarItem(String id) {
        // TODO
        // Esborra l'ítem amb aquest id
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // l'item es del tipus d'ítem seleccionat
        // retorna fals si es invalid o no s'ha pogut esborrar
        // pot ser una paraula, un numero, estar buit, etc
        return false;
    }

    // TODO: Pablo, s'han de borrar les seves valoracions!!!!
    public Map<String, String> obtenirItem(String id) {
        // TODO
        // Retorna un mapa amb els noms del atributs i el valor dels atributs de l'ítem amb aquest id
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // l'item es del tipus d'ítem seleccionat
        // retorna null si l'id no es valid
        return new HashMap<>();
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
        // carrega un conjunt d'items
        // pero no el selecciona
    }

    public void esborrarTotsElsItems() {
        // TODO
        itemsActuals = new ConjuntItems(estatPrograma.obteTipusItem(nomTipusItemActual));
    }

    public void editarTipusItem(Map<String, String> relacioNomsTipusAtributs) {
        // TODO
        // relaciona el nom de l'atribut anterior amb el nou (es a dir permet canviar el nom del tipus d'atribut)
        // es una mica horrible perque ha de passar per tots els items per canviar, crec
        // si el nou atribut no té nom ("") vol dir que s'ha eliminat
    }

    public ArrayList<String> obtenirRecomanacioCollaborative(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        // TODO
        // retorna conjunt d'ids d'items recomanats
        // utilitza l'usuari que ha iniciat sessio, el tipus d'item seleccionat, els conjunts del tipus d'item seleccionat
        // i el filtre que li passa
        return new ArrayList<>();
    }

    public ArrayList<String> obtenirRecomanacioContentBased(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        // TODO
        // retorna conjunt d'ids d'items recomanats
        // utilitza l'usuari que ha iniciat sessio, el tipus d'item seleccionat, els conjunts del tipus d'item seleccionat
        // i el filtre que li passa
        return new ArrayList<>();
    }

    public ArrayList<String> obtenirRecomanacioHibrida(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        // TODO
        // retorna conjunt d'ids d'items recomanats
        // utilitza l'usuari que ha iniciat sessio, el tipus d'item seleccionat, els conjunts del tipus d'item seleccionat
        // i el filtre que li passa
        return new ArrayList<>();
    }

    public double avaluarRecomanacio() {
        // TODO
        return 0.0;
    }

    public void esborrarTotesLesValoracions() {
        //TODO
    }

    public ArrayList<ArrayList<String>> obtenirValoracions() {
        //TODO
        return new ArrayList<>();
    }

    public void deseleccionarTipusItem() throws IOException {
        controladorPersistencia.borrarTipusItem(nomTipusItemActual);
        controladorPersistencia.guardarTipusItem(estatPrograma.obteTipusItem(nomTipusItemActual).convertirAArrayList(), nomTipusItemActual);
        // TODO (edgar): peta perquè valoracionsTipusItemActual es nul
        controladorPersistencia.guardarConjuntValoracions(valoracionsTipusItemActual.convertirAArrayList(), nomTipusItemActual);
        controladorPersistencia.guardarConjuntItems(itemsActuals.converteixAArray(), nomTipusItemActual, "basic");
        nomTipusItemActual = null;
    }

    public ArrayList<ArrayList<String>> obteUsuaris() {
        return estatPrograma.obtenirTotsElsUsuaris().obtenirLlistaUsuaris();
    }

    public ArrayList<String> importarUsuaris(String absolutePath) throws Exception {
        ArrayList<ArrayList<String>> llistaUsuaris = controladorPersistencia.llegirCSVQualsevol(absolutePath);
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

    public String canviaContrasenyaUsuari(String id, String novaContrasenya) throws Exception {
        Id idUsuari = new Id(Integer.parseInt(id), true);
        if (!estatPrograma.conteUsuari(idUsuari) || !estatPrograma.obtenirUsuari(idUsuari).isActiu()) {
            throw new Exception("L'id d'usuari seleccionat no existeix");
        }

        else {
            if (!novaContrasenya.isBlank()) {
                System.out.println(estatPrograma.obtenirUsuari(idUsuari).obteContrasenya());
                estatPrograma.obtenirUsuari(idUsuari).setContrasenya(novaContrasenya);
            }
        }

        System.out.println(estatPrograma.obtenirUsuari(idUsuari).obteContrasenya());
        return estatPrograma.obtenirUsuari(idUsuari).obteContrasenya();
    }

    public void canviaNomUsuari(String id, String nouNom) throws Exception {
        Id idUsuari = new Id(Integer.parseInt(id), true);
        if (!estatPrograma.conteUsuari(idUsuari) || !estatPrograma.obtenirUsuari(idUsuari).isActiu()) {
            throw new Exception("L'id d'usuari seleccionat no existeix");
        }

        else {
            if (!nouNom.isBlank()) {
                estatPrograma.obtenirUsuari(idUsuari).setNom(nouNom);
            }
        }
    }
}
