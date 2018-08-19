package gui;

import gui.constant.GuiConst;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * This class is the the outer layer above Chessboard which
 * shows background image and other UI components. It is the
 * interface between chessboard and other classes.
 *
 * @author Chang ta'z jun
 * @version Version 1.1
 */
public class Background extends JPanel {
    private BufferedImage background, blackNext, whiteNext;

    /**
     * Chessboard is the component of this class
     */
    private Chessboard chessboard;

    /**
     * Indicates which player should go in the next turn
     */
    static boolean blackTurn = true;

    /**
     * Indicates whether the game is over or not
     */
    static boolean gameOnProgress = true;

    Background() throws Exception {
        init();
    }

    private void init() throws Exception {
        this.background = ImageIO.read(new File("src/gui/assets/backgroundAutumn.jpg"));
        this.blackNext = ImageIO.read(new File("src/gui/assets/blackNext.png"));
        this.whiteNext = ImageIO.read(new File("src/gui/assets/whiteNext.png"));
        this.setPreferredSize(new Dimension(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT));
        this.setLayout(null);

        chessboard = new Chessboard(this);
        chessboard.setBounds(245, 12, GuiConst.BOARD_WIDTH, GuiConst.BOARD_HEIGHT);
        this.add(chessboard);
    }

    /**
     * Draws the background and user tips
     *
     * @param g The java.awt.Graphics class is the abstract base class for drawing components.
     */
    @Override public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
        if (blackTurn) {
            g.drawImage(blackNext, 1000, 450, null);
        } else if (!blackTurn) {
            g.drawImage(whiteNext, 1000, 450, null);
        }

    }

    void resetGame() {
        this.chessboard.resetGame();
    }
}
