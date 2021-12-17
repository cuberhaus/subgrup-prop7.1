package presentacio.controladors;

import presentacio.vistes.VistaMenuRecomanacions;

import java.util.ArrayList;

public class ControladorMenuRecomanacions {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuRecomanacions instancia;
    private static VistaMenuRecomanacions vistaMenuRecomanacions;

    private ControladorMenuRecomanacions() {
    }

    public static ControladorMenuRecomanacions obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuRecomanacions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }

    public boolean sessioIniciada() {
        return controladorPresentacio.esSessioIniciada();
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
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
