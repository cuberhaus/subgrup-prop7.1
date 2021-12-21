package presentacio.vistes;

import domini.classes.Pair;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maria.prat
 */
public class VistaDialegCrearTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;
    private JPanel panellLlistaTipusAtributs;
    private JPanel panellCrearTipusItem;
    private JScrollPane panellScrollLlistaTipusAtributs;

    public VistaDialegCrearTipusItem() throws IOException, NomInternIncorrecteException {
        super(null, Dialog.ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        inicialitzarDialegCrearTipusItem();
    }

    private void inicialitzarDialegCrearTipusItem() {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Crea un nou tipus d'ítem");
        setResizable(false);
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

        panellLlistaTipusAtributs = new JPanel();
        panellLlistaTipusAtributs.setLayout(new BoxLayout(panellLlistaTipusAtributs, BoxLayout.Y_AXIS));

        panellScrollLlistaTipusAtributs = new JScrollPane(panellLlistaTipusAtributs);
        panellScrollLlistaTipusAtributs.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));

        panellCrearTipusItem.add(panellScrollLlistaTipusAtributs);

        JButton botoCrearTipusItem = new JButton("Crea tipus d'ítem");
        botoCrearTipusItem.addActionListener(e -> {
            // TODO (maria): comprovar que les distàncies són compatibles amb el valor atribut
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
                    JOptionPane.showMessageDialog(this,
                            "No pot haver-hi un tipus d'atribut sense nom.");
                    return;
                }
                if (nomAValorAtribut.containsKey(nomTipusAtribut)) {
                    JOptionPane.showMessageDialog(this,
                            "No pot haver-hi dos tipus d'atributs amb el mateix nom.");
                    return;
                }
                String valorTipusAtribut = (String) ((JComboBox<?>) tipusAtribut.getComponent(3)).getSelectedItem();
                String distanciaTipusAtribut = (String) ((JComboBox<?>) tipusAtribut.getComponent(5)).getSelectedItem();
                nomAValorAtribut.put(nomTipusAtribut, new Pair<>(valorTipusAtribut, distanciaTipusAtribut));
            }
            try {
                controladorMenuTipusItem.crearTipusItem(nom, nomAValorAtribut);
                dispose();
                JOptionPane.showMessageDialog(this,
                        "Tipus d'ítem creat amb èxit.");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this,
                        "No s'ha pogut crear el tipus d'ítem. Torna-ho a intentar.");
            } catch (NomInternIncorrecteException e2) {
                System.out.print(e2.getMessage());
                JOptionPane.showMessageDialog(this,
                        "Ja existeix un tipus d'ítem amb aquest nom.");
            }
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