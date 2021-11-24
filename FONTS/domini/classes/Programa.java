package domini.classes;

import java.util.*;

/**
 * Representa l'estat del programa.
 * @author pol.casacuberta
 */

public class Programa {
    /** Conté l'estat de la sessio */
    private Sessio sessio = new SessioNoIniciada();

    /** Conté l'única instància de Programa */
    private static Programa instancia_unica = null;

    /** Conté el conjunt d'usuaris */
    private ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();

    /** Conté el conjunt de tipus d'items */
    private HashSet<TipusItem> tipusItems = new HashSet<>();

    /**
     * Constructora per defecte de Programa
     */
    private Programa() {
    }

    /**
     * Constructora de Programa
     * Crea una instància única de Programa
     * @return <code>Programa</code>
     */
    public static Programa obtenirInstancia() {
        if (instancia_unica == null) {
            instancia_unica = new Programa();
        }
        return instancia_unica;
    }

    /**
     * Restaura les variables a l'estat inicial
     */
    public void reset() {
        sessio = new SessioNoIniciada();
        instancia_unica = null;
        conjuntUsuaris = new ConjuntUsuaris();
        tipusItems = new HashSet<>();
    }

    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     */
    public void tancarSessio() {
        sessio.tancarSessio(this);
    }

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     * @param usuari Usuari amb el qual iniciem la sessio.
     */
    public void iniciarSessio(Usuari usuari) {
        sessio.iniciarSessio(this, usuari);
    }

    /**
     * Canvia l'estat de la sessió a SessioTancada
     * @param sessio estat de la sessio
     */
    public void cambiarEstat(Sessio sessio) {
        this.sessio = sessio;
    }

    /**
     * Retorna true si la sessio està iniciada.
     * @return <code>boolean</code>
     */
    public boolean isSessioIniciada() {
        return sessio.isSessioIniciada();
    }

    /**
     * Retorna l'usuari amb la sessio iniciada.
     * @return Usuari
     */
    public Usuari obtenirUsuariSessioIniciada() {
        return sessio.obtenirUsuariSessioIniciada();
    }

    /**
     * Consultora dona true si l'usuari passat com a parametre existeix en el conjunt
     * @param usuari usuari
     * @return booleà
     */
    public boolean conteUsuari(Usuari usuari) {
        return conjuntUsuaris.conte(usuari.obtenirId());
    }

    /**
     * Consultora obté l'usuari amb l'id passat com a paràmetre
     * @param idUsuari id de l'usuari
     * @return Usuari amb l'id desitjat
     */
    public Usuari obtenirUsuari(Id idUsuari) {
        return conjuntUsuaris.obtenir(idUsuari);
    }

    /**
     * Afegeix un usuari al conjunt d'usuaris.
     * @param  usuari el paràmetre s'ha afegit al conjunt si no hi era abans.
     */
    public void afegirUsuari(Usuari usuari) {
        conjuntUsuaris.afegir(usuari);
    }

    /**
     * Afegeix un tipus d'item al conjunt de tipus d'items.
     * Retorna true si s'ha afegit correctament, retorna false si ja hi era
     * @param  tipusItem el paràmetre s'ha afegit al conjunt si no hi era abans.
     * @return boolean
     */
    public boolean afegirTipusItem(TipusItem tipusItem) {
        if (tipusItems.contains(tipusItem)) {
            return false;
        }
        tipusItems.add(tipusItem);
        return true;
    }

    /**
     * Marca com a no actiu un usuari del conjunt d'usuaris.
     * @param usuari el paràmetre s'ha marcat com a no actiu.
     */
    public void esborraUsuari(Usuari usuari) {
        conjuntUsuaris.esborrar(usuari);
    }

//    /**
//     * Afegeix un conjunt de Programa al conjunt de conjunts de programa
//     * Retorna true si s'ha afegit correctament, retorna false si ja hi era
//     * @param  ConjuntPrograma el paràmetre s'ha afegit al conjunt si no hi era abans.
//     */
//    public void afegirConjuntPrograma(ConjuntPrograma) {
//    }
//    /**
//     * Esborra un conjunt de Programa del conjunt de conjunts de programa.
//     * Retorna true si s'ha esborrat correctament, retorna false si no hi era
//     * @param  ConjuntPrograma el conjunt de Programa s'ha esborrat del conjunt, si hi era.
//     */
//    public void esborrarConjuntPrograma(ConjuntPrograma) {
//    }
}