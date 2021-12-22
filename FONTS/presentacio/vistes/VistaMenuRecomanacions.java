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

    /**
     * Guardem el nom del tipus d'ítem del filtre que tenim guardat per actualitzar-lo quan canviem el tipus d'ítem
     * seleccionat.
     */
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
        inicialitzarPanellObtenirRecomanacio();
        instancia.add(panellObtenirRecomanacio);
        instancia.add(Box.createVerticalGlue());
    }

    private static void inicialitzarPanellSeleccionarMetode() {
        panellSeleccionarMetode = new JPanel(new FlowLayout());
        JLabel descripcio = new JLabel("Selecciona el mètode:");
        panellSeleccionarMetode.add(descripcio);
        metodeComboBox = new JComboBox<>(new String[]{"Basat en els ítems que has valorat",
                "Basat en usuaris amb gustos semblants als teus", "Basat en tot"});
        panellSeleccionarMetode.add(metodeComboBox);
        panellSeleccionarMetode.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private static void inicialitzarPanellSeleccionarFiltre() {
        panellSeleccionarFiltre = new JPanel(new FlowLayout());

        JLabel descripcio = new JLabel("Selecciona un filtre pels atributs del tipus d'ítem seleccionat:");
        descripcio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellSeleccionarFiltre.add(descripcio);

        JButton botoEditarFiltre = new JButton("Edita filtre");
        botoEditarFiltre.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        panellObtenirRecomanacio = new JPanel(new FlowLayout());

        JButton botoObtenirRecomanacio = new JButton("Obté recomanació");
        botoObtenirRecomanacio.setAlignmentX(Component.CENTER_ALIGNMENT);
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
