package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;

/**
 * @author maria.prat
 */
public class DialegMostrarTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;

    // TODO: afegir editar TipusItem

    public DialegMostrarTipusItem() {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        setBounds(Pantalla.centreHoritzontal(Pantalla.amplada / 2), Pantalla.centreVertical(Pantalla.altura / 2),
                Pantalla.amplada / 4, Pantalla.altura / 4);
        setTitle("Mostrar tipus d'ítem");
    }
}