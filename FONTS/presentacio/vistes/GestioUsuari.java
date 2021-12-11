package presentacio.vistes;

import domini.controladors.ControladorDomini;
import presentacio.controladors.ControladorGestioUsuari;
import presentacio.controladors.ControladorPresentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author pol.casacuberta
 */

public class GestioUsuari extends JPanel {

    private JButton afegirUsuari;
    private JButton eliminarUsuari;
    private JButton iniciarSessio;
    private JButton tancarSessio;
    private JLabel idLabel;
    private JTextField idText;
    private JLabel contrasenyaLabel;
    private JPasswordField contrasenyaText;
    private JLabel nomLabel;
    private JTextField nomText;

    ControladorGestioUsuari controladorGestioUsuari = new ControladorGestioUsuari();

    public GestioUsuari() {
        this.inicialitzarGestioUsuari();
    }

    public void inicialitzarGestioUsuari() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setLayout(gridBagLayout);

        afegirUsuari = new JButton("Afegir Usuari");
        afegirUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica d'afegir Usuari
            }
        });
        gridBagConstraints.insets = new Insets(10,10,10,10); // Afegeix padding per a que els elements no estiguin massa junts
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(afegirUsuari,gridBagConstraints);

        eliminarUsuari = new JButton("Eliminar Usuari");
        eliminarUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica d'eliminar Usuari
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(eliminarUsuari,gridBagConstraints);

        iniciarSessio = new JButton("Iniciar Sessió");
        iniciarSessio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorGestioUsuari.iniciarSessio(Integer.parseInt(idText.getText()), String.valueOf(contrasenyaText.getPassword()));
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(iniciarSessio,gridBagConstraints);

        tancarSessio = new JButton("Tancar Sessió");
        tancarSessio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica de tancar sessio
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        this.add(tancarSessio,gridBagConstraints);

        idLabel = new JLabel("Id: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        this.add(idLabel,gridBagConstraints);

        idText = new JTextField();
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

        contrasenyaLabel = new JLabel("Contrasenya: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        this.add(contrasenyaLabel,gridBagConstraints);

        contrasenyaText = new JPasswordField();
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

        nomLabel = new JLabel("Nom: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        this.add(nomLabel,gridBagConstraints);

        nomText = new JTextField();
        nomText.setColumns(10);
        nomText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pitjar enter fa una acció
            }
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        this.add(nomText,gridBagConstraints);
    }
}
