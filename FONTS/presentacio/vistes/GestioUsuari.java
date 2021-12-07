package presentacio.vistes;

import javax.swing.*;
import java.awt.*;

public class GestioUsuari extends JPanel {

    public GestioUsuari() {
        this.inicialitzarGestioUsuari();
    }
    public void inicialitzarGestioUsuari() {
        JButton button = new JButton();
        this.add(button);
    }
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.drawOval(0, 0, width, height);
    }
}
