package gui;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

/**
 * This class is a sub-panel of chessboard, it shows the
 * game result.
 *
 * @author Chang ta'z jun
 * @version Version 1.0
 */
public class GameResultPane extends JPanel {

    private Image win;

    GameResultPane() throws Exception {
        init();
        this.setVisible(true);
    }

    private void init() throws IOException {
        this.win = new ImageIcon(this.getClass().getResource("/assets/win.png")).getImage();
        this.setPreferredSize(new Dimension(247, 87));
    }

    @Override public void paintComponent(Graphics g) {
        g.drawImage(win, 0, 0, null);
    }

    /*
    Inner buttonListener class
    private class buttonListener implements ActionListener {

        @Override public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGameButton) {
                chessboard.resetGame();
            }
        }
    }
    */
}

