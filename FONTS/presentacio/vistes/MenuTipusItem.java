package presentacio.vistes;

import domini.classes.TipusItem;
import presentacio.controladors.ControladorMenuTipusItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;

public class MenuTipusItem extends JPanel {

    private final String kPrototipNomTipusItem = new String(new char[20]).replace('\0', '*');
    private final String kMissatgeTipusItemNoSeleccionat = "No s'ha escollit cap tipus d'ítem";

    private final ControladorMenuTipusItem controladorMenuTipusItem;
    private TipusItem tipusItemSeleccionat;

    private JLabel textItemSeleccionat;
    private JButton botoVeureTipusItem;

    private JPanel panellAfegirTipusItem;
    private JPanel panellSeleccionarTipusItem;
    private JPanel panellMostrarTipusItemSeleccionat;

    public MenuTipusItem(){
        controladorMenuTipusItem = ControladorMenuTipusItem.obtenirInstancia();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        inicialitzarMenuTipusItem();
    }

    private void inicialitzarMenuTipusItem(){
        JLabel titol = new JLabel("Quin tipus d'ítem vols que et recomani?");
        titol.setFont(new Font("Sans", Font.PLAIN, 20));
        titol.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcio = new JLabel("Afegeix un tipus d'ítem nou o selecciona un tipus d'ítem existent");
        descripcio.setFont(new Font("Sans", Font.PLAIN, 14));
        descripcio.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue());
        this.add(titol);
        this.add(Box.createVerticalStrut(10));
        this.add(descripcio);
        this.add(Box.createVerticalGlue());
        inicialitzarPanellAfegirTipusItem();
        this.add(panellAfegirTipusItem);
        inicialitzarPanellSeleccionarTipusItem();
        this.add(panellSeleccionarTipusItem);
        inicialitzarPanellMostrarTipusItemSeleccionat();
        this.add(panellMostrarTipusItemSeleccionat);
        this.add(Box.createVerticalGlue());
    }

    private void inicialitzarPanellAfegirTipusItem() {
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

    private void crearTipusItem() {
        DialegCrearTipusItem dialegCrearTipusItem = new DialegCrearTipusItem();
        dialegCrearTipusItem.setVisible(true);
    }

    private void inicialitzarPanellSeleccionarTipusItem() {
        panellSeleccionarTipusItem = new JPanel(new FlowLayout());
        JComboBox<TipusItem> tipusItemsComboBox = new JComboBox<>(controladorMenuTipusItem.obtenirTipusItemsCarregats());
        tipusItemsComboBox.setPrototypeDisplayValue(new TipusItem(kPrototipNomTipusItem));
        tipusItemsComboBox.setSelectedIndex(-1);
        panellSeleccionarTipusItem.add(tipusItemsComboBox);
        JButton selecciona = new JButton("Selecciona");
        selecciona.addActionListener(e -> {
            tipusItemSeleccionat = (TipusItem) tipusItemsComboBox.getSelectedItem();
            if (tipusItemSeleccionat == null) {
                textItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
                botoVeureTipusItem.setEnabled(false);
            } else {
                textItemSeleccionat.setText(tipusItemSeleccionat.obtenirNom());
                botoVeureTipusItem.setEnabled(true);
            }
        });
        panellSeleccionarTipusItem.add(selecciona);
        panellSeleccionarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void inicialitzarPanellMostrarTipusItemSeleccionat() {
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
        if (tipusItemSeleccionat == null) {
            textItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
            botoVeureTipusItem.setEnabled(false);
        } else {
            textItemSeleccionat.setText(tipusItemSeleccionat.obtenirNom());
            botoVeureTipusItem.setEnabled(true);
        }
        textItemSeleccionat.setFont(new Font("Sans", Font.PLAIN, 16));
        informacio.add(textItemSeleccionat);
        panellMostrarTipusItemSeleccionat.add(informacio);

        panellMostrarTipusItemSeleccionat.add(botoVeureTipusItem);
    }

    private void mostrarTipusItemSeleccionat() {
        DialegMostrarTipusItem dialegMostrarTipusItem = new DialegMostrarTipusItem();
        dialegMostrarTipusItem.setVisible(true);
    }
}
