package GUI;

import javax.swing.*;


public class Game {
    private JFrame frame = new JFrame("Gomoku");
    public void start() throws Exception{
        Background background = new Background();
        frame.setSize(1024,768);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(200, 95);
        frame.add(background);
        frame.setVisible(true);
    }
}
