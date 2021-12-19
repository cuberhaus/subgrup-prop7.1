package presentacio.controladors;

import presentacio.vistes.VistaMenuValoracions;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que representa el controlador que gestiona les valoracions
 *
 * @author pol.casacuberta
 */

public class ControladorMenuValoracions {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuValoracions instancia;
    private static VistaMenuValoracions vistaMenuValoracions;

    private ControladorMenuValoracions() {
    }

    public static ControladorMenuValoracions obtenirInstancia() throws IOException {
        if (instancia == null) {
            instancia = new ControladorMenuValoracions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuValoracions = VistaMenuValoracions.obtenirInstancia();
        }
        return instancia;
    }

    public boolean idUsuariEsValid(String id) {
        if (id == null || id.equals("")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, "L'id d'Usuari està buit");
            return false;
        } else if (!id.matches("-?\\d+")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, "L'id d'Usuari no és un numero natural");
            return false;
        }
        return true;
    }

    public boolean idItemEsValid(String id) {
        if (id == null || id.equals("")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, "L'id d'item està buit");
            return false;
        } else if (!id.matches("-?\\d+")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, "L'id d'item no és un numero natural");
            return false;
        }
        return true;
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) throws Exception {
        try {
            if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
                if (!controladorPresentacio.existeixValoracio(usuariId, itemId)) {
                    controladorPresentacio.afegirValoracio(usuariId, itemId, valor);
                } else {
                    JOptionPane.showMessageDialog(vistaMenuValoracions, "La valoració ja existeix");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
    }

    public void esborrarValoracio(String usuariId, String itemId) throws Exception {
        try {
            if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
                controladorPresentacio.esborrarValoracio(usuariId, itemId);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
    }

    public void editarValoracio(String usuariId, String itemId, String valor) throws Exception {
        try {
            if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
                if (controladorPresentacio.existeixValoracio(usuariId, itemId)) {
                    controladorPresentacio.editarValoracio(usuariId, itemId, valor);
                } else {
                    JOptionPane.showMessageDialog(vistaMenuValoracions, "La valoració no existeix");
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
    }

    public void carregarConjuntValoracions(String rutaAbsoluta) throws Exception {
        //TODO: comprovar que l'arxiu donat té el format correcte
        controladorPresentacio.carregarConjuntValoracions(rutaAbsoluta);
    }

    public void esborrarTotesLesValoracions() {
        try {
            controladorPresentacio.esborrarTotesLesValoracions();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    public ArrayList<ArrayList<String>> obtenirValoracions() {
        return controladorPresentacio.obtenirValoracions();
    }

    public boolean existeixValoracio(String idUsuari, String idItem) throws Exception {
        try {
            return controladorPresentacio.existeixValoracio(idUsuari, idItem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
        return false;
    }

}
