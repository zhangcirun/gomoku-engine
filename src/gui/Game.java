package gui;

import javax.swing.*;


public class Game {
    private JFrame frame = new JFrame("Gomoku");
    public void start() throws Exception{
        Background background = new Background();
        frame.setSize(1024,576);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //center the frame
        frame.setLocationRelativeTo(null);
        frame.add(background);
        frame.setVisible(true);
    }
}
