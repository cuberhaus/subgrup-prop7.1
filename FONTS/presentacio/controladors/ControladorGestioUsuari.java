package presentacio.controladors;

import presentacio.vistes.VistaGestioUsuari;

import javax.swing.*;

/**
 * Classe que representa el controlador que gestiona els usuaris
 * @author pol.casacuberta
 */

public class ControladorGestioUsuari {
    private static ControladorPresentacio controladorPresentacio;
    private static ControladorGestioUsuari instanciaUnica;
    private static VistaGestioUsuari vistaGestioUsuari;

    private ControladorGestioUsuari() {
    }

    public static ControladorGestioUsuari obtenirInstancia(){
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorGestioUsuari();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaGestioUsuari = VistaGestioUsuari.obtenirInstancia();
        }
        return instanciaUnica;
    }

    public boolean idIsValid(String id) {
        if (id == null || id.equals("")) {
            System.out.println("Id text is empty");
            JOptionPane.showMessageDialog(vistaGestioUsuari,"Id text està buit");
            return false;
        }
        else if (!id.matches("-?\\d+")){
            JOptionPane.showMessageDialog(vistaGestioUsuari,"L'id no és un numero natural");
            return false;
        }
        return true;
    }

    public void iniciarSessio(String id, String contrasenya) {
        int idSessio = controladorPresentacio.obtenirSessio();
        if(idIsValid(id)) {
            if (idSessio == 0) {
                if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                    controladorPresentacio.iniciarSessio(Integer.parseInt(id), contrasenya);
                }
                else {
                    JOptionPane.showMessageDialog(vistaGestioUsuari,"L'usuari no existeix");
                }
            }
            else {
                System.out.println("Has de tancar la sessió abans d'obrir-ne un altre");
                JOptionPane.showMessageDialog(vistaGestioUsuari,"Has de tancar la sessió abans d'obrir-ne un altre");
            }
        }
    }

    public void afegirUsuari(String id, String contrasenya, String nom) {
        if (idIsValid(id)) {
            if(!controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                controladorPresentacio.afegirUsuari(id,contrasenya,nom);
            }
            else {
                JOptionPane.showMessageDialog(vistaGestioUsuari,"L'usuari ja existeix");
            }
        }
    }

    public void esborrarUsuari(String id) {
        if(idIsValid(id)) {
            if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                controladorPresentacio.esborrarUsuari(id);
            }
            else {
                JOptionPane.showMessageDialog(vistaGestioUsuari,"L'usuari no existeix");
            }
        }
    }

    public void tancarSessio() {
        controladorPresentacio.tancarSessio();
    }
}
