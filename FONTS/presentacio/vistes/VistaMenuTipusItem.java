package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * @author maria.prat
 */
public class VistaMenuTipusItem extends JPanel {

    private static final String kPrototipNomTipusItem = new String(new char[20]).replace('\0', '*');
    private static final String kMissatgeTipusItemNoSeleccionat = "No s'ha escollit cap tipus d'ítem";

    private static ControladorMenuTipusItem controladorMenuTipusItem;
    private static VistaMenuTipusItem instancia;

    private static JLabel textTipusItemSeleccionat;
    private static JButton botoVeureTipusItem;
    private static JButton botoEditarTipusItem;

    private static JPanel panellAfegirTipusItem;
    private static JPanel panellSeleccionarTipusItem;
    private static JPanel panellAdministrarTipusItem;
    private static JPanel panellMostrarTipusItemSeleccionat;

    private VistaMenuTipusItem() {
    }

    public static VistaMenuTipusItem obtenirInstancia() throws IOException {
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
        // TODO: afegir editar tipus item seleccionat
    }

    private static void inicialitzarPanellAfegirTipusItem() {
        panellAfegirTipusItem = new JPanel(new FlowLayout());
        JButton botoCrearTipusItem = new JButton("Crea un nou tipus d'ítem");
        botoCrearTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoCrearTipusItem.addActionListener(e -> {
            VistaDialegCrearTipusItem vistaDialegCrearTipusItem = null;
            try {
                vistaDialegCrearTipusItem = new VistaDialegCrearTipusItem();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(instancia,
                        "No s'ha pogut carregar un tipus d'ítem d'aquest arxiu.");
            }
            vistaDialegCrearTipusItem.setVisible(true);
        });
        panellAfegirTipusItem.add(botoCrearTipusItem);
        JButton botoCarregarTipusItem = new JButton("Carrega un nou tipus d'ítem");
        botoCarregarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoCarregarTipusItem.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(instancia,
                    "Introdueix el nom del tipus d'ítem:");
            JDialog dialegFitxer = new JDialog();
            JFileChooser selectorFitxer = new JFileChooser();
            int estatSelectorFitxer = selectorFitxer.showOpenDialog(dialegFitxer);
            if (estatSelectorFitxer == APPROVE_OPTION) {
                File rutaFitxer = selectorFitxer.getSelectedFile();
                try {
                    controladorMenuTipusItem.carregarTipusItem(nom, rutaFitxer.getAbsolutePath());
                    JOptionPane.showMessageDialog(instancia, "Tipus d'ítem carregat amb èxit.");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(instancia,
                            "No s'ha pogut carregar un tipus d'ítem d'aquest arxiu.");
                }
            }
        });
        panellAfegirTipusItem.add(botoCarregarTipusItem);
    }

    private static void inicialitzarPanellSeleccionarTipusItem() {
        panellSeleccionarTipusItem = new JPanel(new FlowLayout());
        JComboBox<String> tipusItemsComboBox = new JComboBox<>(
                controladorMenuTipusItem.obtenirNomsTipusItemsCarregats().toArray(new String[0]));
        tipusItemsComboBox.setPrototypeDisplayValue(kPrototipNomTipusItem);
        tipusItemsComboBox.setSelectedIndex(-1);
        panellSeleccionarTipusItem.add(tipusItemsComboBox);
        JButton selecciona = new JButton("Selecciona");
        selecciona.addActionListener(e -> {
            try {
                controladorMenuTipusItem.seleccionarTipusItem((String) tipusItemsComboBox.getSelectedItem());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia,
                        "No s'ha pogut seleccionar el tipus d'ítem.");
            }
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                textTipusItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
                botoVeureTipusItem.setEnabled(false);
                botoEditarTipusItem.setEnabled(false);
            } else {
                textTipusItemSeleccionat.setText(controladorMenuTipusItem.obtenirNomTipusItemSeleccionat());
                botoVeureTipusItem.setEnabled(true);
                botoEditarTipusItem.setEnabled(true);
            }
        });
        panellSeleccionarTipusItem.add(selecciona);
    }

    private static void inicialitzarPanellAdministrarTipusItem() {
        panellAdministrarTipusItem = new JPanel(new FlowLayout());
        JButton deselecciona = new JButton("Deselecciona");
        deselecciona.addActionListener(e -> {
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                try {
                    controladorMenuTipusItem.deseleccionarTipusItem();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(instancia,
                            "No s'ha pogut deseleccionar el tipus d'ítem seleccionat.");
                }
                textTipusItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
                botoVeureTipusItem.setEnabled(false);
                botoEditarTipusItem.setEnabled(false);
            }
        });
        panellAdministrarTipusItem.add(deselecciona);
        JButton esborra = new JButton("Esborra");
        esborra.addActionListener(e -> {
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar el tipus d'ítem seleccionat" +
                        " i totes les seves dades?", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
                if (resposta == 0) {
                    try {
                        controladorMenuTipusItem.esborrarTipusItemSeleccionat();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(instancia,
                                "No s'ha pogut esborrar el tipus d'ítem seleccionat.");
                    }
                    textTipusItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
                    botoVeureTipusItem.setEnabled(false);
                    botoEditarTipusItem.setEnabled(false);
                }
            }
        });
        panellAdministrarTipusItem.add(esborra);
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
                VistaDialegMostrarTipusItem vistaDialegMostrarTipusItem = null;
                try {
                    vistaDialegMostrarTipusItem = new VistaDialegMostrarTipusItem();
                } catch (IOException ex) {
                    //TODO: catch
                    ex.printStackTrace();
                }
                vistaDialegMostrarTipusItem.setVisible(true);
            }
        });

        botoEditarTipusItem = new JButton("Edita el tipus d'ítem seleccionat");
        botoEditarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoEditarTipusItem.addActionListener(e -> {
            if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                VistaDialegEditarTipusItem vistaDialegEditarTipusItem = null;
                try {
                    vistaDialegEditarTipusItem = new VistaDialegEditarTipusItem();
                } catch (IOException ex) {
                    //TODO: catch
                    ex.printStackTrace();
                }
                vistaDialegEditarTipusItem.setVisible(true);
            }
        });

        textTipusItemSeleccionat = new JLabel();
        if (!controladorMenuTipusItem.existeixTipusItemSeleccionat()) {
            textTipusItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
            botoVeureTipusItem.setEnabled(false);
            botoEditarTipusItem.setEnabled(false);
        } else {
            textTipusItemSeleccionat.setText(controladorMenuTipusItem.obtenirNomTipusItemSeleccionat());
            botoVeureTipusItem.setEnabled(true);
            botoEditarTipusItem.setEnabled(true);
        }
        textTipusItemSeleccionat.setFont(new Font("Sans", Font.PLAIN, 16));
        informacio.add(textTipusItemSeleccionat);
        panellMostrarTipusItemSeleccionat.add(informacio);

        botons.add(botoVeureTipusItem);
        botons.add(botoEditarTipusItem);

        panellMostrarTipusItemSeleccionat.add(botons);
    }
}
