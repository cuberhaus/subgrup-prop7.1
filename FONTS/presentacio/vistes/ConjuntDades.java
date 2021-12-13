package presentacio.vistes;

import presentacio.controladors.ControladorConjuntDades;

import javax.swing.*;
import java.awt.*;

/**
 * @author pol.casacuberta
 */

public class ConjuntDades extends JPanel {

    private final ControladorConjuntDades controladorConjuntDades;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;
    private JLabel seleccionarConjuntLabel;
    private JButton exportarConjuntDades;
    private JComboBox seleccionarConjuntCombo;

    public ConjuntDades() {
        controladorConjuntDades = ControladorConjuntDades.obtenirInstancia();
        inicialitzarConjuntDades();
    }

    public void inicialitzarConjuntDades() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
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

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        exportarConjuntDades = new JButton();
//        exportarConjuntDades.addActionListener(e -> controladorConjuntDades.exportarConjuntDades());

        this.add(exportarConjuntDades, gridBagConstraints);
    }
}
