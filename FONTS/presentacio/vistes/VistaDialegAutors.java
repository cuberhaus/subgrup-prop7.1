package presentacio.vistes;

import javax.swing.*;
import java.awt.*;

/**
 * @author maria.prat
 */
public class VistaDialegAutors extends JDialog {

    public VistaDialegAutors(){
        super(null, ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel panell = new JPanel(new BorderLayout());
        setTitle("PROP 2020-2021 Q1 - FIB UPC");

        JLabel text = new JLabel();
        text.setText("<html><h1>Grup 31 - Subgrup 7.1</h1> <h2>Autors:</h2> <div style='padding-left:10px'> Pol Casacuberta Gil <br> Edgar Moreno Martinez <br> Maria Prat Colomer <br> Pablo Vega Gallego <br> </div> <h2> Tutor:</h2> <div style='padding-left:10px'> Sergio Alvarez </div></html>");
        text.setFont(new Font("Sans", Font.PLAIN, 14));
        text.setHorizontalAlignment(SwingConstants.CENTER);

        panell.add(text,BorderLayout.CENTER);

        pack();
        setBounds(Pantalla.centreHoritzontal(Pantalla.amplada / 3),
                  Pantalla.centreVertical(Pantalla.altura / 3),
                Pantalla.amplada / 3, Pantalla.altura / 3);
        setLocation(Pantalla.centreHoritzontal(this.getWidth()), Pantalla.centreVertical(this.getHeight()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(panell);
    }
}
