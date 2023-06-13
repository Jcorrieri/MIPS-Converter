package converter.gui.panels;

import converter.Conversions;
import converter.gui.CustomJFrame;
import converter.gui.CustomJButton;
import converter.gui.CustomJPanel;
import converter.gui.SwapButton;

import javax.swing.*;
import java.awt.*;

public class OtherPanel extends CustomJPanel {

    private final JTextField decBinInputField, decBinOutputField, decHexInputField;
    private final JTextField decHexOutputField, binHexInputField, binHexOutputField;
    private final SwapButton swapDecBinBtn, swapDecHexBtn, swapBinHexBtn;
    private final JLabel swapIcon1, swapIcon2, swapIcon3;
    private final JLabel decBinLeft, decBinRight, decHexLeft, decHexRight, binHexLeft, binHexRight;
    private final String swapIconLiteSrc = "/resources/images/swap-icon-light.png";
    private final String swapIconDarkSrc = "/resources/images/swap-icon-dark.png";

    public OtherPanel() {
        super();
        setName(CustomJPanel.OTHER);

        JLabel toLabel, title;
        layout.gridwidth = 3;
        layout.gridy = 1; // Title

        layout.gridx = 0;
        title = new JLabel("Other Conversions");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        add(title, layout);

        layout.insets = new Insets(35, 0, 0, 0);
        layout.gridwidth = 1;

        layout.gridy = 2; // Section 1 Labels

        layout.gridx = 0;
        decBinLeft = new JLabel("Decimal");
        add(decBinLeft, layout);

        layout.gridx = 1;
        toLabel = new JLabel("to");
        add(toLabel, layout);

        layout.gridx = 2;
        decBinRight = new JLabel("Binary");
        add(decBinRight, layout);

        layout.insets = new Insets(15, 5, 0, 5);
        layout.gridy = 3; // Section 1 Fields

        layout.gridx = 0;
        decBinInputField = new JTextField();
        decBinInputField.setColumns(18);
        add(decBinInputField, layout);

        layout.gridx = 1;
        swapIcon1 = new JLabel();
        swapIcon1.setIcon( new ImageIcon(CustomJFrame.createCustomImg(swapIconLiteSrc, 32, 32) ) );
        add(swapIcon1, layout);

        layout.gridx = 2;
        decBinOutputField = new JTextField();
        decBinOutputField.setColumns(18);
        decBinOutputField.setEditable(false);
        add(decBinOutputField, layout);

        layout.gridy = 4; // Section 1 Buttons

        layout.gridx = 0;
        swapDecBinBtn = new SwapButton("Swap", e -> swapDecBin());
        add(swapDecBinBtn, layout);

        layout.gridx = 1;
        CustomJButton clearDecBinBtn = new CustomJButton(
                "Clear", e -> clearFields(decBinInputField, decBinOutputField)
        );
        add(clearDecBinBtn, layout);

        layout.gridx = 2;
        CustomJButton convertDecBinBtn = new CustomJButton("Convert", e -> convertDecBin());
        add(convertDecBinBtn, layout);

        layout.insets = new Insets(35, 0, 0, 0);
        layout.gridy = 5; // Section 2 Labels

        layout.gridx = 0;
        decHexLeft = new JLabel("Decimal");
        add(decHexLeft, layout);

        layout.gridx = 1;
        toLabel = new JLabel("to");
        add(toLabel, layout);

        layout.gridx = 2;
        decHexRight = new JLabel("Hex");
        add(decHexRight, layout);

        layout.insets = new Insets(15, 5, 0, 5);
        layout.gridy = 6; // Section 2 Fields

        layout.gridx = 0;
        decHexInputField = new JTextField();
        decHexInputField.setColumns(18);
        add(decHexInputField, layout);

        layout.gridx = 1;
        swapIcon2 = new JLabel();
        swapIcon2.setIcon( new ImageIcon(CustomJFrame.createCustomImg(swapIconLiteSrc, 32, 32) ) );
        add(swapIcon2, layout);

        layout.gridx = 2;
        decHexOutputField = new JTextField();
        decHexOutputField.setColumns(18);
        decHexOutputField.setEditable(false);
        add(decHexOutputField, layout);

        layout.gridy = 7; // Section 2 Buttons

        layout.gridx = 0;
        swapDecHexBtn = new SwapButton("Swap", e -> swapDecHex());
        add(swapDecHexBtn, layout);

        layout.gridx = 1;
        CustomJButton clearDecHexBtn = new CustomJButton(
                "Clear", e -> clearFields(decHexInputField, decHexOutputField)
        );
        add(clearDecHexBtn, layout);

        layout.gridx = 2;
        CustomJButton convertDecHexBtn = new CustomJButton("Convert", e -> convertDecHex());
        add(convertDecHexBtn, layout);

        layout.insets = new Insets(35, 0, 0, 0);
        layout.gridy = 8; // Section 3 Labels

        layout.gridx = 0;
        binHexLeft = new JLabel("Binary");
        add(binHexLeft, layout);

        layout.gridx = 1;
        toLabel = new JLabel("to");
        add(toLabel, layout);

        layout.gridx = 2;
        binHexRight = new JLabel("Hex");
        add(binHexRight, layout);

        layout.insets = new Insets(15, 5, 0, 5);
        layout.gridy = 9; // Section 3 Fields

        layout.gridx = 0;
        binHexInputField = new JTextField();
        binHexInputField.setColumns(18);
        add(binHexInputField, layout);

        layout.gridx = 1;
        swapIcon3 = new JLabel();
        swapIcon3.setIcon( new ImageIcon(CustomJFrame.createCustomImg(swapIconLiteSrc, 32, 32) ) );
        add(swapIcon3, layout);

        layout.gridx = 2;
        binHexOutputField = new JTextField();
        binHexOutputField.setColumns(18);
        binHexOutputField.setEditable(false);
        add(binHexOutputField, layout);

        layout.gridy = 10; // Section 3 Buttons

        layout.gridx = 0;
        swapBinHexBtn = new SwapButton("Swap", e -> swapBinHex());
        add(swapBinHexBtn, layout);

        layout.gridx = 1;
        CustomJButton clearBinHexBtn = new CustomJButton(
                "Clear", e -> clearFields(binHexInputField, binHexOutputField)
        );
        add(clearBinHexBtn, layout);

        layout.gridx = 2;
        CustomJButton convertBinHexBtn = new CustomJButton("Convert", e -> convertBinHex());
        add(convertBinHexBtn, layout);
    }

