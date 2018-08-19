package gui;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * This class is a sub-panel of chessboard, it shows the
 * game result.
 *
 * @author Chang ta'z jun
 * @version Version 1.0
 */
public class GameResultPane extends JPanel {

    private BufferedImage win;

    GameResultPane() throws Exception {
        init();
        this.setVisible(true);
    }

    private void init() throws IOException {
        this.win = ImageIO.read(new File("src/gui/assets/win.png"));
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

