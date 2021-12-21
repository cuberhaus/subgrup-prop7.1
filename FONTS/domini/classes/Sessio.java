package domini.classes;

import excepcions.SessioNoIniciadaException;

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
     * @throws Exception si l'estat és sessioNoIniciada
     */
    abstract void tancarSessio(Programa programa) throws SessioNoIniciadaException;

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     *
     * @param usuari   Usuari amb el qual iniciem la sessió.
     * @param programa Instancia de programa
     * @throws Exception si l'estat és sessioIniciada
     */
    abstract void iniciarSessio(Programa programa, Usuari usuari) throws Exception;

    /**
     * Retorna true si la sessió està iniciada.
     *
     * @return boolean
     */
    abstract boolean isSessioIniciada();

    /**
     * Retorna l'usuari amb la sessió iniciada.
     *
     * @return Usuari amb sessió iniciada
     * @throws Exception si l'estat és sessioNoIniciada
     */
    abstract public Usuari obtenirUsuariSessioIniciada() throws Exception;
}