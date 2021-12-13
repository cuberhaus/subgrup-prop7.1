package presentacio.controladors;

import domini.classes.TipusItem;
import domini.controladors.ControladorDomini;

/**
 * @author maria.prat i pol.casacuberta
 */
public class ControladorPresentacio {

    private static ControladorDomini controladorDomini;
    private static ControladorPresentacio instanciaUnica = null;
    private static ControladorGestioUsuari controladorGestioUsuari;
    private static ControladorMenuPrincipal controladorMenuPrincipal;

    private ControladorPresentacio() {
    }

    public static ControladorPresentacio obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorPresentacio();
            controladorDomini = new ControladorDomini();
            controladorGestioUsuari = ControladorGestioUsuari.obtenirInstancia();
            controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
            controladorMenuPrincipal.inicialitzarControladorMenuPrincipal();
        }
        return instanciaUnica;
    }

    public TipusItem[] obtenirTipusItemsCarregats() {
        // TODO: implementar
        return new TipusItem[]{new TipusItem("Llibres"), new TipusItem("Música"), new TipusItem("Tipus d'item amb un nom molt llarg")};
        //return new TipusItem[]{};
    }

    public int obtenirSessio() {
        return controladorDomini.obtenirSessio();
    }

    public void iniciarSessio(int idSessio, String contrasenya) {
        controladorDomini.iniciarSessio(idSessio, contrasenya);
    }

    public boolean existeixUsuari(int id) {
        return controladorDomini.existeixUsuari(id);
    }

    public void afegirUsuari(String id, String contrasenya, String nom) {
        controladorDomini.afegirUsuari(id,contrasenya,nom);
    }

    public void esborrarUsuari(String id) {
        controladorDomini.esborrarUsuari(id);
    }

    public void tancarSessio() {
        controladorDomini.tancarSessio();
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) {
        controladorDomini.afegirValoracio(usuariId,itemId,valor);
    }

    public boolean existeixValoracio(String usuariId, String itemId) {
        return controladorDomini.existeixValoracio(usuariId,itemId);
    }

    public void esborraValoracio(String usuariId, String itemId) {
        controladorDomini.esborraValoracio(usuariId,itemId);
    }

    public void editarValoracio(String usuariId, String itemId, String valor) {
        controladorDomini.editarValoracio(usuariId,itemId,valor);
    }

    public void carregaConjuntValoracions(String pathAbsolut) {
        controladorDomini.carregaConjuntValoracions(pathAbsolut);
    }

    public String[] obtenirLlistaConjunts() {
        return controladorDomini.obtenirLlistaConjunts();
    }

    public void exportarConjuntDades(String pathConjunt) {
        controladorDomini.exportarConjuntDades(pathConjunt);
    }
}
