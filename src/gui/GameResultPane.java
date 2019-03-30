package gui;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * This class is a GUI component for showing the game result.
 *
 * @author Cirun Zhang
 * @version Version 1.0
 */
public class GameResultPane extends JPanel {

    private Image win;

    GameResultPane() {
        init();
        this.setVisible(true);
    }

    /**
     * Initialize the GameResultPane component
     */
    private void init() {
        this.win = new ImageIcon(this.getClass().getResource("/assets/win.png")).getImage();
        this.setPreferredSize(new Dimension(247, 87));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(win, 0, 0, null);
    }

}

