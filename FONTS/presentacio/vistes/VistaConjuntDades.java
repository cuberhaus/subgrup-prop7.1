package presentacio.vistes;

import presentacio.controladors.ControladorConjuntDades;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * @author pol.casacuberta
 */

public class VistaConjuntDades extends JPanel {
    private static VistaConjuntDades instancia;

    private static ControladorConjuntDades controladorConjuntDades = null;
    private static GridBagLayout gridBagLayout;
    private static GridBagConstraints gridBagConstraints;
    private static JLabel seleccionarConjuntLabel;
    private static JButton exportarConjuntDades;
    private static JComboBox<String> seleccionarConjuntCombo;
    private static JButton preprocessarCDButton;
    private static JButton esborrarConjuntButton;
    private static JFileChooser jFileChooser;

    private VistaConjuntDades() {
    }

    public static VistaConjuntDades obtenirInstancia() {
        if (instancia == null) {
            instancia = new VistaConjuntDades();
            controladorConjuntDades = ControladorConjuntDades.obtenirInstancia();
            inicialitzarConjuntDades();
        }
        return instancia;
    }

    public static void inicialitzarConjuntDades() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10,10,10,10); // Afegeix padding per a que els elements no estiguin massa junts
        instancia.setLayout(gridBagLayout);

        seleccionarConjuntLabel = new JLabel("Selecciona el conjunt");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        instancia.add(seleccionarConjuntLabel, gridBagConstraints);

        String[] opcions = controladorConjuntDades.obtenirLlistaConjunts().toArray(new String[0]);
        seleccionarConjuntCombo = new JComboBox<>(opcions);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        instancia.add(seleccionarConjuntCombo,gridBagConstraints);


        jFileChooser = new JFileChooser();
        jFileChooser.addActionListener(e -> {
        });

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        exportarConjuntDades = new JButton("Exportar conjunt");
        exportarConjuntDades.addActionListener(e -> {
            JDialog pathDialog = new JDialog();
            int estatJfile = jFileChooser.showOpenDialog(pathDialog);
            if(estatJfile == APPROVE_OPTION) {
                File pathConjunt = jFileChooser.getSelectedFile();
                controladorConjuntDades.exportarConjuntDades(pathConjunt.getAbsolutePath());
            }
        });
        instancia.add(exportarConjuntDades,gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        preprocessarCDButton = new JButton("Preprocessar conjunt");
        instancia.add(preprocessarCDButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        esborrarConjuntButton = new JButton("Esborrar conjunt");
        esborrarConjuntButton.addActionListener(e-> {
            String conjuntaEsborrar = (String) seleccionarConjuntCombo.getSelectedItem();
            controladorConjuntDades.esborraConjunt(conjuntaEsborrar);
        });
        instancia.add(esborrarConjuntButton, gridBagConstraints);
    }
}
