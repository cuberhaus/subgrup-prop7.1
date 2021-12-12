package presentacio.controladors;

import presentacio.vistes.GestioValoracions;

import javax.swing.*;

/**
 * @author pol.casacuberta
 */

public class ControladorGestioValoracions {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorGestioValoracions instanciaUnica;
    private static GestioValoracions gestioValoracions;

    private ControladorGestioValoracions() {
    }

    public static ControladorGestioValoracions obtenirInstancia() {
        if (instanciaUnica == null){
            instanciaUnica = new ControladorGestioValoracions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            gestioValoracions = new GestioValoracions();
        }
        return instanciaUnica;
    }

    public boolean idIsValid(String id) {
        if (id == null || id.equals("")) {
            System.out.println("Id text is empty");
            JOptionPane.showMessageDialog(gestioValoracions,"Id text està buit");
            return false;
        }
        else if (!id.matches("-?\\d+")){
            JOptionPane.showMessageDialog(gestioValoracions,"L'id no és un numero natural");
            return false;
        }
        return true;
    }

    public GestioValoracions getGestioValoracions() {
        return gestioValoracions;
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) {
        if (idIsValid(usuariId) && idIsValid(itemId)) {
            if (!controladorPresentacio.existeixValoracio(usuariId, itemId)) {
                controladorPresentacio.afegirValoracio(usuariId, itemId, valor);
            } else {
                JOptionPane.showMessageDialog(gestioValoracions, "La valoració ja existeix");
            }
        }
    }

    public void esborraValoracio(String usuariId, String itemId) {
        if (idIsValid(usuariId) && idIsValid(itemId)) {
            controladorPresentacio.esborraValoracio(usuariId, itemId);
        }
    }

    public void editaValoracio(String usuariId, String itemId, String valor) {
        if (idIsValid(usuariId) && idIsValid(itemId)) {
            if (controladorPresentacio.existeixValoracio(usuariId,itemId)) {
                controladorPresentacio.editarValoracio(usuariId,itemId,valor);
            }
            else {
                JOptionPane.showMessageDialog(gestioValoracions,"La valoració no existeix");
            }

        }
    }
}
