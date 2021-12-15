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

/** Classe que representa el controlador de domini*/
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
    public ArrayList<ArrayList<String>> llegirCSV(String ubicacio) throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        return lector.llegirCSV(ubicacio);
    }

    /**
     * Funció que donada la ubicació on es vol escriure el CSV i el seu contingut, escriu el fitxer.
     * @param ubicacio ubicacio <code>String</code> que conté el destí de les dades.
     * @param taula <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> del contingut del fitxer CSV a escriure.
     * @throws IOException s'ha produït un error en l'escriptura
     */
    public void escriptorCSV(String ubicacio, ArrayList<ArrayList<String>> taula) throws IOException {
        EscriptorDeCSV escriptor = new EscriptorDeCSV();
        escriptor.escriureCSV(ubicacio, taula);
    }

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
     * Afegeix un Usuari, falta donar un paràmetre del conjunt on es vol afegir aquest usuari
     * @param id id del usuari
     * @param contrasenya contrasenya del usuari
     * @param nom nom del usuari
     */
    public void afegirUsuari(int id, String contrasenya, String nom) {
        usuaris.afegir(new Usuari(new Id(id, true), nom, contrasenya));
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

    public ArrayList<Map<String, String>> obtenirItems() {
        // TODO
        // retorna una llista d'items
        // cada item es un map amb el nom de l'atribut i el seu valor
        return new ArrayList<>();
    }
}
