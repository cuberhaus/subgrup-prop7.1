package presentacio.controladors;

import excepcions.*;
import presentacio.vistes.VistaMenuRecomanacions;
import utilitats.Pair;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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

    public static boolean sessioIniciada() {
        return controladorPresentacio.esSessioIniciada();
    }

    public static boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    public static String avaluarRecomanacio(ArrayList<Pair<String, String>> valoracions) {
        ArrayList<Pair<Integer, Double>> valoracionsAmbFormat = new ArrayList<>();
        try {
            for (Pair<String, String> valoracio : valoracions) {
                if (valoracio.y != null && !valoracio.y.isEmpty()) {
                    double valor = Double.parseDouble(valoracio.y);
                    if (valor < 0) {
                        JOptionPane.showMessageDialog(vistaMenuRecomanacions, "Les valoracions han de ser nombres positius.");
                        return "";
                    }
                    valoracionsAmbFormat.add(new Pair<>(Integer.parseInt(valoracio.x), valor));
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "Les valoracions han de ser nombres.");
            return "";
        }
        return String.valueOf(controladorPresentacio.avaluarRecomanacio(valoracionsAmbFormat));
    }

    public static ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No hi ha cap tipus d'ítem seleccionat.");
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirNomAtributsTipusItemSeleccionat();
    }

    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
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

    public Map<String, String> obtenirItem(String itemId) {
        try {
            return controladorPresentacio.obtenirItem(itemId);
        } catch (NoExisteixElementException e1) {
            JOptionPane.showMessageDialog(vistaMenuRecomanacions, "No existeix cap ítem amb identificador " + itemId);
            return new TreeMap<>();
        }
    }
}
