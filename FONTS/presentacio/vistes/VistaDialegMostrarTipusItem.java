package presentacio.vistes;

import utilitats.Pair;
import excepcions.DistanciaNoCompatibleAmbValorException;
import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Vista pel dialeg que permet mostrar un tipus d'item.
 * @author maria.prat
 */
public class VistaDialegMostrarTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;
    private final String nomTipusItem;

    public VistaDialegMostrarTipusItem() throws Exception {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        this.nomTipusItem = controladorMenuTipusItem.obtenirNomTipusItemSeleccionat();
        inicialitzarDialegMostrarTipusItem();
    }

    private void inicialitzarDialegMostrarTipusItem() throws DistanciaNoCompatibleAmbValorException {
        setBounds(Pantalla.centreHoritzontal(5 * Pantalla.amplada / 8),
                Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Mostrar tipus d'ítem");
        setResizable(false);

        JPanel panellMostrarTipusItem = new JPanel();
        panellMostrarTipusItem.setLayout(new BoxLayout(panellMostrarTipusItem, BoxLayout.Y_AXIS));
        panellMostrarTipusItem.setBorder(UIEstil.panelBorder());

        JPanel panellNomTipusItem = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        panellNomTipusItem.setOpaque(false);
        panellNomTipusItem.add(UIEstil.createLabel("Nom del tipus d'ítem:"));
        JTextField textNomTipusItem = UIEstil.createTextField(15);
        textNomTipusItem.setEnabled(false);
        textNomTipusItem.setText(nomTipusItem);
        panellNomTipusItem.add(textNomTipusItem);
        panellMostrarTipusItem.add(panellNomTipusItem);
        panellMostrarTipusItem.add(UIEstil.verticalGap());

        JPanel panellTipusAtributs = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panellTipusAtributs.setOpaque(false);
        JLabel lbl = UIEstil.createLabel("Tipus d'atributs:");
        lbl.setFont(UIEstil.FONT_BUTTON);
        panellTipusAtributs.add(lbl);
        panellMostrarTipusItem.add(panellTipusAtributs);

        JPanel panellLlistaTipusAtributs = new JPanel();
        panellLlistaTipusAtributs.setLayout(new BoxLayout(panellLlistaTipusAtributs, BoxLayout.Y_AXIS));
        Map<String, Pair<String, String>> tipusAtributs = controladorMenuTipusItem.obtenirTipusAtributsTipusItemSeleccionat();
        for (String nomTipusAtribut : tipusAtributs.keySet()) {
            JPanel tipusAtribut = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));

            tipusAtribut.add(UIEstil.createLabel("Nom:"));
            JTextField textNomTipusAtribut = UIEstil.createTextField(10);
            textNomTipusAtribut.setText(nomTipusAtribut);
            textNomTipusAtribut.setEnabled(false);
            tipusAtribut.add(textNomTipusAtribut);

            tipusAtribut.add(UIEstil.createLabel("Valor:"));
            JComboBox<String> comboBoxValor = new JComboBox<>(new String[]{"Booleà", "Categòric", "Numèric", "Textual",
                    "Conjunt booleà", "Conjunt categòric", "Conjunt numèric", "Conjunt textual"});
            comboBoxValor.setFont(UIEstil.FONT_LABEL);
            comboBoxValor.setSelectedItem(tipusAtributs.get(nomTipusAtribut).x);
            comboBoxValor.setEnabled(false);
            tipusAtribut.add(comboBoxValor);

            tipusAtribut.add(UIEstil.createLabel("Distància:"));
            JComboBox<String> comboBoxDist = new JComboBox<>(new String[]{"Diferència de conjunts", "Discreta", "Euclidiana", "Levenshtein", "Zero"});
            comboBoxDist.setFont(UIEstil.FONT_LABEL);
            comboBoxDist.setSelectedItem(tipusAtributs.get(nomTipusAtribut).y);
            comboBoxDist.setEnabled(false);
            tipusAtribut.add(comboBoxDist);

            panellLlistaTipusAtributs.add(tipusAtribut);
        }

        JScrollPane scroll = new JScrollPane(panellLlistaTipusAtributs);
        scroll.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellMostrarTipusItem.add(scroll);

        add(panellMostrarTipusItem);
    }
}
