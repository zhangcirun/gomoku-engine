package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Game {
    private JFrame frame = new JFrame("Gomoku");
    public void start() throws Exception{
        Background background = new Background();
        frame.setSize(1152,648);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //center the frame
        frame.setLocationRelativeTo(null);

        //frame menu
        JMenuBar menuBar = new JMenuBar();

        //reset game
        JMenu menu1 = new JMenu("Menu");
        JMenuItem resetGame = new JMenuItem("Reset Game");
        resetGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reset Game");
                background.resetGame();
            }
        });
        menu1.add(resetGame);

        JMenu menu2 = new JMenu("Help");
        JMenuItem help = new JMenuItem("Game Rules");
        menu2.add(help);

        menuBar.add(menu1);
        menuBar.add(menu2);



        //add background panel along with the chessboard
        frame.add(background);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }
}
