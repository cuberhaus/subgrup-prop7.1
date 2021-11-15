package Domini;
/**
 * Representa l'estat del programa en el cas en que no s'ha iniciat una sessió.
 * @author pol.casacuberta
 */

public class SessioNoIniciada implements Sessio{
    @Override
    public void tancarSessio() {

    }

    @Override
    public void iniciarSessio() {

    }

    @Override
    public boolean isSessioIniciada() {
        return false;
    }
}
