package presentacio.vistes;

import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class VistaMenuItems extends JPanel {
    private static VistaMenuItems instancia;
    private static ControladorMenuItems controladorMenuItems;
    private static JPanel menuLateral;
    private static JTable llistaItems;

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
        ArrayList<String> nomsColumnes = new ArrayList<>();
        nomsColumnes.add("Id");
        nomsColumnes.addAll(controladorMenuItems.obtenirNomsAtributsTipusItemSeleccionat());
        // TODO: revisar que això està bé i que l'ordre de les columnes i els atributs és el mateix
        // TODO: potser cal un JScrollPane per la taula
        DefaultTableModel llistaItemsTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        llistaItems = new JTable(llistaItemsTableModel);
        ArrayList<ArrayList<String>> items = controladorMenuItems.obtenirItems();
        for (ArrayList<String> item : items) {
            llistaItemsTableModel.addRow(item.toArray());
        }
        instancia.add(llistaItems, BorderLayout.CENTER);
    }

    private static void inicialitzarMenuLateral() {
        menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.add(Box.createVerticalGlue());
        JButton botoCrearItem = new JButton("Crea un nou ítem");
        botoCrearItem.addActionListener(e -> {
            if (!controladorMenuItems.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                crearItem();
            }
        });
        // TODO: hi ha d'haver un tipus d'ítem seleccionat
        menuLateral.add(botoCrearItem);
        JButton botoEditarItem = new JButton("Edita un ítem");
        menuLateral.add(botoEditarItem);
        JButton botoEsborrarItem = new JButton("Esborra un ítem");
        // TODO: afegir confirmacio
        menuLateral.add(botoEsborrarItem);
        JButton carregarConjuntItems = new JButton("Afegeix ítems des d'un conjunt");
        // TODO: han de ser del tipus d'ítem seleccionat o que no hi hagi un tipus d'ítem seleccionat
        menuLateral.add(carregarConjuntItems);
        menuLateral.add(Box.createVerticalGlue());
        instancia.add(menuLateral, BorderLayout.EAST);
    }

    private static void crearItem() {
        VistaDialegCrearItem vistaDialegCrearItem = new VistaDialegCrearItem();
        vistaDialegCrearItem.setVisible(true);
    }
}
