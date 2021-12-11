package presentacio.controladors;

import presentacio.vistes.GestioValoracions;

/**
 * @author pol.casacuberta
 */

public class ControladorGestioValoracions {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorGestioValoracions instanciaUnica;
    private static GestioValoracions gestioValoracions;

    private ControladorGestioValoracions() {
    }

    public static ControladorGestioValoracions obtenirInstancia() {
        if (instanciaUnica == null){
            instanciaUnica = new ControladorGestioValoracions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            gestioValoracions = new GestioValoracions();
        }
        return instanciaUnica;
    }

    public GestioValoracions getGestioValoracions() {
        return gestioValoracions;
    }

}
