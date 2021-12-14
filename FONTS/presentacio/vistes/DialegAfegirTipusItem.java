package presentacio.vistes;

import presentacio.controladors.ControladorPresentacio;

import javax.swing.*;
import java.awt.*;

public class DialegAfegirTipusItem extends JDialog {

    // TODO: afegir editar TipusItem

    public DialegAfegirTipusItem(){
        super(null, Dialog.ModalityType.APPLICATION_MODAL);
        setBounds(Pantalla.centreHoritzontal(Pantalla.amplada / 2), Pantalla.centreVertical(Pantalla.altura / 2),
                Pantalla.amplada / 4, Pantalla.altura / 4);
        setTitle("Afegir nou tipus d'ítem");
    }
}