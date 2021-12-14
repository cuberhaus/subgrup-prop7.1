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
    private static GestioValoracions instanciaUnica;

    private static GridBagLayout gridBagLayout;
    private static GridBagConstraints gridBagConstraints;
    private static JLabel usuariIdLabel;
    private static JTextField usuariIdText;
    private static JLabel itemIdLabel;
    private static JTextField itemIdText;
    private static JLabel valorLabel;
    private static JTextField valorText;
    private static JButton creaValoracio;
    private static JButton esborraValoracio;
    private static JButton editaValoracio;
    private static JFileChooser jFileChooser;
    private static JButton conjuntDeValoracionsButton;

    private static ControladorGestioValoracions controladorGestioValoracions = null;

    private GestioValoracions() {
    }

    public static GestioValoracions obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new GestioValoracions();
            inicialitzarGestioValoracions();
            controladorGestioValoracions = ControladorGestioValoracions.obtenirInstancia();
        }
        return instanciaUnica;
    }

    public static void inicialitzarGestioValoracions() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        instanciaUnica.setLayout(gridBagLayout);

        usuariIdLabel = new JLabel("Id Usuari: ");
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); // Afegeix padding
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        instanciaUnica.add(usuariIdLabel, gridBagConstraints);

        usuariIdText = new JTextField();
        usuariIdText.setColumns(10);
        usuariIdText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        instanciaUnica.add(usuariIdText, gridBagConstraints);

        itemIdLabel = new JLabel("Id Item: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        instanciaUnica.add(itemIdLabel, gridBagConstraints);

        itemIdText = new JTextField();
        itemIdText.setColumns(10);
        itemIdText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        instanciaUnica.add(itemIdText, gridBagConstraints);


        valorLabel = new JLabel("Valor: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        instanciaUnica.add(valorLabel, gridBagConstraints);

        valorText = new JTextField();
        valorText.setColumns(10);
        valorText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        instanciaUnica.add(valorText, gridBagConstraints);

        creaValoracio = new JButton("Crea Valoració");
        creaValoracio.addActionListener(e -> controladorGestioValoracions.afegirValoracio(usuariIdText.getText(), itemIdText.getText(), valorText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        instanciaUnica.add(creaValoracio, gridBagConstraints);

        esborraValoracio = new JButton("Esborra valoració");
        esborraValoracio.addActionListener(e -> controladorGestioValoracions.esborraValoracio(usuariIdText.getText(), itemIdText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        instanciaUnica.add(esborraValoracio, gridBagConstraints);

        editaValoracio = new JButton("Edita valoració");
        editaValoracio.addActionListener(e -> controladorGestioValoracions.editaValoracio(usuariIdText.getText(),itemIdText.getText(),valorText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        instanciaUnica.add(editaValoracio, gridBagConstraints);

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
        instanciaUnica.add(conjuntDeValoracionsButton, gridBagConstraints);
    }
}
