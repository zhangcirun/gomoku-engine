package GUI;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Chessboard extends JPanel {
    private int TILE_NUM = 15;
    private int X_OFFSET  = 24;
    private int Y_OFFSET = 25;
    private int BOARD_WIDTH;  //535 px
    private int BOARD_HETGHT; //536 px
    private double TILE_WIDTH;   //about 35 px
    private boolean isBlack = true;
    private int chess[][];

    private BufferedImage board;
    private BufferedImage black;
    private BufferedImage white;

    public void init() throws Exception{
        this.board = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/GUI/assets/chessboard.jpg"));
        this.black = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/GUI/assets/black.png"));
        this.white = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/GUI/assets/white.png"));
        BOARD_WIDTH = board.getWidth();
        BOARD_HETGHT = board.getHeight();
        TILE_WIDTH = BOARD_WIDTH / TILE_NUM;
        chess = new int[TILE_NUM][TILE_NUM]; // 0 for empty 1 black -1 white
        System.out.println(TILE_WIDTH);
        enter();

    }

    private void enter(){
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){

                int xArrayPosition =  (int)Math.round((e.getX() - X_OFFSET) / TILE_WIDTH);
                int yArrayPosition =   (int)Math.round((e.getY() - Y_OFFSET) / TILE_WIDTH);
                //add to array
                chess[xArrayPosition][yArrayPosition] = isBlack? 1 : -1;
                //reverse the flag
                isBlack = !isBlack;
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g){
        g.drawImage(board, 0, 0, null);

        for(int i = 0; i < TILE_NUM; i++){
            for(int j = 0; j < TILE_NUM; j++){
                if(chess[i][j] == 1){
                    g.drawImage(black, (int)((i* TILE_WIDTH) + 5), (int)((j* TILE_WIDTH) + 5), null);
                }
                else if(chess[i][j] == -1){
                    g.drawImage(white, (int)((i* TILE_WIDTH) + 6), (int)((j* TILE_WIDTH) + 6), null);
                }
            }
        }
    }
}
