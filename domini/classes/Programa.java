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

    private Programa() {
    }

    public static Programa obtenirInstancia() {
        if (instancia_unica == null) {
            instancia_unica = new Programa();
        }
        return instancia_unica;
    }

    public void tancarSessio() {
        sessio.tancarSessio(this);
    }

    public void iniciarSessio(Usuari usuari) {
        sessio.iniciarSessio(this, usuari);
    }

    public void cambiarEstat(Sessio sessio) {
        this.sessio = sessio;
    }

    public boolean imprimirEstat() {
        return sessio.isSessioIniciada();
    }
}
