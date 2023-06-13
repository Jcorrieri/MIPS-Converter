package converter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {

    private ArrayList<String> inputData;
    private ArrayList<String> outputData;
    private final JFileChooser inFileChooser, outFileChooser;

    public FileHandler() {
        inputData = new ArrayList<>();
        outputData = new ArrayList<>();

        inFileChooser = new JFileChooser();
        outFileChooser = new JFileChooser();

        FileNameExtensionFilter inFilter = new FileNameExtensionFilter("Plain Text Files", "txt");

        inFileChooser.setDialogTitle("Import MIPS Code");
        inFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        inFileChooser.setFileFilter(inFilter);
        inFileChooser.setAcceptAllFileFilterUsed(false);
        inFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        outFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        outFileChooser.setDialogTitle("Download 'MIPS-Converter.txt'");
        outFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
    }

    public void processInput(String fileName, JTextArea inputArea) {
        try {
            File file = new File(fileName);
            Scanner scnr = new Scanner(file);

            inputArea.setText("");

            while (scnr.hasNext()) {
                inputArea.append(scnr.nextLine());
                if (scnr.hasNext())
                    inputArea.append("\n");
            }

            scnr.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void processOutput(String src) throws IOException {
        File file = new File(src + "\\MIPS-Converter-Output.txt");
        FileWriter writer;

        int ver = 2;
        while (file.exists()) {
            file = new File(src + "\\MIPS-Converter-Output(" + ver + ").txt");
            ver++;
        }

        writer = new FileWriter(file);
        PrintWriter out = new PrintWriter(writer);

        for (String hex : outputData) {
            out.println(hex);
        }

        out.close();
        writer.close();
    }

    public void updateUI() {
        inFileChooser.updateUI();
        outFileChooser.updateUI();
    }

    public JFileChooser getInFileChooser() { return inFileChooser; }

    public JFileChooser getOutFileChooser() { return outFileChooser; }

    public void setInputData(ArrayList<String> inputData) { this.inputData = inputData; }

    public void setOutputData(ArrayList<String> outputData) { this.outputData = outputData; }

    public ArrayList<String> getInputData() { return inputData; }

    public ArrayList<String> getOutputData() { return outputData; }

    public void clearInputData() { inputData = null; }

    public void clearOutputData() { outputData = null; }

    public void clearAllData() { clearInputData(); clearOutputData(); }
}
