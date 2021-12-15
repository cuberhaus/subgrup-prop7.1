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

        JPanel panell = new JPanel(new BorderLayout());
        setTitle("PROP 2020-2021 Q1 - FIB UPC");

        JLabel text = new JLabel();
        text.setText("<html><h1>Grup 31 - Subgrup 7.1</h1> <h2>Autors:</h2> <div style='padding-left:10px'> Pol Casacuberta Gil <br> Edgar Moreno Martínez <br> Maria Prat Colomer <br> Pablo Vega Gallego <br> </div> <h2> Tutor:</h2> <div style='padding-left:10px'> Sergio Álvarez </div></html>");
        text.setFont(new Font("Sans", Font.PLAIN, 14));
        text.setHorizontalAlignment(SwingConstants.CENTER);

        panell.add(text,BorderLayout.CENTER);

        pack();
        setBounds(VistaPantalla.centreHoritzontal(VistaPantalla.amplada / 3),
                  VistaPantalla.centreVertical(VistaPantalla.altura / 3),
                VistaPantalla.amplada / 3, VistaPantalla.altura / 3);
        setLocation(VistaPantalla.centreHoritzontal(this.getWidth()), VistaPantalla.centreVertical(this.getHeight()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(panell);
    }
}
