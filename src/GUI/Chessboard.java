package GUI;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Chessboard extends JPanel {
    private static BufferedImage board;

    public static void init() throws Exception{
        board = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/GUI/assets/chessboard.jpg"));
    }

    @Override
    public void paint(Graphics g ){
        try {
            init();
        }catch (Exception e){
            System.out.println("Och!!");
        }
        g.drawImage(board, 0, 0, null);
    }
}
