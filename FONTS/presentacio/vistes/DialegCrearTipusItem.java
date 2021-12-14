package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;

/**
 * @author maria.prat
 */
public class DialegCrearTipusItem extends JDialog {

    private final ControladorMenuTipusItem controladorMenuTipusItem;

    // TODO: afegir editar TipusItem

    public DialegCrearTipusItem() {
        super(null, Dialog.ModalityType.APPLICATION_MODAL);
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        setBounds(Pantalla.centreHoritzontal(Pantalla.amplada / 2), Pantalla.centreVertical(Pantalla.altura / 2),
                Pantalla.amplada / 4, Pantalla.altura / 4);
        setTitle("Crea un nou tipus d'ítem");
    }
}