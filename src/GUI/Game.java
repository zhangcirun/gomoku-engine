package GUI;

import javax.swing.*;
import java.awt.*;

public class Game {
    private final int BOARD_WIDTH = 535;
    private final int BOARD_HETGHT = 536;
    JFrame frame = new JFrame("五子棋游戏");
    public void start(){
        Chessboard chessboard = new Chessboard();

        chessboard.setPreferredSize(new Dimension(BOARD_WIDTH , BOARD_HETGHT));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(415, 95);
        frame.add(chessboard);
        frame.pack();
        frame.setVisible(true);
    }
}
