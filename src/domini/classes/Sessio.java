package domini.classes;

/**
 * Representa l'estat del programa.
 * @author pol.casacuberta
 */

public abstract class Sessio {
    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     * @throws IllegalStateException si l'estat es sessioNoIniciada
     */
    abstract void tancarSessio(Programa programa);

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     * @param usuari Usuari amb el qual iniciem la sessio.
     * @throws IllegalStateException si l'estat es sessioIniciada
     */
    abstract void iniciarSessio(Programa programa, Usuari usuari);

    /**
     * Retorna true si la sessio està iniciada.
     */
    abstract boolean isSessioIniciada();

    /**
     * Retorna l'usuari amb la sessio iniciada.
     * @throws IllegalStateException si l'estat es sessioNoIniciada
     */
    abstract public Usuari obtenirUsuariSessioIniciada();
}