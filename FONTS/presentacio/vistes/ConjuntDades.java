package presentacio.vistes;

import presentacio.controladors.ControladorConjuntDades;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * @author pol.casacuberta
 */

public class ConjuntDades extends JPanel {

    private final ControladorConjuntDades controladorConjuntDades;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;
    private JLabel seleccionarConjuntLabel;
    private JButton exportarConjuntDades;
    private JComboBox<String> seleccionarConjuntCombo;
    private JButton preprocessarCDButton;
    private JButton esborrarConjuntButton;
    private JFileChooser jFileChooser;

    public ConjuntDades() {
        controladorConjuntDades = ControladorConjuntDades.obtenirInstancia();
        inicialitzarConjuntDades();
    }

    public void inicialitzarConjuntDades() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10,10,10,10); // Afegeix padding per a que els elements no estiguin massa junts
        this.setLayout(gridBagLayout);

        seleccionarConjuntLabel = new JLabel("Selecciona el conjunt");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(seleccionarConjuntLabel, gridBagConstraints);

        String[] opcions = controladorConjuntDades.obtenirLlistaConjunts();
        seleccionarConjuntCombo = new JComboBox<>(opcions);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.add(seleccionarConjuntCombo,gridBagConstraints);


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
        this.add(exportarConjuntDades,gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        preprocessarCDButton = new JButton("Preprocessar conjunt");
        this.add(preprocessarCDButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        esborrarConjuntButton = new JButton("Esborrar conjunt");
        this.add(esborrarConjuntButton, gridBagConstraints);
    }
}
