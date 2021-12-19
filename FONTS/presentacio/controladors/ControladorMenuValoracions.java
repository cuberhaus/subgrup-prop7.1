package presentacio.controladors;

import presentacio.vistes.VistaMenuValoracions;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que representa el controlador que gestiona les valoracions
 *
 * @author pol.casacuberta
 */

public class ControladorMenuValoracions {
    /**
     * Conté el controlador Presentació
     */
    private static ControladorPresentacio controladorPresentacio;
    /**
     * Conté l'única instància del controlador del menu de valoracions
     */
    private static ControladorMenuValoracions instancia;
    /**
     * Conté la vista del menú de valoracions
     */
    private static VistaMenuValoracions vistaMenuValoracions;

    /**
     * Constructora per defecte de ControladorMenuValoracions
     */
    private ControladorMenuValoracions() {
    }

    /**
     * Constructora de ControladorMenuValoracions
     * Crea una instància única de ControladorMenuValoracions
     * @return <code> ControladorMenuUsuaris </code>
     * @throws IOException No s'ha pogut obtenir la instancia del controlador
     */
    public static ControladorMenuValoracions obtenirInstancia() throws IOException {
        if (instancia == null) {
            instancia = new ControladorMenuValoracions();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuValoracions = VistaMenuValoracions.obtenirInstancia();
        }
        return instancia;
    }

    public boolean idUsuariEsValid(String id) {
        if (id == null || id.equals("")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, "L'id d'Usuari està buit");
            return false;
        } else if (!id.matches("-?\\d+")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, "L'id d'Usuari no és un numero natural");
            return false;
        }
        return true;
    }

    public boolean idItemEsValid(String id) {
        if (id == null || id.equals("")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, "L'id d'item està buit");
            return false;
        } else if (!id.matches("-?\\d+")) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, "L'id d'item no és un numero natural");
            return false;
        }
        return true;
    }

    /**
     * Afegeix una valoració amb l'identificador d'usuari i d'ítem i el valor donat
     * @param usuariId identificador de l'usuari
     * @param itemId identificador de l'ítem
     * @param valor valor de la valoració
     * @throws Exception no s'ha pogut afegir la valoració
     */
    public void afegirValoracio(String usuariId, String itemId, String valor) throws Exception {
        try {
            if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
                if (!controladorPresentacio.existeixValoracio(usuariId, itemId)) {
                    controladorPresentacio.afegirValoracio(usuariId, itemId, valor);
                } else {
                    JOptionPane.showMessageDialog(vistaMenuValoracions, "La valoració ja existeix");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
    }

    /**
     * Esborra una valoració del conjunt amb els identificadors donats
     * @param usuariId identificador de l'usuari
     * @param itemId identificador d'ítem
     * @throws Exception no s'ha pogut esborrar la valoració
     */
    public void esborrarValoracio(String usuariId, String itemId) throws Exception {
        try {
            if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
                controladorPresentacio.esborrarValoracio(usuariId, itemId);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
    }

    /**
     * Edita el valor d'una valoració amb els identificadors donats
     * @param usuariId identificador de l'usuari
     * @param itemId identificador de l'ítem
     * @param valor nou valor de la valoració
     * @throws Exception no s'ha pogut editar la valoració
     */
    public void editarValoracio(String usuariId, String itemId, String valor) throws Exception {
        try {
            if (idUsuariEsValid(usuariId) && idItemEsValid(itemId)) {
                if (controladorPresentacio.existeixValoracio(usuariId, itemId)) {
                    controladorPresentacio.editarValoracio(usuariId, itemId, valor);
                } else {
                    JOptionPane.showMessageDialog(vistaMenuValoracions, "La valoració no existeix");
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
    }

    /**
     * Carrega un conjunt de valoracions des d'un arxiu localitzat en rutaAbsoluta
     * @param rutaAbsoluta path de l'arxiu
     * @throws Exception no s'han pogut carregar les valoracions
     */
    public void carregarConjuntValoracions(String rutaAbsoluta) throws Exception {
        //TODO: comprovar que l'arxiu donat té el format correcte
        controladorPresentacio.carregarConjuntValoracions(rutaAbsoluta);
    }

    /**
     * Esborra totes les valoracions del tipus d'ítem seleccionat
     */
    public void esborrarTotesLesValoracions() {
        try {
            controladorPresentacio.esborrarTotesLesValoracions();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
    }

    /**
     * Consultora de si el tipus d'ítem seleccionat existeix o no
     * @return retorna true si l'ítem existeix, altrament, false
     */
    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    /**
     * Obté les valoracions del tipus d'ítem seleccionat
     * @return valoracions del tipus d'ítem seleccionat
     */
    public ArrayList<ArrayList<String>> obtenirValoracions() {
        return controladorPresentacio.obtenirValoracions();
    }

    /**
     * Consultora de si existeix la valoració amb els identificadors donats
     * @param idUsuari identificador d'usuari
     * @param idItem identificador d'ítem
     * @return retorna true si la valoració existeix, altrament, retorna false
     * @throws Exception No s'ha pogut consultar el valor
     */
    public boolean existeixValoracio(String idUsuari, String idItem) throws Exception {
        try {
            return controladorPresentacio.existeixValoracio(idUsuari, idItem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaMenuValoracions, e.getMessage());
        }
        return false;
    }

}
