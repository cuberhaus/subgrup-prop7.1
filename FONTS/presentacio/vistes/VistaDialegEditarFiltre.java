package presentacio.vistes;

import presentacio.controladors.ControladorMenuRecomanacions;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Vista pel dialeg que permet editar el filtre d'una recomanacio
 * @author maria.prat
 */
public class VistaDialegEditarFiltre extends JDialog {

    private final ControladorMenuRecomanacions controladorMenuRecomanacions;

    public VistaDialegEditarFiltre(Map<String, Boolean> nomsAtributsFiltre) throws Exception {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuRecomanacions = ControladorMenuRecomanacions.obtenirInstancia();
        inicialitzarDialegEditarFiltre(nomsAtributsFiltre);
    }

    private void inicialitzarDialegEditarFiltre(Map<String, Boolean> nomsAtributsFiltre) {
        setBounds(Pantalla.centreHoritzontal(5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Edita el filtre");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());
        panellPrincipal.setBorder(UIEstil.panelBorder());
        add(panellPrincipal);

        JPanel panellLlistaAtributs = new JPanel();
        panellLlistaAtributs.setLayout(new BoxLayout(panellLlistaAtributs, BoxLayout.Y_AXIS));
        panellLlistaAtributs.setBorder(UIEstil.panelBorder());

        JScrollPane panellScrollLlistaAtributs = new JScrollPane(panellLlistaAtributs);
        panellScrollLlistaAtributs.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellPrincipal.add(panellScrollLlistaAtributs, BorderLayout.CENTER);

        if (ControladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat().isEmpty()) {
            JLabel text = UIEstil.createLabel("El tipus d'ítem seleccionat no té cap atribut.");
            text.setFont(UIEstil.FONT_BUTTON);
            text.setAlignmentX(Component.CENTER_ALIGNMENT);
            panellLlistaAtributs.add(Box.createVerticalGlue());
            panellLlistaAtributs.add(text);
            panellLlistaAtributs.add(Box.createVerticalGlue());
        } else {
            for (Map.Entry<String, Boolean> atribut : nomsAtributsFiltre.entrySet()) {
                JCheckBox checkBoxAtribut = new JCheckBox();
                checkBoxAtribut.setText(atribut.getKey());
                checkBoxAtribut.setSelected(atribut.getValue());
                checkBoxAtribut.setFont(UIEstil.FONT_LABEL);
                checkBoxAtribut.setAlignmentX(Component.LEFT_ALIGNMENT);
                checkBoxAtribut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                panellLlistaAtributs.add(checkBoxAtribut);
                panellLlistaAtributs.add(UIEstil.verticalGap());
            }
        }

        JPanel panellBoto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panellBoto.setOpaque(false);
        JButton botoGuardarFiltre = UIEstil.createAccentButton("Guarda filtre");
        panellBoto.add(botoGuardarFiltre);
        panellPrincipal.add(panellBoto, BorderLayout.SOUTH);

        botoGuardarFiltre.addActionListener(e -> {
            if (!ControladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat().isEmpty()) {
                for (Component component : panellLlistaAtributs.getComponents()) {
                    if (component instanceof JCheckBox) {
                        JCheckBox atribut = (JCheckBox) component;
                        nomsAtributsFiltre.put(atribut.getText(), atribut.isSelected());
                    }
                }
            }
            dispose();
            JOptionPane.showMessageDialog(this, "Filtre guardat amb èxit.");
        });
    }
}
