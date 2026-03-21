package presentacio.vistes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Utilitats d'estil compartides per totes les vistes de l'aplicació.
 * @author pol.casacuberta
 */
public class UIEstil {

    public static final Color ACCENT = new Color(88, 166, 255);
    public static final Color ACCENT_HOVER = new Color(110, 180, 255);
    public static final Color DANGER = new Color(255, 85, 85);
    public static final Color SUCCESS = new Color(80, 200, 120);
    public static final Color BG_PANEL = new Color(43, 43, 43);
    public static final Color BG_CARD = new Color(50, 50, 50);
    public static final Color TEXT_PRIMARY = new Color(220, 220, 220);
    public static final Color TEXT_SECONDARY = new Color(160, 160, 160);
    public static final Color TABLE_ALT_ROW = new Color(47, 47, 47);

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.PLAIN, 15);
    public static final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_TABLE_HEADER = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_TABLE_CELL = new Font("SansSerif", Font.PLAIN, 13);

    public static final Dimension BUTTON_SIZE = new Dimension(220, 36);
    public static final Dimension BUTTON_SIZE_SMALL = new Dimension(160, 32);
    public static final int PADDING = 16;
    public static final int PADDING_SMALL = 8;

    public static Border panelBorder() {
        return new EmptyBorder(PADDING, PADDING, PADDING, PADDING);
    }

    public static Border cardBorder() {
        return new CompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1, true),
                new EmptyBorder(PADDING, PADDING, PADDING, PADDING)
        );
    }

    public static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setPreferredSize(BUTTON_SIZE);
        btn.setMaximumSize(BUTTON_SIZE);
        btn.setMinimumSize(BUTTON_SIZE);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JButton createSmallButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setPreferredSize(BUTTON_SIZE_SMALL);
        btn.setMaximumSize(BUTTON_SIZE_SMALL);
        btn.setMinimumSize(BUTTON_SIZE_SMALL);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JButton createAccentButton(String text) {
        JButton btn = createButton(text);
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    public static JButton createDangerButton(String text) {
        JButton btn = createButton(text);
        btn.setBackground(DANGER);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    public static JLabel createTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    public static JLabel createSubtitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SUBTITLE);
        lbl.setForeground(TEXT_SECONDARY);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    public static JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static JLabel createHint(String text) {
        JLabel lbl = new JLabel("<html><i>💡 Pista: " + text + "</i></html>");
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(new Color(255, 204, 0)); // A soft yellow/orange for hints
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    public static JTextField createTextField(int columns) {
        JTextField tf = new JTextField(columns);
        tf.setFont(FONT_LABEL);
        tf.setPreferredSize(new Dimension(columns * 12, 32));
        return tf;
    }

    public static JPasswordField createPasswordField(int columns) {
        JPasswordField pf = new JPasswordField(columns);
        pf.setFont(FONT_LABEL);
        pf.setPreferredSize(new Dimension(columns * 12, 32));
        return pf;
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_TABLE_CELL);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(Color.WHITE);
        table.setFillsViewportHeight(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? BG_PANEL : TABLE_ALT_ROW);
                }
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return c;
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HEADER);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 34));
    }

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(cardBorder());
        panel.setBackground(BG_CARD);
        return panel;
    }

    public static JPanel createButtonColumn(int gap) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        panel.setOpaque(false);
        return panel;
    }

    public static Component verticalGap() {
        return Box.createRigidArea(new Dimension(0, PADDING_SMALL));
    }

    public static Component verticalGapLarge() {
        return Box.createRigidArea(new Dimension(0, PADDING));
    }
}
