package presentacio.vistes;

import excepcions.FormatIncorrecteException;
import excepcions.NoExisteixElementException;
import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuItems;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maria.prat
 */
public class VistaDialegEditarItem extends JDialog {

    private final ControladorMenuItems controladorMenuItems;

    public VistaDialegEditarItem(String id, Map<String, String> atributs) throws DistanciaNoCompatibleAmbValorException, NomInternIncorrecteException, IOException {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuItems = ControladorMenuItems.obtenirInstancia();
        inicialitzarDialegEditarItem(id, atributs);
    }

    private void inicialitzarDialegEditarItem(String id, Map<String, String> atributs) {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Edita un ítem");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());
        add(panellPrincipal);

        JPanel panellLlistaAtributs = new JPanel();
        panellLlistaAtributs.setLayout(new BoxLayout(panellLlistaAtributs, BoxLayout.Y_AXIS));

        JScrollPane panellScrollLlistaAtributs = new JScrollPane(panellLlistaAtributs);
        panellScrollLlistaAtributs.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellPrincipal.add(panellScrollLlistaAtributs, BorderLayout.CENTER);

        if (controladorMenuItems.obtenirNomsAtributsTipusItemSeleccionat().isEmpty()) {
            JLabel text = new JLabel("El tipus d'ítem seleccionat no té cap atribut.");
            text.setAlignmentX(Component.CENTER_ALIGNMENT);
            text.setFont(new Font("Sans", Font.BOLD, 13));
            panellLlistaAtributs.add(Box.createVerticalGlue());
            panellLlistaAtributs.add(text);
            panellLlistaAtributs.add(Box.createVerticalGlue());
        } else {
            for (Map.Entry<String, String> atribut : atributs.entrySet()) {
                JPanel panellAtribut = new JPanel(new FlowLayout());
                JLabel etiquetaNomAtribut = new JLabel(atribut.getKey());
                panellAtribut.add(etiquetaNomAtribut);
                JTextField valorAtribut = new JTextField();
                valorAtribut.setColumns(10);
                valorAtribut.setText(atribut.getValue());
                panellAtribut.add(valorAtribut);
                panellLlistaAtributs.add(panellAtribut);
            }
        }

        JPanel panellBotoGuardarItem = new JPanel(new FlowLayout());
        JButton botoGuardarItem = new JButton("Guarda ítem");
        botoGuardarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panellBotoGuardarItem.add(botoGuardarItem);
        panellPrincipal.add(panellBotoGuardarItem, BorderLayout.SOUTH);

        botoGuardarItem.addActionListener(e -> {
            Map<String, String> valorsAtributs = new HashMap<>();
            if (!controladorMenuItems.obtenirNomsAtributsTipusItemSeleccionat().isEmpty()) {
                for (Component component : panellLlistaAtributs.getComponents()) {
                    JPanel atribut = (JPanel) component;
                    String nomAtribut = ((JLabel) atribut.getComponent(0)).getText();
                    String valorTipusAtribut = ((JTextField) atribut.getComponent(1)).getText();
                    valorsAtributs.put(nomAtribut, valorTipusAtribut);
                }
            }
            try {
                controladorMenuItems.editarItem(id, valorsAtributs);
                JOptionPane.showMessageDialog(this, "S'ha editat l'ítem amb èxit");
            } catch (NoExisteixElementException ex) {
                JOptionPane.showMessageDialog(this, "No existeix cap ítem amb aquest identificador.");
            } catch (FormatIncorrecteException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "No s'ha pogut editar l'ítem.");
            }
        });
        botoGuardarItem.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}