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

    /**
     * Constructora per defecte de ControladorMenuUsuaris
     */
    private ControladorMenuUsuaris() {
    }

    /**
     * Constructora de ControladorMenuUsuaris
     * Crea una instància única de ControladorMenuUsuaris
     * @return <code> ControladorMenuUsuaris </code>
     */
    public static ControladorMenuUsuaris obtenirInstancia() throws IOException {
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
     * S'intenta iniciar la sessió amb les dades donades
     * @param id id amb el que volem iniciar sessió
     * @param contrasenya contrasenya amb què intentem iniciar la sessió
     * @return True si la sessió hem pogut iniciar sessió
     * @throws Exception No s'ha pogut iniciar sessió
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

    /**
     * Afegir un Usuari al conjunt
     * @param nom de l'usuari
     * @param contrasenya de l'usuari
     * @return retorna l'id de l'usuari
     * @throws Exception no s'ha pogut crear l'usuari
     */
    public int afegirUsuari(String nom, String contrasenya) throws Exception {
        int id = controladorPresentacio.afegirUsuari(nom, contrasenya);
        JOptionPane.showMessageDialog(vistaMenuUsuaris,"S'ha creat correctament l'usuari: " + id);
        return id;
    }

    /**
     * Esborra un usuari del conjunt si és actiu i l'id és correcte
     * @param id id de l'usuari
     * @throws Exception No s'ha pogut crear l'usuari
     */
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

    /**
     * Tanca la sessió
     * @throws Exception No s'ha pogut tancar la sessió
     */
    public void tancarSessio() throws Exception{
        try {
            controladorPresentacio.tancarSessio();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris,"La sessió ja és tancada");
        }
    }

    /**
     * Exporta un conjunt d'usuaris en l'arxiu descrit per absolutePath
     * @param absolutePath path on es crea l'arxiu
     * @throws IOException No s'ha pogut exportar l'arxiu
     */
    public void exportarConjuntUsuaris(String absolutePath) throws IOException {
        controladorPresentacio.exportarConjuntUsuaris(absolutePath);
    }

    /**
     * Esborra el conjunt d'usuaris
     */
    public void esborrarConjuntUsuaris() {
        controladorPresentacio.esborrarConjuntUsuaris();
    }

    /**
     * Retorna tots els usuaris del conjunt d'usuaris
     * @return Conjunt d'usuaris
     */
    public ArrayList<ArrayList<String>> obteUsuaris() {
        return controladorPresentacio.obteUsuaris();
    }

    public void importarUsuaris(String absolutePath) throws Exception{
       ArrayList<String> usuarisNoInicialitzats = controladorPresentacio.importarUsuaris(absolutePath);
       if (usuarisNoInicialitzats != null) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris,"Aquests usuaris no s'han pogut inicialitzar"+usuarisNoInicialitzats);
       }
    }

    public void canviaContrasenyaUsuari(String id, String novaContrasenya) throws Exception {
        //TODO: comprovar id es valid
        controladorPresentacio.canviaContrasenyaUsuari(id,novaContrasenya);
    }
    public void canviaNomUsuari(String id, String nouNom) throws Exception {
        //TODO: comprovar id es valid
        controladorPresentacio.canviaNomUsuari(id,nouNom);
    }
}
