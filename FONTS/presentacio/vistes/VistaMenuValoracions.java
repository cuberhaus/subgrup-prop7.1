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

    private static GridBagLayout gridBagLayout;
    private static GridBagConstraints gridBagConstraints;
    private static JLabel usuariIdLabel;
    private static JTextField usuariIdText;
    private static JLabel itemIdLabel;
    private static JTextField itemIdText;
    private static JLabel valorLabel;
    private static JTextField valorText;
    private static JButton creaValoracio;
    private static JButton esborraValoracio;
    private static JButton editaValoracio;
    private static JFileChooser jFileChooser;
    private static JButton conjuntDeValoracionsButton;

    private static ControladorMenuValoracions controladorMenuValoracions;
    private static JTable llistaValoracions;
    private static DefaultTableModel llistaValoracionsTableModel;
    private static JScrollPane jScrollPane;
    private static BorderLayout borderLayout;
    private static JPanel jpanel;

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

    private static void inicialitzarMenuValoracions() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); // Afegeix padding

        jpanel = new JPanel();
        jpanel.setLayout(gridBagLayout);

        usuariIdLabel = new JLabel("Id Usuari: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jpanel.add(usuariIdLabel, gridBagConstraints);

        usuariIdText = new JTextField();
        usuariIdText.setColumns(10);
        usuariIdText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jpanel.add(usuariIdText, gridBagConstraints);

        itemIdLabel = new JLabel("Id Item: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jpanel.add(itemIdLabel, gridBagConstraints);

        itemIdText = new JTextField();
        itemIdText.setColumns(10);
        itemIdText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jpanel.add(itemIdText, gridBagConstraints);


        valorLabel = new JLabel("Valor: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jpanel.add(valorLabel, gridBagConstraints);

        valorText = new JTextField();
        valorText.setColumns(10);
        valorText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jpanel.add(valorText, gridBagConstraints);

        creaValoracio = new JButton("Crea Valoració");
        creaValoracio.addActionListener(e -> {
            try {
                controladorMenuValoracions.afegirValoracio(usuariIdText.getText(), itemIdText.getText(), valorText.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jpanel.add(creaValoracio, gridBagConstraints);

        esborraValoracio = new JButton("Esborra valoració");
        esborraValoracio.addActionListener(e -> {
            try {
                controladorMenuValoracions.esborrarValoracio(usuariIdText.getText(), itemIdText.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jpanel.add(esborraValoracio, gridBagConstraints);

        editaValoracio = new JButton("Edita valoració");
        editaValoracio.addActionListener(e -> {
            //TODO implementar vistaDialegEditarValoracio
            if (!controladorMenuValoracions.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                String idItem = JOptionPane.showInputDialog(instancia,
                        "Introdueix l'identificador de l'ítem valorat que vols editar:");
                String idUsuari = JOptionPane.showInputDialog(instancia, "Introdueix l'identificador de l'usuari que ha valorat l'ítem");
                try {
                    if (controladorMenuValoracions.existeixValoracio(idUsuari, idItem)) {
                        JOptionPane.showMessageDialog(instancia, "L'identificador de la valoracio no és vàlid.");
                    } else {
    //                    VistaDialegEditarValoracio vistaDialegEditarValoracio = new VistaDialegEditarValoracio(idItem, idUsuari);
    //                    vistaDialegEditarValoracio.setVisible(true);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            try {
                controladorMenuValoracions.editarValoracio(usuariIdText.getText(), itemIdText.getText(), valorText.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jpanel.add(editaValoracio, gridBagConstraints);

        jFileChooser = new JFileChooser();
        jFileChooser.addActionListener(e -> {
        });

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
                    // TODO: catch this
                    ex.printStackTrace();
                }
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jpanel.add(conjuntDeValoracionsButton, gridBagConstraints);

        JButton esborrarTotesLesValoracions = new JButton("Esborra totes les valoracions");
        esborrarTotesLesValoracions.addActionListener(e -> {
            if (!controladorMenuValoracions.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            } else {
                int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar totes les valoracions", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
                if (resposta == 0) {
                    controladorMenuValoracions.esborrarTotesLesValoracions();
                    JOptionPane.showMessageDialog(instancia, "S'han esborrat les valoracions amb èxit");
                }
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jpanel.add(esborrarTotesLesValoracions, gridBagConstraints);

        borderLayout = new BorderLayout();
        instancia.setLayout(borderLayout);
        instancia.add(jpanel,BorderLayout.CENTER);
    }
}
