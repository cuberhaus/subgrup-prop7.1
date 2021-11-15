package domini.classes;

import java.util.Set;

/**
 * Representa l'estat del programa.
 * @author pol.casacuberta
 */
public class Programa {
    private Sessio sessio;
    private static Programa instancia_unica = null;
    private Set<Usuari> usuaris;
    private Set<TipusItem> tipusItems;
//    ArrayList<conjuntPrograma> conjuntsPrograma;

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

    public boolean isSessioIniciada() {
        return sessio.isSessioIniciada();
    }
}
