package presentacio.controladors;

import excepcions.NomInternIncorrecteException;
import excepcions.SessioNoIniciadaException;
import presentacio.EncarregatActualitzarVistes;
import presentacio.vistes.VistaMenuUsuaris;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que representa el controlador que gestiona els usuaris
 *
 * @author pol.casacuberta
 */

public class ControladorMenuUsuaris implements EncarregatActualitzarVistes.Observador {
    /**
     * Conté el controlador Presentació
     */
    private static ControladorPresentacio controladorPresentacio;

    /**
     * Conté l'única instància de ControladorMenuUsuaris
     */
    private static ControladorMenuUsuaris instancia;

    /**
     * Conté la vista del menu d'usuaris
     */
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
     * @throws IOException no existeix el fitxer o no es pot obrir
     * @throws NomInternIncorrecteException el nom del fitxer intern no existeix
     */
    public static ControladorMenuUsuaris obtenirInstancia() throws IOException, NomInternIncorrecteException {
        try {
            if (instancia == null) {
                instancia = new ControladorMenuUsuaris();
                controladorPresentacio = ControladorPresentacio.obtenirInstancia();
                vistaMenuUsuaris = VistaMenuUsuaris.obtenirInstancia();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
        }
        return instancia;
    }

    /**
     * Comprova que un id és un valor natural, si no ho és llença una excepció 0 no inclòs
     *
     * @param id identificador
     * @throws Exception l'id no és vàlid
     */
    public void idEsValid(String id) throws Exception {
        if (id == null || id.equals("")) {
            throw new Exception("Id text està buit");
        } else if (!id.matches("^[0-9]+$")) {
            throw new Exception("L'id no és un numero natural");
        }
    }

    /**
     * S'intenta iniciar la sessió amb les dades donades
     *
     * @param id          id amb el que volem iniciar sessió
     * @param contrasenya contrasenya amb què intentem iniciar la sessió
     * @return True si la sessió hem pogut iniciar sessió
     */
    public boolean iniciarSessio(String id, String contrasenya) {
        try {
            boolean sessioIniciada = controladorPresentacio.sessioIniciada();
            idEsValid(id);
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
        }
        return false;
    }

    /**
     * Afegir un Usuari al conjunt
     *
     * @param nom         de l'usuari
     * @param contrasenya de l'usuari
     * @return retorna l'id de l'usuari
     */
    public int afegirUsuari(String nom, String contrasenya) {
        try {
            int id = controladorPresentacio.afegirUsuari(nom, contrasenya);
            JOptionPane.showMessageDialog(vistaMenuUsuaris, "S'ha creat correctament l'usuari: " + id);
            return id;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
        }
        return -1;
    }

    /**
     * Esborra un usuari del conjunt si és actiu i l'id és correcte
     *
     * @param id id de l'usuari
     */
    public void esborrarUsuari(String id) {
        try {
            idEsValid(id);
            if (controladorPresentacio.existeixUsuari(Integer.parseInt(id))) {
                controladorPresentacio.esborrarUsuari(Integer.parseInt(id));
                JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'usuari s'ha esborrat correctament");
            } else {
                JOptionPane.showMessageDialog(vistaMenuUsuaris, "L'usuari no existeix");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
        }
    }

    /**
     * Tanca la sessió
     */
    public void tancarSessio() {
        try {
            controladorPresentacio.tancarSessio();
        } catch (SessioNoIniciadaException e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, "La sessió ja és tancada");
        }
    }

    /**
     * Exporta un conjunt d'usuaris en l'arxiu descrit per absolutePath
     *
     * @param absolutePath path on es crea l'arxiu
     * @throws IOException No s'ha pogut exportar l'arxiu
     */
    public void exportarConjuntUsuaris(String absolutePath) throws IOException {
        try {
            controladorPresentacio.exportarConjuntUsuaris(absolutePath);
        } catch (Exception e){
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
        }
    }

    /**
     * Esborra el conjunt d'usuaris
     */
    public void esborrarConjuntUsuaris() {
        controladorPresentacio.esborrarConjuntUsuaris();
    }

    /**
     * Retorna tots els usuaris del conjunt d'usuaris
     *
     * @return Conjunt d'usuaris
     */
    public ArrayList<ArrayList<String>> obteUsuaris() {
        return controladorPresentacio.obtenirUsuaris();
    }

    /**
     * Importa un conjunt d'usuaris des d'un arxiu extern
     *
     * @param absolutePath path de l'arxiu des d'on s'obtenen les dades
     * @throws Exception No s'ha pogut importar l'arxiu
     */
    public void importarUsuaris(String absolutePath) throws Exception {
        try {
            ArrayList<String> usuarisNoInicialitzats = controladorPresentacio.importarUsuaris(absolutePath);
            if (usuarisNoInicialitzats != null && usuarisNoInicialitzats.size() != 0) {
                JOptionPane.showMessageDialog(vistaMenuUsuaris, "Aquests usuaris no s'han pogut inicialitzar" + usuarisNoInicialitzats);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
        }
    }

    /**
     * Canvia la contrasenya de l'usuari amb l'id donat
     *
     * @param id              identificador de l'usuari
     * @param novaContrasenya nova contrasenya de l'usuari
     */
    public void canviaContrasenyaUsuari(String id, char[] novaContrasenya) {
        try {
            idEsValid(id);
            controladorPresentacio.canviarContrasenyaUsuari(id, novaContrasenya);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
        }
    }

    /**
     * Canvia el nom de l'usuari amb l'id donat
     *
     * @param id     identificador de l'usuari
     * @param nouNom nou nom de l'usuari
     */
    public void canviaNomUsuari(String id, String nouNom) {
        try {
            idEsValid(id);
            controladorPresentacio.canviarNomUsuari(id, nouNom);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuUsuaris, e.getMessage());
        }
    }

    @Override
    public void actualitzar() {
        VistaMenuUsuaris.actualitzarTaula();
    }
}
