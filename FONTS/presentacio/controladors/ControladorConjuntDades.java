package presentacio.controladors;

import presentacio.vistes.VistaMenuConjuntsDades;

import java.util.ArrayList;

/**
 * Classe que representa el controlador que gestiona els conjunts de dades
 * @author pol.casacuberta
 */
public class ControladorConjuntDades {
    private static ControladorConjuntDades instanciaUnica;
    private static ControladorPresentacio controladorPresentacio;
    private static VistaMenuConjuntsDades vistaMenuConjuntsDades;

    private ControladorConjuntDades() {
    }

    public static ControladorConjuntDades obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorConjuntDades();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuConjuntsDades = VistaMenuConjuntsDades.obtenirInstancia();
        }
        return instanciaUnica;
    }

    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorPresentacio.obtenirLlistaConjunts();
    }

    public void exportarConjuntDades(String pathConjunt) {
        controladorPresentacio.exportarConjuntDades(pathConjunt);
    }

    public void esborraConjunt(String conjuntaEsborrar) {
        controladorPresentacio.esborrarConjunt(conjuntaEsborrar);
    }
}
