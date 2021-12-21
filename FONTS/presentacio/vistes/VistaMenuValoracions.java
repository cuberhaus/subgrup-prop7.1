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

    private static GridBagLayout gridBagLayout;
    private static GridBagConstraints gridBagConstraints;
    private static JLabel usuariIdLabel;
    private static JTextField usuariIdText;
    private static JLabel itemIdLabel;
    private static JTextField itemIdText;
    private static JLabel valorLabel;
    private static JTextField valorText;
    private static JButton creaValoracioButton;
    private static JButton esborraValoracioButton;
    private static JButton editaValoracioButton;
    private static JFileChooser jFileChooser;
    private static JButton conjuntDeValoracionsButton;
    private static JTable llistaValoracions;
    private static DefaultTableModel llistaValoracionsTableModel;
    private static JScrollPane jScrollPane;
    private static BorderLayout borderLayout;
    private static JPanel jpanel;
    private static JButton esborrarTotesLesValoracionsButton;
    private static JButton exportarConjuntValoracions;

    /**
     * Constructora per defecte de VistaMenuUsuaris
     */
    private VistaMenuValoracions() {
    }

    /**
     * Constructora de VistaMenuValoracions
     * Crea una instància única de VistaMenuValoracions
     * @return <code> ControladorMenuValoracions </code>
     * @throws IOException No s'ha pogut obtenir la instància de VistaMenuValoracions
     */
    public static VistaMenuValoracions obtenirInstancia() throws IOException {
        if (instancia == null) {
            instancia = new VistaMenuValoracions();
            controladorMenuValoracions = ControladorMenuValoracions.obtenirInstancia();
            inicialitzarMenuValoracions();
            inicialitzarLlistaValoracions();
        }
        return instancia;
    }

    /**
     * Inicialitza la llista de valoracions
     */
    private static void inicialitzarLlistaValoracions() {
        ArrayList<String> nomsColumnes = new ArrayList<>();
        nomsColumnes.add("Identificador d'Usuari");
        nomsColumnes.add("Identificador d'Item");
        nomsColumnes.add("Valoracio");
        llistaValoracionsTableModel = new DefaultTableModel(nomsColumnes.toArray(), 0);
        ArrayList<ArrayList<String>> valoracions = controladorMenuValoracions.obtenirValoracions();
        for (ArrayList<String> valoracio : valoracions) {
            llistaValoracionsTableModel.addRow(valoracio.toArray());
        }
//        llistaValoracionsTableModel.removeRow(llistaValoracionsTableModel.getRowCount()-1);
        llistaValoracions = new JTable(llistaValoracionsTableModel);
        jScrollPane = new JScrollPane(llistaValoracions);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        instancia.add(jScrollPane, BorderLayout.WEST);
    }

    /**
     * Sobreescriu la taula de valoracions amb el conjunt de valoracions del domini
     */
    private static void actualitzarLlistaUsuaris() {
        llistaValoracionsTableModel.setRowCount(0);
        ArrayList<ArrayList<String>> usuaris = controladorMenuValoracions.obtenirValoracions();
        for (ArrayList<String> usuari : usuaris) {
            llistaValoracionsTableModel.addRow(usuari.toArray());
        }
        llistaValoracionsTableModel.removeRow(llistaValoracionsTableModel.getRowCount()-1);
        jScrollPane.revalidate();
    }

    /**
     * Esborra el text de tots els camps de text
     */
    private static void clearText() {
        itemIdText.setText("");
        usuariIdText.setText("");
        valorText.setText("");
    }

    /**
     * Inicialitza objectes del menu Valoracions
     */
    private static void inicialitzarMenuValoracions() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); // Afegeix padding

        jpanel = new JPanel();
        jpanel.setLayout(gridBagLayout);

        jFileChooser = new JFileChooser();
        jFileChooser.addActionListener(e -> {
        });

        usuariIdLabel();
        usuariIdText();
        itemIdLabel();
        itemIdText();
        valorLabel();
        valorText();
        creaValoracioButton();
        esborraValoracioButton();
        editaValoracioButton();
        conjuntDeValoracionsButton();
        esborrarTotesLesValoracionsButton();
        exportarConjuntValoracions();

        borderLayout = new BorderLayout();
        instancia.setLayout(borderLayout);
        instancia.add(jpanel, BorderLayout.CENTER);
    }

    private static void exportarConjuntValoracions() {
        exportarConjuntValoracions = new JButton("Exportar valoracions");
        exportarConjuntValoracions.addActionListener(e-> {
            JDialog pathDialog = new JDialog();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int estatJfile = jFileChooser.showOpenDialog(pathDialog);
            if (estatJfile == APPROVE_OPTION) {
                File pathConjunt = jFileChooser.getSelectedFile();
                controladorMenuValoracions.exportaValoracions(pathConjunt.getAbsolutePath());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        jpanel.add(exportarConjuntValoracions, gridBagConstraints);
    }

    private static void conjuntDeValoracionsButton() {
        conjuntDeValoracionsButton = new JButton("Carrega conjunt de valoracions");
        conjuntDeValoracionsButton.addActionListener(e -> {
            // TODO: logica
            JDialog pathDialog = new JDialog();
            int estatJfile = jFileChooser.showOpenDialog(pathDialog);
            if (estatJfile == APPROVE_OPTION) {
                File pathConjuntVal = jFileChooser.getSelectedFile();
                System.out.println(pathConjuntVal.getAbsolutePath());
                try {
                    controladorMenuValoracions.carregarConjuntValoracions(pathConjuntVal.getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(instancia, ex.getMessage());
                }
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jpanel.add(conjuntDeValoracionsButton, gridBagConstraints);
    }

    private static void editaValoracioButton() {
        editaValoracioButton = new JButton("Edita valoració");
        editaValoracioButton.addActionListener(e -> {
            try {
                if (controladorMenuValoracions.existeixTipusItemSeleccionat()) {
                    if (controladorMenuValoracions.existeixValoracio(usuariIdText.getText(), itemIdText.getText())) {
                        controladorMenuValoracions.editarValoracio(usuariIdText.getText(),itemIdText.getText(),valorText.getText());
                    } else {
                        JOptionPane.showMessageDialog(instancia, "La valoració no existeix");
                    }
                } else {
                    JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jpanel.add(editaValoracioButton, gridBagConstraints);
    }

    private static void esborrarTotesLesValoracionsButton() {
        esborrarTotesLesValoracionsButton = new JButton("Esborra totes les valoracions");
        esborrarTotesLesValoracionsButton.addActionListener(e -> {
            try {
                if (controladorMenuValoracions.existeixTipusItemSeleccionat()) {
                    int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar totes les valoracions", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
                    if (resposta == 0) {
                        controladorMenuValoracions.esborrarTotesLesValoracions();
                        JOptionPane.showMessageDialog(instancia, "S'han esborrat les valoracions amb èxit");
                    }
                } else {
                    JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
                }

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jpanel.add(esborrarTotesLesValoracionsButton, gridBagConstraints);
    }

    private static void esborraValoracioButton() {
        esborraValoracioButton = new JButton("Esborra valoració");
        esborraValoracioButton.addActionListener(e -> {
            try {
                controladorMenuValoracions.esborrarValoracio(usuariIdText.getText(), itemIdText.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jpanel.add(esborraValoracioButton, gridBagConstraints);
    }

    private static void creaValoracioButton() {
        creaValoracioButton = new JButton("Crea Valoració");
        creaValoracioButton.addActionListener(e -> {
            try {
                controladorMenuValoracions.afegirValoracio(usuariIdText.getText(), itemIdText.getText(), valorText.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(instancia, ex.getMessage());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jpanel.add(creaValoracioButton, gridBagConstraints);
    }

    private static void valorText() {
        valorText = new JTextField();
        valorText.setColumns(10);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jpanel.add(valorText, gridBagConstraints);
    }

    private static void valorLabel() {
        valorLabel = new JLabel("Valor: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jpanel.add(valorLabel, gridBagConstraints);
    }

    private static void itemIdText() {
        itemIdText = new JTextField();
        itemIdText.setColumns(10);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jpanel.add(itemIdText, gridBagConstraints);
    }

    private static void itemIdLabel() {
        itemIdLabel = new JLabel("Id Item: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jpanel.add(itemIdLabel, gridBagConstraints);
    }

    private static void usuariIdText() {
        usuariIdText = new JTextField();
        usuariIdText.setColumns(10);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jpanel.add(usuariIdText, gridBagConstraints);
    }

    private static void usuariIdLabel() {
        usuariIdLabel = new JLabel("Id Usuari: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpanel.add(usuariIdLabel, gridBagConstraints);
    }
}
