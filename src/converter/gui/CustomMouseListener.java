package converter.gui;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CustomMouseListener extends MouseInputAdapter {

    @Override
    public void mouseEntered(MouseEvent e) { e.getComponent().setBackground(Color.DARK_GRAY); }

    @Override
    public void mouseExited(MouseEvent e) { e.getComponent().setBackground(Color.BLACK); }
}