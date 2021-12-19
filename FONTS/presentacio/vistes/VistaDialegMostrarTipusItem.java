package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author maria.prat
 */
public class VistaDialegMostrarTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;
    private final String nomTipusItem;
    private JPanel panellMostrarTipusItem;
    private JPanel panellLlistaTipusAtributs;
    private JScrollPane panellScrollLlistaTipusAtributs;

    // TODO: afegir editar TipusItem

    public VistaDialegMostrarTipusItem() {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        this.nomTipusItem = controladorMenuTipusItem.obtenirNomTipusItemSeleccionat();
        inicialitzarDialegMostrarTipusItem();
    }

    private void inicialitzarDialegMostrarTipusItem() {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8),
                Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Mostrar tipus d'ítem");
        setResizable(false);
        inicialitzarPanellMostrarTipusItem();
    }

    private void inicialitzarPanellMostrarTipusItem() {
        panellMostrarTipusItem = new JPanel();
        panellMostrarTipusItem.setLayout(new BoxLayout(panellMostrarTipusItem, BoxLayout.Y_AXIS));

        JPanel panellNomTipusItem = new JPanel(new FlowLayout());
        JLabel etiquetaNomTipusItem = new JLabel("Nom del tipus d'ítem:");
        panellNomTipusItem.add(etiquetaNomTipusItem);
        JTextField textNomTipusItem = new JTextField();
        textNomTipusItem.setColumns(10);
        textNomTipusItem.setEnabled(false);
        textNomTipusItem.setText(nomTipusItem);
        panellNomTipusItem.add(textNomTipusItem);
        panellMostrarTipusItem.add(panellNomTipusItem);

        JPanel panellTipusAtributs = new JPanel(new FlowLayout());
        JLabel etiquetaLlistaTipusAtributs = new JLabel("Tipus d'atributs:");
        panellTipusAtributs.add(etiquetaLlistaTipusAtributs);
        panellMostrarTipusItem.add(panellTipusAtributs);

        panellLlistaTipusAtributs = new JPanel();
        panellLlistaTipusAtributs.setLayout(new BoxLayout(panellLlistaTipusAtributs, BoxLayout.Y_AXIS));
        // TODO: posar asserts o alguna cosa per comprovar que els noms dels tipus d'atributs estan be i que els
        // valors i les distancies son coherents?
        Map<String, String> valorsTipusAtributs = controladorMenuTipusItem.obtenirValorsTipusAtributs(nomTipusItem);
        Map<String, String> distanciesTipusAtributs =
                controladorMenuTipusItem.obtenirDistanciesTipusAtributs(nomTipusItem);
        for (String nomTipusAtribut : valorsTipusAtributs.keySet()) {
            JPanel tipusAtribut = new JPanel(new FlowLayout());

            tipusAtribut.add(new JLabel("Nom:"));
            JTextField textNomTipusAtribut = new JTextField();
            textNomTipusAtribut.setColumns(10);
            textNomTipusAtribut.setText(nomTipusAtribut);
            textNomTipusAtribut.setEnabled(false);
            tipusAtribut.add(textNomTipusAtribut);

            tipusAtribut.add(new JLabel("Valor:"));
            JComboBox<String> comboBoxValorTipusAtribut = new JComboBox<>(new String[]{"Booleà", "Categòric", "Numèric", "Textual",
                    "Conjunt booleà", "Conjunt categòric", "Conjunt numèric", "Conjunt textual"});
            comboBoxValorTipusAtribut.setSelectedItem(valorsTipusAtributs.get(nomTipusAtribut));
            comboBoxValorTipusAtribut.setEnabled(false);
            tipusAtribut.add(comboBoxValorTipusAtribut);

            tipusAtribut.add(new JLabel("Distància:"));
            JComboBox<String> comboBoxDistanciaTipusAtribut = new JComboBox<>(new String[]{"Diferència de conjunts", "Discreta", "Euclidiana", "Levenshtein",
                    "Zero"});
            comboBoxDistanciaTipusAtribut.setSelectedItem(distanciesTipusAtributs.get(nomTipusAtribut));
            comboBoxDistanciaTipusAtribut.setEnabled(false);
            tipusAtribut.add(comboBoxDistanciaTipusAtribut);

            panellLlistaTipusAtributs.add(tipusAtribut);
        }

        panellScrollLlistaTipusAtributs = new JScrollPane(panellLlistaTipusAtributs);
        panellScrollLlistaTipusAtributs.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));

        panellMostrarTipusItem.add(panellScrollLlistaTipusAtributs);

        add(panellMostrarTipusItem);
    }
}