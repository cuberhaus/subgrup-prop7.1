package presentacio.controladors;

import presentacio.vistes.VistaMenuUsuaris;

import javax.swing.*;

/**
 * Classe que representa el controlador que gestiona els usuaris
 *
 * @author pol.casacuberta
 */

public class ControladorMenuUsuaris {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuUsuaris instancia;
    private static VistaMenuUsuaris vistaMenuUsuaris;

    private ControladorMenuUsuaris() {
    }

    public static ControladorMenuUsuaris obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorMenuUsuaris();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuUsuaris = VistaMenuUsuaris.obtenirInstancia();
        }
        return instancia;
    }

    public boolean idEsValid(String id) {
        if (id == null || id.equals("")) {
            System.out.println("Id text is empty");
            JOptionPane.showMessageDialog(vistaMenuUsuaris, "Id text està buit");
            return false;
        } else if (!id.matches("-?\\d+")) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'id no és un numero natural");
            return false;
        }
        return true;
    }

    public boolean iniciarSessio(String id, String contrasenya) throws Exception {
        boolean sessioIniciada = controladorPresentacio.esSessioIniciada();
        if (idEsValid(id)) {
            if (!sessioIniciada) {
                if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                    try{
                        controladorPresentacio.iniciarSessio(Integer.parseInt(id), contrasenya);
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(vistaMenuUsuaris,e.getMessage());
                    }
                    return true;
                } else {
                    JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'usuari no existeix");
                }
            } else {
                System.out.println("Has de tancar la sessió abans d'obrir-ne un altre");
                JOptionPane.showMessageDialog(vistaMenuUsuaris, "Has de tancar la sessió abans d'obrir-ne un altre");
                return true;
            }
        }
        return false;
    }

    public void afegirUsuari(String nom, String contrasenya) throws Exception {
        JOptionPane.showMessageDialog(vistaMenuUsuaris,"S'ha creat l'usuari correctament");
        controladorPresentacio.afegirUsuari(nom, contrasenya);
    }

    public void esborrarUsuari(String id) throws Exception {
        if (idEsValid(id)) {
            if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                controladorPresentacio.esborrarUsuari(Integer.parseInt(id));
                JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'usuari s'ha esborrat correctament");
            } else {
                JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'usuari no existeix");
            }
        }
    }

    public void tancarSessio() throws Exception{
        try {
            controladorPresentacio.tancarSessio();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris,"La sessió ja és tancada");
        }
    }

    public void exportarConjuntUsuaris(String absolutePath) {
        controladorPresentacio.exportarConjuntUsuaris(absolutePath);
    }

    public void esborrarConjuntUsuaris() {
        controladorPresentacio.esborrarConjuntUsuaris();
    }
}
