package presentacio.controladors;

import presentacio.vistes.GestioValoracions;

import javax.swing.*;

/**
 * Classe que representa el controlador que gestiona les valoracions
 * @author pol.casacuberta
 */

public class ControladorGestioValoracions {
    private ControladorPresentacio controladorPresentacio;
    private static ControladorGestioValoracions instancia;
    private GestioValoracions gestioValoracions;

    private ControladorGestioValoracions() {
        controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        gestioValoracions = GestioValoracions.obtenirInstancia();
    }

    public static ControladorGestioValoracions obtenirInstancia() {
        if (instancia == null){
            instancia = new ControladorGestioValoracions();
        }
        return instancia;
    }

    public boolean idUsuariIsValid(String id) {
        if (id == null || id.equals("")) {
            JOptionPane.showMessageDialog(gestioValoracions,"L'id d'Usuari està buit");
            return false;
        }
        else if (!id.matches("-?\\d+")){
            JOptionPane.showMessageDialog(gestioValoracions,"L'id d'Usuari no és un numero natural");
            return false;
        }
        return true;
    }

    public boolean idItemIsValid(String id) {
        if (id == null || id.equals("")) {
            JOptionPane.showMessageDialog(gestioValoracions,"L'id d'item està buit");
            return false;
        }
        else if (!id.matches("-?\\d+")){
            JOptionPane.showMessageDialog(gestioValoracions,"L'id d'item no és un numero natural");
            return false;
        }
        return true;
    }

    public GestioValoracions getGestioValoracions() {
        return gestioValoracions;
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) {
        if (idUsuariIsValid(usuariId) && idItemIsValid(itemId)) {
            if (!controladorPresentacio.existeixValoracio(usuariId, itemId)) {
                controladorPresentacio.afegirValoracio(usuariId, itemId, valor);
            } else {
                JOptionPane.showMessageDialog(gestioValoracions, "La valoració ja existeix");
            }
        }
    }

    public void esborraValoracio(String usuariId, String itemId) {
        if (idUsuariIsValid(usuariId) && idItemIsValid(itemId)) {
            controladorPresentacio.esborraValoracio(usuariId, itemId);
        }
    }

    public void editaValoracio(String usuariId, String itemId, String valor) {
        if (idUsuariIsValid(usuariId) && idItemIsValid(itemId)) {
            if (controladorPresentacio.existeixValoracio(usuariId,itemId)) {
                controladorPresentacio.editarValoracio(usuariId,itemId,valor);
            }
            else {
                JOptionPane.showMessageDialog(gestioValoracions,"La valoració no existeix");
            }

        }
    }

    public void carregaConjuntValoracions(String pathAbsolut) {
        //TODO: comprovar que l'arxiu donat té el format correcte
        controladorPresentacio.carregaConjuntValoracions(pathAbsolut);
    }
}
