package presentacio.controladors;

/**
 * @author pol.casacuberta
 */
public class ControladorConjuntDades {
    private static ControladorConjuntDades instanciaUnica;
    private static ControladorPresentacio controladorPresentacio;

    private ControladorConjuntDades() {
    }

    public static ControladorConjuntDades obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorConjuntDades();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
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
        controladorPresentacio.esborraConjunt(conjuntaEsborrar);
    }
}
