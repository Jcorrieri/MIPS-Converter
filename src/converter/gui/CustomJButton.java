package converter.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomJButton extends JButton {

    /**
     * Creates a default button with text only
     * @param text text to display
     * @param actionListener assign an action listener
     */
    public CustomJButton(String text, ActionListener actionListener) {
        super(text);
        addActionListener(actionListener);
    }

    /**
     * TitleBar menu button with icon and text
     * @param imageIcon imageIcon to display
     * @param text text to display
     * @param a assign an action listener
     * @param m assign a CustomMouseListener
     */
    public CustomJButton(ImageIcon imageIcon, String text, ActionListener a, CustomMouseListener m) {
        super(imageIcon);
        setBackground(Color.BLACK);
        setBorder(null);
        setForeground(Color.WHITE);
        setText(text);
        addActionListener(a);
        addMouseListener(m);
    }

    /**
     * Converter panel buttons
     * @param imageIcon imageIcon to display
     * @param bg the background color
     * @param a assign an action listener
     */
    public CustomJButton(ImageIcon imageIcon, Color bg, ActionListener a) {
        super(imageIcon);
        setBackground(bg);
        setForeground( (bg == CustomJFrame.LIGHT_BACKGROUND) ? Color.BLACK : Color.WHITE );
        setFocusPainted(false);
        addActionListener(a);
    }

    /**
     * Removes border and restores default insets (0, 0, 0, 0)
     */
    public void reset() {
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(null);
    }
}
