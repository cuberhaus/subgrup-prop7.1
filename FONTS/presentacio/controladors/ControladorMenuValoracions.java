package presentacio.controladors;

import presentacio.vistes.VistaMenuValoracions;

import javax.swing.*;

/**
 * Classe que representa el controlador que gestiona les valoracions
 * @author pol.casacuberta
 */

public class ControladorMenuValoracions {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuValoracions instanciaUnica;
    private static VistaMenuValoracions vistaMenuValoracions;

    private ControladorMenuValoracions() {
    }

    public static ControladorMenuValoracions obtenirInstancia() {
        if (instanciaUnica == null){
            instanciaUnica = new ControladorMenuValoracions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuValoracions = VistaMenuValoracions.obtenirInstancia();
        }
        return instanciaUnica;
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

    public VistaMenuValoracions getGestioValoracions() {
        return vistaMenuValoracions;
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
            controladorPresentacio.esborraValoracio(usuariId, itemId);
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
        controladorPresentacio.carregaConjuntValoracions(pathAbsolut);
    }
}
