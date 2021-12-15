package presentacio.controladors;

import presentacio.vistes.ConjuntDades;

/**
 * Classe que representa el controlador que gestiona els conjunts de dades
 * @author pol.casacuberta
 */
public class ControladorConjuntDades {
    private static ControladorConjuntDades instanciaUnica;
    private ControladorPresentacio controladorPresentacio;
    private ConjuntDades conjuntDades;

    private ControladorConjuntDades() {
        controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        conjuntDades = ConjuntDades.obtenirInstancia();
    }

    public static ControladorConjuntDades obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorConjuntDades();
        }
        return instanciaUnica;
    }

    public String[] obtenirLlistaConjunts() {
        return controladorPresentacio.obtenirLlistaConjunts();
    }

    public void exportarConjuntDades(String pathConjunt) {
        controladorPresentacio.exportarConjuntDades(pathConjunt);
    }

    public void esborraConjunt(String conjuntaEsborrar) {
        controladorPresentacio.esborrarConjunt(conjuntaEsborrar);
    }
}
