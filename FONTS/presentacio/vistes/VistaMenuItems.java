package presentacio.vistes;

import presentacio.EncarregatActualitzarVistes;
import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * Vista del menu d'items.
 * @author maria.prat
 */
public class VistaMenuItems extends JPanel {
    private static VistaMenuItems instancia;
    private static ControladorMenuItems controladorMenuItems;

    private static JPanel menuLateral;
    private static JTable llistaItems;
    private static DefaultTableModel llistaItemsTableModel;
    private static JScrollPane llistaItemsScroll;
    private static EncarregatActualitzarVistes EncarregatTipusItemSeleccionat;

    private VistaMenuItems() {
    }

    public static VistaMenuItems obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new VistaMenuItems();
            controladorMenuItems = ControladorMenuItems.obtenirInstancia();
            inicialitzarMenuItems();
        }
        return instancia;
    }

    private static void inicialitzarMenuItems() {
        instancia.setLayout(new BorderLayout(UIEstil.PADDING, 0));
        instancia.setBorder(UIEstil.panelBorder());
        inicialitzarLlistaItems();
        inicialitzarMenuLateral();
    }

    private static void inicialitzarLlistaItems() {
        ArrayList<String> nomsColumnes = new ArrayList<>();
        nomsColumnes.add("Identificador d'ítem");

        llistaItemsTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        ArrayList<String> itemIds = controladorMenuItems.obtenirIdsItems();
        for (String itemId : itemIds) {
            llistaItemsTableModel.addRow(new String[]{itemId});
        }
        llistaItems = new JTable(llistaItemsTableModel);
        llistaItems.setEnabled(false);
        UIEstil.styleTable(llistaItems);
        llistaItemsScroll = new JScrollPane(llistaItems);
        llistaItemsScroll.setPreferredSize(new Dimension(300, 0));
        instancia.add(llistaItemsScroll, BorderLayout.WEST);
    }

    private static void inicialitzarMenuLateral() {
        menuLateral = UIEstil.createButtonColumn(UIEstil.PADDING_SMALL);

        menuLateral.add(Box.createVerticalGlue());

        JButton botoCrearItem = UIEstil.createAccentButton("Crea un nou ítem");
        botoCrearItem.addActionListener(e -> {
            controladorMenuItems.crearNouItem();
            actualitzarTaula();
        });
        menuLateral.add(botoCrearItem);
        menuLateral.add(UIEstil.verticalGap());

        JButton botoMostrarItem = UIEstil.createButton("Mostra un ítem");
        botoMostrarItem.addActionListener(e -> controladorMenuItems.mostarItem());
        menuLateral.add(botoMostrarItem);
        menuLateral.add(UIEstil.verticalGap());

        JButton botoEditarItem = UIEstil.createButton("Edita un ítem");
        botoEditarItem.addActionListener(e -> controladorMenuItems.editarItem());
        menuLateral.add(botoEditarItem);
        menuLateral.add(UIEstil.verticalGap());

        JButton botoEsborrarItem = UIEstil.createDangerButton("Esborra un ítem");
        botoEsborrarItem.addActionListener(e -> {
            controladorMenuItems.esborrarItem();
            actualitzarTaula();
        });
        menuLateral.add(botoEsborrarItem);
        menuLateral.add(UIEstil.verticalGap());

        JButton botoEsborrarTotsElsItems = UIEstil.createDangerButton("Esborra tots els ítems");
        botoEsborrarTotsElsItems.addActionListener(e -> {
            controladorMenuItems.esborrarTotsElsItems();
            actualitzarTaula();
        });
        menuLateral.add(botoEsborrarTotsElsItems);
        menuLateral.add(UIEstil.verticalGapLarge());

        JButton botoCarregarConjuntItems = UIEstil.createButton("Afegeix ítems des d'un conjunt");
        botoCarregarConjuntItems.addActionListener(e -> {
            boolean deduirTipusItem = true;
            if (controladorMenuItems.existeixTipusItemSeleccionat()) {
                int resposta = JOptionPane.showConfirmDialog(instancia,
                        "Vols deduïr el tipus d'ítem del conjunt?", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
                deduirTipusItem = (resposta == JOptionPane.YES_OPTION);
            }
            String nomTipusItem = controladorMenuItems.obtenirNomTipusItemSeleccionat();
            if (deduirTipusItem) {
                nomTipusItem = JOptionPane.showInputDialog("Introdueix el nom del nou tipus d'ítem:");
            }
            if (nomTipusItem != null) {
                controladorMenuItems.carregarConjuntItems(deduirTipusItem, nomTipusItem);
                EncarregatActualitzarVistes.notificarObservadors();
            }
        });
        menuLateral.add(botoCarregarConjuntItems);
        menuLateral.add(UIEstil.verticalGap());

        JButton botoExportarConjuntItems = UIEstil.createButton("Exporta el conjunt d'ítems");
        botoExportarConjuntItems.addActionListener(e -> {
            JDialog dialegRuta = new JDialog();
            JFileChooser selectorFitxer = new JFileChooser();
            selectorFitxer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int estatJFile = selectorFitxer.showOpenDialog(dialegRuta);
            if (estatJFile == APPROVE_OPTION) {
                File rutaConjuntItems = selectorFitxer.getSelectedFile();
                controladorMenuItems.exportarItems(rutaConjuntItems.getAbsolutePath());
            }
        });
        menuLateral.add(botoExportarConjuntItems);

        menuLateral.add(Box.createVerticalGlue());
        instancia.add(menuLateral, BorderLayout.CENTER);
    }

    public static void actualitzarTaula() {
        llistaItemsTableModel.setRowCount(0);
        ArrayList<ArrayList<String>> usuaris = controladorMenuItems.obtenirItems();
        for (ArrayList<String> usuari : usuaris) {
            llistaItemsTableModel.addRow(usuari.toArray());
        }
        llistaItemsScroll.revalidate();
    }
}
