package presentacio.vistes;

import presentacio.controladors.ControladorMenuUsuaris;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * @author pol.casacuberta
 */

public class VistaMenuUsuaris extends JPanel {
    private static VistaMenuUsuaris instancia;

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

    private static ControladorMenuUsuaris controladorMenuUsuaris;
    private static JFileChooser jFileChooser;
    private static JButton exportarConjuntDades;
    private static JButton esborrarConjuntButton;
    private static JTable llistaUsuaris;

    private VistaMenuUsuaris() {
    }

    public static VistaMenuUsuaris obtenirInstancia() {
        if (instancia == null) {
            instancia = new VistaMenuUsuaris();
            controladorMenuUsuaris = ControladorMenuUsuaris.obtenirInstancia();
            inicialitzarMenuUsuaris();
            inicialitzarLlistaUsuaris();
        }
        return instancia;
    }

    private static void inicialitzarLlistaUsuaris() {
        ArrayList<String> nomsColumnes = new ArrayList<>();
        nomsColumnes.add("nom");
        nomsColumnes.add("userid");
        nomsColumnes.add("esActiu");
        // TODO: revisar que això està bé i que l'ordre de les columnes i els atributs és el mateix
        // TODO: potser cal un JScrollPane per la taula
        DefaultTableModel llistaValoracionsTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        llistaUsuaris = new JTable(llistaValoracionsTableModel);
        ArrayList<ArrayList<String>> usuaris = controladorMenuUsuaris.obteUsuaris();
        for (ArrayList<String> usuari : usuaris) {
            llistaValoracionsTableModel.addRow(usuari.toArray());
        }
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        instancia.add(llistaUsuaris, gridBagConstraints);
    }

    private static void actualitzarLlistaUsuaris() {
        ArrayList<String> nomsColumnes = new ArrayList<>();
        nomsColumnes.add("nom");
        nomsColumnes.add("userid");
        nomsColumnes.add("esActiu");
        // TODO: revisar que això està bé i que l'ordre de les columnes i els atributs és el mateix
        // TODO: potser cal un JScrollPane per la taula
        DefaultTableModel llistaUsuarisTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        llistaUsuaris = new JTable(llistaUsuarisTableModel);
        JTableHeader jTableHeader = llistaUsuaris.getTableHeader();
        ArrayList<ArrayList<String>> usuaris = controladorMenuUsuaris.obteUsuaris();
        for (ArrayList<String> usuari : usuaris) {
            llistaUsuarisTableModel.addRow(usuari.toArray());
        }
    }

    private static void inicialitzarMenuUsuaris() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); // Afegeix padding per a que els elements no estiguin massa junts
        instancia.setLayout(gridBagLayout);

        usuariActiuLabel = new JLabel("Usuari actiu:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        instancia.add(usuariActiuLabel, gridBagConstraints);

        usuariActiuInfo = new JLabel("Sessio no iniciada");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        instancia.add(usuariActiuInfo, gridBagConstraints);

        nomLabel = new JLabel("Nom: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        instancia.add(nomLabel, gridBagConstraints);

        nomText = new JTextField();
        nomText.setColumns(10);
        nomText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        instancia.add(nomText, gridBagConstraints);

        idLabel = new JLabel("Id: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        instancia.add(idLabel, gridBagConstraints);

        idText = new JTextField();
        idText.setColumns(10);
        idText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        instancia.add(idText, gridBagConstraints);

        contrasenyaLabel = new JLabel("Contrasenya: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        instancia.add(contrasenyaLabel, gridBagConstraints);

        contrasenyaText = new JPasswordField();
        contrasenyaText.setColumns(10);
        contrasenyaText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        instancia.add(contrasenyaText, gridBagConstraints);

        afegirUsuari = new JButton("Afegir Usuari");
        afegirUsuari.addActionListener(e -> {
            try {
                controladorMenuUsuaris.afegirUsuari(nomText.getText(), String.valueOf(contrasenyaText.getPassword()));
                actualitzarLlistaUsuaris();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        instancia.add(afegirUsuari, gridBagConstraints);

        eliminarUsuari = new JButton("Esborrar Usuari");
        eliminarUsuari.addActionListener(e -> {
            try {
                controladorMenuUsuaris.esborrarUsuari(idText.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        instancia.add(eliminarUsuari, gridBagConstraints);

        iniciarSessio = new JButton("Iniciar Sessió");
        iniciarSessio.addActionListener(e -> {
            try {
                if (controladorMenuUsuaris.iniciarSessio(idText.getText(), String.valueOf(contrasenyaText.getPassword()))) {
                    usuariActiuInfo.setText(idText.getText());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        instancia.add(iniciarSessio, gridBagConstraints);

        tancarSessio = new JButton("Tancar Sessió");
        tancarSessio.addActionListener(e -> {
            try {
                controladorMenuUsuaris.tancarSessio();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            usuariActiuInfo.setText("Sessio no iniciada");
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        instancia.add(tancarSessio, gridBagConstraints);

        jFileChooser = new JFileChooser();
        jFileChooser.addActionListener(e -> {
        });

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        exportarConjuntDades = new JButton("Exportar Usuaris");
        exportarConjuntDades.addActionListener(e -> {
            JDialog pathDialog = new JDialog();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int estatJfile = jFileChooser.showOpenDialog(pathDialog);
            if (estatJfile == APPROVE_OPTION) {
                File pathConjunt = jFileChooser.getSelectedFile();
                try {
                    controladorMenuUsuaris.exportarConjuntUsuaris(pathConjunt.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        instancia.add(exportarConjuntDades, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        esborrarConjuntButton = new JButton("Esborrar tots els usuaris");
        esborrarConjuntButton.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar tots els usuaris", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                controladorMenuUsuaris.esborrarConjuntUsuaris();
                JOptionPane.showMessageDialog(instancia, "S'han esborrat els usuaris amb èxit");
            }
        });
        instancia.add(esborrarConjuntButton, gridBagConstraints);
    }
}
