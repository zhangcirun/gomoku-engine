package gui;

import gui.constant.GuiConst;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is the main frame of the game. The structure
 * of the project is illustrated below
 *
 * |MainFrame |
 *            |Background |
 *                        |Chessboard |
 *                                    |ResultPane
 *
 * @author Chang ta'z jun
 * @version Version 1.1
 */
public class MainFrame extends JFrame{
    private Background background;
    private SettingPane settingPane;

    public MainFrame() throws Exception {
        this.background = new Background();
        init();
        addMenu();
        addSettingPane();
        this.add(background);
        this.setVisible(true);
    }

    private void init(){
        this.setTitle("Gomoku");
        this.setSize(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //center the frame
        this.setLocationRelativeTo(null);
    }

    /**
     * Add menu bar and menu items to the main frame
     */
    private void addMenu(){
        //frame menu
        JMenuBar menuBar = new JMenuBar();

        //menu1
        JMenu menu1 = new JMenu("Menu");
        JMenuItem resetGame = new JMenuItem("Reset Game");
        JMenuItem clearTextArea = new JMenuItem("Clear Text Field");
        JMenuItem revert = new JMenuItem("Revert");

        resetGame.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                System.out.println("Reset Game");
                background.resetGame();
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

        //menu2
        JMenu menu2 = new JMenu("Setting");
        JMenuItem gameSetting = new JMenuItem("Game Setting");

        gameSetting.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                settingPane.setVisible(true);
            }
        });

        menu2.add(gameSetting);

        //menu3
        JMenu menu3 = new JMenu("Help");
        JMenuItem help = new JMenuItem("Rules");
        menu3.add(help);

        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        this.setJMenuBar(menuBar);
    }

    private void addSettingPane(){
        settingPane = new SettingPane();
        settingPane.setLocationRelativeTo(this);
    }
}
