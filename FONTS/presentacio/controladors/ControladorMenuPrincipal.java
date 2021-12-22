package presentacio.controladors;

import presentacio.EncarregatActualitzarVistes;
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

    /**
     * Constructor per defecte del controlador
     */
    private ControladorMenuPrincipal () {
    }

    /**
     * @return l'única instància del controlador, seguint el patró Singleton
     * @throws Exception si hi ha algun problema a l'hora de carregar les dades del programa
     */
    public static ControladorMenuPrincipal obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ControladorMenuPrincipal();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuPrincipal = VistaMenuPrincipal.obtenirInstancia();
            vistaMenuPrincipal.setVisible(true);
            // Guarda les dades del programa abans de tancar la finestra principal.
            vistaMenuPrincipal.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    try {
                        controladorPresentacio.guardarPrograma();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(vistaMenuPrincipal,
                                "No s'han pogut guardar correctament les dades.");
                    }
                }
            });
            // Afegeix els controladors de les vistes com a observadors de l'encarregat d'actualitzar-les
            EncarregatActualitzarVistes.afegirObservador(ControladorMenuTipusItem.obtenirInstancia());
            EncarregatActualitzarVistes.afegirObservador(ControladorMenuItems.obtenirInstancia());
            EncarregatActualitzarVistes.afegirObservador(ControladorMenuUsuaris.obtenirInstancia());
            EncarregatActualitzarVistes.afegirObservador(ControladorMenuValoracions.obtenirInstancia());
        }
        return instancia;
    }

    /**
     * Obre el manual d'usuari.
     * @throws IOException si hi ha un problema a l'hora de llegir l'arxiu
     */
    public void obreManual() throws IOException{
        controladorPresentacio.obreManual();
    }
}
