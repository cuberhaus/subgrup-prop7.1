package presentacio.vistes;

import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class VistaMenuItems extends JPanel {
    private static VistaMenuItems instancia;
    private static ControladorMenuItems controladorMenuItems;
    private static JPanel menuLateral;
    private static JPanel llistaItems;

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
        instancia.setLayout(new BorderLayout());
        inicialitzarMenuLateral();
        inicialitzarLlistaItems();
    }

    private static void inicialitzarLlistaItems() {
        llistaItems = new JPanel();
        llistaItems.setLayout(new BoxLayout(llistaItems, BoxLayout.Y_AXIS));
        ArrayList<Map<String, String>> items = controladorMenuItems.obtenirItems();
        instancia.add(llistaItems, BorderLayout.CENTER);
    }

    private static void inicialitzarMenuLateral() {
        menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        JButton botoCrearItem = new JButton("Crea un nou ítem");
        // TODO: hi ha d'haver un tipus d'ítem seleccionat
        menuLateral.add(botoCrearItem);
        JButton botoEsborrarItemsSeleccionats = new JButton("Esborra els ítems seleccionats");
        // TODO: afegir confirmacio
        menuLateral.add(botoEsborrarItemsSeleccionats);
        JButton carregarConjuntItems = new JButton("Afegeix ítems des d'un conjunt");
        // TODO: han de ser del tipus d'ítem seleccionat o que no hi hagi un tipus d'ítem seleccionat
        menuLateral.add(carregarConjuntItems);
        instancia.add(menuLateral, BorderLayout.EAST);
    }
}
