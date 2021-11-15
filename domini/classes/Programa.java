package domini.classes;

import java.util.Set;

/**
 * Representa l'estat del programa.
 * @author pol.casacuberta
 */

public class Programa {
    private Sessio sessio;
    private Set<Usuari> usuaris;
    private Set<TipusItem> tipusItems;
//    ArrayList<conjuntPrograma> conjuntsPrograma;
    public void tancarSessio() {
        sessio.tancarSessio(this);
    }

    public void iniciarSessio(Usuari usuari) {
        sessio.iniciarSessio(this, usuari);
    }

    public void changeState(Sessio sessio) {
        this.sessio = sessio;
    }

    public boolean printStatus() {
        return sessio.isSessioIniciada();
    }
}
