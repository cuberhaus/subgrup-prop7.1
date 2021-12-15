package presentacio.controladors;

import presentacio.vistes.VistaMenuUsuaris;

import javax.swing.*;

/**
 * Classe que representa el controlador que gestiona els usuaris
 * @author pol.casacuberta
 */

public class ControladorMenuUsuaris {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuUsuaris instanciaUnica;
    private static VistaMenuUsuaris vistaMenuUsuaris;

    private ControladorMenuUsuaris() {
    }

    public static ControladorMenuUsuaris obtenirInstancia(){
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorMenuUsuaris();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuUsuaris = VistaMenuUsuaris.obtenirInstancia();
        }
        return instanciaUnica;
    }

    public boolean idEsValid(String id) {
        if (id == null || id.equals("")) {
            System.out.println("Id text is empty");
            JOptionPane.showMessageDialog(vistaMenuUsuaris,"Id text està buit");
            return false;
        }
        else if (!id.matches("-?\\d+")){
            JOptionPane.showMessageDialog(vistaMenuUsuaris,"L'id no és un numero natural");
            return false;
        }
        return true;
    }

    public boolean iniciarSessio(String id, String contrasenya) {
        boolean sessioIniciada = controladorPresentacio.sessioIniciada();
        if(idEsValid(id)) {
            if (sessioIniciada) {
                if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                    controladorPresentacio.iniciarSessio(Integer.parseInt(id), contrasenya);
                    return true;
                }
                else {
                    JOptionPane.showMessageDialog(vistaMenuUsuaris,"L'usuari no existeix");
                }
            }
            else {
                System.out.println("Has de tancar la sessió abans d'obrir-ne un altre");
                JOptionPane.showMessageDialog(vistaMenuUsuaris,"Has de tancar la sessió abans d'obrir-ne un altre");
            }
        }
        return false;
    }

    public void afegirUsuari(String nom, String contrasenya) {
        controladorPresentacio.afegirUsuari(nom,contrasenya);
    }

    public void esborrarUsuari(String id) {
        if(idEsValid(id)) {
            if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                controladorPresentacio.esborrarUsuari(Integer.parseInt(id));
            }
            else {
                JOptionPane.showMessageDialog(vistaMenuUsuaris,"L'usuari no existeix");
            }
        }
    }

    public void tancarSessio() {
        controladorPresentacio.tancarSessio();
    }

    public void exportarConjuntDadesUsuari(String absolutePath) {
        controladorPresentacio.exportarConjuntDadesUsuari(absolutePath);
    }

    public void esborraConjuntUsuaris() {
        controladorPresentacio.esborrarConjuntUsuaris();
    }
}
