package GUI;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
public class Chessboard extends JPanel {
    private  BufferedImage board;
    private  BufferedImage black;
    private  BufferedImage white;
    //
    private int x = 0;
    private int y = 0;

    public void init() throws Exception{
        this.board = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/GUI/assets/chessboard.jpg"));
        this.black = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/GUI/assets/black.png"));
        this.white = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/GUI/assets/white.png"));
        enter();

    }

    public void enter(){
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){

                System.out.println(e.getX());
                System.out.println(e.getY());
                x = e.getX();
                y = e.getY();
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g){
        g.drawImage(board, 0, 0, null);
        g.drawImage(black,x,y,null);
        /*
        g.drawImage(black,7,11,null);
        g.drawImage(white,7,45,null);
        g.drawImage(black,42,11,null);
        g.drawImage(white,42,45,null);
        g.drawImage(black,77,11,null);
        */
    }

}
