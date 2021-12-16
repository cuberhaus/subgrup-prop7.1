package domini.classes;

/**
 * Representa l'estat de la sessió en el cas en què no s'ha iniciat una sessió.
 *
 * @author pol.casacuberta
 */

public class SessioNoIniciada extends Sessio {
    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     *
     * @throws IllegalStateException si l'estat es sessioNoIniciada
     */
    @Override
    public void tancarSessio(Programa programa) throws IllegalStateException {
        throw new IllegalStateException("No es pot tancar la sessió d'una sessió ja tancada");
    }

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     *
     * @param usuari Usuari amb el qual iniciem la sessio.
     * @throws IllegalStateException si l'estat és sessioIniciada
     */
    @Override
    public void iniciarSessio(Programa programa, Usuari usuari) {
        programa.cambiarEstat(new SessioIniciada(usuari));
    }

    /**
     * Retorna true si la sessio està iniciada.
     */
    @Override
    public boolean isSessioIniciada() {
        return false;
    }

    /**
     * Retorna l'usuari amb la sessio iniciada.
     *
     * @throws IllegalStateException si l'estat es sessioNoIniciada
     */
    @Override
    public Usuari obtenirUsuariSessioIniciada() throws IllegalStateException {
        throw new IllegalStateException("L'estat de la sessio no és SessioIniciada");
    }
}
