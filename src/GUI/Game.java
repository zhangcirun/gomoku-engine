package GUI;

import javax.swing.*;
import java.awt.*;

public class Game {
    // width and height of the chessboard image
    private final int BOARD_WIDTH = 535;
    private final int BOARD_HETGHT = 536;
    private JFrame frame = new JFrame("Gomoku");
    public void start() throws Exception{
        Chessboard chessboard = new Chessboard();
        chessboard.init();

        chessboard.setPreferredSize(new Dimension(BOARD_WIDTH , BOARD_HETGHT));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(415, 95);
        frame.add(chessboard);
        frame.pack();
        frame.setVisible(true);
    }
}
