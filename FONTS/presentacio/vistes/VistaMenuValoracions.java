package presentacio.vistes;

import presentacio.controladors.ControladorMenuValoracions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
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

    private VistaMenuValoracions() {
    }

    public static VistaMenuValoracions obtenirInstancia() {
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
        nomsColumnes.add("userId");
        nomsColumnes.add("itemId");
        nomsColumnes.add("ratings");
        // TODO: revisar que això està bé i que l'ordre de les columnes i els atributs és el mateix
        // TODO: potser cal un JScrollPane per la taula
        DefaultTableModel llistaValoracionsTableModel = new DefaultTableModel(nomsColumnes.toArray(), 1);
        llistaValoracions = new JTable(llistaValoracionsTableModel);
        ArrayList<ArrayList<String>> valoracions = controladorGestioValoracions.obtenirValoracions();
        for (ArrayList<String> valoracio : valoracions) {
            llistaValoracionsTableModel.addRow(valoracio.toArray());
        }
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;

        instancia.add(llistaValoracions, gridBagConstraints);
    }

    private static void inicialitzarMenuValoracions() {
        gridBagLayout = new GridBagLayout();
        gridBagConstraints = new GridBagConstraints();
        instancia.setLayout(gridBagLayout);

        usuariIdLabel = new JLabel("Id Usuari: ");
        gridBagConstraints.insets = new Insets(10, 10, 10, 10); // Afegeix padding
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        instancia.add(usuariIdLabel, gridBagConstraints);

        usuariIdText = new JTextField();
        usuariIdText.setColumns(10);
        usuariIdText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        instancia.add(usuariIdText, gridBagConstraints);

        itemIdLabel = new JLabel("Id Item: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        instancia.add(itemIdLabel, gridBagConstraints);

        itemIdText = new JTextField();
        itemIdText.setColumns(10);
        itemIdText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        instancia.add(itemIdText, gridBagConstraints);


        valorLabel = new JLabel("Valor: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        instancia.add(valorLabel, gridBagConstraints);

        valorText = new JTextField();
        valorText.setColumns(10);
        valorText.addActionListener(e -> {
            // Al pitjar enter fa una acció
        });
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        instancia.add(valorText, gridBagConstraints);

        creaValoracio = new JButton("Crea Valoració");
        creaValoracio.addActionListener(e -> controladorMenuValoracions.afegirValoracio(usuariIdText.getText(), itemIdText.getText(), valorText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        instancia.add(creaValoracio, gridBagConstraints);

        esborraValoracio = new JButton("Esborra valoració");
        esborraValoracio.addActionListener(e -> controladorMenuValoracions.esborraValoracio(usuariIdText.getText(), itemIdText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        instancia.add(esborraValoracio, gridBagConstraints);

        editaValoracio = new JButton("Edita valoració");
        editaValoracio.addActionListener(e -> controladorMenuValoracions.editaValoracio(usuariIdText.getText(),itemIdText.getText(),valorText.getText()));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        instancia.add(editaValoracio, gridBagConstraints);

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
                controladorMenuValoracions.carregaConjuntValoracions(pathConjuntVal.getAbsolutePath());
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        instancia.add(conjuntDeValoracionsButton, gridBagConstraints);

        JButton esborrarTotesLesValoracions = new JButton("Esborra totes les valoracions");
        esborrarTotesLesValoracions.addActionListener(e-> {
            if (!controladorMenuValoracions.existeixTipusItemSeleccionat()) {
                JOptionPane.showMessageDialog(instancia, "No hi ha cap tipus d'ítem seleccionat.");
            }
            else {
                int resposta = JOptionPane.showConfirmDialog(instancia, "Segur que vols esborrar totes les valoracions", "Selecciona una opció", JOptionPane.YES_NO_OPTION);
                if (resposta == 0) {
                    controladorMenuValoracions.esborrarTotesLesValoracions();
                    JOptionPane.showMessageDialog(instancia, "S'han esborrat les valoracions amb èxit");
                }
            }
        });
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        instancia.add(esborrarTotesLesValoracions, gridBagConstraints);
    }
}
