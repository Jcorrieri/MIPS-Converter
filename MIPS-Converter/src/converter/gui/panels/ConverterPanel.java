package converter.gui.panels;

import converter.gui.CustomJFrame;
import converter.gui.CustomJButton;
import converter.gui.CustomJPanel;

import javax.swing.*;
import java.awt.*;

public class ConverterPanel extends JDialog {

    private final CustomJFrame frame;
    private final CustomJButton mipsToBinBtn, mipsToHexBtn, otherBtn;

    private final String mipsToBinLiteSrc = "/resources/images/mipsToBinLight.png";
    private final String mipsToBinDarkSrc = "/resources/images/mipsToBinDark.png";
    private final String mipsToHexLiteSrc = "/resources/images/mipsToHexLight.png";
    private final String mipsToHexDarkSrc = "/resources/images/mipsToHexDark.png";
    private final String otherLiteSrc = "/resources/images/otherLight.png";
    private final String otherDarkSrc = "/resources/images/otherDark.png";

    private final ImageIcon mipsToBinLite =
            new ImageIcon( CustomJFrame.createCustomImg(mipsToBinLiteSrc, 150, 150) );

    private final ImageIcon mipsToBinDark =
            new ImageIcon( CustomJFrame.createCustomImg(mipsToBinDarkSrc, 150, 150) );

    private final ImageIcon mipsToHexLite =
            new ImageIcon( CustomJFrame.createCustomImg(mipsToHexLiteSrc, 150, 150) );

    private final ImageIcon mipsToHexDark =
            new ImageIcon( CustomJFrame.createCustomImg(mipsToHexDarkSrc, 150, 150) );

    private final ImageIcon otherLite =
            new ImageIcon( CustomJFrame.createCustomImg(otherLiteSrc, 150, 150) );

    private final ImageIcon otherDark =
            new ImageIcon( CustomJFrame.createCustomImg(otherDarkSrc, 150, 150) );

    public ConverterPanel(CustomJFrame frame) {
        super(frame, "Choose Converter", true);
        setSize(595, 350);
        setResizable(false);
        setLocationRelativeTo(frame);
        setLayout(new GridBagLayout());
        setBackground(CustomJFrame.LIGHT_BACKGROUND);

        this.frame = frame;

        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(0, 15, 10, 15);

        layout.gridy = 0; // Labels

        layout.gridx = 0;
        JLabel mipsToHexLabel = new JLabel("Mips to Hex");
        mipsToHexLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        add(mipsToHexLabel, layout);

        layout.gridx = 1;
        JLabel mipsToBinLabel = new JLabel("Mips to Binary");
        mipsToBinLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        add(mipsToBinLabel, layout);

        layout.gridx = 2;
        JLabel hexBinDecLabel = new JLabel("Other");
        hexBinDecLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        add(hexBinDecLabel, layout);

        layout.gridy = 1; // Buttons

        layout.gridx = 0;
        mipsToHexBtn = new CustomJButton(
                mipsToHexLite, getBackground(), e -> frame.updatePanel(CustomJPanel.MIPS_TO_HEX)
        );
        add(mipsToHexBtn, layout);

        layout.gridx = 1;
        mipsToBinBtn = new CustomJButton(
                mipsToBinLite, getBackground(), e -> frame.updatePanel(CustomJPanel.MIPS_TO_BINARY)
        );
        add(mipsToBinBtn, layout);

        layout.gridx = 2;
        otherBtn = new CustomJButton(
                otherLite, getBackground(), e -> frame.updatePanel(CustomJPanel.OTHER)
        );
        add(otherBtn, layout);
    }

    // Switch between light/dark UI
    public void updateUI() {
        if (frame.getBackground() == CustomJFrame.LIGHT_BACKGROUND) {
            mipsToHexBtn.setIcon(mipsToHexLite);
            mipsToHexBtn.setBackground(CustomJFrame.LIGHT_BACKGROUND);
            mipsToBinBtn.setIcon(mipsToBinLite);
            mipsToBinBtn.setBackground(CustomJFrame.LIGHT_BACKGROUND);
            otherBtn.setIcon(otherLite);
            otherBtn.setBackground(CustomJFrame.LIGHT_BACKGROUND);
        }
        else {
            mipsToHexBtn.setIcon(mipsToHexDark);
            mipsToHexBtn.setBackground(CustomJFrame.DARK_BACKGROUND.darker());
            mipsToBinBtn.setIcon(mipsToBinDark);
            mipsToBinBtn.setBackground(CustomJFrame.DARK_BACKGROUND.darker());
            otherBtn.setIcon(otherDark);
            otherBtn.setBackground(CustomJFrame.DARK_BACKGROUND.darker());
        }
    }

    public void display() {
        setLocationRelativeTo(frame);
        setVisible(true);
    }
}
