package presentacio.controladors;

import presentacio.vistes.VistaMenuRecomanacions;

public class ControladorMenuRecomanacions {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuRecomanacions instancia;
    private static VistaMenuRecomanacions vistaMenuRecomanacions;

    private ControladorMenuRecomanacions() {
    }

    public static ControladorMenuRecomanacions obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuRecomanacions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }
}
