package presentacio.vistes;

import domini.classes.Pair;
import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuItems;
import presentacio.controladors.ControladorMenuRecomanacions;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maria.prat
 */
public class VistaDialegEditarFiltre extends JDialog {

    private final ControladorMenuRecomanacions controladorMenuRecomanacions;

    public VistaDialegEditarFiltre(Map<String, Boolean> nomsAtributsFiltre) throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuRecomanacions = ControladorMenuRecomanacions.obtenirInstancia();
        inicialitzarDialegEditarFiltre(nomsAtributsFiltre);
    }

    private void inicialitzarDialegEditarFiltre(Map<String, Boolean> nomsAtributsFiltre) {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Edita el filtre");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());
        add(panellPrincipal);

        JPanel panellLlistaAtributs = new JPanel();
        panellLlistaAtributs.setLayout(new BoxLayout(panellLlistaAtributs, BoxLayout.Y_AXIS));

        JScrollPane panellScrollLlistaAtributs = new JScrollPane(panellLlistaAtributs);
        panellScrollLlistaAtributs.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellPrincipal.add(panellScrollLlistaAtributs, BorderLayout.CENTER);

        if (controladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat().isEmpty()) {
            JLabel text = new JLabel("El tipus d'ítem seleccionat no té cap atribut.");
            text.setAlignmentX(Component.CENTER_ALIGNMENT);
            text.setFont(new Font("Sans", Font.BOLD, 13));
            panellLlistaAtributs.add(Box.createVerticalGlue());
            panellLlistaAtributs.add(text);
            panellLlistaAtributs.add(Box.createVerticalGlue());
        } else {
            for (Map.Entry<String, Boolean> atribut : nomsAtributsFiltre.entrySet()) {
                JCheckBox checkBoxAtribut = new JCheckBox();
                checkBoxAtribut.setText(atribut.getKey());
                checkBoxAtribut.setSelected(atribut.getValue());
                checkBoxAtribut.setAlignmentX(Component.CENTER_ALIGNMENT);
                checkBoxAtribut.setAlignmentY(Component.CENTER_ALIGNMENT);
                panellLlistaAtributs.add(checkBoxAtribut);
            }
        }

        JPanel panellBotoGuardarFiltre = new JPanel(new FlowLayout());
        JButton botoGuardarFiltre = new JButton("Guarda filtre");
        botoGuardarFiltre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellBotoGuardarFiltre.add(botoGuardarFiltre);
        panellPrincipal.add(panellBotoGuardarFiltre, BorderLayout.SOUTH);

        botoGuardarFiltre.addActionListener(e -> {
            if (!controladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat().isEmpty()) {
                for (Component component : panellLlistaAtributs.getComponents()) {
                    JCheckBox atribut = (JCheckBox) component;
                    nomsAtributsFiltre.put(atribut.getText(), atribut.isSelected());
                }
            }
            dispose();
            JOptionPane.showMessageDialog(this, "Filtre guardat amb èxit.");
        });
        botoGuardarFiltre.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}