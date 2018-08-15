package gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameResultPane extends JPanel {

    private BufferedImage win;
    private JButton newGameButton;
    private Chessboard chessboard;

    public GameResultPane(Chessboard chessboard) throws Exception {
        /*
        * still need to do: centering, change image, button appearance
        * */
        this.chessboard = chessboard;
        this.win =
            ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/win.png"));
        this.setPreferredSize(new Dimension(247, 87));
        this.setVisible(true);
    }

    @Override public void paintComponent(Graphics g) {
        g.drawImage(win, 0, 0, null);
    }

    /*Inner buttonListener class*/
    private class buttonListener implements ActionListener {

        @Override public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGameButton) {
                chessboard.resetGame();
            }
        }
    }
}

