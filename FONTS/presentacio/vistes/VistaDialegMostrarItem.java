package presentacio.vistes;

import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Vista pel dialeg que permet mostrar un item.
 * @author maria.prat
 */
public class VistaDialegMostrarItem extends JDialog {

    private final ControladorMenuItems controladorMenuItems;

    public VistaDialegMostrarItem(String id, Map<String, String> atributs) throws Exception {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuItems = ControladorMenuItems.obtenirInstancia();
        inicialitzarDialegMostrarItem(id, atributs);
    }

    private void inicialitzarDialegMostrarItem(String id, Map<String, String> atributs) {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Mostra un ítem");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());
        add(panellPrincipal);

        JPanel panellLlistaAtributs = new JPanel();
        panellLlistaAtributs.setLayout(new BoxLayout(panellLlistaAtributs, BoxLayout.Y_AXIS));

        JScrollPane panellScrollLlistaAtributs = new JScrollPane(panellLlistaAtributs);
        panellScrollLlistaAtributs.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellPrincipal.add(panellScrollLlistaAtributs, BorderLayout.CENTER);

        if (controladorMenuItems.obtenirNomsAtributsTipusItemSeleccionat().isEmpty()) {
            JLabel text = new JLabel("El tipus d'ítem seleccionat no té cap atribut.");
            text.setAlignmentX(Component.CENTER_ALIGNMENT);
            text.setFont(new Font("Sans", Font.BOLD, 13));
            panellLlistaAtributs.add(Box.createVerticalGlue());
            panellLlistaAtributs.add(text);
            panellLlistaAtributs.add(Box.createVerticalGlue());
        } else {
            for (Map.Entry<String, String> atribut : atributs.entrySet()) {
                JPanel panellAtribut = new JPanel(new FlowLayout());
                JLabel etiquetaNomAtribut = new JLabel(atribut.getKey());
                panellAtribut.add(etiquetaNomAtribut);
                JTextField valorAtribut = new JTextField();
                valorAtribut.setColumns(10);
                valorAtribut.setText(atribut.getValue());
                valorAtribut.setEnabled(false);
                panellAtribut.add(valorAtribut);
                panellLlistaAtributs.add(panellAtribut);
            }
        }
    }
}