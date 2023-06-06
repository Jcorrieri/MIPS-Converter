package converter.gui.panels;

import converter.Main;
import converter.gui.CustomJButton;
import converter.gui.CustomJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MipsToHexPanel extends CustomJPanel {

    protected CustomJButton convertButton, fileSelectButton, clearButton, swapButton;
    private final JTextArea outputArea, inputArea;

    private final JLabel inputBoxLabel,outputBoxLabel;

    private final String CONVERT_MIPS_TO_HEX_STR = "Convert MIPS instructions to hex";
    private final String CONVERT_HEX_TO_MIPS_STR = "Convert hex to MIPS instructions";

    public MipsToHexPanel() {
        super();
        setName(CustomJPanel.MIPS_TO_HEX);

        InnerActionListener listener = new InnerActionListener();

        layout.gridwidth = 2;
        layout.insets = new Insets(5, 0, 10, 0);

        layout.gridx = 0; layout.gridy = 1;
        JLabel title = new JLabel("MIPS To Hex");
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        this.add(title, layout);

        layout.gridx = 0; layout.gridy = 2;
        fileSelectButton = new CustomJButton("Select input file", listener);
        this.add(fileSelectButton, layout);

        layout.gridx = 0; layout.gridy = 3;
        convertButton = new CustomJButton(CONVERT_MIPS_TO_HEX_STR, listener);
        this.add(convertButton, layout);

        layout.gridwidth = 1;
        layout.insets = new Insets(5, 0, 10, 0);

        layout.gridx = 0; layout.gridy = 4;
        inputBoxLabel = new JLabel("Input (MIPS Instructions)");
        this.add(inputBoxLabel, layout);

        layout.gridx = 1; layout.gridy = 4;
        outputBoxLabel = new JLabel("Output (Hex)");
        this.add(outputBoxLabel, layout);

        layout.gridx = 0; layout.gridy = 5;
        layout.insets = new Insets(5, 0, 10, 5);
        inputArea = new JTextArea(21, 10);
        inputArea.setEditable(true);

        JScrollPane inputScrollPane = new JScrollPane(inputArea);
        this.add(inputScrollPane, layout);

        layout.gridx = 1; layout.gridy = 5;
        layout.insets = new Insets(5, 5, 10, 0);
        outputArea = new JTextArea(21, 10);
        outputArea.setEditable(false);

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        this.add(outputScrollPane, layout);

        layout.gridwidth = 2;
        layout.insets = new Insets(5, 0, 10, 0);

        layout.gridx = 0; layout.gridy = 6;
        swapButton = new CustomJButton("Swap", e -> swap());
        this.add(swapButton, layout);

        layout.gridx = 0; layout.gridy = 7;
        clearButton = new CustomJButton("Clear", e -> clearFields());
        this.add(clearButton, layout);
    }

    private void swap() {
        String newInputLabel, newOutputLabel, temp;

        if (convertButton.getText().equals(CONVERT_MIPS_TO_HEX_STR)) {
            convertButton.setText(CONVERT_HEX_TO_MIPS_STR);
            newInputLabel = "Input (Hex)";
            newOutputLabel = "Output (MIPS Instructions)";
        } else {
            convertButton.setText(CONVERT_MIPS_TO_HEX_STR);
            newInputLabel = "Input (MIPS Instructions)";
            newOutputLabel = "Output (Hex)";
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

    public void clearFields() {
        inputArea.setText("");
        outputArea.setText("");
        fileHandler.clearAllData();
    }

    private class InnerActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == fileSelectButton) {

                int option = fileHandler.getInFileChooser().showOpenDialog(MipsToHexPanel.this);

                if (option == JFileChooser.APPROVE_OPTION) {
                    String inputSrc = fileHandler.getInFileChooser().getSelectedFile().toString();
                    fileHandler.processInput(inputSrc, inputArea);
                    System.out.println("Selected Input File: " + inputSrc);
                }
            } else if (e.getSource() == convertButton) {

                setInputData(inputArea);

                if (inputArea.getText().equals("") || fileHandler.getInputData() == null) {
                    createDialogueBox("No input data", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (convertButton.getText().equals(CONVERT_MIPS_TO_HEX_STR))
                    fileHandler.setOutputData( Main.convertMipsToHex(fileHandler.getInputData()) );
                else
                    fileHandler.setOutputData( Main.convertHexToMips(fileHandler.getInputData()) );

                setOutputTextArea(outputArea);
            }
        }
    }
}
