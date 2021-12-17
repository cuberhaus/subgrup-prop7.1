package domini.controladors;

import domini.classes.*;
import domini.classes.atributs.TipusAtribut;
import domini.classes.csv.TaulaCSV;
import persistencia.controladors.ControladorPersistencia;

import java.io.IOException;
import java.util.*;

/**
 * Classe que representa el controlador de domini
 * @author edgar.moreno && pablo.vega
 */
public class ControladorDomini {

    private static ControladorDomini instancia;
    private final ControladorPersistencia controladorPersistencia;

    private final Programa estatPrograma;
    private int ultimIdUsat = 0;
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
    public int obtenirSessio() {
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

    public boolean existeixUsuari(int id) {
        Id id_bo = new Id(id, true);
        return estatPrograma.conteUsuari(id_bo)&& estatPrograma.obtenirUsuari(id_bo).isActiu();
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
    public void afegirUsuari(String nom, String contrasenya) {
        Id id = obteIdUsuariDisponible();
        estatPrograma.afegirUsuari(new Usuari(id, nom, contrasenya));
    }

    /**
     * Esborra usuari, falta parametre del conjunt
     * @param id id del usuari
     */
    public void esborrarUsuari(int id) {
        estatPrograma.esborraUsuari(new Id(id, true));
    }

    /**
     * Tanca la sessio de programa
     */
    public void tancarSessio() {
        this.estatPrograma.tancarSessio();
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        valoracionsTipusItemActual.afegir(new Valoracio(Double.parseDouble(valor), us, item));
    }

    public boolean existeixValoracio(String usuariId, String itemId) {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        return valoracionsTipusItemActual.conte(us, item);
    }

    public void esborraValoracio(String usuariId, String itemId) {
        Usuari us = estatPrograma.obtenirUsuari(new Id(Integer.parseInt(usuariId)));
        Item item = itemsActuals.obtenir(new Id(Integer.parseInt(itemId)));
        valoracionsTipusItemActual.esborrar(valoracionsTipusItemActual.obte(us, item));
    }

    public void editarValoracio(String usuariId, String itemId, String valor) {
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

    // TODO: ficar pre o no? Ara mateix avisar a la maria
    public void carregarTipusItem(String rutaAbsoluta, String nom) throws IOException {
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

    // TODO: ficar pre o no, ara mateix avisar a la maria
    public void crearTipusItem(String nom, Map<String, Pair<String, String>> nomValorAtributAValorDistancia) throws IOException {
        TreeMap<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (var fila : nomValorAtributAValorDistancia.entrySet()) {
            tipusAtributs.put(fila.getKey(), new TipusAtribut(fila.getValue().x(), fila.getValue().y()));
        }
        TipusItem tipus = new TipusItem(nom, tipusAtributs);
        nomTipusItemActual = nom;
        estatPrograma.afegirTipusItem(nom, tipus);
        controladorPersistencia.guardarTipusItem(tipus.converteixAArray(), nom);
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

    public void exportarConjuntDadesUsuari(String absolutePath) {
        //TODO
    }

    public void esborraConjuntUsuaris() {
        //TODO
    }

    public String obtenirNomTipusItemSeleccionat() {
        // Retorna null si no hi ha cap tipus item seleccionat
        return nomTipusItemActual;
    }

    public void esborrarTipusItemSeleccionat() {
        // TODO
        // el posa a null
    }

    public void seleccionarTipusItem(String nomTipusItem) throws IOException {
        // TODO
        // marca com a tipus item seleccionat el que te aquest nom
        // en principi esta carregat, si cal gestionar excepcions poseume un todo a la vista please

        if (nomTipusItemActual != null) {
            // TODO ha de guardar lanterior
        }
        ArrayList<ArrayList<String>> tipus_item_raw = controladorPersistencia.obtenirTipusItem(nomTipusItem);
    }

    public ArrayList<ArrayList<String>> obtenirItems() {
        // TODO
        // retorna una llista d'items
        // cada item es una arraylist amb els atributs
        // la primera columna ha de tenir l'id de l'item
        return new ArrayList<>();
    }

    public ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        // TODO
        return new ArrayList<>();
    }

    public boolean existeixTipusItemSeleccionat() {
        // TODO
        // retorna true si hi ha un tipus item seleccionat
        return true;
    }

    public boolean afegirItem(Map<String, String> valorsAtributs) {
        // TODO
        // Crea un item amb els valors donats i del tipus de l'ítem seleccionat
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // retorna false si no s'ha pogut fer i cert si tot esta be
        return false;
    }

    public boolean esborrarItem(String id) {
        // TODO
        // Esborra l'ítem amb aquest id
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // l'item es del tipus d'ítem seleccionat
        // retorna fals si es invalid o no s'ha pogut esborrar
        // pot ser una paraula, un numero, estar buit, etc
        return false;
    }

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

    public void carregarConjuntItems(String rutaAbsoluta) {
        // TODO
        // carrega un conjunt d'items
    }

    public void esborrarTotsElsItems() {
        // TODO
        // hi ha un tipus d'ítem seleccionat pero millor comprovar
        // esborra tots els items del tipus d'ítem seleccionat
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
}
