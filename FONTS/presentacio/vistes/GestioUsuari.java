package presentacio.vistes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestioUsuari extends JPanel {

    public GestioUsuari() {
        this.inicialitzarGestioUsuari();
    }

    public void inicialitzarGestioUsuari() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setLayout(gridBagLayout);


        JButton afegirUsuari = new JButton("Afegir Usuari");
        afegirUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica d'afegir Usuari
            }
        });
        gridBagConstraints.insets = new Insets(10,10,10,10); // Afegeix padding per a que
        // els elements no estiguin massa junts
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(afegirUsuari,gridBagConstraints);

        JButton eliminarUsuari = new JButton("Eliminar Usuari");
        eliminarUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica d'eliminar Usuari
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(eliminarUsuari,gridBagConstraints);

        JButton iniciarSessio = new JButton("Iniciar Sessió");
        iniciarSessio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica d'iniciar sessio
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(iniciarSessio,gridBagConstraints);

        JButton tancarSessio = new JButton("Tancar Sessió");
        tancarSessio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica de tancar sessio
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        this.add(tancarSessio,gridBagConstraints);

        JLabel idLabel = new JLabel("Id: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        this.add(idLabel,gridBagConstraints);

        JTextField idText = new JTextField();
        idText.setColumns(10);
        idText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pitjar enter fa una acció
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        this.add(idText,gridBagConstraints);

        JLabel contrasenyaLabel = new JLabel("Contrasenya: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        this.add(contrasenyaLabel,gridBagConstraints);

        JPasswordField contrasenyaText = new JPasswordField();
        contrasenyaText.setColumns(10);
        contrasenyaText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pitjar enter fa una acció
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        this.add(contrasenyaText,gridBagConstraints);

        JLabel nomLabel = new JLabel("Nom: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        this.add(nomLabel,gridBagConstraints);

        JTextField nomText = new JTextField();
        idText.setColumns(10);
        idText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pitjar enter fa una acció
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        this.add(idText,gridBagConstraints);
    }
}
