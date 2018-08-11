package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Background extends JPanel{
    private BufferedImage background, blackNext, whiteNext;
    private Chessboard chessboard;
    static boolean isBlack = true;
    static boolean isGameProgress = true;

    public Background() throws Exception{
        init();
    }

    private void init() throws Exception{
        this.background = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/backgroundAutumn.jpg"));
        this.blackNext = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/blackNext.png"));
        this.whiteNext = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/whiteNext.png"));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1152, 648));

        chessboard = new Chessboard(this);
        chessboard.setBounds(245,12,535,536);


        this.add(chessboard);
    }

    public void resetGame(){
        this.chessboard.resetGame();
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(background,0,0,null);
        if(isBlack){
            g.drawImage(blackNext,1000,450,null);
        }else if(!isBlack){
            g.drawImage(whiteNext,1000,450,null);
        }

    }

}
