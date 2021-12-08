package presentacio.vistes;

import domini.classes.TipusItem;
import presentacio.controladors.ControladorPresentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTipusItem extends JPanel {

    private final String kPrototipNomTipusItem = new String(new char[20]).replace('\0', '*');
    private final String kMissatgeTipusItemNoSeleccionat = "No s'ha escollit cap tipus d'ítem";

    private ControladorPresentacio controladorPresentacio;
    private TipusItem tipusItemSeleccionat;
    private JLabel textItemSeleccionat;

    private JPanel panellSeleccionarTipusItem;
    private JPanel panellMostrarTipusItemSeleccionat;

    public MenuTipusItem(ControladorPresentacio controladorPresentacio){
        this.controladorPresentacio = controladorPresentacio;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        inicialitzarMenuTipusItem();
    }

    private void inicialitzarMenuTipusItem(){
        JLabel text = new JLabel("Quin tipus d'ítem vols que et recomani?");
        text.setFont(new Font("Sans", Font.PLAIN, 20));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalGlue());
        this.add(text);
        this.add(Box.createVerticalGlue());
        inicialitzarPanellSeleccionarTipusItem();
        this.add(panellSeleccionarTipusItem);
        inicialitzarPanellMostrarTipusItemSeleccionat();
        this.add(panellMostrarTipusItemSeleccionat);
        this.add(Box.createVerticalGlue());
    }

    private void inicialitzarPanellSeleccionarTipusItem() {
        panellSeleccionarTipusItem = new JPanel(new FlowLayout());
        JComboBox<TipusItem> tipusItemsComboBox = new JComboBox<>(controladorPresentacio.obtenirTipusItemsCarregats());
        tipusItemsComboBox.setPrototypeDisplayValue(new TipusItem(kPrototipNomTipusItem));
        panellSeleccionarTipusItem.add(tipusItemsComboBox);
        JButton carrega = new JButton("Carrega");
        carrega.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipusItemSeleccionat = (TipusItem) tipusItemsComboBox.getSelectedItem();
                if (tipusItemSeleccionat == null) {
                    textItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
                } else {
                    textItemSeleccionat.setText(tipusItemSeleccionat.obtenirNom());
                }
            }
        });
        panellSeleccionarTipusItem.add(carrega);
        panellSeleccionarTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botoAfegirTipusItem = new JButton("Afegeix un nou tipus d'ítem");
        botoAfegirTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellSeleccionarTipusItem.add(botoAfegirTipusItem);
    }

    private void inicialitzarPanellMostrarTipusItemSeleccionat() {
        panellMostrarTipusItemSeleccionat = new JPanel();
        panellMostrarTipusItemSeleccionat.setLayout(new BoxLayout(panellMostrarTipusItemSeleccionat, BoxLayout.Y_AXIS));

        JPanel informacio = new JPanel(new FlowLayout());
        JLabel text = new JLabel("Tipus d'ítem seleccionat:");
        text.setFont(new Font("Sans", Font.BOLD, 16));
        informacio.add(text);
        textItemSeleccionat = new JLabel();
        if (tipusItemSeleccionat == null) {
            textItemSeleccionat.setText(kMissatgeTipusItemNoSeleccionat);
        } else {
            textItemSeleccionat.setText(tipusItemSeleccionat.obtenirNom());
        }
        textItemSeleccionat.setFont(new Font("Sans", Font.PLAIN, 16));
        informacio.add(textItemSeleccionat);
        panellMostrarTipusItemSeleccionat.add(informacio);
        JButton botoVeureTipusItem = new JButton("Veu el tipus d'ítem seleccionat");
        botoVeureTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellMostrarTipusItemSeleccionat.add(botoVeureTipusItem);
    }
}
