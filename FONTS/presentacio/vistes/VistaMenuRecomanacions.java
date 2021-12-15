package presentacio.vistes;

import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;

public class VistaMenuRecomanacions extends JPanel {
    private static VistaMenuRecomanacions instancia;
    private static ControladorMenuItems controladorMenuItems;

    private VistaMenuRecomanacions() {
    }

    public static VistaMenuRecomanacions obtenirInstancia() {
        if (instancia == null) {
            instancia = new VistaMenuRecomanacions();
            controladorMenuItems = ControladorMenuItems.obtenirInstancia();
            inicialitzarMenuItems();
        }
        return instancia;
    }

    private static void inicialitzarMenuItems() {
        instancia.setLayout(new BoxLayout(instancia, BoxLayout.Y_AXIS));
    }
}
