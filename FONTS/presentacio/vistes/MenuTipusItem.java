package presentacio.vistes;

import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * @author maria.prat
 */
public class MenuTipusItem extends JPanel {

    private static final String kPrototipNomTipusItem = new String(new char[20]).replace('\0', '*');
    private static final String kMissatgeTipusItemNoSeleccionat = "No s'ha escollit cap tipus d'ítem";

    private static ControladorMenuTipusItem controladorMenuTipusItem;
    private static MenuTipusItem instancia;

    private static String nomTipusItemSeleccionat;

    private static JLabel textItemSeleccionat;
    private static JButton botoVeureTipusItem;

    private static JPanel panellAfegirTipusItem;
    private static JPanel panellSeleccionarTipusItem;
    private static JPanel panellMostrarTipusItemSeleccionat;

    private MenuTipusItem() {
    }

    public static MenuTipusItem obtenirInstancia() {
        if (instancia == null) {
            instancia = new MenuTipusItem();
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
        inicialitzarPanellMostrarTipusItemSeleccionat();
        instancia.add(panellMostrarTipusItemSeleccionat);
        instancia.add(Box.createVerticalGlue());
    }

    private static void inicialitzarPanellAfegirTipusItem() {
        panellAfegirTipusItem = new JPanel(new FlowLayout());
        JButton botoCrearTipusItem = new JButton("Crea un nou tipus d'ítem");
        botoCrearTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoCrearTipusItem.addActionListener(actionEvent -> crearTipusItem());
        panellAfegirTipusItem.add(botoCrearTipusItem);
        JButton botoCarregarTipusItem = new JButton("Carrega un nou tipus d'ítem");
        botoCarregarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoCarregarTipusItem.addActionListener(e -> {
            JDialog dialegFitxer = new JDialog();
            JFileChooser selectorFitxer = new JFileChooser();
            int estatSelectorFitxer = selectorFitxer.showOpenDialog(dialegFitxer);
            if (estatSelectorFitxer == APPROVE_OPTION) {
                File rutaFitxer = selectorFitxer.getSelectedFile();
                controladorMenuTipusItem.carregaTipusItem(rutaFitxer.getAbsolutePath());
            }
        });
        panellAfegirTipusItem.add(botoCarregarTipusItem);
    }

    private static void crearTipusItem() {
        DialegCrearTipusItem dialegCrearTipusItem = new DialegCrearTipusItem();
        dialegCrearTipusItem.setVisible(true);
    }

    private static void inicialitzarPanellSeleccionarTipusItem() {
        panellSeleccionarTipusItem = new JPanel(new FlowLayout());
        JComboBox<String> tipusItemsComboBox = new JComboBox<>(controladorMenuTipusItem.obtenirNomsTipusItemsCarregats());
        tipusItemsComboBox.setPrototypeDisplayValue(kPrototipNomTipusItem);
        tipusItemsComboBox.setSelectedIndex(-1);
        panellSeleccionarTipusItem.add(tipusItemsComboBox);
        JButton selecciona = new JButton("Selecciona");
        selecciona.addActionListener(e -> {
            nomTipusItemSeleccionat = (String) tipusItemsComboBox.getSelectedItem();
            if (nomTipusItemSeleccionat == null) {
                textItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
                botoVeureTipusItem.setEnabled(false);
            } else {
                textItemSeleccionat.setText(nomTipusItemSeleccionat);
                botoVeureTipusItem.setEnabled(true);
            }
        });
        panellSeleccionarTipusItem.add(selecciona);
        JButton esborra = new JButton("Esborra");
        esborra.addActionListener(e -> {
            if (nomTipusItemSeleccionat == null) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                controladorMenuTipusItem.esborrarTipusItem(nomTipusItemSeleccionat);
                nomTipusItemSeleccionat = null;
                textItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
                botoVeureTipusItem.setEnabled(false);
            }
        });
        panellSeleccionarTipusItem.add(esborra);
        panellSeleccionarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private static void inicialitzarPanellMostrarTipusItemSeleccionat() {
        panellMostrarTipusItemSeleccionat = new JPanel();
        panellMostrarTipusItemSeleccionat.setLayout(new BoxLayout(panellMostrarTipusItemSeleccionat, BoxLayout.Y_AXIS));

        JPanel informacio = new JPanel(new FlowLayout());
        JLabel text = new JLabel("Tipus d'ítem seleccionat:");
        text.setFont(new Font("Sans", Font.BOLD, 16));
        informacio.add(text);

        botoVeureTipusItem = new JButton("Mostra el tipus d'ítem seleccionat");
        botoVeureTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoVeureTipusItem.addActionListener(actionEvent -> mostrarTipusItemSeleccionat());

        textItemSeleccionat = new JLabel();
        if (nomTipusItemSeleccionat == null) {
            textItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
            botoVeureTipusItem.setEnabled(false);
        } else {
            textItemSeleccionat.setText(nomTipusItemSeleccionat);
            botoVeureTipusItem.setEnabled(true);
        }
        textItemSeleccionat.setFont(new Font("Sans", Font.PLAIN, 16));
        informacio.add(textItemSeleccionat);
        panellMostrarTipusItemSeleccionat.add(informacio);

        panellMostrarTipusItemSeleccionat.add(botoVeureTipusItem);
    }

    private static void mostrarTipusItemSeleccionat() {
        DialegMostrarTipusItem dialegMostrarTipusItem = new DialegMostrarTipusItem();
        dialegMostrarTipusItem.setVisible(true);
    }
}
