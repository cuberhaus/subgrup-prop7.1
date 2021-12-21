package presentacio.vistes;

import utilitats.Pair;
import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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

    public VistaDialegMostrarTipusItem() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        this.nomTipusItem = controladorMenuTipusItem.obtenirNomTipusItemSeleccionat();
        inicialitzarDialegMostrarTipusItem();
    }

    private void inicialitzarDialegMostrarTipusItem() throws DistanciaNoCompatibleAmbValorException {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8),
                Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Mostrar tipus d'ítem");
        setResizable(false);
        inicialitzarPanellMostrarTipusItem();
    }

    private void inicialitzarPanellMostrarTipusItem() throws DistanciaNoCompatibleAmbValorException {
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
        Map<String, Pair<String, String>> tipusAtributs = controladorMenuTipusItem.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat();
        for (String nomTipusAtribut : tipusAtributs.keySet()) {
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
            comboBoxValorTipusAtribut.setSelectedItem(tipusAtributs.get(nomTipusAtribut).x);
            comboBoxValorTipusAtribut.setEnabled(false);
            tipusAtribut.add(comboBoxValorTipusAtribut);

            tipusAtribut.add(new JLabel("Distància:"));
            JComboBox<String> comboBoxDistanciaTipusAtribut = new JComboBox<>(new String[]{"Diferència de conjunts", "Discreta", "Euclidiana", "Levenshtein",
                    "Zero"});
            comboBoxDistanciaTipusAtribut.setSelectedItem(tipusAtributs.get(nomTipusAtribut).y);
            System.out.println(tipusAtributs.get(nomTipusAtribut).y);
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