package domini.classes;
/**
 * Representa l'estat del programa en el cas en què s'ha iniciat sessió.
 * @author pol.casacuberta
 */

public class SessioIniciada extends Sessio{
    /** Conté l'usuari que ha iniciat la sessió */
    private Usuari usuari = null;

    SessioIniciada(Usuari usuari) {
        this.usuari = usuari;
    }

    @Override
    public void tancarSessio(Programa programa) {
        usuari = null;
        programa.cambiarEstat(new SessioNoIniciada());
    }

    @Override
    public void iniciarSessio(Programa programa, Usuari usuari) throws IllegalStateException{
        throw new IllegalStateException("No es pot iniciar sessió sense abans tancar la sessió");
    }

    @Override
    public boolean isSessioIniciada() {
        return true;
    }

    @Override
    public Usuari obtenirUsuariSessioIniciada() {
        return usuari;
    }
}
