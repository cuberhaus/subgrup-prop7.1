package presentacio.vistes;

import presentacio.controladors.ControladorPresentacio;

import javax.swing.*;

public class DialegMostrarTipusItem extends JDialog {

    // TODO: afegir editar TipusItem

    public DialegMostrarTipusItem(){
        super(null, ModalityType.APPLICATION_MODAL);
        setBounds(Pantalla.centreHoritzontal(Pantalla.amplada / 2), Pantalla.centreVertical(Pantalla.altura / 2),
                Pantalla.amplada / 4, Pantalla.altura / 4);
        setTitle("Mostrar tipus d'ítem");
    }
}