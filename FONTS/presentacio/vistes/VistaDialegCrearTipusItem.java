package presentacio.vistes;

import utilitats.Pair;
import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Vista pel dialeg que permet la creacio d'un tipus d'item.
 * @author maria.prat
 */
public class VistaDialegCrearTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;
    private JPanel panellLlistaTipusAtributs;
    private JPanel panellCrearTipusItem;
    private JScrollPane panellScrollLlistaTipusAtributs;

    public VistaDialegCrearTipusItem() throws Exception {
        super(null, Dialog.ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        inicialitzarDialegCrearTipusItem();
    }

    private void inicialitzarDialegCrearTipusItem() {
        setBounds(Pantalla.centreHoritzontal(5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Crea un nou tipus d'ítem");
        setResizable(false);
        inicialitzarPanellCrearTipusItem();
    }

    private void inicialitzarPanellCrearTipusItem() {
        panellCrearTipusItem = new JPanel();
        panellCrearTipusItem.setLayout(new BoxLayout(panellCrearTipusItem, BoxLayout.Y_AXIS));
        panellCrearTipusItem.setBorder(UIEstil.panelBorder());

        JPanel panellNomTipusItem = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        panellNomTipusItem.setOpaque(false);
        panellNomTipusItem.add(UIEstil.createLabel("Nom del tipus d'ítem:"));
        JTextField nomTipusItem = UIEstil.createTextField(15);
        panellNomTipusItem.add(nomTipusItem);
        panellCrearTipusItem.add(panellNomTipusItem);
        panellCrearTipusItem.add(UIEstil.verticalGap());

        JPanel panellTipusAtributs = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        panellTipusAtributs.setOpaque(false);
        panellTipusAtributs.add(UIEstil.createLabel("Tipus d'atributs:"));
        JButton botoAfegirTipusAtribut = UIEstil.createSmallButton("Afegeix atribut");
        botoAfegirTipusAtribut.setBackground(UIEstil.ACCENT);
        botoAfegirTipusAtribut.setForeground(Color.WHITE);
        botoAfegirTipusAtribut.addActionListener(e -> {
            afegeixPanellTipusAtribut();
            panellScrollLlistaTipusAtributs.validate();
            panellScrollLlistaTipusAtributs.repaint();
        });
        panellTipusAtributs.add(botoAfegirTipusAtribut);
        panellCrearTipusItem.add(panellTipusAtributs);
        panellCrearTipusItem.add(UIEstil.verticalGap());

        panellLlistaTipusAtributs = new JPanel();
        panellLlistaTipusAtributs.setLayout(new BoxLayout(panellLlistaTipusAtributs, BoxLayout.Y_AXIS));

        panellScrollLlistaTipusAtributs = new JScrollPane(panellLlistaTipusAtributs);
        panellScrollLlistaTipusAtributs.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellCrearTipusItem.add(panellScrollLlistaTipusAtributs);
        panellCrearTipusItem.add(UIEstil.verticalGap());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        JButton botoCrearTipusItem = UIEstil.createAccentButton("Crea tipus d'ítem");
        botoCrearTipusItem.addActionListener(e -> {
            String nom = nomTipusItem.getText();
            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El tipus d'ítem ha de tenir un nom.");
                return;
            }
            Map<String, Pair<String, String>> nomAValorAtribut = new HashMap<>();
            for (Component component : panellLlistaTipusAtributs.getComponents()) {
                JPanel tipusAtribut = (JPanel) component;
                String nomTipusAtribut = ((JTextField) tipusAtribut.getComponent(1)).getText();
                if (nomTipusAtribut.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No pot haver-hi un tipus d'atribut sense nom.");
                    return;
                }
                if (nomAValorAtribut.containsKey(nomTipusAtribut)) {
                    JOptionPane.showMessageDialog(this, "No pot haver-hi dos tipus d'atributs amb el mateix nom.");
                    return;
                }
                String valorTipusAtribut = (String) ((JComboBox<?>) tipusAtribut.getComponent(3)).getSelectedItem();
                String distanciaTipusAtribut = (String) ((JComboBox<?>) tipusAtribut.getComponent(5)).getSelectedItem();
                nomAValorAtribut.put(nomTipusAtribut, new Pair<>(valorTipusAtribut, distanciaTipusAtribut));
            }
            if (controladorMenuTipusItem.crearTipusItem(this, nom, nomAValorAtribut)) {
                dispose();
            }
        });
        bottomPanel.add(botoCrearTipusItem);
        panellCrearTipusItem.add(bottomPanel);

        add(panellCrearTipusItem);
    }

    private void afegeixPanellTipusAtribut() {
        JPanel tipusAtribut = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        tipusAtribut.add(UIEstil.createLabel("Nom:"));
        JTextField nomTipusAtribut = UIEstil.createTextField(10);
        tipusAtribut.add(nomTipusAtribut);

        tipusAtribut.add(UIEstil.createLabel("Valor:"));
        JComboBox<String> valorCombo = new JComboBox<>(new String[]{"Booleà", "Categòric", "Numèric", "Textual",
                "Conjunt booleà", "Conjunt categòric", "Conjunt numèric", "Conjunt textual"});
        valorCombo.setFont(UIEstil.FONT_LABEL);
        tipusAtribut.add(valorCombo);

        tipusAtribut.add(UIEstil.createLabel("Distància:"));
        JComboBox<String> distCombo = new JComboBox<>(new String[]{"Diferència de conjunts", "Discreta", "Euclidiana", "Levenshtein", "Zero"});
        distCombo.setFont(UIEstil.FONT_LABEL);
        tipusAtribut.add(distCombo);

        JButton botoEsborrar = UIEstil.createSmallButton("Esborra");
        botoEsborrar.setBackground(UIEstil.DANGER);
        botoEsborrar.setForeground(Color.WHITE);
        botoEsborrar.setPreferredSize(new Dimension(100, 28));
        botoEsborrar.setMaximumSize(new Dimension(100, 28));
        botoEsborrar.setMinimumSize(new Dimension(100, 28));
        botoEsborrar.addActionListener(e -> {
            panellLlistaTipusAtributs.remove(tipusAtribut);
            panellScrollLlistaTipusAtributs.validate();
            panellScrollLlistaTipusAtributs.repaint();
        });
        tipusAtribut.add(botoEsborrar);
        panellLlistaTipusAtributs.add(tipusAtribut);
    }
}
