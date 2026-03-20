package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;

/**
 * Vista pel dialeg que permet l'edicio d'un tipus d'item.
 * @author maria.prat
 */
public class VistaDialegEditarTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;
    private final String nomTipusItem;
    private JPanel panellEditarTipusItem;
    private JPanel panellLlistaTipusAtributs;
    private JScrollPane panellScrollLlistaTipusAtributs;

    public VistaDialegEditarTipusItem() throws Exception {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        this.nomTipusItem = controladorMenuTipusItem.obtenirNomTipusItemSeleccionat();
        inicialitzarDialegEditarTipusItem();
    }

    private void inicialitzarDialegEditarTipusItem() {
        setBounds(Pantalla.centreHoritzontal(5 * Pantalla.amplada / 8),
                Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Editar tipus d'ítem");
        setResizable(false);

        JPanel panellPrincipal = new JPanel();
        panellPrincipal.setLayout(new BoxLayout(panellPrincipal, BoxLayout.Y_AXIS));
        panellPrincipal.setBorder(UIEstil.panelBorder());

        panellPrincipal.add(Box.createVerticalGlue());

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        formPanel.setOpaque(false);
        formPanel.add(UIEstil.createLabel("Edita el nom del tipus d'ítem:"));

        JTextField nomField = UIEstil.createTextField(15);
        nomField.setText(controladorMenuTipusItem.obtenirNomTipusItemSeleccionat());
        formPanel.add(nomField);

        JButton botoGuardar = UIEstil.createAccentButton("Guarda");
        botoGuardar.setPreferredSize(UIEstil.BUTTON_SIZE_SMALL);
        botoGuardar.setMaximumSize(UIEstil.BUTTON_SIZE_SMALL);
        botoGuardar.setMinimumSize(UIEstil.BUTTON_SIZE_SMALL);
        botoGuardar.addActionListener(e -> {
            if (controladorMenuTipusItem.editarTipusItem(this, nomField.getText())) {
                dispose();
            }
        });
        formPanel.add(botoGuardar);

        panellPrincipal.add(formPanel);
        panellPrincipal.add(Box.createVerticalGlue());

        add(panellPrincipal);
    }
}
