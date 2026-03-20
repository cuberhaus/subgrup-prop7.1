package presentacio.vistes;

import presentacio.EncarregatActualitzarVistes;
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
        setBounds(Pantalla.centreHoritzontal(5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Avalua la recomanació");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());
        panellPrincipal.setBorder(UIEstil.panelBorder());
        add(panellPrincipal);

        JPanel panellAvaluacio = new JPanel();
        panellAvaluacio.setLayout(new BoxLayout(panellAvaluacio, BoxLayout.Y_AXIS));
        panellAvaluacio.setBorder(UIEstil.panelBorder());

        JScrollPane panellScrollAvaluacio = new JScrollPane(panellAvaluacio);
        panellScrollAvaluacio.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellPrincipal.add(panellScrollAvaluacio, BorderLayout.CENTER);

        if (recomanacio.isEmpty()) {
            JLabel text = UIEstil.createLabel("No s'ha recomanat cap ítem.");
            text.setFont(UIEstil.FONT_BUTTON);
            text.setAlignmentX(Component.CENTER_ALIGNMENT);
            panellAvaluacio.add(Box.createVerticalGlue());
            panellAvaluacio.add(text);
            panellAvaluacio.add(Box.createVerticalGlue());
        } else {
            for (String idItem : recomanacio) {
                JPanel itemRecomanat = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
                itemRecomanat.add(UIEstil.createLabel(idItem));
                JTextField valoracioItem = UIEstil.createTextField(10);
                itemRecomanat.add(valoracioItem);
                panellAvaluacio.add(itemRecomanat);
            }
        }

        JPanel panellObtenirAvaluacio = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        panellObtenirAvaluacio.setOpaque(false);
        panellObtenirAvaluacio.add(UIEstil.createLabel("Avaluació de la recomanació (NDCG):"));
        JTextField valorAvaluacio = UIEstil.createTextField(10);
        valorAvaluacio.setEnabled(false);
        panellObtenirAvaluacio.add(valorAvaluacio);

        JButton botoCalcular = UIEstil.createSmallButton("Calcular");
        botoCalcular.setBackground(UIEstil.ACCENT);
        botoCalcular.setForeground(Color.WHITE);
        botoCalcular.addActionListener(e -> {
            ArrayList<Pair<String, String>> valoracions = new ArrayList<>();
            if (recomanacio.isEmpty()) {
                valorAvaluacio.setText("-");
            } else {
                for (Component component : panellAvaluacio.getComponents()) {
                    String idItem = ((JLabel) ((JPanel) component).getComponent(0)).getText();
                    String valorValoracio = ((JTextField) ((JPanel) component).getComponent(1)).getText();
                    valoracions.add(new Pair<>(idItem, valorValoracio));
                }
                try {
                    String avaluacio = ControladorMenuRecomanacions.avaluarRecomanacio(valoracions);
                    valorAvaluacio.setText(avaluacio.substring(0, Math.min(5, avaluacio.length())));
                    EncarregatActualitzarVistes.notificarObservadors();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "No es pot avaluar la recomanació.");
                }
            }
        });
        panellObtenirAvaluacio.add(botoCalcular);
        panellPrincipal.add(panellObtenirAvaluacio, BorderLayout.SOUTH);
    }
}
