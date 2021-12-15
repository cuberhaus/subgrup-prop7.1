package presentacio.vistes;

import presentacio.controladors.ControladorMenuPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author maria.prat
 */
public class MenuPrincipal extends JFrame {

    private static MenuPrincipal instancia;
    private ControladorMenuPrincipal controladorMenuPrincipal;
    private JMenuBar menuBarra;
    private JTabbedPane menuPestanyes;

    private MenuPrincipal() {
        controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
        inicialitzarMenuPrincipal();
    }

    public static MenuPrincipal obtenirInstancia() {
        if (instancia == null) {
            instancia = new MenuPrincipal();
        }
        return instancia;
    }

    private void inicialitzarMenuPrincipal() {
        instancia.setTitle("Menu Principal");

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

    private void inicialitzarMenuBarra() {
        menuBarra = new JMenuBar();
        JMenu informacio = new JMenu("Sobre el recomanador");

        informacio.add(new JMenuItem("Manual d'usuari"));
        // TODO(maria): obrir manual d'usuari

        informacio.add(new JMenuItem(new AbstractAction("Autors") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new DialegAutors().setVisible(true);
            }
        }));

        menuBarra.add(informacio);
        menuBarra.add(Box.createHorizontalGlue());
    }

    private void inicialitzarMenuPestanyes() {
        menuPestanyes = new JTabbedPane();
        menuPestanyes.add("Tipus d'ítem", MenuTipusItem.obtenirInstancia());
        menuPestanyes.add("Ítems", new JPanel());
        menuPestanyes.add("Usuaris", GestioUsuari.obtenirInstancia());
        menuPestanyes.add("Valoracions", GestioValoracions.obtenirInstancia());
        menuPestanyes.add("Conjunt de dades", ConjuntDades.obtenirInstancia());
        menuPestanyes.add("Recomanacions", new JPanel());
    }
}
