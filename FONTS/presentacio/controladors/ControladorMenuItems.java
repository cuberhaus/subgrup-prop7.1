package presentacio.controladors;

import excepcions.*;
import presentacio.EncarregatActualitzarVistes;
import presentacio.vistes.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * Classe que representa el controlador del Menu d'items
 * @author maria.prat
 */
public class ControladorMenuItems implements EncarregatActualitzarVistes.Observador {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuItems instancia;
    private static VistaMenuItems vistaMenuItems;

    /**
     * Constructor per defecte del controlador
     */
    private ControladorMenuItems () {
    }

    /**
     * @return l'única instància del controlador, seguint el patró Singleton
     * @throws Exception si hi ha algun problema a l'hora de carregar les dades del programa
     */
    public static ControladorMenuItems obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ControladorMenuItems();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
            vistaMenuItems = VistaMenuItems.obtenirInstancia();
        }
        return instancia;
    }

    /**
     * @return Matriu que conté els items carregats al programa. Cada fila de la matriu representa un item i cada
     * columna es correspon amb un atribut.
     */
    public ArrayList<ArrayList<String>> obtenirItems() {
        if (!existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirItems();
    }

    /**
     * @return Llista que conté els noms dels atributs del tipus d'item seleccionat. Si no hi ha cap tipus d'item
     * seleccionat, retorna una llista buida.
     */
    public ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        if (!existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirNomAtributsTipusItemSeleccionat();
    }

    /**
     * @return cert si hi ha un tipus d'item seleccionat. Altrament, retorna fals.
     */
    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    /**
     * Comprova si hi ha un tipus d'item seleccionat i afegeix un nou item al conjunt d'items del programa.
     * Emet un missatge si hi ha algun error.
     * @param valorsAtributs conté un mapa que relaciona el nom de cada atribut de l'item amb el seu valor en forma de
     *                       String
     */
    public void afegirItem(Component component, Map<String, String> valorsAtributs) {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(component, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            try {
                String nouId = controladorPresentacio.afegirItem(valorsAtributs);
                JOptionPane.showMessageDialog(component, "Item creat amb èxit amb identificador " + nouId);
            } catch (FormatIncorrecteException ex) {
                JOptionPane.showMessageDialog(component, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(component, "No s'ha pogut crear un ítem amb els valors donats.");
            }
        }
    }

    /**
     * Comprova si hi ha un tipus d'item seleccionat, demana un identificador a l'usuari i esborra l'item amb
     * l'identificador donat. Emet un missatge si hi ha algun error.
     */
    public void esborrarItem() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            try {
                String id = JOptionPane.showInputDialog("Introdueix l'identificador de l'ítem que vols esborrar:");
                if (!controladorPresentacio.esborrarItem(id)) {
                    JOptionPane.showMessageDialog(vistaMenuItems, "L'identificador introduït no és vàlid.");
                } else {
                    JOptionPane.showMessageDialog(vistaMenuItems, "L'ítem s'ha esborrat amb èxit.");
                }
            } catch (NoExisteixElementException ex) {
                JOptionPane.showMessageDialog(vistaMenuItems, "No s'ha pogut esborrar l'ítem.");
            }
        }
    }

    /**
     * Comprova si hi ha un tipus d'item seleccionat, demana un identificador a l'usuari i crea un diàleg per editar
     * l'item amb l'identificador donat. Emet un missatge si hi ha algun error.
     */
    public void editarItem() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            String id = JOptionPane.showInputDialog("Introdueix l'identificador de l'ítem que vols editar:");
            if (id != null) {
                if (!id.isEmpty()) {
                    VistaDialegEditarItem vistaDialegEditarItem;
                    try {
                        vistaDialegEditarItem = new VistaDialegEditarItem(id, controladorPresentacio.obtenirItem(id));
                        vistaDialegEditarItem.setVisible(true);
                    } catch (NoExisteixElementException ex) {
                        JOptionPane.showMessageDialog(vistaMenuItems, "No existeix cap ítem amb identificador " + id);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(vistaMenuItems, "No s'ha pogut editar l'ítem.");
                    }
                } else {
                    JOptionPane.showMessageDialog(vistaMenuItems, "Un identificador no pot ser buit.");
                }
            }
        }
    }

    /**
     * Edita l'item amb l'identificador donat, substituint els atributs antics pels donats. Emet missatges al component
     * donat si hi ha algun error.
     * @return cert si ha pogut editar l'item i fals si hi ha hagut algun error
     * @param component Component on es mostraran els missatges
     * @param id String que conté l'identificador de l'item
     * @param valorsAtributs mapa que relaciona el nom de l'atribut amb el seu nou valor
     */
    public boolean editarItem(Component component, String id, Map<String, String> valorsAtributs) {
        try {
            controladorPresentacio.editarItem(id, valorsAtributs);
            JOptionPane.showMessageDialog(component, "S'ha editat l'ítem amb èxit");
            return true;
        } catch (NoExisteixElementException ex) {
            JOptionPane.showMessageDialog(component, "No existeix cap ítem amb aquest identificador.");
        } catch (FormatIncorrecteException ex) {
            JOptionPane.showMessageDialog(component, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(component, "No s'ha pogut editar l'ítem.");
        }
        return false;
    }

    /**
     * Demana un arxiu a l'usuari i carrega un conjunt d'items de l'arxiu. El tipus d'item es pot deduir del conjunt
     * o be utilitzar el tipus d'item seleccionat. En el cas de deduir-se, el nom del nou tipus d'item sera el donat.
     * Emet un missatge si hi ha algun error.
     * @param deduirTipusItem indica si s'ha de deduir el tipus d'item del conjunt o si es vol utilitzar el tipus d'item
     *                        seleccionat
     * @param nomTipusItem nom del nou tipus d'item si es vol deduir del conjunt
     */
    public void carregarConjuntItems(boolean deduirTipusItem, String nomTipusItem) {
        JDialog dialegFitxer = new JDialog();
        JFileChooser selectorFitxer = new JFileChooser();
        int estatSelectorFitxer = selectorFitxer.showOpenDialog(dialegFitxer);
        if (estatSelectorFitxer == APPROVE_OPTION) {
            File rutaFitxer = selectorFitxer.getSelectedFile();
            try {
                controladorPresentacio.carregarConjuntItems(deduirTipusItem, nomTipusItem, rutaFitxer.getAbsolutePath());
            } catch (JaExisteixElementException e1) {
                JOptionPane.showMessageDialog(vistaMenuItems,
                        e1.getMessage());
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(vistaMenuItems,
                        "No s'han pogut afegir ítems d'aquest conjunt. Error:\n" + e2.getMessage());
            }
        }
    }

    /**
     * Comprova que hi ha un tipus d'item seleccionat i esborra tots els items d'aquest tipus d'item.
     */
    public void esborrarTotsElsItems() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            int resposta = JOptionPane.showConfirmDialog(vistaMenuItems,
                    "Segur que vols esborrar tots els ítems?", "Selecciona una opció",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                controladorPresentacio.esborrarTotsElsItems();
                JOptionPane.showMessageDialog(vistaMenuItems, "S'han esborrat els ítems amb èxit.");
            }
        }
    }

    /**
     * @return cert si l'usuari ha iniciat sessio i, altrament, retorna fals
     */
    public boolean sessioIniciada() {
        return controladorPresentacio.sessioIniciada();
    }

    /**
     * Comprova que hi ha un tipu d'item seleccionat i crea un dialeg per crear un nou item d'aquest tipus. Emet un
     * missatge si hi ha algun error.
     *
     */
    public void crearNouItem() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            VistaDialegCrearItem vistaDialegCrearItem;
            try {
                vistaDialegCrearItem = new VistaDialegCrearItem();
                vistaDialegCrearItem.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vistaMenuItems, "No s'ha pogut crear l'ítem.");
            }
        }
    }

    /**
     * @return el nom del tipus d'item seleccionat, retorna null si no n'hi ha cap de seleccionat
     */
    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    /**
     * @return una llista amb els identificadors dels items carregats o una llista buida si no hi ha cap tipus d'item
     * seleccionat
     */
    public ArrayList<String> obtenirIdsItems() {
        if (!existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirIdsItems();
    }

    /**
     * Comprova si hi ha un tipus d'item seleccionat, demana un identificador a l'usuari i crea un dialeg que mostra
     * l'item amb l'identificador donat. Emet un missatge si hi ha algun error.
     */
    public void mostarItem() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            String id = JOptionPane.showInputDialog("Introdueix l'identificador de l'ítem que vols veure:");
            if (id != null) {
                if (!id.isEmpty()) {
                    VistaDialegMostrarItem vistaDialegMostrarItem;
                    try {
                        vistaDialegMostrarItem = new VistaDialegMostrarItem(id, controladorPresentacio.obtenirItem(id));
                        vistaDialegMostrarItem.setVisible(true);
                    } catch (NoExisteixElementException ex) {
                        JOptionPane.showMessageDialog(vistaMenuItems, "No existeix cap ítem amb identificador " + id);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(vistaMenuItems, "No s'ha pogut mostrar l'ítem.");
                    }
                } else {
                    JOptionPane.showMessageDialog(vistaMenuItems, "Un identificador no pot ser buit.");
                }
            }
        }
    }

    @Override
    public void actualitzar() {
        VistaMenuItems.actualitzarTaula();
    }

    /**
     * @param rutaAbsoluta exportar un conjunt d'items a la ruta donada
     */
    public void exportarItems(String rutaAbsoluta) {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            try {
                controladorPresentacio.exportarItems(rutaAbsoluta);
                JOptionPane.showMessageDialog(vistaMenuItems, "S'ha exportat el conjunt d'ítems amb èxit.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(vistaMenuItems, "No s'ha pogut exportar el conjunt d'ítems.");
            }
        }
    }

    /**
     * @param nomAtribut nom de l'atribut
     * @return nom en format intern del valor atribut de l'atribut amb el nom donat del tipus d'ítem seleccionat
     */
    public String obtenirValorAtributTipusItemSeleccionat(String nomAtribut) {
        return controladorPresentacio.obtenirNomValorAtributExtern(
                controladorPresentacio.obtenirValorAtributTipusItemSeleccionat(nomAtribut));
    }
}
