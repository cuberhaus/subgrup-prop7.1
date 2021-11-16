package domini.classes;
/**
 * Representa l'estat del programa en el cas en què s'ha iniciat sessió.
 * @author pol.casacuberta
 */

public class SessioIniciada extends Sessio{
    private Usuari usuari = null;

    SessioIniciada(Usuari usuari) {
        this.usuari = usuari;
    }

    @Override
    public void tancarSessio(Programa programa) {
        usuari = null;
        programa.cambiarEstat(new SessioNoIniciada());
    }

    @Override
    public void iniciarSessio(Programa programa, Usuari usuari) {
        this.usuari = usuari;
        programa.cambiarEstat(new SessioIniciada(usuari));
    }

    @Override
    public boolean isSessioIniciada() {
        return true;
    }

    @Override
    public Usuari obtenirUsuariSessioIniciada() throws IllegalStateException{
        return usuari;
    }
//    private static SessioIniciada sessio_unica = null;
//
//    private SessioIniciada() {
//    }
//
//    public static SessioIniciada getInstance() {
//        if (sessio_unica == null) sessio_unica = new SessioIniciada();
//        return sessio_unica;
//    }
}

//public enum SessioIniciada implements Programa{
//    INSTANCE;
//
//    private SessioIniciada() {
//    }
//}

