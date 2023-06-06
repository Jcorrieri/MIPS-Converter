package converter.gui;

import converter.FileHandler;
import converter.mips.Instruction;
import converter.gui.panels.MipsToBinaryPanel;
import converter.gui.panels.MipsToHexPanel;
import converter.gui.panels.OtherPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import static converter.gui.CustomJFrame.LIGHT_BACKGROUND;
import static converter.mips.Instruction.INSTRUCTIONS;

public abstract class CustomJPanel extends JPanel {

    public static final String MIPS_TO_HEX = "Mips to hex";
    public static final String MIPS_TO_BINARY = "Mips to binary";
    public static final String OTHER = "Other";

    protected GridBagConstraints layout;
    protected FileHandler fileHandler;

    public CustomJPanel() {
        super();
        setBackground(LIGHT_BACKGROUND);
        setLayout(new GridBagLayout());
        layout = new GridBagConstraints();
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void createDialogueBox(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    public void setInputData(JTextArea inputArea) {
        ArrayList<String> inputData = new ArrayList<>();

        Scanner textAreaInput = new Scanner(inputArea.getText());
        while (textAreaInput.hasNextLine())
            inputData.add(textAreaInput.nextLine());

        fileHandler.setInputData(inputData);
    }

    public void setOutputTextArea(JTextArea outputArea) {
        outputArea.setText("");

        ArrayList<String> outputData = fileHandler.getOutputData();

        for (int i = 0; i < outputData.size(); i++) {
            outputArea.append(outputData.get(i));
            if (i != outputData.size() - 1)
                outputArea.append("\n");
        }
    }

    public void clearFields() {
        if (this instanceof MipsToHexPanel p)
            p.clearFields();
        else if (this instanceof MipsToBinaryPanel p)
            p.clearFields();
        else if (this instanceof OtherPanel p)
            p.clearFields();
    }

    public void displayInfo() {
        String message;
        if (getName().equals(MIPS_TO_HEX) || getName().equals(MIPS_TO_BINARY))
            message = getSupportedMips();
        else
            message = getSupportedOther();

        createDialogueBox(message, "Display Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getSupportedMips() {

        StringBuilder supported = new StringBuilder("Supported Instructions: \n");
        for (Instruction i : INSTRUCTIONS)
            supported.append(i.name()).append(" ");

        supported.append("\n\nInput Formats:\n").append("R-Type:    inst $rd, $rs, $rt   |   inst $rs, $rt   |   " +
                "inst $rs\n");

        supported.append("I-Type:    inst $rs, $rt   |   inst $rs, i($rt)\n");
        supported.append("J-Type:    inst (decimal address)   |   inst (hexadecimal address)\n");

        supported.append("\nAccepted file types:   plaintext (.txt)");

        return supported.toString();
    }

    private String getSupportedOther() {
        return """
                Decimal Input Format:   21   |   42,006   |   2,147,483,647
                Decimal Input Range:   0 - 2,147,483,647

                Hex Input Format:   0xAE12   |   AE12   |   0xae12   |   ae12
                Hex Input Range:   0 - 7FFFFFFF (Max 8 characters)

                Binary Input Range:   0 - 1111111111111111111111111111111 (Max 31 bits)""";
    }
}
