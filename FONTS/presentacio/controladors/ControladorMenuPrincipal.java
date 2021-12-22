package presentacio.controladors;

import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.vistes.VistaMenuPrincipal;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Classe que representa el controlador del Menu principal
 * @author pol.casacuberta i maria.prat
 */

public class ControladorMenuPrincipal {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuPrincipal instancia;
    private static VistaMenuPrincipal vistaMenuPrincipal;

    private ControladorMenuPrincipal () {
    }

    public static ControladorMenuPrincipal obtenirInstancia() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        if (instancia == null) {
            instancia = new ControladorMenuPrincipal();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuPrincipal = VistaMenuPrincipal.obtenirInstancia();
            vistaMenuPrincipal.setVisible(true);
            vistaMenuPrincipal.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    try {
                        controladorPresentacio.guardarPrograma();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(vistaMenuPrincipal, "No s'han pogut guardar correctament les dades.");
                    }
                }
            });
        }
        return instancia;
    }

    /**
     * Obre el manual d'usuari.
     * @throws IOException si hi ha cap problema.
     */
    public void obreManual() throws IOException{
        controladorPresentacio.obreManual();
    }
}
