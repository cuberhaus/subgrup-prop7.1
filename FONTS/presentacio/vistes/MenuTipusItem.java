package presentacio.vistes;

import domini.classes.TipusItem;
import presentacio.controladors.ControladorMenuTipusItem;
import presentacio.controladors.ControladorPresentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JButton botoAfegirTipusItem = new JButton("Afegeix un nou tipus d'ítem");
        botoAfegirTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoAfegirTipusItem.addActionListener(actionEvent -> afegirTipusItem());
        panellAfegirTipusItem.add(botoAfegirTipusItem);
    }

    private void afegirTipusItem() {
        DialegAfegirTipusItem dialegAfegirTipusItem = new DialegAfegirTipusItem();
        dialegAfegirTipusItem.setVisible(true);
    }

    private void inicialitzarPanellSeleccionarTipusItem() {
        panellSeleccionarTipusItem = new JPanel(new FlowLayout());
        JComboBox<TipusItem> tipusItemsComboBox = new JComboBox<>(controladorMenuTipusItem.obtenirTipusItemsCarregats());
        tipusItemsComboBox.setPrototypeDisplayValue(new TipusItem(kPrototipNomTipusItem));
        tipusItemsComboBox.setSelectedIndex(-1);
        panellSeleccionarTipusItem.add(tipusItemsComboBox);
        JButton selecciona = new JButton("Selecciona");
        selecciona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipusItemSeleccionat = (TipusItem) tipusItemsComboBox.getSelectedItem();
                if (tipusItemSeleccionat == null) {
                    textItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
                    botoVeureTipusItem.setEnabled(false);
                } else {
                    textItemSeleccionat.setText(tipusItemSeleccionat.obtenirNom());
                    botoVeureTipusItem.setEnabled(true);
                }
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
