package domini.classes;

import excepcions.SessioIniciadaException;
import excepcions.SessioNoIniciadaException;

/**
 * Representa l'estat de la sessió en el cas en què no s'ha iniciat una sessió.
 *
 * @author pol.casacuberta
 */

public class SessioNoIniciada extends Sessio {
    /**
     * Canvia l'estat de la sessió a SessioNoIniciada
     *
     * @throws SessioNoIniciadaException si l'estat és sessioNoIniciada
     */
    @Override
    public void tancarSessio(Programa programa) throws SessioNoIniciadaException {
        throw new SessioNoIniciadaException("No es pot tancar la sessió d'una sessió ja tancada");
    }

    /**
     * Canvia l'estat de la sessió a SessioIniciada amb el paràmetre usuari.
     *
     * @param usuari Usuari amb el qual iniciem la sessió.
     */
    @Override
    public void iniciarSessio(Programa programa, Usuari usuari) {
        programa.cambiarEstat(new SessioIniciada(usuari));
    }

    /**
     * Retorna true si la sessió està iniciada.
     */
    @Override
    public boolean isSessioIniciada() {
        return false;
    }

    /**
     * Retorna l'usuari amb la sessió iniciada.
     *
     * @throws SessioNoIniciadaException si l'estat és sessioNoIniciada
     */
    @Override
    public Usuari obtenirUsuariSessioIniciada() throws SessioNoIniciadaException {
        throw new SessioNoIniciadaException("L'estat de la sessio no és SessioIniciada");
    }
}
