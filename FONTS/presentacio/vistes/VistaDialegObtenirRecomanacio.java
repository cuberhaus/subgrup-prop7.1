package presentacio.vistes;

import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NomInternIncorrecteException;
import presentacio.controladors.ControladorMenuItems;
import presentacio.controladors.ControladorMenuRecomanacions;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author maria.prat
 */
public class VistaDialegObtenirRecomanacio extends JDialog {

    private final ControladorMenuRecomanacions controladorMenuRecomanacions;

    public VistaDialegObtenirRecomanacio(String descripcioMetode,
                                         Map<String, Boolean> nomsAtributsFiltre) throws IOException, NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        super(null, ModalityType.APPLICATION_MODAL);
        controladorMenuRecomanacions = ControladorMenuRecomanacions.obtenirInstancia();
        inicialitzarDialegObtenirRecomanacio(descripcioMetode, nomsAtributsFiltre);
    }

    private void inicialitzarDialegObtenirRecomanacio(String descripcioMetode, Map<String, Boolean> nomsAtributsFiltre) {
        setBounds(Pantalla.centreHoritzontal( 5 * Pantalla.amplada / 8), Pantalla.centreVertical(Pantalla.altura / 2),
                5 * Pantalla.amplada / 8, Pantalla.altura / 2);
        setTitle("Obté recomanació");
        setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());
        add(panellPrincipal);

        JPanel panellRecomanacio = new JPanel();
        panellRecomanacio.setLayout(new BoxLayout(panellRecomanacio, BoxLayout.Y_AXIS));

        JScrollPane panellScrollRecomanacio = new JScrollPane(panellRecomanacio);
        panellScrollRecomanacio.setPreferredSize(new Dimension(getWidth(), 3 * getHeight() / 4));
        panellPrincipal.add(panellScrollRecomanacio, BorderLayout.CENTER);

        ArrayList<String> recomanacio = controladorMenuRecomanacions.obtenirRecomanacio(descripcioMetode, nomsAtributsFiltre);
    }
}