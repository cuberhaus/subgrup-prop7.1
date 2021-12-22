package presentacio.controladors;

import excepcions.NoExisteixElementException;
import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import excepcions.SessioNoIniciadaException;
import presentacio.vistes.VistaMenuRecomanacions;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ControladorMenuRecomanacions {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuRecomanacions instancia;
    private static VistaMenuRecomanacions vistaMenuRecomanacions;

    private ControladorMenuRecomanacions() {
    }

    public static ControladorMenuRecomanacions obtenirInstancia() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
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

    public double avaluarRecomanacio() {
        return controladorPresentacio.avaluarRecomanacio();
    }

    public ArrayList<String> obtenirNomAtributsTipusItemSeleccionat() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No hi ha cap tipus d'ítem seleccionat.");
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirNomAtributsTipusItemSeleccionat();
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    public ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        if (!existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirNomAtributsTipusItemSeleccionat();
    }

    public ArrayList<String> obtenirRecomanacio(String descripcioMetode, Map<String, Boolean> nomsAtributsFiltre) {
        ArrayList<String> filtre = new ArrayList<>();
        nomsAtributsFiltre.forEach((nomAtribut, inclos) -> {
            if (inclos) {
                filtre.add(nomAtribut);
            }
        });
        try {
            switch (descripcioMetode) {
                case "Basat en els ítems que has valorat":
                    return controladorPresentacio.obtenirRecomanacioContentBased(filtre, true);
                case "Basat en usuaris amb gustos semblants als teus":
                    return controladorPresentacio.obtenirRecomanacioCollaborative(filtre, true);
                case "Basat en tot":
                    return controladorPresentacio.obtenirRecomanacioHibrida(filtre, true);
            }
        } catch (SessioNoIniciadaException e1) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No has iniciat la sessió.");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No s'ha pogut obtenir la recomanació.");
        }
        return new ArrayList<>();
    }
}
