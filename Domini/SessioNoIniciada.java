package Domini;
/**
 * Representa l'estat del programa en el cas en que no s'ha iniciat una sessió.
 * @author pol.casacuberta
 */

public class SessioNoIniciada extends Sessio{
    private Usuari usuari = null;

    @Override
    public void tancarSessio(Programa programa) {
        // no cal fer res
    }

    @Override
    public void iniciarSessio(Programa programa,Usuari usuari) {
        this.usuari = usuari;
        programa.changeState(new SessioIniciada(usuari));
    }

    @Override
    public boolean isSessioIniciada() {
        return false;
    }
}
