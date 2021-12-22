package presentacio.vistes;

import presentacio.controladors.ControladorMenuRecomanacions;
import utilitats.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Vista pel dialeg que permet l'avaluacio d'una recomanacio.
 * @author maria.prat
 */
public class VistaDialegAvaluarRecomanacio extends JDialog {

    private final ControladorMenuRecomanacions controladorMenuRecomanacions;

    public VistaDialegAvaluarRecomanacio(ArrayList<String> recomanacio) throws Exception {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuRecomanacions = ControladorMenuRecomanacions.obtenirInstancia();
        inicialitzarDialegAvaluarRecomanacio(recomanacio);
    }

    private void inicialitzarDialegAvaluarRecomanacio(ArrayList<String> recomanacio) {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Avalua la recomanació");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());
        add(panellPrincipal);

        JPanel panellAvaluacio = new JPanel();
        panellAvaluacio.setLayout(new BoxLayout(panellAvaluacio, BoxLayout.Y_AXIS));

        JScrollPane panellScrollAvaluacio = new JScrollPane(panellAvaluacio);
        panellScrollAvaluacio.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellPrincipal.add(panellScrollAvaluacio, BorderLayout.CENTER);

        if (recomanacio.isEmpty()) {
            JLabel text = new JLabel("No s'ha recomanat cap ítem.");
            text.setAlignmentX(Component.CENTER_ALIGNMENT);
            text.setFont(new Font("Sans", Font.BOLD, 13));
            panellAvaluacio.add(Box.createVerticalGlue());
            panellAvaluacio.add(text);
            panellAvaluacio.add(Box.createVerticalGlue());
        } else {
            for (String idItem : recomanacio) {
                JPanel itemRecomanat = new JPanel(new FlowLayout());
                itemRecomanat.setAlignmentY(Component.CENTER_ALIGNMENT);
                JLabel etiquetaItem = new JLabel(idItem);
                itemRecomanat.add(etiquetaItem);
                JTextField valoracioItem = new JTextField();
                valoracioItem.setColumns(10);
                itemRecomanat.add(valoracioItem);
                panellAvaluacio.add(itemRecomanat);
            }
        }

        JPanel panellObtenirAvaluacio = new JPanel(new FlowLayout());
        JLabel etiquetaAvaluacio = new JLabel("Avaluació de la recomanació (NDGC): ");
        panellObtenirAvaluacio.add(etiquetaAvaluacio);
        JTextField valorAvaluacio = new JTextField();
        valorAvaluacio.setEnabled(false);
        valorAvaluacio.setColumns(10);
        panellObtenirAvaluacio.add(valorAvaluacio);
        JButton botoObtenirAvaluacio = new JButton("Calcular");
        botoObtenirAvaluacio.addActionListener(e -> {
            ArrayList<Pair<String, String>> valoracions = new ArrayList<>();
            if (recomanacio.isEmpty()) {
                valorAvaluacio.setText("-");
            } else {
                for (Component component : panellAvaluacio.getComponents()) {
                    String idItem = ((JLabel) ((JPanel) component).getComponent(0)).getText();
                    String valorValoracio = ((JTextField) ((JPanel) component).getComponent(1)).getText();
                    valoracions.add(new Pair<>(idItem, valorValoracio));
                }
                valorAvaluacio.setText(ControladorMenuRecomanacions.avaluarRecomanacio(valoracions));
            }
        });
        panellObtenirAvaluacio.add(botoObtenirAvaluacio);

        panellPrincipal.add(panellObtenirAvaluacio, BorderLayout.SOUTH);
    }
}