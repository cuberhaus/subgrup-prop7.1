package presentacio.vistes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    }
}
