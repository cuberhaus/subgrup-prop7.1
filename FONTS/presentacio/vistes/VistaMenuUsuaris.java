package presentacio.vistes;

import presentacio.controladors.ControladorMenuUsuaris;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private static JScrollPane jScrollPane;
    private static DefaultTableModel llistaUsuarisTableModel;
    private static BorderLayout borderLayout;
    private static JPanel jpanel;
    private static JButton importarButton;

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
        nomsColumnes.add("Nom");
        nomsColumnes.add("Identificador d'Usuari");
        nomsColumnes.add("Actiu");
        llistaUsuarisTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        ArrayList<ArrayList<String>> usuaris = controladorMenuUsuaris.obteUsuaris();
        for (ArrayList<String> usuari : usuaris) {
            llistaUsuarisTableModel.addRow(usuari.toArray());
        }
        llistaUsuarisTableModel.removeRow(llistaUsuarisTableModel.getRowCount()-1);
        llistaUsuaris = new JTable(llistaUsuarisTableModel);
        jScrollPane = new JScrollPane(llistaUsuaris);
        instancia.add(jScrollPane, BorderLayout.WEST);
    }

    private static void actualitzarLlistaUsuaris() {
        llistaUsuarisTableModel.setRowCount(0);
        ArrayList<ArrayList<String>> usuaris = controladorMenuUsuaris.obteUsuaris();
        for (ArrayList<String> usuari : usuaris) {
            llistaUsuarisTableModel.addRow(usuari.toArray());
        }
        llistaUsuarisTableModel.removeRow(llistaUsuarisTableModel.getRowCount()-1);
        jScrollPane.revalidate();
    }
    private static void clearText() {
        idText.setText("");
        contrasenyaText.setText("");
        nomText.setText("");
    }
    private static void inicialitzarMenuUsuaris() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); // Afegeix padding per a que els elements no estiguin massa junts

        jpanel = new JPanel();
        jpanel.setLayout(gridBagLayout);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        importarButton = new JButton("Importar usuaris");
        jpanel.add(importarButton, gridBagConstraints);
        importarButton.addActionListener(e-> {
            JDialog pathDialog = new JDialog();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int estatJfile = jFileChooser.showOpenDialog(pathDialog);
            if (estatJfile == APPROVE_OPTION) {
                File pathConjunt = jFileChooser.getSelectedFile();
                try {
                    controladorMenuUsuaris.importarUsuaris(pathConjunt.getAbsolutePath());
                    actualitzarLlistaUsuaris();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        usuariActiuLabel = new JLabel("Usuari actiu:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpanel.add(usuariActiuLabel, gridBagConstraints);

        usuariActiuInfo = new JLabel("Sessio no iniciada");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jpanel.add(usuariActiuInfo, gridBagConstraints);

        nomLabel = new JLabel("Nom: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jpanel.add(nomLabel, gridBagConstraints);

        nomText = new JTextField();
        nomText.setColumns(10);
        nomText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jpanel.add(nomText, gridBagConstraints);

        idLabel = new JLabel("Id: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jpanel.add(idLabel, gridBagConstraints);

        idText = new JTextField();
        idText.setColumns(10);
        idText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jpanel.add(idText, gridBagConstraints);

        contrasenyaLabel = new JLabel("Contrasenya: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jpanel.add(contrasenyaLabel, gridBagConstraints);

        contrasenyaText = new JPasswordField();
        contrasenyaText.setColumns(10);
        contrasenyaText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jpanel.add(contrasenyaText, gridBagConstraints);

        afegirUsuari = new JButton("Afegir Usuari");
        afegirUsuari.addActionListener(e -> {
            try {
                int id = controladorMenuUsuaris.afegirUsuari(nomText.getText(), String.valueOf(contrasenyaText.getPassword()));
                llistaUsuarisTableModel.addRow(new String[]{nomText.getText(), String.valueOf(id), String.valueOf(true)});
                jScrollPane.revalidate();
                clearText();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jpanel.add(afegirUsuari, gridBagConstraints);

        eliminarUsuari = new JButton("Esborrar Usuari");
        eliminarUsuari.addActionListener(e -> {
            try {
                controladorMenuUsuaris.esborrarUsuari(idText.getText());
                actualitzarLlistaUsuaris();
                clearText();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jpanel.add(eliminarUsuari, gridBagConstraints);

        iniciarSessio = new JButton("Iniciar Sessió");
        iniciarSessio.addActionListener(e -> {
            try {
                if (controladorMenuUsuaris.iniciarSessio(idText.getText(), String.valueOf(contrasenyaText.getPassword()))) {
                    usuariActiuInfo.setText(idText.getText());
                    clearText();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jpanel.add(iniciarSessio, gridBagConstraints);

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
        jpanel.add(tancarSessio, gridBagConstraints);

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
        jpanel.add(exportarConjuntDades, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        esborrarConjuntButton = new JButton("Esborrar tots els usuaris");
        esborrarConjuntButton.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar tots els usuaris", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                controladorMenuUsuaris.esborrarConjuntUsuaris();
                actualitzarLlistaUsuaris();
                JOptionPane.showMessageDialog(instancia, "S'han esborrat els usuaris amb èxit");
            }
        });
        jpanel.add(esborrarConjuntButton, gridBagConstraints);

        borderLayout = new BorderLayout();
        instancia.setLayout(borderLayout);
        instancia.add(jpanel,BorderLayout.CENTER);
    }
}
