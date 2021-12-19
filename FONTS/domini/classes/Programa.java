package domini.classes;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Representa l'estat del programa.
 *
 * @author pol.casacuberta
 */

public class Programa {
    /**
     * Conté l'única instància de Programa
     */
    private static Programa instancia = null;
    /**
     * Conté l'estat de la sessio
     */
    private Sessio sessio = new SessioNoIniciada();
    /**
     * Conté el conjunt d'usuaris
     */
    private ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();

    /**
     * Conté el conjunt de tipus d'ítems
     */
    private TreeMap<String, TipusItem> tipusItems = new TreeMap<>();

    /**
     * Constructora per defecte de Programa
     */
    private Programa() {
    }

    /**
     * Constructora de Programa
     * Crea una instància única de Programa
     *
     * @return <code>Programa</code>
     */
    public static Programa obtenirInstancia() {
        if (instancia == null) {
            instancia = new Programa();
        }
        return instancia;
    }

    /**
     * Restaura les variables a l'estat inicial
     */
    public void reset() {
        sessio = new SessioNoIniciada();
        instancia = null;
        conjuntUsuaris = new ConjuntUsuaris();
        tipusItems = new TreeMap<>();
    }

    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     */
    public void tancarSessio() throws Exception {
        sessio.tancarSessio(this);
    }

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     *
     * @param usuari Usuari amb el qual iniciem la sessió.
     */
    public void iniciarSessio(Usuari usuari) throws Exception {
        sessio.iniciarSessio(this, usuari);
    }

    /**
     * Canvia l'estat de la sessió a SessioTancada
     *
     * @param sessio estat de la sessió
     */
    public void cambiarEstat(Sessio sessio) {
        this.sessio = sessio;
    }

    /**
     * Retorna true si la sessió està iniciada.
     *
     * @return <code>boolean</code>
     */
    public boolean isSessioIniciada() {
        return sessio.isSessioIniciada();
    }

    /**
     * Retorna l'usuari amb la sessió iniciada.
     *
     * @return Usuari
     */
    public Usuari obtenirUsuariSessioIniciada() throws Exception {
        return sessio.obtenirUsuariSessioIniciada();
    }

    /**
     * Consultora dona true si l'usuari passat com a paràmetre existeix en el conjunt
     *
     * @param id id
     * @return booleà
     */
    public boolean conteUsuari(Id id) {
        return conjuntUsuaris.conte(id);
    }

    /**
     * Consultora obté l'usuari amb l'id passat com a paràmetre
     *
     * @param idUsuari id de l'usuari
     * @return Usuari amb l'id desitjat
     */
    public Usuari obtenirUsuari(Id idUsuari) throws Exception{
        return conjuntUsuaris.obtenir(idUsuari);
    }

    public ConjuntUsuaris obtenirTotsElsUsuaris() {
        return conjuntUsuaris;
    }

    /**
     * Afegeix un usuari al conjunt d'usuaris.
     *
     * @param usuari el paràmetre s'ha afegit al conjunt si no hi era abans.
     */
    public void afegirUsuari(Usuari usuari) {
        conjuntUsuaris.afegir(usuari);
    }

    /**
     * Afegeix un tipus d'ítem al conjunt de tipus d'ítems.
     * Retorna true si s'ha afegit correctament, retorna false si ja hi era
     *
     * @param tipusItem el paràmetre s'ha afegit al conjunt si no hi era abans.
     */
    public void afegirTipusItem(String nom, TipusItem tipusItem) {
        tipusItems.put(nom, tipusItem);
    }

    public boolean conteTipusItem(String nom) {
        return tipusItems.containsKey(nom);
    }

    public void esborraTipusItem(String nom) {
        tipusItems.remove(nom);
    }

    public TipusItem obteTipusItem(String nom) {
        return tipusItems.get(nom);
    }
    /**
     * Marca com a no actiu un usuari del conjunt d'usuaris.
     *
     * @param id el paràmetre s'ha marcat com a no actiu.
     */
    public void esborraUsuari(Id id) {
        conjuntUsuaris.esborrar(id);
    }

    public void esborraTotsUsuaris() {conjuntUsuaris.esborrarTotsUsuaris();}

    public ArrayList<String> obteTipusItem() {
        return new ArrayList<>(tipusItems.keySet());
    }
}
