package presentacio.vistes;

import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.FormatIncorrecteException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maria.prat
 */
public class VistaDialegCrearItem extends JDialog {

    private final ControladorMenuItems controladorMenuItems;

    public VistaDialegCrearItem() throws DistanciaNoCompatibleAmbValorException, NomInternIncorrecteException, IOException {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuItems = ControladorMenuItems.obtenirInstancia();
        inicialitzarDialegCrearItem();
    }

    private void inicialitzarDialegCrearItem() {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Crea un nou ítem");
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
            for (String nomAtribut : controladorMenuItems.obtenirNomsAtributsTipusItemSeleccionat()) {
                JPanel panellAtribut = new JPanel(new FlowLayout());
                JLabel etiquetaNomAtribut = new JLabel(nomAtribut);
                panellAtribut.add(etiquetaNomAtribut);
                JTextField valorAtribut = new JTextField();
                valorAtribut.setColumns(10);
                panellAtribut.add(valorAtribut);
                panellLlistaAtributs.add(panellAtribut);
            }
        }

        JPanel panellBotoCrearItem = new JPanel(new FlowLayout());
        JButton botoCrearItem = new JButton("Crea ítem");
        botoCrearItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellBotoCrearItem.add(botoCrearItem);
        panellPrincipal.add(panellBotoCrearItem, BorderLayout.SOUTH);

        botoCrearItem.addActionListener(e -> {
            Map<String, String> valorsAtributs = new HashMap<>();
            if (!controladorMenuItems.obtenirNomsAtributsTipusItemSeleccionat().isEmpty()) {
                for (Component component : panellLlistaAtributs.getComponents()) {
                    JPanel atribut = (JPanel) component;
                    String nomAtribut = ((JLabel) atribut.getComponent(0)).getText();
                    String valorTipusAtribut = ((JTextField) atribut.getComponent(1)).getText();
                    valorsAtributs.put(nomAtribut, valorTipusAtribut);
                }
            }
            controladorMenuItems.afegirItem(valorsAtributs);
            dispose();
        });
        botoCrearItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}