    private void convertDecBin() {
        if (decBinInputField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Input cannot be blank", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (swapDecBinBtn.getConversion() == SwapButton.ALT) {
            decBinOutputField.setText( Integer.toString( Conversions.binaryToDecimal(decBinInputField.getText()) ) );
            return;
        }

        try {
            String[] data = decBinInputField.getText().split(",");

            StringBuilder integer = new StringBuilder();
            for (String datum : data)
                integer.append(datum);

            decBinOutputField.setText( Conversions.decimalToBinary( Integer.parseInt(integer.toString()) ) );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "See 'Display Info'", "Error: Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void convertDecHex() {
        if (decHexInputField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Input cannot be blank", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (swapDecHexBtn.getConversion() == SwapButton.ALT) {
            decHexOutputField.setText( Integer.toString( Conversions.hexToDecimal(decHexInputField.getText()) ) );
            return;
        }

        try {
            String[] data = decHexInputField.getText().split(",");

            StringBuilder integer = new StringBuilder();
            for (String datum : data)
                integer.append(datum);

            decHexOutputField.setText( Conversions.decimalToHex( Integer.parseInt(integer.toString()) ) );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "See 'Display Info'", "Error: Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void convertBinHex() {
        if (binHexInputField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Input cannot be blank", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (swapBinHexBtn.getConversion() == SwapButton.ALT) {
            binHexOutputField.setText( Conversions.hexToBinary(binHexInputField.getText()) );
            return;
        }
        binHexOutputField.setText( Conversions.binaryToHex(binHexInputField.getText()) );
    }

    public void updateIcons() {
        if (swapIcon1 == null || swapIcon2 == null || swapIcon3 == null)
            return;

        if (getBackground() == CustomJFrame.LIGHT_BACKGROUND) {
            swapIcon1.setIcon(new ImageIcon( CustomJFrame.createCustomImg(swapIconLiteSrc, 32, 32) ));
            swapIcon2.setIcon(new ImageIcon( CustomJFrame.createCustomImg(swapIconLiteSrc, 32, 32) ));
            swapIcon3.setIcon(new ImageIcon( CustomJFrame.createCustomImg(swapIconLiteSrc, 32, 32) ));
        } else {
            swapIcon1.setIcon(new ImageIcon( CustomJFrame.createCustomImg(swapIconDarkSrc, 32, 32) ));
            swapIcon2.setIcon(new ImageIcon( CustomJFrame.createCustomImg(swapIconDarkSrc, 32, 32) ));
            swapIcon3.setIcon(new ImageIcon( CustomJFrame.createCustomImg(swapIconDarkSrc, 32, 32) ));
        }
    }

    private void swapDecBin() {
        swapDecBinBtn.swap();

        if (swapDecBinBtn.getConversion() == SwapButton.ALT) {
            decBinLeft.setText("Binary");
            decBinRight.setText("Decimal");
        } else {
            decBinLeft.setText("Decimal");
            decBinRight.setText("Binary");
        }

        String temp = decBinInputField.getText();
        decBinInputField.setText( decBinOutputField.getText() );
        decBinOutputField.setText(temp);
    }

    private void swapDecHex() {
        swapDecHexBtn.swap();

        if (swapDecHexBtn.getConversion() == SwapButton.ALT) {
            decHexLeft.setText("Hex");
            decHexRight.setText("Decimal");
        } else {
            decHexLeft.setText("Decimal");
            decHexRight.setText("Hex");
        }

        String temp = decHexInputField.getText();
        decHexInputField.setText( decHexOutputField.getText() );
        decHexOutputField.setText(temp);
    }

    private void swapBinHex() {
        swapBinHexBtn.swap();

        if (swapBinHexBtn.getConversion() == SwapButton.ALT) {
            binHexLeft.setText("Hex");
            binHexRight.setText("Binary");
        } else {
            binHexLeft.setText("Binary");
            binHexRight.setText("Hex");
        }

        String temp = binHexInputField.getText();
        binHexInputField.setText(binHexOutputField.getText());
        binHexOutputField.setText(temp);
    }
}
