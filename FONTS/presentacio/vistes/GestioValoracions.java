package presentacio.vistes;

import presentacio.controladors.ControladorGestioValoracions;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * @author pol.casacuberta
 */

public class GestioValoracions extends JPanel {
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;
    private JLabel usuariIdLabel;
    private JTextField usuariIdText;
    private JLabel itemIdLabel;
    private JTextField itemIdText;
    private JLabel valorLabel;
    private JTextField valorText;
    private JButton creaValoracio;
    private JButton esborraValoracio;
    private JButton editaValoracio;
    private JFileChooser jFileChooser;
    private JButton conjuntDeValoracionsButton;

    private ControladorGestioValoracions controladorGestioValoracions = null;

    public GestioValoracions() {
        this.inicialitzarGestioValoracions();
        controladorGestioValoracions = ControladorGestioValoracions.obtenirInstancia();
    }

    public void inicialitzarGestioValoracions() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        this.setLayout(gridBagLayout);

        usuariIdLabel = new JLabel("Id Usuari: ");
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); // Afegeix padding
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(usuariIdLabel, gridBagConstraints);

        usuariIdText = new JTextField();
        usuariIdText.setColumns(10);
        usuariIdText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.add(usuariIdText, gridBagConstraints);

        itemIdLabel = new JLabel("Id Item: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(itemIdLabel, gridBagConstraints);

        itemIdText = new JTextField();
        itemIdText.setColumns(10);
        itemIdText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        this.add(itemIdText, gridBagConstraints);


        valorLabel = new JLabel("Valor: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(valorLabel, gridBagConstraints);

        valorText = new JTextField();
        valorText.setColumns(10);
        valorText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        this.add(valorText, gridBagConstraints);

        creaValoracio = new JButton("Crea Valoració");
        creaValoracio.addActionListener(e -> controladorGestioValoracions.afegirValoracio(usuariIdText.getText(), itemIdText.getText(), valorText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        this.add(creaValoracio, gridBagConstraints);

        esborraValoracio = new JButton("Esborra valoració");
        esborraValoracio.addActionListener(e -> controladorGestioValoracions.esborraValoracio(usuariIdText.getText(), itemIdText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        this.add(esborraValoracio, gridBagConstraints);

        editaValoracio = new JButton("Edita valoració");
        editaValoracio.addActionListener(e -> controladorGestioValoracions.editaValoracio(usuariIdText.getText(),itemIdText.getText(),valorText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        this.add(editaValoracio, gridBagConstraints);

        jFileChooser = new JFileChooser();
        jFileChooser.addActionListener(e -> {
        });

        conjuntDeValoracionsButton = new JButton("Carrega conjunt de valoracions");
        conjuntDeValoracionsButton.addActionListener(e -> {
            // TODO: logica
            JDialog pathDialog = new JDialog();
            int estatJfile = jFileChooser.showOpenDialog(pathDialog);
            if (estatJfile == APPROVE_OPTION) {
                File pathConjuntVal = jFileChooser.getSelectedFile();
                System.out.println(pathConjuntVal.getAbsolutePath());
                controladorGestioValoracions.carregaConjuntValoracions(pathConjuntVal.getAbsolutePath());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        this.add(conjuntDeValoracionsButton, gridBagConstraints);
    }
}
