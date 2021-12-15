package presentacio.vistes;

import presentacio.controladors.ControladorGestioUsuari;

import javax.swing.*;
import java.awt.*;

/**
 * @author pol.casacuberta
 */

public class GestioUsuari extends JPanel {
    private static GestioUsuari instancia;

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
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;

    private ControladorGestioUsuari controladorGestioUsuari;

    private GestioUsuari() {
        controladorGestioUsuari = ControladorGestioUsuari.obtenirInstancia();
        inicialitzarGestioUsuari();
    }

    public static GestioUsuari obtenirInstancia() {
        if (instancia == null) {
            instancia = new GestioUsuari();
        }
        return instancia;
    }

    public void inicialitzarGestioUsuari() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10,10,10,10); // Afegeix padding per a que els elements no estiguin massa junts
        instancia.setLayout(gridBagLayout);

        afegirUsuari = new JButton("Afegir Usuari");
        afegirUsuari.addActionListener(e -> controladorGestioUsuari.afegirUsuari(idText.getText(), String.valueOf(contrasenyaText.getPassword()), nomText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        instancia.add(afegirUsuari, gridBagConstraints);

        eliminarUsuari = new JButton("Esborrar Usuari");
        eliminarUsuari.addActionListener(e -> controladorGestioUsuari.esborrarUsuari(idText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        instancia.add(eliminarUsuari, gridBagConstraints);

        iniciarSessio = new JButton("Iniciar Sessió");
        iniciarSessio.addActionListener(e -> controladorGestioUsuari.iniciarSessio(idText.getText(), String.valueOf(contrasenyaText.getPassword())));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        instancia.add(iniciarSessio, gridBagConstraints);

        tancarSessio = new JButton("Tancar Sessió");
        tancarSessio.addActionListener(e -> controladorGestioUsuari.tancarSessio());
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        instancia.add(tancarSessio, gridBagConstraints);

        idLabel = new JLabel("Id: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        instancia.add(idLabel, gridBagConstraints);

        idText = new JTextField();
        idText.setColumns(10);
        idText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        instancia.add(idText, gridBagConstraints);

        contrasenyaLabel = new JLabel("Contrasenya: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        instancia.add(contrasenyaLabel, gridBagConstraints);

        contrasenyaText = new JPasswordField();
        contrasenyaText.setColumns(10);
        contrasenyaText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        instancia.add(contrasenyaText, gridBagConstraints);

        nomLabel = new JLabel("Nom: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        instancia.add(nomLabel, gridBagConstraints);

        nomText = new JTextField();
        nomText.setColumns(10);
        nomText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        instancia.add(nomText, gridBagConstraints);
    }
}
