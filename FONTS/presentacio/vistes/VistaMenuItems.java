package presentacio.vistes;

import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.JFileChooser.APPROVE_OPTION;

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
                VistaDialegCrearItem vistaDialegCrearItem = new VistaDialegCrearItem();
                vistaDialegCrearItem.setVisible(true);
            }
        });
        // TODO: hi ha d'haver un tipus d'ítem seleccionat
        menuLateral.add(botoCrearItem);
        JButton botoEditarItem = new JButton("Edita un ítem");
        botoEditarItem.addActionListener(e -> {
            if (!controladorMenuItems.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                String id = JOptionPane.showInputDialog(instancia,
                        "Introdueix l'identificador de l'ítem que vols editar:");
                Map<String, String> atributs = controladorMenuItems.obtenirItem(id);
                if (atributs == null) {
                    JOptionPane.showMessageDialog(instancia, "L'identificador introduït no és vàlid.");
                } else {
                    VistaDialegEditarItem vistaDialegEditarItem = new VistaDialegEditarItem(id, atributs);
                    vistaDialegEditarItem.setVisible(true);
                }
            }
        });
        menuLateral.add(botoEditarItem);
        JButton botoEsborrarItem = new JButton("Esborra un ítem");
        botoEsborrarItem.addActionListener(e -> {
            if (!controladorMenuItems.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                String id = JOptionPane.showInputDialog(instancia,
                        "Introdueix l'identificador de l'ítem que vols esborrar:");
                if (!controladorMenuItems.esborrarItem(id)) {
                    JOptionPane.showMessageDialog(instancia, "L'identificador introduït no és vàlid.");
                } else {
                    JOptionPane.showMessageDialog(instancia, "L'ítem s'ha esborrat amb èxit.");
                }
            }
        });
        menuLateral.add(botoEsborrarItem);

        JButton botoEsborrarTotsElsItems = new JButton("Esborra tots els ítems");
        botoEsborrarTotsElsItems.addActionListener(e -> {
            if (!controladorMenuItems.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                int resposta = JOptionPane.showConfirmDialog(instancia,
                        "Segur que vols esborrar tots els ítems?", "Selecciona una opció",
                        JOptionPane.YES_NO_OPTION);
                if (resposta == 0) {
                    controladorMenuItems.esborrarTotsElsItems();
                    JOptionPane.showMessageDialog(instancia, "S'han esborrat els ítems amb èxit.");
                }
            }
        });
        menuLateral.add(botoEsborrarTotsElsItems);

        JButton botoCarregarConjuntItems = new JButton("Afegeix ítems des d'un conjunt");
        botoCarregarConjuntItems.addActionListener(e -> {
            JDialog dialegFitxer = new JDialog();
            JFileChooser selectorFitxer = new JFileChooser();
            int estatSelectorFitxer = selectorFitxer.showOpenDialog(dialegFitxer);
            if (estatSelectorFitxer == APPROVE_OPTION) {
                File rutaFitxer = selectorFitxer.getSelectedFile();
                controladorMenuItems.carregarConjuntItems(rutaFitxer.getAbsolutePath());
                // TODO: afegir missatge d'error
            }
        });
        // TODO: han de ser del tipus d'ítem seleccionat o que no hi hagi un tipus d'ítem seleccionat
        menuLateral.add(botoCarregarConjuntItems);
        menuLateral.add(Box.createVerticalGlue());
        instancia.add(menuLateral, BorderLayout.EAST);
    }
}
