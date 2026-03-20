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
        instancia.setTitle("Sistema de Recomanació");
        instancia.setResizable(true);

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
        instancia.setMinimumSize(new Dimension(900, 600));
    }

    private static void inicialitzarMenuBarra() {
        menuBarra = new JMenuBar();
        menuBarra.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        JMenu informacio = new JMenu("Sobre el recomanador");
        informacio.setFont(UIEstil.FONT_LABEL);

        JMenuItem autorsItem = new JMenuItem(new AbstractAction("Autors") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new VistaDialegAutors().setVisible(true);
            }
        });
        autorsItem.setFont(UIEstil.FONT_LABEL);
        informacio.add(autorsItem);

        JMenuItem manualItem = new JMenuItem(new AbstractAction("Manual d'usuari") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    controladorMenuPrincipal.obrirManual();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(menuBarra, "No s'ha pogut obrir el manual d'usuari.");
                }
            }
        });
        manualItem.setFont(UIEstil.FONT_LABEL);
        informacio.add(manualItem);

        menuBarra.add(informacio);
        menuBarra.add(Box.createHorizontalGlue());
    }

    private static void inicialitzarMenuPestanyes() throws Exception {
        menuPestanyes = new JTabbedPane();
        menuPestanyes.setFont(UIEstil.FONT_BUTTON);
        menuPestanyes.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        menuPestanyes.add("  Tipus d'ítem  ", VistaMenuTipusItem.obtenirInstancia());
        menuPestanyes.add("  Items  ", VistaMenuItems.obtenirInstancia());
        menuPestanyes.add("  Usuaris  ", VistaMenuUsuaris.obtenirInstancia());
        menuPestanyes.add("  Valoracions  ", VistaMenuValoracions.obtenirInstancia());
        menuPestanyes.add("  Recomanacions  ", VistaMenuRecomanacions.obtenirInstancia());
    }
}
