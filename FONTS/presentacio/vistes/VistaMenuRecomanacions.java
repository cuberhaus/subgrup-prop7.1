package presentacio.vistes;

import presentacio.controladors.ControladorMenuRecomanacions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VistaMenuRecomanacions extends JPanel {
    private static int amplada;
    private static int altura;
    private static VistaMenuRecomanacions instancia;
    private static ControladorMenuRecomanacions controladorMenuRecomanacions;
    private static JPanel panellSeleccionarMetode;
    private static JPanel panellSeleccionarFiltre;
    private static JPanel panellMostrarRecomanacio;
    private static JPanel panellAvaluarRecomanacio;

    private VistaMenuRecomanacions() {
        amplada = this.getWidth();
        altura = this.getHeight();
    }

    public static VistaMenuRecomanacions obtenirInstancia() {
        if (instancia == null) {
            instancia = new VistaMenuRecomanacions();
            controladorMenuRecomanacions = ControladorMenuRecomanacions.obtenirInstancia();
            inicialitzarMenuRecomanacions();
        }
        return instancia;
    }

    private static void inicialitzarMenuRecomanacions() {
        instancia.setLayout(new BoxLayout(instancia, BoxLayout.Y_AXIS));

        JLabel titol = new JLabel("Vols que et recomani ítems?");
        titol.setFont(new Font("Sans", Font.PLAIN, 20));
        titol.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descripcio = new JLabel("Selecciona quin mètode de recomanació i filtre vols que faci servir.");
        descripcio.setFont(new Font("Sans", Font.PLAIN, 14));
        descripcio.setAlignmentX(Component.CENTER_ALIGNMENT);

        instancia.add(Box.createVerticalGlue());
        instancia.add(titol);
        instancia.add(Box.createVerticalStrut(10));
        instancia.add(descripcio);
        instancia.add(Box.createVerticalGlue());
        inicialitzarPanellSeleccionarMetode();
        instancia.add(panellSeleccionarMetode);
        inicialitzarPanellSeleccionarFiltre();
        instancia.add(panellSeleccionarFiltre);
        inicialitzarPanellMostrarRecomanacio();
        instancia.add(panellMostrarRecomanacio);
        inicialitzarPanellAvaluarRecomanacio();
        instancia.add(panellAvaluarRecomanacio);
        instancia.add(Box.createVerticalGlue());

        /*
        controladorMenuRecomanacions.sessioIniciada();
        controladorMenuRecomanacions.existeixTipusItemSeleccionat();
        controladorMenuRecomanacions.obtenirRecomanacioCollaborative(new ArrayList<>(), false);
        controladorMenuRecomanacions.obtenirRecomanacioContentBased(new ArrayList<>(), false);
        controladorMenuRecomanacions.obtenirRecomanacioHibrida(new ArrayList<>(), false);
        controladorMenuRecomanacions.avaluarRecomanacio();
         */
    }

    private static void inicialitzarPanellSeleccionarMetode() {
        panellSeleccionarMetode = new JPanel(new FlowLayout());
        JLabel descripcio = new JLabel("Selecciona el mètode:");
        panellSeleccionarMetode.add(descripcio);
        JComboBox<String> metodeComboBox = new JComboBox<>(new String[]{"Basat en els ítems que has valorat",
                "Basat en usuaris amb gustos semblants als teus", "Basat en tot"});
        panellSeleccionarMetode.add(metodeComboBox);
        panellSeleccionarMetode.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private static void inicialitzarPanellSeleccionarFiltre() {
        panellSeleccionarFiltre = new JPanel();
        panellSeleccionarFiltre.setLayout(new BoxLayout(panellSeleccionarFiltre, BoxLayout.Y_AXIS));

        JPanel panellLlistaAtributs = new JPanel(new FlowLayout());
        JScrollPane panellScroll = new JScrollPane();
        panellScroll.setPreferredSize(new Dimension(3 * amplada / 4, 3 * altura / 4));
        if (controladorMenuRecomanacions.existeixTipusItemSeleccionat()) {
            ArrayList<String> nomsAtributs = controladorMenuRecomanacions.obtenirNomAtributsTipusItemSeleccionat();
            for (String nomsAtribut : nomsAtributs) {
                panellScroll.add(new JCheckBox(nomsAtribut));
            }
        } else {
            JLabel textTipusItemSeleccionat = new JLabel("No hi ha cap tipus d'ítem seleccionat.");
            textTipusItemSeleccionat.setAlignmentX(Component.CENTER_ALIGNMENT);
            panellScroll.add(textTipusItemSeleccionat);
        }
        panellLlistaAtributs.add(panellScroll);
        panellSeleccionarFiltre.add(panellLlistaAtributs);
    }

    private static void inicialitzarPanellMostrarRecomanacio() {
        panellMostrarRecomanacio = new JPanel();
    }

    private static void inicialitzarPanellAvaluarRecomanacio() {
        panellAvaluarRecomanacio = new JPanel();
    }
}
