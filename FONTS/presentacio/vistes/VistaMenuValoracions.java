package presentacio.vistes;

import presentacio.controladors.ControladorMenuValoracions;

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
public class VistaMenuValoracions extends JPanel {
    private static VistaMenuValoracions instancia;
    private static ControladorMenuValoracions controladorMenuValoracions;

    private static JTextField usuariIdText;
    private static JTextField itemIdText;
    private static JTextField valorText;
    private static JTable llistaValoracions;
    private static DefaultTableModel llistaValoracionsTableModel;
    private static JScrollPane jScrollPane;

    private VistaMenuValoracions() {
    }

    public static VistaMenuValoracions obtenirInstancia() throws IOException {
        if (instancia == null) {
            instancia = new VistaMenuValoracions();
            controladorMenuValoracions = ControladorMenuValoracions.obtenirInstancia();
            inicialitzarMenuValoracions();
            inicialitzarLlistaValoracions();
        }
        return instancia;
    }

    private static void inicialitzarLlistaValoracions() {
        ArrayList<String> nomsColumnes = new ArrayList<>();
        nomsColumnes.add("Identificador d'usuari");
        nomsColumnes.add("Identificador d'ítem");
        nomsColumnes.add("Valoració");
        llistaValoracionsTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        ArrayList<ArrayList<String>> valoracions = controladorMenuValoracions.obtenirValoracions();
        for (ArrayList<String> valoracio : valoracions) {
            llistaValoracionsTableModel.addRow(valoracio.toArray());
        }
        llistaValoracions = new JTable(llistaValoracionsTableModel);
        llistaValoracions.setEnabled(false);
        UIEstil.styleTable(llistaValoracions);
        jScrollPane = new JScrollPane(llistaValoracions);
        jScrollPane.setPreferredSize(new Dimension(400, 0));
        instancia.add(jScrollPane, BorderLayout.WEST);
    }

    public static void actualitzarTaula() {
        llistaValoracionsTableModel.setRowCount(0);
        ArrayList<ArrayList<String>> valoracions = controladorMenuValoracions.obtenirValoracions();
        for (ArrayList<String> valoracio : valoracions) {
            llistaValoracionsTableModel.addRow(valoracio.toArray());
        }
        jScrollPane.revalidate();
    }

    private static void clearText() {
        usuariIdText.setText("");
        itemIdText.setText("");
        valorText.setText("");
    }

    private static void inicialitzarMenuValoracions() {
        instancia.setLayout(new BorderLayout(UIEstil.PADDING, 0));
        instancia.setBorder(UIEstil.panelBorder());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(UIEstil.panelBorder());

        formPanel.add(UIEstil.createHint("Puntua ítems perquè el sistema aprengui els gustos dels usuaris."));
        formPanel.add(UIEstil.verticalGapLarge());

        // Form fields
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(UIEstil.createLabel("Id Usuari:"), gbc);
        gbc.gridx = 1;
        usuariIdText = UIEstil.createTextField(15);
        fieldsPanel.add(usuariIdText, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        fieldsPanel.add(UIEstil.createLabel("Id Item:"), gbc);
        gbc.gridx = 1;
        itemIdText = UIEstil.createTextField(15);
        fieldsPanel.add(itemIdText, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        fieldsPanel.add(UIEstil.createLabel("Valor:"), gbc);
        gbc.gridx = 1;
        valorText = UIEstil.createTextField(15);
        fieldsPanel.add(valorText, gbc);

        fieldsPanel.setMaximumSize(new Dimension(400, 130));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(fieldsPanel);
        formPanel.add(UIEstil.verticalGapLarge());

        // Buttons
        JPanel buttonsPanel = UIEstil.createButtonColumn(UIEstil.PADDING_SMALL);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton creaBtn = UIEstil.createAccentButton("Afegir valoració");
        creaBtn.addActionListener(e -> {
            try {
                controladorMenuValoracions.afegirValoracio(usuariIdText.getText(), itemIdText.getText(), valorText.getText());
                actualitzarTaula();
                clearText();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        buttonsPanel.add(creaBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton editaBtn = UIEstil.createButton("Editar valoració");
        editaBtn.addActionListener(e -> {
            try {
                if (controladorMenuValoracions.existeixTipusItemSeleccionat()) {
                    controladorMenuValoracions.editarValoracio(usuariIdText.getText(), itemIdText.getText(), valorText.getText());
                    actualitzarTaula();
                    clearText();
                } else {
                    JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        buttonsPanel.add(editaBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton esborraBtn = UIEstil.createDangerButton("Esborrar valoració");
        esborraBtn.addActionListener(e -> {
            try {
                controladorMenuValoracions.esborrarValoracio(usuariIdText.getText(), itemIdText.getText());
                actualitzarTaula();
                clearText();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        buttonsPanel.add(esborraBtn);
        buttonsPanel.add(UIEstil.verticalGapLarge());

        JButton importaBtn = UIEstil.createButton("Importar valoracions");
        importaBtn.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int estat = jFileChooser.showOpenDialog(instancia);
            if (estat == APPROVE_OPTION) {
                try {
                    controladorMenuValoracions.carregarConjuntValoracions(jFileChooser.getSelectedFile().getAbsolutePath());
                    actualitzarTaula();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(instancia, ex.getMessage());
                }
            }
        });
        buttonsPanel.add(importaBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton exportaBtn = UIEstil.createButton("Exportar valoracions");
        exportaBtn.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int estat = jFileChooser.showOpenDialog(instancia);
            if (estat == APPROVE_OPTION) {
                try {
                    controladorMenuValoracions.exportaValoracions(jFileChooser.getSelectedFile().getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(instancia, ex.getMessage());
                }
            }
        });
        buttonsPanel.add(exportaBtn);
        buttonsPanel.add(UIEstil.verticalGap());

        JButton esborrarTotsBtn = UIEstil.createDangerButton("Esborrar totes les valoracions");
        esborrarTotsBtn.addActionListener(e -> {
            try {
                if (controladorMenuValoracions.existeixTipusItemSeleccionat()) {
                    int resposta = JOptionPane.showConfirmDialog(instancia,
                            "Segur que vols esborrar totes les valoracions",
                            "Selecciona una opció", JOptionPane.YES_NO_OPTION);
                    if (resposta == 0) {
                        controladorMenuValoracions.esborrarTotesLesValoracions();
                        actualitzarTaula();
                        JOptionPane.showMessageDialog(instancia, "S'han esborrat les valoracions amb èxit");
                    }
                } else {
                    JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
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
