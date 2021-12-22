package presentacio.vistes;

import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.EncarregatActualitzarVistes;
import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

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
        instancia.setLayout(new BorderLayout());
        inicialitzarMenuLateral();
        inicialitzarLlistaItems();
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
        llistaItemsScroll = new JScrollPane(llistaItems);
        instancia.add(llistaItemsScroll, BorderLayout.WEST);
    }

    private static void inicialitzarMenuLateral() {
        menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.add(Box.createVerticalGlue());
        JButton botoCrearItem = new JButton("Crea un nou ítem");
        botoCrearItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoCrearItem.addActionListener(e -> {
            controladorMenuItems.crearNouItem();
            actualitzarTaula();
        });
        menuLateral.add(botoCrearItem);
        JButton botoMostrarItem = new JButton("Mostra un ítem");
        botoMostrarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoMostrarItem.addActionListener(e -> controladorMenuItems.mostarItem());
        menuLateral.add(botoMostrarItem);
        JButton botoEditarItem = new JButton("Edita un ítem");
        botoEditarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoEditarItem.addActionListener(e -> controladorMenuItems.editarItem());
        menuLateral.add(botoEditarItem);
        JButton botoEsborrarItem = new JButton("Esborra un ítem");
        botoEsborrarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoEsborrarItem.addActionListener(e -> {
            controladorMenuItems.esborrarItem();
            actualitzarTaula();
        });
        menuLateral.add(botoEsborrarItem);

        JButton botoEsborrarTotsElsItems = new JButton("Esborra tots els ítems");
        botoEsborrarTotsElsItems.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoEsborrarTotsElsItems.addActionListener(e -> {
            controladorMenuItems.esborrarTotsElsItems();
            actualitzarTaula();
        });
        menuLateral.add(botoEsborrarTotsElsItems);

        JButton botoCarregarConjuntItems = new JButton("Afegeix ítems des d'un conjunt");
        botoCarregarConjuntItems.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        JButton botoExportarConjuntItems = new JButton("Exporta el conjunt d'ítems");
        botoExportarConjuntItems.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoExportarConjuntItems.addActionListener(e -> {
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
