package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameResult extends JPanel{
    private BufferedImage blackWin, whiteWin;


    public GameResult() throws Exception{
        this.setLayout(null);
        this.blackWin = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/blackWin.png"));
        this.whiteWin = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/whiteWin.png"));

        JButton newGame = new JButton();
        newGame.setBounds(241,12,100,100);
        this.add(newGame);
        this.setPreferredSize(new Dimension(300, 300));
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(blackWin,0,0,null);
    }
}
