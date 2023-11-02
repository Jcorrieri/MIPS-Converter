package converter.gui.panels;

import converter.Main;
import converter.gui.CustomJButton;
import converter.gui.CustomJPanel;

import javax.swing.*;
import java.awt.*;

public class MipsToBinaryPanel extends CustomJPanel {

    protected CustomJButton convertButton, fileSelectButton, clearButton, swapButton;
    private final JTextArea outputArea, inputArea;
    private final JLabel inputBoxLabel,outputBoxLabel;

    private final String CONVERT_MIPS_TO_BIN_STR = "Convert MIPS instructions to binary";
    private final String CONVERT_BIN_TO_MIPS_STR = "Convert binary to MIPS instructions";

    public MipsToBinaryPanel() {
        super();
        setName(CustomJPanel.MIPS_TO_BINARY);

        inputArea = new JTextArea(21, 18);
        inputArea.setEditable(true);
        outputArea = new JTextArea(21, 18);
        outputArea.setEditable(false);

        layout.gridwidth = 2;
        layout.insets = new Insets(5, 0, 10, 0);

        layout.gridx = 0; layout.gridy = 1;
        JLabel title = new JLabel("MIPS To Binary");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        this.add(title, layout);

        layout.gridx = 0; layout.gridy = 2;
        fileSelectButton = new CustomJButton("Select input file", e -> selectFile(this, inputArea));
        this.add(fileSelectButton, layout);

        layout.gridx = 0; layout.gridy = 3;
        convertButton = new CustomJButton(CONVERT_MIPS_TO_BIN_STR, e -> convert());
        this.add(convertButton, layout);

        layout.gridwidth = 1;
        layout.insets = new Insets(5, 0, 10, 0);

        layout.gridx = 0; layout.gridy = 4;
        inputBoxLabel = new JLabel("Input (MIPS Instructions)");
        this.add(inputBoxLabel, layout);

        layout.gridx = 1; layout.gridy = 4;
        outputBoxLabel = new JLabel("Output (Binary)");
        this.add(outputBoxLabel, layout);

        layout.gridx = 0; layout.gridy = 5;
        layout.insets = new Insets(5, 0, 10, 5);
        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        this.add(inputScrollPane, layout);

        layout.gridx = 1; layout.gridy = 5;
        layout.insets = new Insets(5, 5, 10, 0);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        this.add(outputScrollPane, layout);

        layout.gridwidth = 2;
        layout.insets = new Insets(5, 0, 10, 0);

        layout.gridx = 0; layout.gridy = 6;
        swapButton = new CustomJButton("Swap", e -> swap());
        this.add(swapButton, layout);

        layout.gridx = 0; layout.gridy = 7;
        clearButton = new CustomJButton("Clear", e -> clearFields(inputArea, outputArea));
        this.add(clearButton, layout);
    }

    private void swap() {
        String newInputLabel, newOutputLabel, temp;

        if (convertButton.getText().equals(CONVERT_MIPS_TO_BIN_STR)) {
            convertButton.setText(CONVERT_BIN_TO_MIPS_STR);
            newInputLabel = "Input (Binary)";
            newOutputLabel = "Output (MIPS Instructions)";
        } else {
            convertButton.setText(CONVERT_MIPS_TO_BIN_STR);
            newInputLabel = "Input (MIPS Instructions)";
            newOutputLabel = "Output (Binary)";
        }

        temp = inputArea.getText();
        inputArea.setText(outputArea.getText());
        outputArea.setText(temp);

        inputBoxLabel.setText(newInputLabel);
        outputBoxLabel.setText(newOutputLabel);

        int tempCols = inputArea.getColumns();
        inputArea.setColumns(outputArea.getColumns());
        outputArea.setColumns(tempCols);
    }

    private void convert() {
        setInputData(inputArea);

        if (inputArea.getText().equals("") || fileHandler.getInputData() == null) {
            JOptionPane.showMessageDialog(this, "No input data", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (convertButton.getText().equals(CONVERT_MIPS_TO_BIN_STR))
            fileHandler.setOutputData( Main.convertMipsToBinary(fileHandler.getInputData()) );
        else
            fileHandler.setOutputData( Main.convertBinaryToMips(fileHandler.getInputData()) );

        setOutputTextArea(outputArea);
    }
}
