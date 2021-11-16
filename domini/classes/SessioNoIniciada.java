package domini.classes;
/**
 * Representa l'estat del programa en el cas en que no s'ha iniciat una sessió.
 * @author pol.casacuberta
 */

public class SessioNoIniciada extends Sessio{
    @Override
    public void tancarSessio(Programa programa) throws IllegalStateException{
        throw new IllegalStateException("No es pot tancar la sessió d'una sessió ja tancada");
    }

    @Override
    public void iniciarSessio(Programa programa,Usuari usuari) {
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
