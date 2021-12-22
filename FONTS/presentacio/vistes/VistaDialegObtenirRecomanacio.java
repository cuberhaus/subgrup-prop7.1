package presentacio.vistes;

import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuRecomanacions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author maria.prat
 */
public class VistaDialegObtenirRecomanacio extends JDialog {

    private final ControladorMenuRecomanacions controladorMenuRecomanacions;
    private DefaultTableModel llistaRecomanacionsTableModel;

    public VistaDialegObtenirRecomanacio(String descripcioMetode,
                                         Map<String, Boolean> nomsAtributsFiltre) throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuRecomanacions = ControladorMenuRecomanacions.obtenirInstancia();
        inicialitzarDialegObtenirRecomanacio(descripcioMetode, nomsAtributsFiltre);
    }

    private void inicialitzarDialegObtenirRecomanacio(String descripcioMetode, Map<String, Boolean> nomsAtributsFiltre) {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Obté recomanació");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());
        add(panellPrincipal);

        ArrayList<String> recomanacio = controladorMenuRecomanacions.obtenirRecomanacio(descripcioMetode, nomsAtributsFiltre);

        ArrayList<String> nomsColumnes = new ArrayList<>();
        nomsColumnes.add("Identificador d'ítem");
        nomsColumnes.addAll(controladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat());

        llistaRecomanacionsTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        for (String itemId : recomanacio) {
            Map<String, String> itemMapa = controladorMenuRecomanacions.obtenirItem(itemId);
            ArrayList<String> item = new ArrayList<>();
            for (String nomAtribut : nomsColumnes) {
                item.add(itemMapa.get(nomAtribut));
            }
            llistaRecomanacionsTableModel.addRow(item.toArray());
        }
        JTable llistaRecomanacions = new JTable(llistaRecomanacionsTableModel);
        llistaRecomanacions.setEnabled(false);
        JScrollPane llistaRecomanacionsScroll = new JScrollPane(llistaRecomanacions);
        panellPrincipal.add(llistaRecomanacionsScroll, BorderLayout.CENTER);

        JButton botoAvaluarRecomanacio = new JButton("Avalua recomanació");
        botoAvaluarRecomanacio.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoAvaluarRecomanacio.addActionListener(e -> {
            VistaDialegAvaluarRecomanacio vistaDialegAvaluarRecomanacio;
            try {
                vistaDialegAvaluarRecomanacio = new VistaDialegAvaluarRecomanacio(recomanacio);
                vistaDialegAvaluarRecomanacio.setVisible(true);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "No es pot avaluar aquesta recomanació.");
            }
        });

        panellPrincipal.add(botoAvaluarRecomanacio, BorderLayout.SOUTH);
    }
}