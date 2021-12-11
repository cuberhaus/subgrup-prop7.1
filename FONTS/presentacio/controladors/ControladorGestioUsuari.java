package presentacio.controladors;

import presentacio.vistes.GestioUsuari;

import javax.swing.*;

/**
 * @author pol.casacuberta
 */

public class ControladorGestioUsuari {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorGestioUsuari instanciaUnica;
    private static GestioUsuari gestioUsuari;

    private ControladorGestioUsuari() {
    }

    public GestioUsuari getGestioUsuari() {
        return gestioUsuari;
    }

    public static ControladorGestioUsuari obtenirInstancia(){
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorGestioUsuari();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            gestioUsuari = new GestioUsuari();
        }
        return instanciaUnica;
    }

    public void iniciarSessio(String id, String contrasenya) {
        int idSessio = controladorPresentacio.obtenirSessio();

        if (id == null) {
            System.out.println("Id text is empty");
            JOptionPane.showMessageDialog(gestioUsuari,"Id text està buit");
        }
        else {
            if (idSessio == 0) {
                controladorPresentacio.iniciarSessio(Integer.parseInt(id), contrasenya);
            }
            else {
                System.out.println("Has de tancar la sessió abans d'obrir-ne un altre");
                JOptionPane.showMessageDialog(gestioUsuari,"Has de tancar la sessió abans d'obrir-ne un altre");
            }
        }
    }
}
