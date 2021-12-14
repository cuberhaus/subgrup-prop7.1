package presentacio.vistes;

import presentacio.controladors.ControladorMenuPrincipal;
import presentacio.controladors.ControladorPresentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author maria.prat
 */
public class MenuPrincipal extends JFrame {

    private final ControladorMenuPrincipal controladorMenuPrincipal;
    private JMenuBar menuBarra;
    private JTabbedPane menuPestanyes;

    public MenuPrincipal() {
        this.inicialitzarMenuPrincipal();
        controladorMenuPrincipal = ControladorMenuPrincipal.obtenirInstancia();
    }

    private void inicialitzarMenuPrincipal() {
        setTitle("Menu Principal");

        JPanel panellPrincipal = new JPanel(new BorderLayout());

        inicialitzarMenuBarra();
        inicialitzarMenuPestanyes();

        panellPrincipal.add(menuBarra, BorderLayout.NORTH);
        panellPrincipal.add(menuPestanyes, BorderLayout.CENTER);

        add(panellPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(Pantalla.centreHoritzontal(3 * Pantalla.amplada / 4),
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
        menuPestanyes.add("Tipus d'ítem", new MenuTipusItem());
        menuPestanyes.add("Ítems", new JPanel());
        GestioUsuari gestioUsuari = new GestioUsuari();
        menuPestanyes.add("Usuaris", gestioUsuari);
        // idea alternativa, fer que les vistes siguin un singleton també per assegurar-nos que son la mateixa instancia
        GestioValoracions gestioValoracions = new GestioValoracions();
        menuPestanyes.add("Valoracions", gestioValoracions);
        ConjuntDades conjuntDades = new ConjuntDades();
        menuPestanyes.add("Conjunt de dades", conjuntDades);
        menuPestanyes.add("Recomanacions", new JPanel());
    }
}
