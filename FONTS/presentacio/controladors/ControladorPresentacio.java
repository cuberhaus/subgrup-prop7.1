package presentacio.controladors;

import domini.controladors.ControladorDomini;

import java.util.ArrayList;
import java.util.Map;

/**
 * Classe que representa el controlador de presentació
 * @author maria.prat i pol.casacuberta
 */
public class ControladorPresentacio {

    private static ControladorDomini controladorDomini;
    private static ControladorPresentacio instanciaUnica = null;
    private static ControladorGestioUsuari controladorGestioUsuari;
    private static ControladorMenuPrincipal controladorMenuPrincipal;
    private static ControladorMenuTipusItem controladorMenuTipusItem;

    private ControladorPresentacio() {
    }

    public static ControladorPresentacio obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorPresentacio();
            controladorDomini = ControladorDomini.obtenirInstancia();
            controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
            controladorGestioUsuari = ControladorGestioUsuari.obtenirInstancia();
            controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        }
        return instanciaUnica;
    }

    public ArrayList<String> obtenirNomsTipusItemsCarregats() {
        return controladorDomini.obtenirNomsTipusItemsCarregats();
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

    public void afegirUsuari(int id, String contrasenya, String nom) {
        controladorDomini.afegirUsuari(id,contrasenya,nom);
    }

    public void esborrarUsuari(int id) {
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

    public ArrayList<String> obtenirLlistaConjunts() {
        return controladorDomini.obtenirLlistaConjunts();
    }

    public void exportarConjuntDades(String pathConjunt) {
        controladorDomini.exportarConjuntDades(pathConjunt);
    }

    public void esborrarConjunt(String conjuntaEsborrar) {
        controladorDomini.esborraConjunt(conjuntaEsborrar);
    }

    public boolean afegirTipusItem(String nom, Map<String, String> valorsTipusAtributs, Map<String, String> distanciesTipusAtributs) {
        return controladorDomini.afegirTipusItem(nom, valorsTipusAtributs, distanciesTipusAtributs);
    }

    public boolean carregarTipusItem(String rutaAbsoluta) {
        return controladorDomini.carregarTipusItem(rutaAbsoluta);
    }

    public void esborrarTipusItem(String nomTipusItem) {
        controladorDomini.esborrarTipusItem(nomTipusItem);
    }

    public Map<String, String> obtenirValorsTipusAtributs(String nomTipusItem) {
        return controladorDomini.obtenirValorsTipusAtributs(nomTipusItem);
    }

    public Map<String, String> obtenirDistanciesTipusAtributs(String nomTipusItem) {
        return controladorDomini.obtenirDistanciesTipusAtributs(nomTipusItem);
    }

    public boolean isSessioIniciada() {
        return controladorDomini.isSessioIniciada();
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorDomini.obtenirNomTipusItemSeleccionat();
    }

    public void esborrarTipusItemSeleccionat() {
        controladorDomini.esborrarTipusItemSeleccionat();
    }

    public void seleccionarTipusItem(String nomTipusItem) {
        controladorDomini.seleccionarTipusItem(nomTipusItem);
    }

    public ArrayList<ArrayList<String>> obtenirItems() {
        return controladorDomini.obtenirItems();
    }

    public ArrayList<String> obtenirNomAtributsTipusItemSeleccionat() {
        return controladorDomini.obtenirNomsAtributsTipusItemSeleccionat();
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorDomini.existeixTipusItemSeleccionat();
    }

    public boolean afegirItem(Map<String, String> valorsAtributs) {
        return controladorDomini.afegirItem(valorsAtributs);
    }

    public boolean esborrarItem(String id) {
        return controladorDomini.esborrarItem(id);
    }

    public Map<String, String> obtenirItem(String id) {
        return controladorDomini.obtenirItem(id);
    }

    public boolean editarItem(String id, Map<String, String> valorsAtributs) {
        return controladorDomini.editarItem(id, valorsAtributs);
    }

    public void carregarConjuntItems(String rutaAbsoluta) {
        controladorDomini.carregarConjuntItems(rutaAbsoluta);
    }

    public void esborrarTotsElsItems() {
        controladorDomini.esborrarTotsElsItems();
    }

    public void editarTipusItem(Map<String, String> relacioNomsTipusAtributs) {
        controladorDomini.editarTipusItem(relacioNomsTipusAtributs);
    }
}
