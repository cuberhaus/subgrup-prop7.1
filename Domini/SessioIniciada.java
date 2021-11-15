package Domini;
/**
 * Representa l'estat del programa en el cas en què s'ha iniciat sessió.
 * @author pol.casacuberta
 */

public class SessioIniciada implements Sessio{
    @Override
    public void tancarSessio() {

    }

    @Override
    public void iniciarSessio() {

    }

    @Override
    public boolean isSessioIniciada() {
        return true;
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

