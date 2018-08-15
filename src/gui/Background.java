package gui;

import gui.constant.GuiConst;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

public class Background extends JPanel {
    private ImageIcon backgroundIcon;
    private BufferedImage background, blackNext, whiteNext;
    public Chessboard chessboard;
    static boolean blackTurn = true;
    static boolean gameOnProgress = true;

    public Background() throws Exception {
        init();
    }

    private void init() throws Exception {
        this.background = ImageIO.read(new File("src/gui/assets/backgroundAutumn.jpg"));
        this.blackNext = ImageIO.read(new File("src/gui/assets/blackNext.png"));
        this.whiteNext = ImageIO.read(new File("src/gui/assets/whiteNext.png"));
        this.backgroundIcon = new ImageIcon("src/gui/assets/backgroundAutumn.jpg");
        this.setPreferredSize(new Dimension(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT));
        this.setLayout(null);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setSize(GuiConst.FRAME_WIDTH, GuiConst.FRAME_HEIGHT);
        backgroundLabel.setIcon(backgroundIcon);

        chessboard = new Chessboard(this);
        chessboard.setBounds(245, 12, GuiConst.BOARD_WIDTH, GuiConst.BOARD_HEIGHT);

        ///this.add(backgroundLabel);
        this.add(chessboard);
    }

    public void resetGame() {
        this.chessboard.resetGame();
    }

    @Override public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
        if (blackTurn) {
            g.drawImage(blackNext, 1000, 450, null);
        } else if (!blackTurn) {
            g.drawImage(whiteNext, 1000, 450, null);
        }

    }

}
