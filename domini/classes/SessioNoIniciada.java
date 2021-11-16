package domini.classes;
/**
 * Representa l'estat del programa en el cas en que no s'ha iniciat una sessió.
 * @author pol.casacuberta
 */

public class SessioNoIniciada extends Sessio{
    private Usuari usuari;

    @Override
    public void tancarSessio(Programa programa) {
        // no cal fer res
    }

    @Override
    public void iniciarSessio(Programa programa,Usuari usuari) {
        this.usuari = usuari;
        programa.cambiarEstat(new SessioIniciada(usuari));
    }

    @Override
    public boolean isSessioIniciada() {
        return false;
    }

    @Override
    public Usuari obtenirUsuariSessioIniciada() throws IllegalStateException{
        throw new IllegalStateException("L'estat de la sessio no és SessioIniciada");
    }
}
