package presentacio.controladors;

import presentacio.vistes.VistaMenuItems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ControladorMenuItems {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuItems instancia;
    private static VistaMenuItems vistaMenuItems;

    private ControladorMenuItems () {
    }

    public static ControladorMenuItems obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuItems();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }

    public ArrayList<ArrayList<String>> obtenirItems() {
        return controladorPresentacio.obtenirItems();
    }

    public ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomAtributsTipusItemSeleccionat();
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    public boolean afegirItem(Map<String, String> valorsAtributs) {
        return controladorPresentacio.afegirItem(valorsAtributs);
    }

    public boolean esborrarItem(String id) {
        return controladorPresentacio.esborrarItem(id);
    }

    public Map<String, String> obtenirItem(String id) {
        return controladorPresentacio.obtenirItem(id);
    }

    public boolean editarItem(String id, Map<String, String> valorsAtributs) {
        return controladorPresentacio.editarItem(id, valorsAtributs);
    }

    public void carregarConjuntItems(String rutaAbsoluta) {
        controladorPresentacio.carregarConjuntItems(rutaAbsoluta);
    }

    public void esborrarTotsElsItems() {
        controladorPresentacio.esborrarTotsElsItems();
    }

    public boolean sessioIniciada() {
        return controladorPresentacio.sessioIniciada();
    }

    public ArrayList<String> obtenirRecomanacioCollaborative(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        return controladorPresentacio.obtenirRecomanacioCollaborative(nomAtributs, filtreInclusiu);
    }

    public ArrayList<String> obtenirRecomanacioContentBased(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        return controladorPresentacio.obtenirRecomanacioContentBased(nomAtributs, filtreInclusiu);
    }

    public ArrayList<String> obtenirRecomanacioHibrida(ArrayList<String> nomAtributs, boolean filtreInclusiu) {
        return controladorPresentacio.obtenirRecomanacioHibrida(nomAtributs, filtreInclusiu);
    }

    public double avaluarRecomanacio() {
        return controladorPresentacio.avaluarRecomanacio();
    }
}
