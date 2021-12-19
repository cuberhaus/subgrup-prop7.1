package presentacio.controladors;

import presentacio.vistes.VistaMenuUsuaris;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

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

    /**
     *
     * @param id id amb el que volem iniciar sessió
     * @param contrasenya
     * @return True si la sessió hem pogut iniciar sessió
     * @throws Exception
     */
    public boolean iniciarSessio(String id, String contrasenya) throws Exception {
        try {
            boolean sessioIniciada = controladorPresentacio.esSessioIniciada();
            if (idEsValid(id)) {
                if (!sessioIniciada) {
                    if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                        controladorPresentacio.iniciarSessio(Integer.parseInt(id), contrasenya);
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'usuari no existeix");
                    }
                } else {
                    JOptionPane.showMessageDialog(vistaMenuUsuaris, "Has de tancar la sessió abans d'obrir-ne un altre");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e);
        }
        return false;
    }

    public int afegirUsuari(String nom, String contrasenya) throws Exception {
        int id = controladorPresentacio.afegirUsuari(nom, contrasenya);
        JOptionPane.showMessageDialog(vistaMenuUsuaris,"S'ha creat correctament l'usuari: " + id);
        return id;
    }

    public void esborrarUsuari(String id) throws Exception {
        try {
            if (idEsValid(id)) {
                if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                    controladorPresentacio.esborrarUsuari(Integer.parseInt(id));
                    JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'usuari s'ha esborrat correctament");
                } else {
                    JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'usuari no existeix");
                }
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
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

    public void exportarConjuntUsuaris(String absolutePath) throws IOException {
        controladorPresentacio.exportarConjuntUsuaris(absolutePath);
    }

    public void esborrarConjuntUsuaris() {
        controladorPresentacio.esborrarConjuntUsuaris();
    }
    public ArrayList<ArrayList<String>> obteUsuaris() {
        return controladorPresentacio.obteUsuaris();
    }

    public void importarUsuaris(String absolutePath) throws Exception{
        controladorPresentacio.importarUsuaris(absolutePath);
    }
}
