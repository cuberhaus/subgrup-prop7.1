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
    private static ConjuntDades instancia;

    private ControladorConjuntDades controladorConjuntDades = null;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;
    private JLabel seleccionarConjuntLabel;
    private JButton exportarConjuntDades;
    private JComboBox<String> seleccionarConjuntCombo;
    private JButton preprocessarCDButton;
    private JButton esborrarConjuntButton;
    private JFileChooser jFileChooser;

    private ConjuntDades() {
        controladorConjuntDades = ControladorConjuntDades.obtenirInstancia();
        inicialitzarConjuntDades();
    }

    public static ConjuntDades obtenirInstancia() {
        if (instancia == null) {
            instancia = new ConjuntDades();
        }
        return instancia;
    }

    public void inicialitzarConjuntDades() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10,10,10,10); // Afegeix padding per a que els elements no estiguin massa junts
        instancia.setLayout(gridBagLayout);

        seleccionarConjuntLabel = new JLabel("Selecciona el conjunt");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        instancia.add(seleccionarConjuntLabel, gridBagConstraints);

        String[] opcions = controladorConjuntDades.obtenirLlistaConjunts();
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
