package presentacio.vistes;

import presentacio.controladors.ControladorMenuRecomanacions;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Vista del menu de recomanacions
 * @author maria.prat
 */
public class VistaMenuRecomanacions extends JPanel {
    private static VistaMenuRecomanacions instancia;
    private static ControladorMenuRecomanacions controladorMenuRecomanacions;
    private static JPanel panellSeleccionarMetode;
    private static JPanel panellSeleccionarFiltre;
    private static JPanel panellObtenirRecomanacio;
    private static JComboBox<String> metodeComboBox;

    private static String nomTipusItemFiltre;
    private static Map<String, Boolean> nomsAtributsFiltre;

    private VistaMenuRecomanacions() {
    }

    public static VistaMenuRecomanacions obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new VistaMenuRecomanacions();
            controladorMenuRecomanacions = ControladorMenuRecomanacions.obtenirInstancia();
            inicialitzarMenuRecomanacions();
        }
        return instancia;
    }

    private static void inicialitzarMenuRecomanacions() {
        instancia.setLayout(new BoxLayout(instancia, BoxLayout.Y_AXIS));
        instancia.setBorder(UIEstil.panelBorder());

        instancia.add(Box.createVerticalGlue());
        instancia.add(UIEstil.createTitle("Vols que et recomani ítems?"));
        instancia.add(UIEstil.verticalGap());
        instancia.add(UIEstil.createSubtitle("Selecciona quin mètode de recomanació i filtre vols que faci servir."));
        instancia.add(UIEstil.verticalGapLarge());
        instancia.add(UIEstil.verticalGapLarge());

        inicialitzarPanellSeleccionarMetode();
        instancia.add(panellSeleccionarMetode);
        instancia.add(UIEstil.verticalGap());
        inicialitzarPanellSeleccionarFiltre();
        instancia.add(panellSeleccionarFiltre);
        instancia.add(UIEstil.verticalGapLarge());
        inicialitzarPanellObtenirRecomanacio();
        instancia.add(panellObtenirRecomanacio);
        instancia.add(Box.createVerticalGlue());
    }

    private static void inicialitzarPanellSeleccionarMetode() {
        panellSeleccionarMetode = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        panellSeleccionarMetode.setOpaque(false);
        JLabel descripcio = UIEstil.createLabel("Selecciona el mètode:");
        panellSeleccionarMetode.add(descripcio);
        metodeComboBox = new JComboBox<>(new String[]{
                "Basat en els ítems que has valorat",
                "Basat en usuaris amb gustos semblants als teus",
                "Basat en tot"
        });
        metodeComboBox.setFont(UIEstil.FONT_LABEL);
        panellSeleccionarMetode.add(metodeComboBox);
        panellSeleccionarMetode.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private static void inicialitzarPanellSeleccionarFiltre() {
        panellSeleccionarFiltre = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        panellSeleccionarFiltre.setOpaque(false);

        JLabel descripcio = UIEstil.createLabel("Selecciona un filtre pels atributs del tipus d'ítem seleccionat:");
        panellSeleccionarFiltre.add(descripcio);

        JButton botoEditarFiltre = UIEstil.createSmallButton("Edita filtre");
        botoEditarFiltre.addActionListener(actionEvent -> {
            if (!ControladorMenuRecomanacions.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                if (nomTipusItemFiltre == null) {
                    nomsAtributsFiltre = new TreeMap<>();
                    for (String nomAtribut : ControladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat()) {
                        nomsAtributsFiltre.put(nomAtribut, true);
                    }
                } else if (!nomTipusItemFiltre.equals(controladorMenuRecomanacions.obtenirNomTipusItemSeleccionat())) {
                    nomsAtributsFiltre = new TreeMap<>();
                    for (String nomAtribut : ControladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat()) {
                        nomsAtributsFiltre.put(nomAtribut, true);
                    }
                }
                nomTipusItemFiltre = controladorMenuRecomanacions.obtenirNomTipusItemSeleccionat();

                VistaDialegEditarFiltre vistaDialegEditarFiltre;
                try {
                    vistaDialegEditarFiltre = new VistaDialegEditarFiltre(nomsAtributsFiltre);
                    vistaDialegEditarFiltre.setVisible(true);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(instancia,
                            "No es pot mostrar aquest tipus d'ítem. Torna-ho a intentar.");
                }
            }
        });
        panellSeleccionarFiltre.add(botoEditarFiltre);
    }

    private static void inicialitzarPanellObtenirRecomanacio() {
        panellObtenirRecomanacio = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panellObtenirRecomanacio.setOpaque(false);

        JButton botoObtenirRecomanacio = UIEstil.createAccentButton("Obté recomanació");
        botoObtenirRecomanacio.addActionListener(actionEvent -> {
            if (!ControladorMenuRecomanacions.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else if (!ControladorMenuRecomanacions.sessioIniciada()) {
                JOptionPane.showMessageDialog(instancia, "No has iniciat la sessió.");
            } else {
                if (nomTipusItemFiltre == null) {
                    nomsAtributsFiltre = new TreeMap<>();
                    for (String nomAtribut : ControladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat()) {
                        nomsAtributsFiltre.put(nomAtribut, true);
                    }
                } else if (!nomTipusItemFiltre.equals(controladorMenuRecomanacions.obtenirNomTipusItemSeleccionat())) {
                    nomsAtributsFiltre = new TreeMap<>();
                    for (String nomAtribut : ControladorMenuRecomanacions.obtenirNomsAtributsTipusItemSeleccionat()) {
                        nomsAtributsFiltre.put(nomAtribut, true);
                    }
                }
                nomTipusItemFiltre = controladorMenuRecomanacions.obtenirNomTipusItemSeleccionat();

                VistaDialegObtenirRecomanacio vistaDialegObtenirRecomanacio;
                try {
                    vistaDialegObtenirRecomanacio = new VistaDialegObtenirRecomanacio(
                            (String) metodeComboBox.getSelectedItem(), nomsAtributsFiltre);
                    vistaDialegObtenirRecomanacio.setVisible(true);
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    JOptionPane.showMessageDialog(instancia,
                            "No es pot mostrar la recomanació. Torna-ho a intentar.");
                }
            }
        });
        panellObtenirRecomanacio.add(botoObtenirRecomanacio);
    }
}
