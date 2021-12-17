package presentacio.vistes;

import presentacio.controladors.ControladorMenuItems;
import presentacio.controladors.ControladorMenuRecomanacions;

import javax.swing.*;
import java.util.ArrayList;

public class VistaMenuRecomanacions extends JPanel {
    private static VistaMenuRecomanacions instancia;
    private static ControladorMenuRecomanacions controladorMenuRecomanacions;

    private VistaMenuRecomanacions() {
    }

    public static VistaMenuRecomanacions obtenirInstancia() {
        if (instancia == null) {
            instancia = new VistaMenuRecomanacions();
            controladorMenuRecomanacions = ControladorMenuRecomanacions.obtenirInstancia();
            inicialitzarMenuItems();
        }
        return instancia;
    }

    private static void inicialitzarMenuItems() {
        instancia.setLayout(new BoxLayout(instancia, BoxLayout.Y_AXIS));
        controladorMenuRecomanacions.sessioIniciada();
        controladorMenuRecomanacions.existeixTipusItemSeleccionat();
        controladorMenuRecomanacions.obtenirRecomanacioCollaborative(new ArrayList<>(), false);
        controladorMenuRecomanacions.obtenirRecomanacioContentBased(new ArrayList<>(), false);
        controladorMenuRecomanacions.obtenirRecomanacioHibrida(new ArrayList<>(), false);
        controladorMenuRecomanacions.avaluarRecomanacio();
    }
}
