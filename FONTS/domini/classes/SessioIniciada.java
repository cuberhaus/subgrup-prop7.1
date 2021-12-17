package domini.classes;

/**
 * Representa l'estat de la sessió en el cas en què s'ha iniciat sessió.
 *
 * @author pol.casacuberta
 */

public class SessioIniciada extends Sessio {
    /**
     * Conté l'usuari que ha iniciat la sessió
     */
    private Usuari usuari;

    public SessioIniciada(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     *
     * @throws Exception si l'estat és sessioNoIniciada
     */
    @Override
    public void tancarSessio(Programa programa) throws Exception {
        usuari = null;
        programa.cambiarEstat(new SessioNoIniciada());
    }

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     *
     * @param usuari Usuari amb el qual iniciem la sessió.
     * @throws Exception si l'estat és sessioIniciada
     */
    @Override
    public void iniciarSessio(Programa programa, Usuari usuari) throws Exception {
        throw new Exception("No es pot iniciar sessió sense abans tancar la sessió");
    }

    /**
     * Retorna true si la sessió està iniciada.
     */
    @Override
    public boolean isSessioIniciada() {
        return true;
    }

    /**
     * Retorna l'usuari amb la sessió iniciada.
     *
     * @throws Exception si l'estat és sessioNoIniciada
     */
    @Override
    public Usuari obtenirUsuariSessioIniciada() throws Exception{
        return usuari;
    }
}
