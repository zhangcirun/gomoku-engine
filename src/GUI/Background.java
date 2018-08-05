package GUI;

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
        this.background = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/GUI/assets/background.png"));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1024, 768));

        chessboard = new Chessboard();
        chessboard.setBounds(245,117,535,536);
        this.add(chessboard);
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(background,0,0,null);
    }

}
