package domini.classes;

/**
 * Representa l'estat de la sessió
 *
 * @author pol.casacuberta
 */

public abstract class Sessio {
    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     *
     * @param programa Instància de programa
     * @throws IllegalStateException si l'estat és sessioNoIniciada
     */
    abstract void tancarSessio(Programa programa);

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     *
     * @param usuari   Usuari amb el qual iniciem la sessio.
     * @param programa Instancia de programa
     * @throws IllegalStateException si l'estat és sessioIniciada
     */
    abstract void iniciarSessio(Programa programa, Usuari usuari);

    /**
     * Retorna true si la sessio està iniciada.
     *
     * @return boolean
     */
    abstract boolean isSessioIniciada();

    /**
     * Retorna l'usuari amb la sessio iniciada.
     *
     * @return Usuari amb sessio iniciada
     * @throws IllegalStateException si l'estat és sessioNoIniciada
     */
    abstract public Usuari obtenirUsuariSessioIniciada();
}