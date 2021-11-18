package domini.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Representa l'estat del programa.
 * @author pol.casacuberta
 */

// TODO: canviar diagrama de classes
public class Programa {
    /** Conté l'estat de la sessio */
    private Sessio sessio = new SessioNoIniciada();

    /** Conté l'única instància de Programa */
    private static Programa instancia_unica = null;

    /** Conté el conjunt d'usuaris */
//    private Map<Usuari,Usuari> usuaris;
    private ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();

    /** Conté el conjunt de tipus d'items */
    private Map<TipusItem,TipusItem> tipusItems = new HashMap<TipusItem,TipusItem>();

//    /** Conté els conjunts de programa */
//    private ArrayList<conjuntPrograma> conjuntsPrograma;

    /**
     * Constructora per defecte de Programa
     */
    private Programa() {
    }

    /**
     * Constructora de Programa
     * Crea una instància única de Programa
     */
    public static Programa obtenirInstancia() {
        if (instancia_unica == null) {
            instancia_unica = new Programa();
        }
        return instancia_unica;
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
     */
    public void cambiarEstat(Sessio sessio) {
        this.sessio = sessio;
    }

    /**
     * Retorna true si la sessio està iniciada.
     */
    public boolean isSessioIniciada() {
        return sessio.isSessioIniciada();
    }

    /**
     * Retorna l'usuari amb la sessio iniciada.
     */
    public Usuari obtenirUsuariSessioIniciada() {
        return sessio.obtenirUsuariSessioIniciada();
    }

    public boolean conteUsuari(Usuari usuari) {
        return conjuntUsuaris.conte(usuari.obtenirId());
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
     */
    public boolean afegirTipusItem(TipusItem tipusItem) {
        if (tipusItems.containsKey(tipusItem)) {
            return false;
        }
        tipusItems.put(tipusItem,tipusItem);
        return true;
    }

    // TODO: junit comprovar que atribut actiu del usuari del conjunt es false
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
