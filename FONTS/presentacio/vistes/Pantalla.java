package presentacio.vistes;

import java.awt.*;

/**
 * @author maria.prat
 */
public class Pantalla {
    public static final int amplada = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int altura = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static int centreHoritzontal(int amplada) {
        return (Pantalla.amplada - amplada) / 2;
    }

    public static int centreVertical(int altura) {
        return (Pantalla.altura - altura) / 2;
    }
}
