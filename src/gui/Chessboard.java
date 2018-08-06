package gui;
import controller.GomokuController;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;

public class Chessboard extends JPanel {
    public static int TILE_NUM = 15;
    private int X_OFFSET  = 24;
    private int Y_OFFSET = 25;
    private int BOARD_WIDTH;  //535 px
    private int BOARD_HEIGHT; //536 px
    private int targetX = 0;
    private int targetY = 0;
    private double TILE_WIDTH;   //about 35 px
    private boolean isBlack = true;
    private int chess[][];

    private BufferedImage board;
    private BufferedImage black;
    private BufferedImage white;
    private BufferedImage target;

    private GomokuController controller;

    public Chessboard() throws  Exception{
        init();
    }

    private void init() throws Exception{
        this.board = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/chessboard.jpg"));
        this.black = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/black.png"));
        this.white = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/white.png"));
        this.target = ImageIO.read(new File("/Users/cirun/Documents/admin/code/java/project/src/gui/assets/target.png"));
        this.BOARD_WIDTH = board.getWidth();
        this.BOARD_HEIGHT = board.getHeight();
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.TILE_WIDTH = BOARD_WIDTH / TILE_NUM;

        this.chess = new int[TILE_NUM][TILE_NUM]; // 0 for empty 1 black -1 white

        this.controller = new GomokuController();
        addActionListener();

    }

    private void addActionListener(){
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {

                int xArrayPosition = (int) Math.round((e.getX() - X_OFFSET) / TILE_WIDTH);
                int yArrayPosition = (int) Math.round((e.getY() - Y_OFFSET) / TILE_WIDTH);


                //add to array
                if (validateArrayPosition(xArrayPosition) && validateArrayPosition(yArrayPosition)
                        && chess[xArrayPosition][yArrayPosition] == 0)
                {
                    chess[xArrayPosition][yArrayPosition] = isBlack ? 1 : -1;
                    System.out.println("placing");
                    //reverse the flag
                    isBlack = !isBlack;

                    //repaint the chessboard GUI
                    repaint();
                    if(controller.isFiveInLine(chess, xArrayPosition, yArrayPosition)){
                        System.out.println("WIN!!");
                    }
                }

            }
            @Override
            public void mouseExited(MouseEvent e){
                    targetX = -50;
                    targetY = -50;
                    repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                 int targetArrayX = (int) Math.round((e.getX() - X_OFFSET) / TILE_WIDTH);
                 int targetArrayY = (int) Math.round((e.getY() - Y_OFFSET) / TILE_WIDTH);
                 if(validateArrayPosition(targetArrayX) && validateArrayPosition(targetArrayY)){
                     int newTargetX = (int)(targetArrayX * TILE_WIDTH);
                     int newTargetY = (int)(targetArrayY * TILE_WIDTH);

                     if(newTargetX != targetX || newTargetY != targetY){
                         targetX = newTargetX;
                         targetY = newTargetY;
                         repaint();
                     }
                 }
            }
        });

    }

    @Override
    public void paint(Graphics g){
        g.drawImage(board, 0, 0, null);
        g.drawImage(target, targetX + 4, targetY + 3, null);

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

    public static boolean validateArrayPosition(int arrayPosition){
        return arrayPosition >= 0 && arrayPosition <= 14;
    }
}
