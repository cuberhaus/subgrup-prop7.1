package presentacio.controladors;

import excepcions.*;
import presentacio.EncarregatActualitzarTaules;
import presentacio.vistes.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.JFileChooser.APPROVE_OPTION;

public class ControladorMenuItems implements EncarregatActualitzarTaules.Observador {

    private static ControladorPresentacio controladorPresentacio;
    private static ControladorMenuItems instancia;
    private static VistaMenuItems vistaMenuItems;

    private ControladorMenuItems () {
    }

    public static ControladorMenuItems obtenirInstancia() throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
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

    public String afegirItem(Map<String, String> valorsAtributs) throws FormatIncorrecteException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return controladorPresentacio.afegirItem(valorsAtributs);
    }

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

    public Map<String, String> obtenirItem(String id) throws NoExisteixElementException {
        return controladorPresentacio.obtenirItem(id);
    }

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

    public void editarItem(String id, Map<String, String> valorsAtributs) throws NoExisteixElementException, FormatIncorrecteException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        controladorPresentacio.editarItem(id, valorsAtributs);
    }

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

    public String obtenirNomTipusItemSeleccionat() {
        return controladorPresentacio.obtenirNomTipusItemSeleccionat();
    }

    public ArrayList<String> obtenirIdsItems() {
        if (!existeixTipusItemSeleccionat()) {
            return new ArrayList<>();
        }
        return controladorPresentacio.obtenirIdsItems();
    }

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
}
