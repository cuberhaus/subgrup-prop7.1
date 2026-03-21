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

    private static JTextField idText;
    private static JPasswordField passwordField;
    private static JTextField nomText;
    private static JLabel usuariActiuInfo;
    private static ControladorMenuUsuaris controladorMenuUsuaris;
    private static JTable llistaUsuaris;
    private static JScrollPane jScrollPane;
    private static DefaultTableModel llistaUsuarisTableModel;

    private VistaMenuUsuaris() {
    }

    public static VistaMenuUsuaris obtenirInstancia() throws IOException, NomInternIncorrecteException {
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
        nomsColumnes.add("Identificador d'usuari");
        nomsColumnes.add("Actiu");
        llistaUsuarisTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        ArrayList<ArrayList<String>> usuaris = controladorMenuUsuaris.obteUsuaris();
        for (ArrayList<String> usuari : usuaris) {
            llistaUsuarisTableModel.addRow(usuari.toArray());
        }
        llistaUsuarisTableModel.removeRow(llistaUsuarisTableModel.getRowCount() - 1);
        llistaUsuaris = new JTable(llistaUsuarisTableModel);
        llistaUsuaris.setEnabled(false);
        UIEstil.styleTable(llistaUsuaris);
        jScrollPane = new JScrollPane(llistaUsuaris);
        jScrollPane.setPreferredSize(new Dimension(350, 0));
        instancia.add(jScrollPane, BorderLayout.WEST);
    }

    public static void actualitzarTaula() {
        llistaUsuarisTableModel.setRowCount(0);
        ArrayList<ArrayList<String>> usuaris = controladorMenuUsuaris.obteUsuaris();
        for (ArrayList<String> usuari : usuaris) {
            llistaUsuarisTableModel.addRow(usuari.toArray());
        }
        llistaUsuarisTableModel.removeRow(llistaUsuarisTableModel.getRowCount() - 1);
        jScrollPane.revalidate();
    }

    private static void clearText() {
        idText.setText("");
        passwordField.setText("");
        nomText.setText("");
    }

    private static void inicialitzarMenuUsuaris() {
        instancia.setLayout(new BorderLayout(UIEstil.PADDING, 0));
        instancia.setBorder(UIEstil.panelBorder());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(UIEstil.panelBorder());

        // Session status
        JPanel sessionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, UIEstil.PADDING_SMALL, 0));
        sessionPanel.setOpaque(false);
        sessionPanel.add(UIEstil.createLabel("Usuari actiu:"));
        usuariActiuInfo = new JLabel("Sessio no iniciada");
        usuariActiuInfo.setFont(new Font("SansSerif", Font.BOLD, 14));
        usuariActiuInfo.setForeground(UIEstil.ACCENT);
        sessionPanel.add(usuariActiuInfo);
        sessionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(sessionPanel);
        
        formPanel.add(UIEstil.verticalGap());
        formPanel.add(UIEstil.createHint("Si no tens cap usuari, crea'n un primer i inicia sessió."));
        formPanel.add(UIEstil.verticalGapLarge());

        // Form fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(UIEstil.createLabel("Nom:"), gbc);
        gbc.gridx = 1;
        nomText = UIEstil.createTextField(15);
        fieldsPanel.add(nomText, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(UIEstil.createLabel("Id:"), gbc);
        gbc.gridx = 1;
        idText = UIEstil.createTextField(15);
        fieldsPanel.add(idText, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        fieldsPanel.add(UIEstil.createLabel("Contrasenya:"), gbc);
        gbc.gridx = 1;
        passwordField = UIEstil.createPasswordField(15);
        fieldsPanel.add(passwordField, gbc);

        fieldsPanel.setMaximumSize(new Dimension(400, 150));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(fieldsPanel);
        formPanel.add(UIEstil.verticalGapLarge());

        // Buttons
        JPanel buttonsPanel = UIEstil.createButtonColumn(UIEstil.PADDING_SMALL);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton afegirBtn = UIEstil.createAccentButton("Afegir usuari");
        afegirBtn.addActionListener(e -> {
            try {
                controladorMenuUsuaris.afegirUsuari(nomText.getText(), String.valueOf(passwordField.getPassword()));
                actualitzarTaula();
                clearText();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        buttonsPanel.add(afegirBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton editarBtn = UIEstil.createButton("Editar usuari");
        editarBtn.addActionListener(e -> {
            try {
                controladorMenuUsuaris.canviaNomUsuari(idText.getText(), nomText.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
            try {
                controladorMenuUsuaris.canviaContrasenyaUsuari(idText.getText(), passwordField.getPassword());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
            actualitzarTaula();
            clearText();
        });
        buttonsPanel.add(editarBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton eliminarBtn = UIEstil.createDangerButton("Esborrar usuari");
        eliminarBtn.addActionListener(e -> {
            try {
                controladorMenuUsuaris.esborrarUsuari(idText.getText());
                actualitzarTaula();
                clearText();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        buttonsPanel.add(eliminarBtn);
        buttonsPanel.add(UIEstil.verticalGapLarge());

        JButton iniciarSessioBtn = UIEstil.createButton("Iniciar sessió");
        iniciarSessioBtn.addActionListener(e -> {
            try {
                if (controladorMenuUsuaris.iniciarSessio(idText.getText(), String.valueOf(passwordField.getPassword()))) {
                    usuariActiuInfo.setText(idText.getText());
                    clearText();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        buttonsPanel.add(iniciarSessioBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton tancarSessioBtn = UIEstil.createButton("Tancar sessió");
        tancarSessioBtn.addActionListener(e -> {
            try {
                controladorMenuUsuaris.tancarSessio();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
            usuariActiuInfo.setText("Sessio no iniciada");
        });
        buttonsPanel.add(tancarSessioBtn);
        buttonsPanel.add(UIEstil.verticalGapLarge());

        JButton importarBtn = UIEstil.createButton("Importar usuaris");
        importarBtn.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int estat = jFileChooser.showOpenDialog(instancia);
            if (estat == APPROVE_OPTION) {
                try {
                    controladorMenuUsuaris.importarUsuaris(jFileChooser.getSelectedFile().getAbsolutePath());
                    actualitzarTaula();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(instancia, ex.getMessage());
                }
            }
        });
        buttonsPanel.add(importarBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton exportarBtn = UIEstil.createButton("Exportar usuaris");
        exportarBtn.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int estat = jFileChooser.showOpenDialog(instancia);
            if (estat == APPROVE_OPTION) {
                try {
                    controladorMenuUsuaris.exportarConjuntUsuaris(jFileChooser.getSelectedFile().getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(instancia, ex.getMessage());
                }
            }
        });
        buttonsPanel.add(exportarBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton esborrarTotsBtn = UIEstil.createDangerButton("Esborrar tots els usuaris");
        esborrarTotsBtn.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar tots els usuaris",
                    "Selecciona una opció", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                controladorMenuUsuaris.esborrarConjuntUsuaris();
                actualitzarTaula();
                JOptionPane.showMessageDialog(instancia, "S'han esborrat els usuaris amb èxit");
            }
        });
        buttonsPanel.add(esborrarTotsBtn);

        JScrollPane buttonsScroll = new JScrollPane(buttonsPanel);
        buttonsScroll.setBorder(null);
        buttonsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        formPanel.add(buttonsScroll);
        instancia.add(formPanel, BorderLayout.CENTER);
    }
}
