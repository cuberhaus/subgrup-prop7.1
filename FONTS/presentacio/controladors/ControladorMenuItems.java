package presentacio.controladors;

import excepcions.NomInternIncorrecteException;
import presentacio.vistes.VistaDialegCrearItem;
import presentacio.vistes.VistaDialegEditarItem;
import presentacio.vistes.VistaMenuItems;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.JFileChooser.APPROVE_OPTION;

public class ControladorMenuItems {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuItems instancia;
    private static VistaMenuItems vistaMenuItems;

    private ControladorMenuItems () {
    }

    public static ControladorMenuItems obtenirInstancia() throws IOException, NomInternIncorrecteException {
        if (instancia == null) {
            instancia = new ControladorMenuItems();
            controladorPresentacio = ControladorPresentacio.obtenirInstancia();
        }
        return instancia;
    }

    public ArrayList<ArrayList<String>> obtenirItems() {
        if (!existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirItems();
    }

    public ArrayList<String> obtenirNomsAtributsTipusItemSeleccionat() {
        if (!existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirNomAtributsTipusItemSeleccionat();
    }

    public boolean existeixTipusItemSeleccionat() {
        return controladorPresentacio.existeixTipusItemSeleccionat();
    }

    public boolean afegirItem(Map<String, String> valorsAtributs) throws Exception {
        return controladorPresentacio.afegirItem(valorsAtributs);
    }

    public void esborrarItem() {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            String id = JOptionPane.showInputDialog(instancia,
                    "Introdueix l'identificador de l'ítem que vols esborrar:");
            if (!controladorPresentacio.esborrarItem(id)) {
                JOptionPane.showMessageDialog(vistaMenuItems, "L'identificador introduït no és vàlid.");
            } else {
                JOptionPane.showMessageDialog(vistaMenuItems, "L'ítem s'ha esborrat amb èxit.");
            }
        }
    }

    public Map<String, String> obtenirItem(String id) {
        return controladorPresentacio.obtenirItem(id);
    }

    public void editarItem() throws IOException, NomInternIncorrecteException {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            String id = JOptionPane.showInputDialog(instancia,
                    "Introdueix l'identificador de l'ítem que vols editar:");
            Map<String, String> atributs = controladorPresentacio.obtenirItem(id);
            if (atributs == null) {
                JOptionPane.showMessageDialog(vistaMenuItems, "L'identificador introduït no és vàlid.");
            } else {
                VistaDialegEditarItem vistaDialegEditarItem = new VistaDialegEditarItem(id, atributs);
                vistaDialegEditarItem.setVisible(true);
            }
        }
    }

    public boolean editarItem(String id, Map<String, String> valorsAtributs) {
        return controladorPresentacio.editarItem(id, valorsAtributs);
    }

    public void carregarConjuntItems() throws Exception {
        JDialog dialegFitxer = new JDialog();
        JFileChooser selectorFitxer = new JFileChooser();
        int estatSelectorFitxer = selectorFitxer.showOpenDialog(dialegFitxer);
        if (estatSelectorFitxer == APPROVE_OPTION) {
            File rutaFitxer = selectorFitxer.getSelectedFile();
            controladorPresentacio.carregarConjuntItems(rutaFitxer.getAbsolutePath());
            // TODO: afegir missatge d'error
        }
    }

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

    public boolean sessioIniciada() {
        return controladorPresentacio.esSessioIniciada();
    }

    public void crearNouItem() throws IOException, NomInternIncorrecteException {
        if (!controladorPresentacio.existeixTipusItemSeleccionat()) {
            JOptionPane.showMessageDialog(vistaMenuItems, "No hi ha cap tipus d'ítem seleccionat.");
        } else {
            VistaDialegCrearItem vistaDialegCrearItem = new VistaDialegCrearItem();
            vistaDialegCrearItem.setVisible(true);
        }
    }
}
