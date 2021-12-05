package presentacio.controladors;

import presentacio.vistes.VistaGestioUsuari;

public class ControladorGestioUsuari {
    private VistaGestioUsuari vistaGestioUsuari;

    public ControladorGestioUsuari(ControladorPresentacio controladorPresentacio) {
        vistaGestioUsuari = new VistaGestioUsuari(controladorPresentacio);
    }

    public void aGestioUsuari () {
        vistaGestioUsuari.pack();
        vistaGestioUsuari.setVisible(true);
    }

    public void setVisible(boolean isVisible) {
        vistaGestioUsuari.setVisible(isVisible);
    }
}
