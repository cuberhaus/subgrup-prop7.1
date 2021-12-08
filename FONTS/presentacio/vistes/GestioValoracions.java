package presentacio.vistes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * @author pol.casacuberta
 */

public class GestioValoracions extends JPanel {
    public GestioValoracions() {
        this.inicialitzarGestioValoracions();
    }
    public void inicialitzarGestioValoracions() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setLayout(gridBagLayout);


        JLabel usuariIdLabel = new JLabel("Id Usuari: ");
        gridBagConstraints.insets = new Insets(10,10,10,10); // Afegeix padding per a que
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(usuariIdLabel,gridBagConstraints);

        JTextField usuariIdText = new JTextField();
        usuariIdText.setColumns(10);
        usuariIdText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pitjar enter fa una acció
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.add(usuariIdText,gridBagConstraints);

        JLabel itemIdLabel = new JLabel("Id Item: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(itemIdLabel,gridBagConstraints);

        JTextField itemIdText = new JTextField();
        itemIdText.setColumns(10);
        itemIdText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pitjar enter fa una acció
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        this.add(itemIdText,gridBagConstraints);


        JLabel valorLabel = new JLabel("Valor: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(itemIdLabel,gridBagConstraints);

        JTextField valorText = new JTextField();
        itemIdText.setColumns(10);
        itemIdText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pitjar enter fa una acció
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        this.add(itemIdText,gridBagConstraints);

        JButton creaValoracio = new JButton("Crea Valoració");
        creaValoracio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        this.add(creaValoracio,gridBagConstraints);

        JButton esborraValoracio = new JButton("Esborra valoració");
        creaValoracio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        this.add(esborraValoracio,gridBagConstraints);

        JButton editaValoracio = new JButton("Edita valoració");
        editaValoracio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        this.add(editaValoracio,gridBagConstraints);

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
//        this.add(jFileChooser,gridBagConstraints);


        JButton conjuntDeValoracionsButton = new JButton("Carrega conjunt de valoracions");
        conjuntDeValoracionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica
                JDialog pathDialog = new JDialog();
                int returnVal = jFileChooser.showOpenDialog(pathDialog);
                if (returnVal == APPROVE_OPTION) {
                    File pathConjuntVal = jFileChooser.getSelectedFile();
                    System.out.println(pathConjuntVal.getAbsolutePath());
                }
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        this.add(conjuntDeValoracionsButton,gridBagConstraints);
    }
}
