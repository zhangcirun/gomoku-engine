package gui;

import gui.constant.GuiConst;
import observer.ReportGenerator;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is the main frame of the GUI infrastructure. The structure
 * of the project is illustrated below
 *
 * |MainFrame |
 *            |Background |
 *                        |Chessboard |
 *                                    |ResultPane
 *
 * @author Cirun Zhang
 * @version Version 1.2
 */
public class MainFrame extends JFrame {
    /**
     * Child gui component
     */
    private static Background background;

    /**
     * Uses or game setting
     */
    private SettingPane settingPane;

    /**
     * Uses for saving report
     */
    private JFileChooser fileChooser;

    public MainFrame() throws Exception {
        background = new Background();
        initMainFrame();
        addMenu();
        initSettingPane();
        initFileChooser();
        this.add(background);
        this.setVisible(true);
    }

    /**
     * Initializes the main game frame
     */
    private void initMainFrame() {
        this.setTitle("Deep Gomoku");
        this.setSize(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //center the frame
        this.setLocationRelativeTo(null);
    }

    /**
     * Add menu bar and menu items to the main frame
     */
    private void addMenu() {
        //frame menu
        JMenuBar menuBar = new JMenuBar();

        //menu1
        JMenu menu1 = new JMenu("Menu");
        JMenuItem resetGame = new JMenuItem("New Game");
        JMenuItem clearTextArea = new JMenuItem("Clear Text Field");
        JMenuItem revert = new JMenuItem("Undo Last Move");

        resetGame.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                System.out.println("New Game");
                settingPane.setVisible(true);
                //resetGame();
            }
        });

        clearTextArea.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                System.out.println("Text Area Cleared");
                Background.clearTextArea();
            }
        });

        revert.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                background.revertHistory();
            }
        });

        menu1.add(resetGame);
        menu1.add(clearTextArea);
        menu1.add(revert);

        //menu3
        JMenu menu3 = new JMenu("Help");
        JMenuItem generateReport = new JMenuItem("Generate Report");

        generateReport.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                int val = fileChooser.showOpenDialog(null);
                if (val == JFileChooser.APPROVE_OPTION) {
                    Background.addMessage("Report saved in " + fileChooser.getSelectedFile().getPath());
                    new ReportGenerator().createReport(fileChooser.getSelectedFile().getPath());
                }
            }
        });

        menu3.add(generateReport);

        menuBar.add(menu1);
        menuBar.add(menu3);
        this.setJMenuBar(menuBar);
    }

    /**
     * Initializes the setting pane
     */
    private void initSettingPane() {
        settingPane = new SettingPane();
        settingPane.setLocationRelativeTo(this);
    }

    /**
     * Initializes the file chooser
     */
    private void initFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    /**
     * Reset the game
     */
    static void resetGame(){
        background.resetGame();
    }

}
