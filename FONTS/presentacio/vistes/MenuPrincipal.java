package presentacio.vistes;

import domini.classes.TipusItem;
import presentacio.controladors.ControladorPresentacio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author maria.prat
 */
public class MenuPrincipal extends JFrame {
    private final String kPrototipNomTipusItem = new String(new char[20]).replace('\0', '*');
    private final String kMissatgeTipusItemNoCarregat = "No s'ha escollit cap tipus d'ítem";

    private JMenuBar menuBarra;
    private JPanel menuTipusItem;
    private JTabbedPane menuPestanyes;
    private TipusItem tipusItemCarregat;

    public MenuPrincipal(String nom, ControladorPresentacio controladorPresentacio) {
        super();
        setTitle(nom);

        JPanel panellPrincipal = new JPanel(new BorderLayout());

        inicialitzarMenuBarra();
        inicialitzarMenuTipusItem(controladorPresentacio);
        inicialitzarMenuPestanyes();

        panellPrincipal.add(menuBarra, BorderLayout.NORTH);
        panellPrincipal.add(menuTipusItem, BorderLayout.CENTER);
        panellPrincipal.add(menuPestanyes, BorderLayout.SOUTH);

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

    private void inicialitzarMenuTipusItem(ControladorPresentacio controladorPresentacio) {
        menuTipusItem = new JPanel(new GridBagLayout());

        JLabel textBenvinguda = new JLabel("Quin tipus d'ítem vols que et recomani?");
        textBenvinguda.setFont(new Font("Sans", Font.PLAIN, 20));
        textBenvinguda.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TODO: en algun lloc s'han de poder esborrar els tipus d'items carregats
        // TODO: limitar la longitud dels noms (i.e. del nom dels TipusItem)
        JPanel panellTipusItemsExistents = new JPanel(new FlowLayout());
        JComboBox<TipusItem> tipusItemsComboBox = new JComboBox<>(controladorPresentacio.obtenirTipusItemsCarregats());
        tipusItemsComboBox.setPrototypeDisplayValue(new TipusItem(kPrototipNomTipusItem));
        panellTipusItemsExistents.add(tipusItemsComboBox);
        JButton carrega = new JButton("Carrega");
        JLabel nomTipusItemCarregat = new JLabel(kMissatgeTipusItemNoCarregat);
        carrega.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipusItemCarregat = (TipusItem) tipusItemsComboBox.getSelectedItem();
                if (tipusItemCarregat == null) {
                    nomTipusItemCarregat.setText(kMissatgeTipusItemNoCarregat);
                } else {
                    nomTipusItemCarregat.setText(tipusItemCarregat.obtenirNom());
                }
            }
        });
        panellTipusItemsExistents.add(carrega);
        panellTipusItemsExistents.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botoAfegeixTipusItem = new JButton("Afegeix un nou tipus d'ítem");
        botoAfegeixTipusItem.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panellTipusItemCarregat = new JPanel(new FlowLayout());
        JLabel textTipusItemCarregat = new JLabel("Tipus d'ítem seleccionat:");
        textTipusItemCarregat.setFont(new Font("Sans", Font.BOLD, 16));
        panellTipusItemCarregat.add(textTipusItemCarregat);
        nomTipusItemCarregat.setFont(new Font("Sans", Font.PLAIN, 16));
        panellTipusItemCarregat.add(nomTipusItemCarregat);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        menuTipusItem.add(textBenvinguda, c);
        c.gridx = 0;
        c.gridy = 1;
        menuTipusItem.add(panellTipusItemsExistents, c);
        c.gridx = 0;
        c.gridy = 2;
        menuTipusItem.add(botoAfegeixTipusItem, c);
        c.gridx = 0;
        c.gridy = 3;
        menuTipusItem.add(panellTipusItemCarregat, c);
    }



    private void inicialitzarMenuPestanyes() {
        menuPestanyes = new JTabbedPane();
        menuPestanyes.add("Tipus d'ítem", new JPanel());
        menuPestanyes.add("Ítems", new JPanel());
        menuPestanyes.add("Usuaris", new JPanel());
        menuPestanyes.add("Valoracions", new JPanel());
        menuPestanyes.add("Recomanacions", new JPanel());
    }
}
