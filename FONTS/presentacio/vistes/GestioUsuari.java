package presentacio.vistes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestioUsuari extends JPanel {

    public GestioUsuari() {
        this.inicialitzarGestioUsuari();
    }

    public void inicialitzarGestioUsuari() {
        JFrame frame = new JFrame();
//        frame.add();
        JButton afegirUsuari = new JButton("Afegir Usuari");
        afegirUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica d'afegir Usuari
            }
        });
        this.add(afegirUsuari);

        JButton eliminarUsuari = new JButton("Eliminar Usuari");
        eliminarUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica d'eliminar Usuari
            }
        });
        this.add(eliminarUsuari);

        JButton iniciarSessio = new JButton("Iniciar Sessió");
        iniciarSessio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica d'iniciar sessio
            }
        });
        this.add(iniciarSessio);

        JButton tancarSessio = new JButton("Tancar Sessió");
        tancarSessio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: logica de tancar sessio
            }
        });
        this.add(tancarSessio);

        JLabel idLabel = new JLabel("Id: ");
        this.add(idLabel);
        JTextField idText = new JTextField();
        idText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al pitjar enter fa una acció
            }
        });
        this.add(idText);
    }
}
