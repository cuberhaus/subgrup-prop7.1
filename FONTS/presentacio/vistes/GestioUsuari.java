package presentacio.vistes;

import presentacio.controladors.ControladorGestioUsuari;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * @author pol.casacuberta
 */

public class GestioUsuari extends JPanel {
    private static GestioUsuari instanciaUnica;

    private static JButton afegirUsuari;
    private static JButton eliminarUsuari;
    private static JButton iniciarSessio;
    private static JButton tancarSessio;
    private static JLabel idLabel;
    private static JTextField idText;
    private static JLabel contrasenyaLabel;
    private static JPasswordField contrasenyaText;
    private static JLabel nomLabel;
    private static JTextField nomText;
    private static GridBagLayout gridBagLayout;
    private static GridBagConstraints gridBagConstraints;
    private static JLabel usuariActiuLabel;
    private static JLabel usuariActiuInfo;

    private static ControladorGestioUsuari controladorGestioUsuari;

    private GestioUsuari() {
    }

    public static GestioUsuari obtenirInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new GestioUsuari();
            controladorGestioUsuari = ControladorGestioUsuari.obtenirInstancia();
            inicialitzarGestioUsuari();
        }
        return instanciaUnica;
    }

    public static void inicialitzarGestioUsuari() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10,10,10,10); // Afegeix padding per a que els elements no estiguin massa junts
        instanciaUnica.setLayout(gridBagLayout);

        usuariActiuLabel = new JLabel("Usuari actiu:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        instanciaUnica.add(usuariActiuLabel, gridBagConstraints);

        usuariActiuInfo = new JLabel("Sessio no iniciada");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        instanciaUnica.add(usuariActiuInfo, gridBagConstraints);

        afegirUsuari = new JButton("Afegir Usuari");
        afegirUsuari.addActionListener(e -> controladorGestioUsuari.afegirUsuari(idText.getText(), String.valueOf(contrasenyaText.getPassword()), nomText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        instanciaUnica.add(afegirUsuari, gridBagConstraints);

        eliminarUsuari = new JButton("Esborrar Usuari");
        eliminarUsuari.addActionListener(e -> controladorGestioUsuari.esborrarUsuari(idText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        instanciaUnica.add(eliminarUsuari, gridBagConstraints);

        iniciarSessio = new JButton("Iniciar Sessió");
        iniciarSessio.addActionListener(e -> {
            if (controladorGestioUsuari.iniciarSessio(idText.getText(), String.valueOf(contrasenyaText.getPassword()))) {
                usuariActiuInfo.setText(idText.getText());
            }
            else {
                usuariActiuInfo.setText("Sessio no iniciada");
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        instanciaUnica.add(iniciarSessio, gridBagConstraints);

        tancarSessio = new JButton("Tancar Sessió");
        tancarSessio.addActionListener(e -> {
            controladorGestioUsuari.tancarSessio();
            usuariActiuInfo.setText("Sessio no iniciada");
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        instanciaUnica.add(tancarSessio, gridBagConstraints);

        idLabel = new JLabel("Id: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        instanciaUnica.add(idLabel, gridBagConstraints);

        idText = new JTextField();
        idText.setColumns(10);
        idText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        instanciaUnica.add(idText, gridBagConstraints);

        contrasenyaLabel = new JLabel("Contrasenya: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        instanciaUnica.add(contrasenyaLabel, gridBagConstraints);

        contrasenyaText = new JPasswordField();
        contrasenyaText.setColumns(10);
        contrasenyaText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        instanciaUnica.add(contrasenyaText, gridBagConstraints);

        nomLabel = new JLabel("Nom: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        instanciaUnica.add(nomLabel, gridBagConstraints);

        nomText = new JTextField();
        nomText.setColumns(10);
        nomText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        instanciaUnica.add(nomText, gridBagConstraints);
    }
}
