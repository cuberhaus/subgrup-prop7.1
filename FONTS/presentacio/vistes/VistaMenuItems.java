package presentacio.vistes;

import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;

public class VistaMenuItems extends JPanel {
    private static VistaMenuItems instancia;
    private static ControladorMenuItems controladorMenuItems;

    private VistaMenuItems() {
    }

    public static VistaMenuItems obtenirInstancia() {
        if (instancia == null) {
            instancia = new VistaMenuItems();
            controladorMenuItems = ControladorMenuItems.obtenirInstancia();
            inicialitzarMenuItems();
        }
        return instancia;
    }

    private static void inicialitzarMenuItems() {
        instancia.setLayout(new BoxLayout(instancia, BoxLayout.Y_AXIS));
    }
}
