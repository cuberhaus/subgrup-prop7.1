package presentacio.vistes;

import excepcions.NoExisteixElementException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class VistaMenuItems extends JPanel {
    private static VistaMenuItems instancia;
    private static ControladorMenuItems controladorMenuItems;
    private static JPanel menuLateral;
    private static JTable llistaItems;

    private VistaMenuItems() {
    }

    public static VistaMenuItems obtenirInstancia() throws IOException, NomInternIncorrecteException {
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
            try {
                controladorMenuItems.crearNouItem();
            } catch (IOException e1) {
                // TODO catch
                e1.printStackTrace();
            } catch (NomInternIncorrecteException e2) {
                //TODO:
                e2.printStackTrace();
            }
        });
        // TODO: hi ha d'haver un tipus d'ítem seleccionat
        menuLateral.add(botoCrearItem);
        JButton botoEditarItem = new JButton("Edita un ítem");
        botoEditarItem.addActionListener(e -> {
            try {
                controladorMenuItems.editarItem();
            } catch (IOException e1) {
                //TODO: cath
                e1.printStackTrace();
            } catch (NomInternIncorrecteException e2) {
                //TODO: catch
                e2.printStackTrace();
            } catch (NoExisteixElementException ex) {
                // TODO
                ex.printStackTrace();
            }
        });
        menuLateral.add(botoEditarItem);
        JButton botoEsborrarItem = new JButton("Esborra un ítem");
        botoEsborrarItem.addActionListener(e -> {
            try {
                controladorMenuItems.esborrarItem();
            } catch (NoExisteixElementException ex) {
                // TODO
                ex.printStackTrace();
            }
        });
        menuLateral.add(botoEsborrarItem);

        JButton botoEsborrarTotsElsItems = new JButton("Esborra tots els ítems");
        botoEsborrarTotsElsItems.addActionListener(e -> controladorMenuItems.esborrarTotsElsItems());
        menuLateral.add(botoEsborrarTotsElsItems);

        JButton botoCarregarConjuntItems = new JButton("Afegeix ítems des d'un conjunt");
        botoCarregarConjuntItems.addActionListener(e -> {
            try {
                controladorMenuItems.carregarConjuntItems();
            } catch (Exception ex) {
                // TODO: catch
            }
        });
        // TODO: han de ser del tipus d'ítem seleccionat o que no hi hagi un tipus d'ítem seleccionat
        menuLateral.add(botoCarregarConjuntItems);
        menuLateral.add(Box.createVerticalGlue());
        instancia.add(menuLateral, BorderLayout.EAST);
    }
}
