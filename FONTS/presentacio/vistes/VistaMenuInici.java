package presentacio.vistes;

import presentacio.controladors.ControladorMenuInici;

import javax.swing.*;
import java.awt.*;

/**
 * Vista d'inici amb l'estat del sistema (Dashboard)
 * @author pol.casacuberta
 */
public class VistaMenuInici extends JPanel {
    private static VistaMenuInici instancia;
    private static ControladorMenuInici controladorMenuInici;

    private JLabel lblTipusItem;
    private JLabel lblUsuaris;
    private JLabel lblItems;
    private JLabel lblValoracions;
    private JLabel lblSessio;

    private VistaMenuInici() {
    }

    public static VistaMenuInici obtenirInstancia() throws Exception {
        if (instancia == null) {
            instancia = new VistaMenuInici();
            controladorMenuInici = ControladorMenuInici.obtenirInstancia();
            instancia.inicialitzarVista();
        }
        return instancia;
    }

    private void inicialitzarVista() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(UIEstil.panelBorder());

        add(UIEstil.createTitle("Benvingut al Sistema de Recomanació"));
        add(UIEstil.verticalGap());
        add(UIEstil.createSubtitle("Aquest és l'estat actual del teu sistema."));
        add(UIEstil.verticalGapLarge());

        JPanel panellEstadistiques = new JPanel(new GridLayout(2, 2, UIEstil.PADDING, UIEstil.PADDING));
        panellEstadistiques.setOpaque(false);
        panellEstadistiques.setMaximumSize(new Dimension(800, 300));
        panellEstadistiques.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Targeta Tipus d'Ítem
        JPanel cardTipusItem = createCard("Tipus d'ítem actual");
        lblTipusItem = UIEstil.createTitle("-");
        cardTipusItem.add(lblTipusItem);
        panellEstadistiques.add(cardTipusItem);

        // Targeta Usuaris
        JPanel cardUsuaris = createCard("Usuaris al sistema");
        lblUsuaris = UIEstil.createTitle("0");
        cardUsuaris.add(lblUsuaris);
        panellEstadistiques.add(cardUsuaris);

        // Targeta Ítems
        JPanel cardItems = createCard("Ítems carregats");
        lblItems = UIEstil.createTitle("0");
        cardItems.add(lblItems);
        panellEstadistiques.add(cardItems);

        // Targeta Valoracions
        JPanel cardValoracions = createCard("Valoracions totals");
        lblValoracions = UIEstil.createTitle("0");
        cardValoracions.add(lblValoracions);
        panellEstadistiques.add(cardValoracions);

        add(panellEstadistiques);
        add(UIEstil.verticalGapLarge());

        JPanel cardSessio = createCard("Estat de la Sessió");
        cardSessio.setMaximumSize(new Dimension(800, 100));
        lblSessio = UIEstil.createSubtitle("Sessió no iniciada");
        cardSessio.add(lblSessio);
        add(cardSessio);

        add(Box.createVerticalGlue());
        
        actualitzar();
    }

    private JPanel createCard(String titol) {
        JPanel targeta = new JPanel();
        targeta.setLayout(new BoxLayout(targeta, BoxLayout.Y_AXIS));
        targeta.setBorder(UIEstil.cardBorder());
        targeta.setBackground(UIEstil.BG_CARD);
        
        JLabel lblTitol = new JLabel(titol);
        lblTitol.setFont(UIEstil.FONT_LABEL);
        lblTitol.setForeground(UIEstil.TEXT_SECONDARY);
        lblTitol.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        targeta.add(lblTitol);
        targeta.add(Box.createVerticalStrut(10));
        return targeta;
    }

    public void actualitzar() {
        String tipus = controladorMenuInici.obtenirNomTipusItemSeleccionat();
        if (tipus == null) {
            lblTipusItem.setText("Cap seleccionat");
            lblTipusItem.setForeground(UIEstil.TEXT_SECONDARY);
        } else {
            lblTipusItem.setText(tipus);
            lblTipusItem.setForeground(UIEstil.ACCENT);
        }

        lblUsuaris.setText(String.valueOf(controladorMenuInici.obtenirNombreUsuaris()));
        lblItems.setText(String.valueOf(controladorMenuInici.obtenirNombreItems()));
        lblValoracions.setText(String.valueOf(controladorMenuInici.obtenirNombreValoracions()));

        if (controladorMenuInici.sessioIniciada()) {
            lblSessio.setText("Sessió iniciada");
            lblSessio.setForeground(UIEstil.SUCCESS);
        } else {
            lblSessio.setText("Sessió no iniciada");
            lblSessio.setForeground(UIEstil.DANGER);
        }
    }
}