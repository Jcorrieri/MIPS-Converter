package converter.gui.panels;

import converter.gui.CustomJFrame;
import converter.gui.CustomJButton;
import converter.gui.CustomJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConverterPanel extends JDialog {

    private final CustomJFrame frame;
    private final CustomJButton mipsToBinBtn;
    private final CustomJButton mipsToHexBtn;
    private final CustomJButton otherBtn;

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

        InnerActionListener listener = new InnerActionListener();
        GridBagConstraints layout = new GridBagConstraints();
        layout.insets = new Insets(0, 15, 10, 15);

        layout.gridx = 0; layout.gridy = 0;
        JLabel mipsToHexLabel = new JLabel("Mips to Hex");
        mipsToHexLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        add(mipsToHexLabel, layout);

        layout.gridx = 0; layout.gridy = 1;
        mipsToHexBtn = new CustomJButton(mipsToHexLite, this.getBackground(), listener);
        add(mipsToHexBtn, layout);

        layout.gridx = 1; layout.gridy = 0;
        JLabel mipsToBinLabel = new JLabel("Mips to Binary");
        mipsToBinLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        add(mipsToBinLabel, layout);

        layout.gridx = 1; layout.gridy = 1;
        mipsToBinBtn = new CustomJButton(mipsToBinLite, this.getBackground(), listener);
        add(mipsToBinBtn, layout);

        layout.gridx = 2; layout.gridy = 0;
        JLabel hexBinDecLabel = new JLabel("Other");
        hexBinDecLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        add(hexBinDecLabel, layout);

        layout.gridx = 2; layout.gridy = 1;
        otherBtn = new CustomJButton(otherLite, this.getBackground(), listener);
        add(otherBtn, layout);
    }

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

    private class InnerActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == mipsToBinBtn) {
                frame.updatePanel(CustomJPanel.MIPS_TO_BINARY);
            } else if (e.getSource() == otherBtn) {
                frame.updatePanel(CustomJPanel.OTHER);
            } else if (e.getSource() == mipsToHexBtn) {
                frame.updatePanel(CustomJPanel.MIPS_TO_HEX);
            }
        }
    }
}
