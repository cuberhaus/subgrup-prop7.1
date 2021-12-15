package presentacio.controladors;

import presentacio.vistes.VistaMenuItems;

import java.util.ArrayList;
import java.util.Map;

public class ControladorMenuItems {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuItems instancia;
    private static VistaMenuItems vistaMenuItems;

    private ControladorMenuItems () {
    }

    public static ControladorMenuItems obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuItems();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }

    public ArrayList<Map<String, String>> obtenirItems() {
        return controladorPresentacio.obtenirItems();
    }
}
