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
package gui;

import gui.constant.GuiConst;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    private Background background;

    public MainFrame() throws Exception {
        this.background = new Background();
        init();
        addMenu();
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

        //reset game
        JMenu menu1 = new JMenu("Menu");
        JMenuItem resetGame = new JMenuItem("Reset MainFrame");

        resetGame.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                System.out.println("Reset MainFrame");
                background.resetGame();
            }
        });

        menu1.add(resetGame);

        JMenu menu2 = new JMenu("Help");
        JMenuItem help = new JMenuItem("MainFrame Rules");
        menu2.add(help);

        menuBar.add(menu1);
        menuBar.add(menu2);
        this.setJMenuBar(menuBar);
    }
}
