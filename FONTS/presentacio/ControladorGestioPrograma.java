package presentacio;

public class ControladorGestioPrograma {
    private VistaGestioPrograma vistaGestioPrograma;

    public ControladorGestioPrograma(ControladorPresentacio controladorPresentacio) {
        vistaGestioPrograma = new VistaGestioPrograma(controladorPresentacio);
    }

    public void aGestioPrograma () {
        vistaGestioPrograma.pack();
        vistaGestioPrograma.setVisible(true);
    }

    public void setVisible(boolean isVisible) {
        vistaGestioPrograma.setVisible(isVisible);
    }
}
