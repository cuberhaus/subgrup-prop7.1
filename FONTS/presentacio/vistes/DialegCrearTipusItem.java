package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maria.prat
 */
public class DialegCrearTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;
    private JPanel panellLlistaTipusAtributs;
    private JPanel panellCrearTipusItem;
    private JScrollPane panellScrollLlistaTipusAtributs;

    public DialegCrearTipusItem() {
        super(null, Dialog.ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        inicialitzarDialegCrearTipusItem();
    }

    private void inicialitzarDialegCrearTipusItem() {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Crea un nou tipus d'ítem");
        inicialitzarPanellCrearTipusItem();
    }

    private void inicialitzarPanellCrearTipusItem() {
        panellCrearTipusItem = new JPanel();
        panellCrearTipusItem.setLayout(new BoxLayout(panellCrearTipusItem, BoxLayout.Y_AXIS));

        JPanel panellNomTipusItem = new JPanel(new FlowLayout());
        JLabel etiquetaNomTipusItem = new JLabel("Nom del tipus d'ítem:");
        panellNomTipusItem.add(etiquetaNomTipusItem);
        JTextField nomTipusItem = new JTextField();
        nomTipusItem.setColumns(10);
        panellNomTipusItem.add(nomTipusItem);
        panellCrearTipusItem.add(panellNomTipusItem);

        JPanel panellTipusAtributs = new JPanel(new FlowLayout());
        JLabel etiquetaLlistaTipusAtributs = new JLabel("Tipus d'atributs:");
        panellTipusAtributs.add(etiquetaLlistaTipusAtributs);
        JButton botoAfegirTipusAtribut = new JButton("Afegeix nou tipus d'atribut");
        botoAfegirTipusAtribut.addActionListener(e -> {
            afegeixPanellTipusAtribut();
            panellScrollLlistaTipusAtributs.validate();
            panellScrollLlistaTipusAtributs.repaint();
        });
        botoAfegirTipusAtribut.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellTipusAtributs.add(botoAfegirTipusAtribut);
        panellCrearTipusItem.add(panellTipusAtributs);

        // TODO: afegir boto esborrar tipus atribut
        panellLlistaTipusAtributs = new JPanel();
        panellLlistaTipusAtributs.setLayout(new BoxLayout(panellLlistaTipusAtributs, BoxLayout.Y_AXIS));

        panellScrollLlistaTipusAtributs = new JScrollPane(panellLlistaTipusAtributs);
        panellScrollLlistaTipusAtributs.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));

        panellCrearTipusItem.add(panellScrollLlistaTipusAtributs);

        JButton botoCrearTipusItem = new JButton("Crea tipus d'ítem");
        botoCrearTipusItem.addActionListener(e -> {
            String nom = nomTipusItem.getText();
            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El tipus d'ítem ha de tenir un nom.");
                return;
            }
            Map<String, String> valorsTipusAtributs = new HashMap<>();
            Map<String, String> distanciesTipusAtributs = new HashMap<>();
            for (Component component : panellLlistaTipusAtributs.getComponents()) {
                JPanel tipusAtribut = (JPanel) component;
                String nomTipusAtribut = ((JTextField) tipusAtribut.getComponent(1)).getText();
                if (nomTipusAtribut.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "No pot haver-hi un tipus d'atribut sense nom.");
                    return;
                }
                if (valorsTipusAtributs.containsKey(nomTipusAtribut)) {
                    JOptionPane.showMessageDialog(this,
                            "No pot haver-hi dos tipus d'atributs amb el mateix nom.");
                    return;
                }
                String valorTipusAtribut = (String) ((JComboBox<?>) tipusAtribut.getComponent(3)).getSelectedItem();
                valorsTipusAtributs.put(nomTipusAtribut, valorTipusAtribut);
                String distanciaTipusAtribut = (String) ((JComboBox<?>) tipusAtribut.getComponent(5)).getSelectedItem();
                distanciesTipusAtributs.put(nomTipusAtribut, distanciaTipusAtribut);
            }
            if (!controladorMenuTipusItem.afegirTipusItem(nom, valorsTipusAtributs, distanciesTipusAtributs)) {
                JOptionPane.showMessageDialog(this, "Ja hi ha un tipus d'ítem carregat amb aquest nom.");
                return;
            }
            dispose();
        });
        botoCrearTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellCrearTipusItem.add(botoCrearTipusItem);

        add(panellCrearTipusItem);
    }

    private void afegeixPanellTipusAtribut() {
        JPanel tipusAtribut = new JPanel(new FlowLayout());
        tipusAtribut.add(new JLabel("Nom:"));
        JTextField nomTipusAtribut = new JTextField();
        nomTipusAtribut.setColumns(10);
        tipusAtribut.add(nomTipusAtribut);
        tipusAtribut.add(new JLabel("Valor:"));
        tipusAtribut.add(new JComboBox<>(new String[]{"Booleà", "Categòric", "Numèric", "Textual",
                "Conjunt booleà", "Conjunt categòric", "Conjunt numèric", "Conjunt textual"}));
        tipusAtribut.add(new JLabel("Distància:"));
        tipusAtribut.add(new JComboBox<>(new String[]{"Diferència de conjunts", "Discreta", "Euclidiana", "Levenshtein",
                "Zero"}));
        JButton botoEsborrarTipusAtribut = new JButton("Esborra");
        botoEsborrarTipusAtribut.addActionListener(e -> {
            panellLlistaTipusAtributs.remove(tipusAtribut);
            panellScrollLlistaTipusAtributs.validate();
            panellScrollLlistaTipusAtributs.repaint();
        });
        tipusAtribut.add(botoEsborrarTipusAtribut);
        panellLlistaTipusAtributs.add(tipusAtribut);
    }
}