package presentacio.vistes;

import excepcions.NomInternIncorrecteException;
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

    private static JButton afegirUsuariButton;
    private static JButton eliminarUsuariButton;
    private static JButton iniciarSessioButton;
    private static JButton tancarSessioButton;
    private static JLabel idLabel;
    private static JTextField idText;
    private static JLabel contrasenyaLabel;
    private static JPasswordField passwordField;
    private static JLabel nomLabel;
    private static JTextField nomText;
    private static GridBagLayout gridBagLayout;
    private static GridBagConstraints gridBagConstraints;
    private static JLabel usuariActiuLabel;
    private static JLabel usuariActiuInfo;
    private static ControladorMenuUsuaris controladorMenuUsuaris;
    private static JFileChooser jFileChooser;
    private static JButton exportarConjuntUsuarisButton;
    private static JButton esborrarConjuntButton;
    private static JTable llistaUsuaris;
    private static JScrollPane jScrollPane;
    private static DefaultTableModel llistaUsuarisTableModel;
    private static BorderLayout borderLayout;
    private static JPanel jpanel;
    private static JButton importarButton;
    private static JButton editarUsuariButton;

    /**
     * Constructora per defecte de VistaMenuUsuaris
     */
    private VistaMenuUsuaris() {
    }

    /**
     * Constructora de VistaMenuUsuaris
     * Crea una instància única de VistaMenuUsuaris
     *
     * @return <code> ControladorMenuUsuaris</code>
     * @throws IOException No s'ha pogut obtenir la instància de VistaMenuUsuaris
     * @throws NomInternIncorrecteException el fitxer intern no existeix
     */
    public static VistaMenuUsuaris obtenirInstancia() throws IOException, NomInternIncorrecteException {
        if (instancia == null) {
            instancia = new VistaMenuUsuaris();
            controladorMenuUsuaris = ControladorMenuUsuaris.obtenirInstancia();
            inicialitzarMenuUsuaris();
            inicialitzarLlistaUsuaris();
        }
        return instancia;
    }

    /**
     * Inicialitza la Llista d'usuaris
     */
    private static void inicialitzarLlistaUsuaris() {
        ArrayList<String> nomsColumnes = new ArrayList<>();
        nomsColumnes.add("Nom");
        nomsColumnes.add("Identificador d'usuari");
        nomsColumnes.add("Actiu");
        llistaUsuarisTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        ArrayList<ArrayList<String>> usuaris = controladorMenuUsuaris.obteUsuaris();
        for (ArrayList<String> usuari : usuaris) {
            llistaUsuarisTableModel.addRow(usuari.toArray());
        }
        llistaUsuarisTableModel.removeRow(llistaUsuarisTableModel.getRowCount()-1);
        llistaUsuaris = new JTable(llistaUsuarisTableModel);
        llistaUsuaris.setEnabled(false);
        jScrollPane = new JScrollPane(llistaUsuaris);
        instancia.add(jScrollPane, BorderLayout.WEST);
    }

    /**
     * Sobreescriu la taula d'usuaris amb el conjunt d'usuaris del domini
     */
    public static void actualitzarTaula() {
        llistaUsuarisTableModel.setRowCount(0);
        ArrayList<ArrayList<String>> usuaris = controladorMenuUsuaris.obteUsuaris();
        for (ArrayList<String> usuari : usuaris) {
            llistaUsuarisTableModel.addRow(usuari.toArray());
        }
        llistaUsuarisTableModel.removeRow(llistaUsuarisTableModel.getRowCount()-1);
        jScrollPane.revalidate();
    }

    /**
     * Esborra el text de tots els camps de text
     */
    private static void clearText() {
        idText.setText("");
        passwordField.setText("");
        nomText.setText("");
    }

    /**
     * Inicialitza objectes del menu Usuaris
     */
    private static void inicialitzarMenuUsuaris() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); // Afegeix padding perquè els elements no estiguin massa junts
        jpanel = new JPanel();
        jpanel.setLayout(gridBagLayout);
        jFileChooser = new JFileChooser();

        // Inicialitza tots els elements
        editarUsuariButton();
        importarButton();
        usuariActiuLabel();
        usuariActiuInfoLabel();
        nomLabel();
        nomText();
        idLabel();
        idText();
        contrasenyaLabel();
        passwordField();
        afegirUsuariButton();
        eliminarUsuariButton();
        iniciarSessioButton();
        tancarSessioButton();
        exportarConjuntUsuarisButton();
        esborrarConjuntButton();

        borderLayout = new BorderLayout();
        instancia.setLayout(borderLayout);
        instancia.add(jpanel,BorderLayout.CENTER);
    }

    private static void usuariActiuLabel() {
        usuariActiuLabel = new JLabel("Usuari actiu:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpanel.add(usuariActiuLabel, gridBagConstraints);
    }

    private static void usuariActiuInfoLabel() {
        usuariActiuInfo = new JLabel("Sessio no iniciada");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jpanel.add(usuariActiuInfo, gridBagConstraints);
    }

    private static void nomLabel() {
        nomLabel = new JLabel("Nom: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jpanel.add(nomLabel, gridBagConstraints);
    }

    private static void nomText() {
        nomText = new JTextField();
        nomText.setColumns(10);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jpanel.add(nomText, gridBagConstraints);
    }

    private static void contrasenyaLabel() {
        contrasenyaLabel = new JLabel("Contrasenya: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jpanel.add(contrasenyaLabel, gridBagConstraints);
    }

    private static void passwordField() {
        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jpanel.add(passwordField, gridBagConstraints);
    }

    private static void idLabel() {
        idLabel = new JLabel("Id: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jpanel.add(idLabel, gridBagConstraints);
    }

    private static void idText() {
        idText = new JTextField();
        idText.setColumns(10);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jpanel.add(idText, gridBagConstraints);
    }

    private static void afegirUsuariButton() {
        afegirUsuariButton = new JButton("Afegir usuari");
        afegirUsuariButton.addActionListener(e -> {
            try {
                int id = controladorMenuUsuaris.afegirUsuari(nomText.getText(), String.valueOf(passwordField.getPassword()));
                actualitzarTaula();
                jScrollPane.revalidate();
                clearText();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jpanel.add(afegirUsuariButton, gridBagConstraints);
    }

    private static void eliminarUsuariButton() {
        eliminarUsuariButton = new JButton("Esborrar usuari");
        eliminarUsuariButton.addActionListener(e -> {
            try {
                controladorMenuUsuaris.esborrarUsuari(idText.getText());
                actualitzarTaula();
                clearText();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jpanel.add(eliminarUsuariButton, gridBagConstraints);
    }

    private static void editarUsuariButton() {
        editarUsuariButton = new JButton("Editar usuari");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jpanel.add(editarUsuariButton, gridBagConstraints);
        editarUsuariButton.addActionListener(e-> {
            try {
                controladorMenuUsuaris.canviaNomUsuari(idText.getText(),nomText.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
            try {
                controladorMenuUsuaris.canviaContrasenyaUsuari(idText.getText(), passwordField.getPassword());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
            actualitzarTaula();
            clearText();
        });
    }

    private static void iniciarSessioButton() {
        iniciarSessioButton = new JButton("Iniciar sessió");
        iniciarSessioButton.addActionListener(e -> {
            try {
                if (controladorMenuUsuaris.iniciarSessio(idText.getText(), String.valueOf(passwordField.getPassword()))) {
                    usuariActiuInfo.setText(idText.getText());
                    clearText();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jpanel.add(iniciarSessioButton, gridBagConstraints);
    }

    private static void tancarSessioButton() {
        tancarSessioButton = new JButton("Tancar sessió");
        tancarSessioButton.addActionListener(e -> {
            try {
                controladorMenuUsuaris.tancarSessio();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
            usuariActiuInfo.setText("Sessio no iniciada");
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jpanel.add(tancarSessioButton, gridBagConstraints);
    }

    private static void importarButton() {
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        importarButton = new JButton("Importar usuaris");
        importarButton.addActionListener(e-> {
            JDialog pathDialog = new JDialog();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int estatJfile = jFileChooser.showOpenDialog(pathDialog);
            if (estatJfile == APPROVE_OPTION) {
                File pathConjunt = jFileChooser.getSelectedFile();
                try {
                    controladorMenuUsuaris.importarUsuaris(pathConjunt.getAbsolutePath());
                    actualitzarTaula();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(instancia, ex.getMessage());
                }
            }
        });
        jpanel.add(importarButton, gridBagConstraints);
    }

    private static void exportarConjuntUsuarisButton() {
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        exportarConjuntUsuarisButton = new JButton("Exportar usuaris");
        exportarConjuntUsuarisButton.addActionListener(e -> {
            JDialog pathDialog = new JDialog();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int estatJfile = jFileChooser.showOpenDialog(pathDialog);
            if (estatJfile == APPROVE_OPTION) {
                File pathConjunt = jFileChooser.getSelectedFile();
                try {
                    controladorMenuUsuaris.exportarConjuntUsuaris(pathConjunt.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(instancia, ex.getMessage());
                }
            }
        });
        jpanel.add(exportarConjuntUsuarisButton, gridBagConstraints);
    }

    private static void esborrarConjuntButton() {
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        esborrarConjuntButton = new JButton("Esborrar tots els usuaris");
        esborrarConjuntButton.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar tots els usuaris", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                controladorMenuUsuaris.esborrarConjuntUsuaris();
                actualitzarTaula();
                JOptionPane.showMessageDialog(instancia, "S'han esborrat els usuaris amb èxit");
            }
        });
        jpanel.add(esborrarConjuntButton, gridBagConstraints);
    }
}
