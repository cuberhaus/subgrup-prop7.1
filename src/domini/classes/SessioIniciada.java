package domini.classes;
/**
 * Representa l'estat de la sessio en el cas en què s'ha iniciat sessió.
 * @author pol.casacuberta
 */

public class SessioIniciada extends Sessio{
    /** Conté l'usuari que ha iniciat la sessió */
    private Usuari usuari = null;

    SessioIniciada(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     * @throws IllegalStateException si l'estat es sessioNoIniciada
     */
    @Override
    public void tancarSessio(Programa programa) {
        usuari = null;
        programa.cambiarEstat(new SessioNoIniciada());
    }

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     * @param usuari Usuari amb el qual iniciem la sessio.
     * @throws IllegalStateException si l'estat es sessioIniciada
     */
    @Override
    public void iniciarSessio(Programa programa, Usuari usuari) throws IllegalStateException{
        throw new IllegalStateException("No es pot iniciar sessió sense abans tancar la sessió");
    }

    /**
     * Retorna true si la sessio està iniciada.
     */
    @Override
    public boolean isSessioIniciada() {
        return true;
    }

    /**
     * Retorna l'usuari amb la sessio iniciada.
     * @throws IllegalStateException si l'estat es sessioNoIniciada
     */
    @Override
    public Usuari obtenirUsuariSessioIniciada() {
        return usuari;
    }
}
