package presentacio.vistes;

import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.FormatIncorrecteException;
import excepcions.JaExisteixElementException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
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
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8),
                Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Editar tipus d'ítem");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new FlowLayout());
        panellPrincipal.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel descripcio = new JLabel("Edita el nom del tipus d'ítem: ");
        descripcio.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcio.setAlignmentY(Component.CENTER_ALIGNMENT);
        panellPrincipal.add(descripcio);

        JTextField nomTipusItem = new JTextField();
        nomTipusItem.setColumns(10);
        nomTipusItem.setText(controladorMenuTipusItem.obtenirNomTipusItemSeleccionat());
        nomTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomTipusItem.setAlignmentY(Component.CENTER_ALIGNMENT);
        panellPrincipal.add(nomTipusItem);

        JButton botoGuardarTipusItem = new JButton("Guarda");
        botoGuardarTipusItem.addActionListener(e -> {
            if (controladorMenuTipusItem.editarTipusItem(this, nomTipusItem.getText())) {
                dispose();
            }
        });
        botoGuardarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoGuardarTipusItem.setAlignmentY(Component.CENTER_ALIGNMENT);
        panellPrincipal.add(botoGuardarTipusItem);

        add(panellPrincipal);
    }
}