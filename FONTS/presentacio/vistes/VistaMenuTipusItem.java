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

    private static void inicialitzarMenuTipusItem() {
        instancia.setLayout(new BoxLayout(instancia, BoxLayout.Y_AXIS));
        instancia.setBorder(UIEstil.panelBorder());

        instancia.add(Box.createVerticalGlue());
        instancia.add(UIEstil.createTitle("Quin tipus d'ítem vols que et recomani?"));
        instancia.add(UIEstil.verticalGap());
        instancia.add(UIEstil.createSubtitle("Afegeix un tipus d'ítem nou o selecciona un tipus d'ítem existent"));
        instancia.add(UIEstil.verticalGapLarge());
        instancia.add(UIEstil.verticalGapLarge());

        inicialitzarPanellAfegirTipusItem();
        instancia.add(panellAfegirTipusItem);
        instancia.add(UIEstil.verticalGap());
        inicialitzarPanellSeleccionarTipusItem();
        instancia.add(panellSeleccionarTipusItem);
        instancia.add(UIEstil.verticalGap());
        inicialitzarPanellAdministrarTipusItem();
        instancia.add(panellAdministrarTipusItem);
        instancia.add(UIEstil.verticalGapLarge());
        inicialitzarPanellMostrarTipusItemSeleccionat();
        instancia.add(panellMostrarTipusItemSeleccionat);
        instancia.add(Box.createVerticalGlue());
    }

    private static void inicialitzarPanellAfegirTipusItem() {
        panellAfegirTipusItem = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING, 0));
        panellAfegirTipusItem.setOpaque(false);

        JButton botoCrearTipusItem = UIEstil.createAccentButton("Crea un nou tipus d'ítem");
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

        JButton botoCarregarTipusItem = UIEstil.createButton("Carrega un nou tipus d'ítem");
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
        panellSeleccionarTipusItem = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        panellSeleccionarTipusItem.setOpaque(false);

        tipusItemsComboBoxModel = new DefaultComboBoxModel<>();
        tipusItemsComboBoxModel.addAll(controladorMenuTipusItem.obtenirNomsTipusItemsCarregats());

        JComboBox<String> tipusItemsComboBox = new JComboBox<>();
        tipusItemsComboBox.setModel(tipusItemsComboBoxModel);
        tipusItemsComboBox.setPrototypeDisplayValue(kPrototipNomTipusItem);
        tipusItemsComboBox.setSelectedIndex(-1);
        tipusItemsComboBox.setFont(UIEstil.FONT_LABEL);
        panellSeleccionarTipusItem.add(tipusItemsComboBox);

        JButton selecciona = UIEstil.createSmallButton("Selecciona");
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
        panellAdministrarTipusItem = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING, 0));
        panellAdministrarTipusItem.setOpaque(false);

        botoDeseleccionarTipusItem = UIEstil.createSmallButton("Deselecciona");
        botoDeseleccionarTipusItem.addActionListener(e -> {
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                controladorMenuTipusItem.desseleccionarTipusItem();
            }
        });
        panellAdministrarTipusItem.add(botoDeseleccionarTipusItem);

        botoEsborrarTipusItem = UIEstil.createSmallButton("Esborra");
        botoEsborrarTipusItem.setBackground(UIEstil.DANGER);
        botoEsborrarTipusItem.setForeground(Color.WHITE);
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
    }

    private static void inicialitzarPanellMostrarTipusItemSeleccionat() {
        panellMostrarTipusItemSeleccionat = new JPanel();
        panellMostrarTipusItemSeleccionat.setLayout(new BoxLayout(panellMostrarTipusItemSeleccionat, BoxLayout.Y_AXIS));
        panellMostrarTipusItemSeleccionat.setOpaque(false);

        JPanel informacio = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        informacio.setOpaque(false);
        JLabel text = UIEstil.createLabel("Tipus d'ítem seleccionat:");
        text.setFont(UIEstil.FONT_BUTTON);
        informacio.add(text);

        textTipusItemSeleccionat = new JLabel();
        textTipusItemSeleccionat.setFont(new Font("SansSerif", Font.ITALIC, 16));
        textTipusItemSeleccionat.setForeground(UIEstil.ACCENT);
        informacio.add(textTipusItemSeleccionat);
        panellMostrarTipusItemSeleccionat.add(informacio);

        JPanel botons = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING, 0));
        botons.setOpaque(false);

        botoVeureTipusItem = UIEstil.createButton("Mostra el tipus d'ítem");
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

        botoEditarTipusItem = UIEstil.createButton("Edita el tipus d'ítem");
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

        botons.add(botoVeureTipusItem);
        botons.add(botoEditarTipusItem);
        panellMostrarTipusItemSeleccionat.add(UIEstil.verticalGap());
        panellMostrarTipusItemSeleccionat.add(botons);

        actualitzarTipusItems();
    }

    public static void actualitzarTipusItems() {
        if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
            textTipusItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
            textTipusItemSeleccionat.setForeground(UIEstil.TEXT_SECONDARY);
            botoVeureTipusItem.setEnabled(false);
            botoEditarTipusItem.setEnabled(false);
            botoDeseleccionarTipusItem.setEnabled(false);
            botoEsborrarTipusItem.setEnabled(false);
        } else {
            textTipusItemSeleccionat.setText(controladorMenuTipusItem.obtenirNomTipusItemSeleccionat());
            textTipusItemSeleccionat.setForeground(UIEstil.ACCENT);
            botoVeureTipusItem.setEnabled(true);
            botoEditarTipusItem.setEnabled(true);
            botoDeseleccionarTipusItem.setEnabled(true);
            botoEsborrarTipusItem.setEnabled(true);
        }
        tipusItemsComboBoxModel.removeAllElements();
        tipusItemsComboBoxModel.addAll(controladorMenuTipusItem.obtenirNomsTipusItemsCarregats());
    }
}
