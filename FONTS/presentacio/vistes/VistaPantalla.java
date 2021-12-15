package presentacio.vistes;

import java.awt.*;

/**
 * @author maria.prat
 */
public class VistaPantalla {
    public static final int amplada = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int altura = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static int centreHoritzontal(int amplada) {
        return (VistaPantalla.amplada - amplada) / 2;
    }

    public static int centreVertical(int altura) {
        return (VistaPantalla.altura - altura) / 2;
    }
}
