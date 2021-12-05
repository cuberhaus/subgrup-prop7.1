package presentacio.controladors;

import presentacio.vistes.VistaGestioConjunts;
import presentacio.vistes.VistaGestioValoracions;

public class ControladorPresentacio {
    private ControladorGestioUsuari controladorGestioUsuari;
    private ControladorGestioPrograma controladorGestioPrograma;
    private VistaGestioConjunts vistaGestioConjunts;
    private VistaGestioValoracions vistaGestioValoracions;

    public ControladorPresentacio() {
        vistaGestioConjunts = new VistaGestioConjunts();
        controladorGestioPrograma = new ControladorGestioPrograma(this);
        controladorGestioUsuari = new ControladorGestioUsuari(this);
        vistaGestioValoracions = new VistaGestioValoracions();
    }

    public void aPantallaInici() {
        controladorGestioUsuari.setVisible(false);
        controladorGestioPrograma.aGestioPrograma();
    }

    public void aGestioUsuari() {
       controladorGestioPrograma.setVisible(false);
       controladorGestioUsuari.aGestioUsuari();
    }
    public void aGestioConjunts() {

    }
    public void aGestioValoracions () {

    }

    public static void main(String[] args) {
        ControladorPresentacio controladorPresentacio = new ControladorPresentacio();
        controladorPresentacio.aPantallaInici();
    }
}
