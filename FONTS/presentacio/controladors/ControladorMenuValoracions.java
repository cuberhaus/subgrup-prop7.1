package presentacio.controladors;

import presentacio.vistes.VistaMenuValoracions;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Classe que representa el controlador que gestiona les valoracions
 * @author pol.casacuberta
 */

public class ControladorMenuValoracions {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuValoracions instancia;
    private static VistaMenuValoracions vistaMenuValoracions;

    private ControladorMenuValoracions() {
    }

    public static ControladorMenuValoracions obtenirInstancia() {
        if (instancia == null){
            instancia = new ControladorMenuValoracions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuValoracions = VistaMenuValoracions.obtenirInstancia();
        }
        return instancia;
    }

    public boolean idUsuariEsValid(String id) {
        if (id == null || id.equals("")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions,"L'id d'Usuari està buit");
            return false;
        }
        else if (!id.matches("-?\\d+")){
            JOptionPane.showMessageDialog(vistaMenuValoracions,"L'id d'Usuari no és un numero natural");
            return false;
        }
        return true;
    }

    public boolean idItemEsValid(String id) {
        if (id == null || id.equals("")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions,"L'id d'item està buit");
            return false;
        }
        else if (!id.matches("-?\\d+")){
            JOptionPane.showMessageDialog(vistaMenuValoracions,"L'id d'item no és un numero natural");
            return false;
        }
        return true;
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) {
        if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
            if (!controladorPresentacio.existeixValoracio(usuariId, itemId)) {
                controladorPresentacio.afegirValoracio(usuariId, itemId, valor);
            } else {
                JOptionPane.showMessageDialog(vistaMenuValoracions, "La valoració ja existeix");
            }
        }
    }

    public void esborraValoracio(String usuariId, String itemId) {
        if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
            controladorPresentacio.esborrarValoracio(usuariId, itemId);
        }
    }

    public void editaValoracio(String usuariId, String itemId, String valor) {
        if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
            if (controladorPresentacio.existeixValoracio(usuariId,itemId)) {
                controladorPresentacio.editarValoracio(usuariId,itemId,valor);
            }
            else {
                JOptionPane.showMessageDialog(vistaMenuValoracions,"La valoració no existeix");
            }

        }
    }

    public void carregaConjuntValoracions(String pathAbsolut) {
        //TODO: comprovar que l'arxiu donat té el format correcte
        controladorPresentacio.carregarConjuntValoracions(pathAbsolut);
    }

    public void esborrarTotesLesValoracions() {
        controladorPresentacio.esborrarTotesLesValoracions();
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    public ArrayList<ArrayList<String>> obtenirValoracions() {
        return controladorPresentacio.obtenirValoracions();
    }
}
