package presentacio.vistes;

import presentacio.controladors.ControladorMenuPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Vista del menu principal
 * @author maria.prat
 */
public class VistaMenuPrincipal extends JFrame {
    private static VistaMenuPrincipal instancia;
    private static ControladorMenuPrincipal controladorMenuPrincipal;
    private static JMenuBar menuBarra;
    private static JTabbedPane menuPestanyes;

    private VistaMenuPrincipal() {
    }

    public static VistaMenuPrincipal obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new VistaMenuPrincipal();
            controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
            inicialitzarMenuPrincipal();
        }
        return instancia;
    }

    private static void inicialitzarMenuPrincipal() throws Exception {
        // TODO (maria): una vista no pot llançar excepció, ha de fer catch i revisar que cap ho faci
        instancia.setTitle("Menu Principal");
        instancia.setResizable(false);

        JPanel panellPrincipal = new JPanel(new BorderLayout());

        inicialitzarMenuBarra();
        inicialitzarMenuPestanyes();

        panellPrincipal.add(menuBarra, BorderLayout.NORTH);
        panellPrincipal.add(menuPestanyes, BorderLayout.CENTER);

        instancia.add(panellPrincipal);
        instancia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instancia.setBounds(Pantalla.centreHoritzontal(3 * Pantalla.amplada / 4),
                Pantalla.centreVertical(3 * Pantalla.altura / 4),
                3 * Pantalla.amplada / 4, 3 * Pantalla.altura / 4);
    }

    private static void inicialitzarMenuBarra() {
        menuBarra = new JMenuBar();
        JMenu informacio = new JMenu("Sobre el recomanador");

        informacio.add(new JMenuItem(new AbstractAction("Autors") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new VistaDialegAutors().setVisible(true);
            }
        }));
        informacio.add(new JMenuItem(new AbstractAction("Manual d'usuari") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    controladorMenuPrincipal.obreManual();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(menuBarra, "No s'ha pogut obrir el manual d'usuari.");
                }
            }
        }));
        menuBarra.add(informacio);
        menuBarra.add(Box.createHorizontalGlue());
    }

    private static void inicialitzarMenuPestanyes() throws Exception {
        menuPestanyes = new JTabbedPane();
        menuPestanyes.add("Tipus d'ítem", VistaMenuTipusItem.obtenirInstancia());
        menuPestanyes.add("Items", VistaMenuItems.obtenirInstancia());
        menuPestanyes.add("Usuaris", VistaMenuUsuaris.obtenirInstancia());
        menuPestanyes.add("Valoracions", VistaMenuValoracions.obtenirInstancia());
        menuPestanyes.add("Recomanacions", VistaMenuRecomanacions.obtenirInstancia());
    }
}
