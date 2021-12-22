package presentacio.vistes;

import presentacio.EncarregatActualitzarVistes;
import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * Vista pel menu de tipus d'ítems
 * @author maria.prat
 */
public class VistaMenuTipusItem extends JPanel {
    private static VistaMenuTipusItem instancia;

    private static final String kPrototipNomTipusItem = new String(new char[20]).replace('\0', '*');
    private static final String kMissatgeTipusItemNoSeleccionat = "No s'ha escollit cap tipus d'ítem";

    private static ControladorMenuTipusItem controladorMenuTipusItem;

    private static JPanel panellAfegirTipusItem;
    private static JPanel panellSeleccionarTipusItem;
    private static JPanel panellAdministrarTipusItem;
    private static JPanel panellMostrarTipusItemSeleccionat;
    private static JLabel textTipusItemSeleccionat;
    private static JButton botoVeureTipusItem;
    private static JButton botoEditarTipusItem;
    private static JButton botoDeseleccionarTipusItem;
    private static JButton botoEsborrarTipusItem;
    private static DefaultComboBoxModel<String> tipusItemsComboBoxModel;

    /**
     * Constructor d'una VistaMenuTipusItem
     */
    private VistaMenuTipusItem() {
    }

    public static VistaMenuTipusItem obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new VistaMenuTipusItem();
            controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
            inicialitzarMenuTipusItem();
        }
        return instancia;
    }

    private static void inicialitzarMenuTipusItem(){
        instancia.setLayout(new BoxLayout(instancia, BoxLayout.Y_AXIS));

        JLabel titol = new JLabel("Quin tipus d'ítem vols que et recomani?");
        titol.setFont(new Font("Sans", Font.PLAIN, 20));
        titol.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcio = new JLabel("Afegeix un tipus d'ítem nou o selecciona un tipus d'ítem existent");
        descripcio.setFont(new Font("Sans", Font.PLAIN, 14));
        descripcio.setAlignmentX(Component.CENTER_ALIGNMENT);

        instancia.add(Box.createVerticalGlue());
        instancia.add(titol);
        instancia.add(Box.createVerticalStrut(10));
        instancia.add(descripcio);
        instancia.add(Box.createVerticalGlue());
        inicialitzarPanellAfegirTipusItem();
        instancia.add(panellAfegirTipusItem);
        inicialitzarPanellSeleccionarTipusItem();
        instancia.add(panellSeleccionarTipusItem);
        inicialitzarPanellAdministrarTipusItem();
        instancia.add(panellAdministrarTipusItem);
        inicialitzarPanellMostrarTipusItemSeleccionat();
        instancia.add(panellMostrarTipusItemSeleccionat);
        instancia.add(Box.createVerticalGlue());
    }

    private static void inicialitzarPanellAfegirTipusItem() {
        panellAfegirTipusItem = new JPanel(new FlowLayout());
        JButton botoCrearTipusItem = new JButton("Crea un nou tipus d'ítem");
        botoCrearTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoCrearTipusItem.addActionListener(e -> {
            try {
                VistaDialegCrearTipusItem vistaDialegCrearTipusItem;
                vistaDialegCrearTipusItem = new VistaDialegCrearTipusItem();
                vistaDialegCrearTipusItem.setVisible(true);
                EncarregatActualitzarVistes.notificarObservadors();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(instancia,
                        "No s'ha pogut crear el tipus d'ítem.");
            }
        });
        panellAfegirTipusItem.add(botoCrearTipusItem);
        JButton botoCarregarTipusItem = new JButton("Carrega un nou tipus d'ítem");
        botoCarregarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoCarregarTipusItem.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(instancia,
                    "Introdueix el nom del tipus d'ítem:");
            if (nom != null) {
                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(instancia,
                            "Un tipus d'ítem no pot tenir un nom buit.");
                } else {
                    JDialog dialegFitxer = new JDialog();
                    JFileChooser selectorFitxer = new JFileChooser();
                    int estatSelectorFitxer = selectorFitxer.showOpenDialog(dialegFitxer);
                    if (estatSelectorFitxer == APPROVE_OPTION) {
                        File rutaFitxer = selectorFitxer.getSelectedFile();
                        controladorMenuTipusItem.carregarTipusItem(nom, rutaFitxer.getAbsolutePath());
                    }
                }
            }
        });
        panellAfegirTipusItem.add(botoCarregarTipusItem);
    }

    private static void inicialitzarPanellSeleccionarTipusItem() {
        panellSeleccionarTipusItem = new JPanel(new FlowLayout());

        tipusItemsComboBoxModel = new DefaultComboBoxModel<>();
        tipusItemsComboBoxModel.addAll(controladorMenuTipusItem.obtenirNomsTipusItemsCarregats());

        JComboBox<String> tipusItemsComboBox = new JComboBox<>();
        tipusItemsComboBox.setModel(tipusItemsComboBoxModel);
        tipusItemsComboBox.setPrototypeDisplayValue(kPrototipNomTipusItem);
        tipusItemsComboBox.setSelectedIndex(-1);
        panellSeleccionarTipusItem.add(tipusItemsComboBox);
        JButton selecciona = new JButton("Selecciona");
        selecciona.addActionListener(e -> {
            String nomTipusItem = (String) tipusItemsComboBox.getSelectedItem();
            if (nomTipusItem != null) {
                controladorMenuTipusItem.seleccionarTipusItem(nomTipusItem);
            } else {
                JOptionPane.showMessageDialog(instancia,
                    "No hi ha cap element de la llista seleccionat.");
            }
        });
        panellSeleccionarTipusItem.add(selecciona);
    }

    private static void inicialitzarPanellAdministrarTipusItem() {
        panellAdministrarTipusItem = new JPanel(new FlowLayout());
        botoDeseleccionarTipusItem = new JButton("Deselecciona");
        botoDeseleccionarTipusItem.addActionListener(e -> {
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                controladorMenuTipusItem.desseleccionarTipusItem();
            }
        });
        panellAdministrarTipusItem.add(botoDeseleccionarTipusItem);
        botoEsborrarTipusItem = new JButton("Esborra");
        botoEsborrarTipusItem.addActionListener(e -> {
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar el tipus d'ítem seleccionat" +
                        " i totes les seves dades?", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
                if (resposta == 0) {
                    controladorMenuTipusItem.esborrarTipusItemSeleccionat();
                }
            }
        });
        panellAdministrarTipusItem.add(botoEsborrarTipusItem);
        panellAdministrarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private static void inicialitzarPanellMostrarTipusItemSeleccionat() {
        panellMostrarTipusItemSeleccionat = new JPanel();
        panellMostrarTipusItemSeleccionat.setLayout(new BoxLayout(panellMostrarTipusItemSeleccionat, BoxLayout.Y_AXIS));

        JPanel informacio = new JPanel(new FlowLayout());
        JLabel text = new JLabel("Tipus d'ítem seleccionat:");
        text.setFont(new Font("Sans", Font.BOLD, 16));
        informacio.add(text);

        JPanel botons = new JPanel(new FlowLayout());

        botoVeureTipusItem = new JButton("Mostra el tipus d'ítem seleccionat");
        botoVeureTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoVeureTipusItem.addActionListener(e -> {
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                VistaDialegMostrarTipusItem vistaDialegMostrarTipusItem;
                try {
                    vistaDialegMostrarTipusItem = new VistaDialegMostrarTipusItem();
                    vistaDialegMostrarTipusItem.setVisible(true);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(instancia,
                            "No es pot mostrar aquest tipus d'ítem. Torna-ho a intentar.");
                }
            }
        });

        botoEditarTipusItem = new JButton("Edita el tipus d'ítem seleccionat");
        botoEditarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoEditarTipusItem.addActionListener(e -> {
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                VistaDialegEditarTipusItem vistaDialegEditarTipusItem;
                try {
                    vistaDialegEditarTipusItem = new VistaDialegEditarTipusItem();
                    vistaDialegEditarTipusItem.setVisible(true);
                    EncarregatActualitzarVistes.notificarObservadors();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(instancia,
                            "No es pot editar aquest tipus d'ítem. Torna-ho a intentar.");
                }
            }
        });

        textTipusItemSeleccionat = new JLabel();
        textTipusItemSeleccionat.setFont(new Font("Sans", Font.PLAIN, 16));
        informacio.add(textTipusItemSeleccionat);
        panellMostrarTipusItemSeleccionat.add(informacio);

        botons.add(botoVeureTipusItem);
        botons.add(botoEditarTipusItem);

        panellMostrarTipusItemSeleccionat.add(botons);

        actualitzarTipusItems();
    }

    public static void actualitzarTipusItems() {
        if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
            textTipusItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
            botoVeureTipusItem.setEnabled(false);
            botoEditarTipusItem.setEnabled(false);
            botoDeseleccionarTipusItem.setEnabled(false);
            botoEsborrarTipusItem.setEnabled(false);
        } else {
            textTipusItemSeleccionat.setText(controladorMenuTipusItem.obtenirNomTipusItemSeleccionat());
            botoVeureTipusItem.setEnabled(true);
            botoEditarTipusItem.setEnabled(true);
            botoDeseleccionarTipusItem.setEnabled(true);
            botoEsborrarTipusItem.setEnabled(true);
        }
        tipusItemsComboBoxModel.removeAllElements();
        tipusItemsComboBoxModel.addAll(controladorMenuTipusItem.obtenirNomsTipusItemsCarregats());
    }
}
