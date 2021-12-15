package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

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

    public VistaDialegEditarTipusItem() {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        this.nomTipusItem = controladorMenuTipusItem.obtenirNomTipusItemSeleccionat();
        inicialitzarDialegMostrarTipusItem();
    }

    private void inicialitzarDialegMostrarTipusItem() {
        setBounds(VistaPantalla.centreHoritzontal( 5 * VistaPantalla.amplada / 8),
                VistaPantalla.centreVertical(VistaPantalla.altura / 2),
                5 * VistaPantalla.amplada / 8, VistaPantalla.altura / 2);
        setTitle("Editar tipus d'ítem");
        // TODO: implementar
        controladorMenuTipusItem.editarTipusItem(new HashMap<>());
    }
}