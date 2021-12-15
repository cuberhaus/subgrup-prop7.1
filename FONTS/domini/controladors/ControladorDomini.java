package domini.controladors;

import domini.classes.ConjuntUsuaris;
import domini.classes.Id;
import domini.classes.Usuari;
import persistencia.classes.EscriptorDeCSV;
import persistencia.classes.LectorDeCSV;
import persistencia.controladors.ControladorPersistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa el controlador de domini
 * @author edgar.moreno && pablo.vega
 */
public class ControladorDomini {

    private static ControladorDomini instancia;
    private final ControladorPersistencia controladorPersistencia;

    ConjuntUsuaris usuaris;

    private ControladorDomini() {
        controladorPersistencia = ControladorPersistencia.obtenirInstancia();
        usuaris = new ConjuntUsuaris();
    }

    public static ControladorDomini obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorDomini();
        }
        return instancia;
    }

    /**
     * Funció que donada la ubicació de l'arxiu CSV, et retorna el contingut en forma de llista.
     * @param ubicacio <code>String</code> que conté la ubicació de l'arxiu.
     * @return <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> del contingut del fitxer CSV
     * @throws IOException s'ha produït un error en la lectura
     */
    /*public ArrayList<ArrayList<String>> llegirCSV(String ubicacio) throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        return lector.llegirCSV(ubicacio);
    }*/

    /**
     * Funció que donada la ubicació on es vol escriure el CSV i el seu contingut, escriu el fitxer.
     * @param ubicacio ubicacio <code>String</code> que conté el destí de les dades.
     * @param taula <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> del contingut del fitxer CSV a escriure.
     * @throws IOException s'ha produït un error en l'escriptura
     */
    /*public void escriptorCSV(String ubicacio, ArrayList<ArrayList<String>> taula) throws IOException {
        EscriptorDeCSV escriptor = new EscriptorDeCSV();
        escriptor.escriureCSV(ubicacio, taula);
    }*/

    /**
     * Obté l'id de l'usuari que ha iniciat la sessió
     * @return retorna 0 en cas que no hi hagi sessió iniciada, altrament retorna l'id de l'usuari
     */
    public int obtenirSessio() {
        // TODO: add logic
        return 0;
    }

    /**
     * Inicia sessió amb l'usuari de id idSessio si la contrasenya és correcte
     * @param idSessio id de l'usuari que inicia la sessió
     * @param contrasenya contrasenya de l'usuari
     */
    public void iniciarSessio(int idSessio, String contrasenya) {
        // TODO: add logic
    }

    public boolean existeixUsuari(int id) {
        Id id_bo = new Id(id, true);
        return usuaris.conte(id_bo) && usuaris.obtenir(id_bo).isActiu();
    }

    /**
     * Afegeix un Usuari que encara no existeix.
     * @param nom nom del usuari
     * @param contrasenya contrasenya del usuari
     */
    public void afegirUsuari(String nom, String contrasenya) {
        //TODO:
    }

    /**
     * Esborra usuari, falta parametre del conjunt
     * @param id id del usuari
     */
    public void esborrarUsuari(int id) {
        usuaris.esborrar(new Id(id, true));
    }

    /**
     * Tanca la sessio de programa
     */
    public void tancarSessio() {
        //TODO:
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) {
        //TODO:
    }

    public boolean existeixValoracio(String usuariId, String itemId) {
        //TODO:
        return false;
    }

    public void esborraValoracio(String usuariId, String itemId) {
        //TODO:
    }

    public void editarValoracio(String usuariId, String itemId, String valor) {
        //TODO:
    }

    public void carregaConjuntValoracions(String pathAbsolut) {
        //TODO:
    }

    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorPersistencia.obtenirNomsConjunts();
    }

    public void exportarConjuntDades(String pathConjunt) {
    }

    public void esborraConjunt(String conjuntaEsborrar) {
    }

    public boolean carregarTipusItem(String rutaAbsoluta) {
        // TODO
        return false;
    }

    public boolean afegirTipusItem(String nom, Map<String, String> valorsTipusAtributs, Map<String, String> distanciesTipusAtributs) {
        // TODO
        // Pot o retornar true/false o llançar excepció. Si llança excepció crec que és millor perquè podem detectar
        // si no funciona perquè ja n'hi ha un amb el mateix nom o si no funciona per algun altre motiu.
        return false;
    }

    /** Retorna els noms dels conjunts d'items coneguts**/
    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorPersistencia.obtenirNomsConjunts();
    }

    public void esborrarTipusItem(String nomTipusItem) {
        // TODO
    }

    public Map<String, String> obtenirValorsTipusAtributs(String nomTipusItem) {
        // TODO
        return new HashMap<>();
    }

    public Map<String, String> obtenirDistanciesTipusAtributs(String nomTipusItem) {
        // TODO
        return new HashMap<>();
    }

    public boolean isSessioIniciada() {
        //TODO
        return false;
    }

    public void exportarConjuntDadesUsuari(String absolutePath) {
        //TODO
    }

    public void esborraConjuntUsuaris() {
        //TODO
    }

    public String obtenirNomTipusItemSeleccionat() {
        // TODO
        // Retorna null si no hi ha cap tipus item seleccionat
        return null;
    }

    public void esborrarTipusItemSeleccionat() {
        // TODO
        // el posa a null
    }

    public void seleccionarTipusItem(String nomTipusItem) {
        // TODO
        // marca com a tipus item seleccionat el que te aquest nom
        // en principi esta carregat, si cal gestionar excepcions poseume un todo a la vista please
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
}
