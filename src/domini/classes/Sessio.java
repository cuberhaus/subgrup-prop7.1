package domini.classes;

/**
 * Representa l'estat de la sessió
 * @author pol.casacuberta
 */

public abstract class Sessio {
    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     * @throws IllegalStateException si l'estat es sessioNoIniciada
     * @param programa c
     */
    abstract void tancarSessio(Programa programa);

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     * @param usuari Usuari amb el qual iniciem la sessio.
     * @param programa c
     * @throws IllegalStateException si l'estat es sessioIniciada
     */
    abstract void iniciarSessio(Programa programa, Usuari usuari);

    /**
     * Retorna true si la sessio està iniciada.
     * @return boolean
     */
    abstract boolean isSessioIniciada();

    /**
     * Retorna l'usuari amb la sessio iniciada.
     * @throws IllegalStateException si l'estat es sessioNoIniciada
     * @return c
     */
    abstract public Usuari obtenirUsuariSessioIniciada();
}