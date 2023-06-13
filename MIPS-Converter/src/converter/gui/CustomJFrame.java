package converter.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import converter.FileHandler;
import converter.gui.panels.ConverterPanel;
import converter.gui.panels.MipsToBinaryPanel;
import converter.gui.panels.MipsToHexPanel;
import converter.gui.panels.OtherPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Handles root pane events and panel switching
 */
public class CustomJFrame extends JFrame {

    final Dimension SIZE = new Dimension(1190, 680);
    final String FRAME_TITLE = "MIPS Converter Project";

    public static final Color LIGHT_BACKGROUND = new Color(240, 240, 240);
    public static final Color DARK_BACKGROUND = new Color(60, 63, 65);
    public static final int LIGHT = 0, DARK = 1;

    public static final Border DARK_MENU_BORDER =
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY.brighter());
    public static final Border LIGHT_MENU_BORDER =
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY);

    private final CustomJButton infoButton, downloadButton, programButton, uiModeButton;
    public final FileHandler fileHandler;
    private final ConverterPanel converterPanel;
    private CustomJPanel currentPanel;

    private final CustomJPanel[] PANELS = {
            new MipsToHexPanel(),
            new MipsToBinaryPanel(),
            new OtherPanel()
    };

    public CustomJFrame() {
        setSize(SIZE);
        setTitle(FRAME_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setBackground(LIGHT_BACKGROUND);
        setResizable(false);

        getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.BLACK);
        getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
        getRootPane().putClientProperty("JRootPane.titleBarShowTitle", false);

        fileHandler = new FileHandler();
        converterPanel = new ConverterPanel(this);
        currentPanel = PANELS[0];
        currentPanel.setFileHandler(fileHandler);

        GridBagConstraints layout = new GridBagConstraints();
        CustomMouseListener mouseListener = new CustomMouseListener();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new GridBagLayout());
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        menuBar.setBorderPainted(false);

        layout.gridy = 0; // Menu buttons

        layout.gridx = 0;
        layout.insets = new Insets(0, 0, 0, 5);

        Image infoImg = createCustomImg("/resources/images/info.png", 22, 22);
        infoButton = new CustomJButton(
                new ImageIcon(infoImg), "Display Info  ", e -> currentPanel.displayInfo(), mouseListener
        );
        menuBar.add(infoButton, layout);

        layout.insets = new Insets(0, 5, 0, 5);

        layout.gridx = 1;
        Image selectImg = createCustomImg("/resources/images/program-select.png", 22, 22);
        programButton = new CustomJButton(
                new ImageIcon(selectImg), "Select Converter  ", e -> converterPanel.display(), mouseListener
        );
        menuBar.add(programButton, layout);

        layout.gridx = 2;
        Image downloadImg = createCustomImg("/resources/images/download.png", 22, 22);
        downloadButton = new CustomJButton(
                new ImageIcon(downloadImg), "Save Output as .txt File  ", e -> download(), mouseListener
        );
        menuBar.add(downloadButton, layout);

        layout.gridx = 3;
        Image uiModeImg = createCustomImg("/resources/images/uimode.png", 22, 22);
        uiModeButton = new CustomJButton(
                new ImageIcon(uiModeImg), "Change Theme  ", e -> updateUI(), mouseListener
        );
        menuBar.add(uiModeButton, layout);

        setJMenuBar(menuBar);
        add(currentPanel);
    }

    private void download() {
        if (fileHandler.getOutputData() == null) {
            createDialogueBox("No output data", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = fileHandler.getOutFileChooser().showSaveDialog(CustomJFrame.this);

        if (option == JFileChooser.APPROVE_OPTION) {
            String outputSrc = fileHandler.getOutFileChooser().getSelectedFile().toString();

            try {
                fileHandler.processOutput(outputSrc);
            } catch (IOException ex) {
                createDialogueBox("Error processing output", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            createDialogueBox("Output file saved successfully", "File Save Successful", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Selected Output File: " + outputSrc);
        }
    }

    private void createDialogueBox(String message, String title, int type) {
        JOptionPane.showMessageDialog(CustomJFrame.this, message, title, type);
    }

    public static Image createCustomImg(String srcFile, int width, int height) {

        try {
            BufferedImage bfInfoImg = ImageIO.read(Objects.requireNonNull(CustomJFrame.class.getResource(srcFile)));
            return bfInfoImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        } catch (IOException  e) {
            System.out.println("Error loading/creating image: " + srcFile);
            throw new RuntimeException(e);
        } catch (NullPointerException ne) {
            System.out.println("Resource File Path Error: " + srcFile);
            throw new RuntimeException(ne);
        }
    }

    public void updatePanel(String name) {
        long start = System.currentTimeMillis();

        remove(currentPanel);
        for (CustomJPanel panel : PANELS)
            if (panel.getName().equals(name)) {
                add(panel);
                currentPanel = panel;
            }

        FlatLaf.updateUI();
        resetMenuButtons();
        currentPanel.setFileHandler(fileHandler);
        converterPanel.dispose();
        System.out.println("Time to change panels: " + (System.currentTimeMillis() - start) + "ms");
    }

    // Light/Dark
    private void updateUI() {
        int type = (getBackground() == LIGHT_BACKGROUND) ? DARK : LIGHT;

        Color bg = (type == DARK) ? DARK_BACKGROUND : LIGHT_BACKGROUND;
        Border menuBorder = (type == DARK) ? DARK_MENU_BORDER : LIGHT_MENU_BORDER;

        if (type == DARK)
            FlatDarkLaf.setup();
        else
            FlatLightLaf.setup();

        FlatLaf.updateUI();
        CustomJFrame.this.getJMenuBar().setBorder(menuBorder);
        this.setBackground(bg);

        for (CustomJPanel panel : PANELS) {
            panel.setBackground(bg);
            if (panel instanceof OtherPanel p)
                p.updateIcons();
        }

        resetMenuButtons();
        converterPanel.updateUI();
        fileHandler.updateUI();
    }

    /**
     * Used to reset the TitleBar menu buttons after updating the program UI
     */
    private void resetMenuButtons() {
        infoButton.reset();
        programButton.reset();
        downloadButton.reset();
        uiModeButton.reset();
    }
}