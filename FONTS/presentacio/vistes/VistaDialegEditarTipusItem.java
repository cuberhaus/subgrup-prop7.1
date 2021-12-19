package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author maria.prat
 */
public class VistaDialegEditarTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;
    private final String nomTipusItem;
    private JPanel panellEditarTipusItem;
    private JPanel panellLlistaTipusAtributs;
    private JScrollPane panellScrollLlistaTipusAtributs;

    // TODO: afegir editar TipusItem

    public VistaDialegEditarTipusItem() throws IOException {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        this.nomTipusItem = controladorMenuTipusItem.obtenirNomTipusItemSeleccionat();
        inicialitzarDialegMostrarTipusItem();
    }

    private void inicialitzarDialegMostrarTipusItem() {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8),
                Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Editar tipus d'ítem");
        setResizable(false);
        // TODO: implementar
        controladorMenuTipusItem.editarTipusItem(new HashMap<>());
    }
}