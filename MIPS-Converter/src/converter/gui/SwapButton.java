package converter.gui;

import java.awt.event.ActionListener;

public class SwapButton extends CustomJButton {

    public static final int DEFAULT = 0, ALT = 1;
    private int conversion = DEFAULT;

    public SwapButton(String text, ActionListener a) {
        super(text, a);
    }

    public void swap() { conversion = (conversion == DEFAULT) ? ALT : DEFAULT; }

    public int getConversion() { return conversion; }
}
