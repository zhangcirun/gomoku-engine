package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Background extends JPanel{
    private BufferedImage background;
    private Chessboard chessboard;

    public Background() throws Exception{
        init();
    }

    private void init() throws Exception{
        this.background = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/backgroundAutumn.jpg"));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1152, 648));

        chessboard = new Chessboard();
        chessboard.setBounds(245,12,535,536);


        this.add(chessboard);
    }

    public void resetGame(){
        this.chessboard.resetGame();
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(background,0,0,null);
    }

}
