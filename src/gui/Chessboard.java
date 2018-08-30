package gui;

import ai.AdvancedAgent;
import ai.HeuristicAgent;
import observer.GomokuObserver;
import gui.constant.GuiConst;
import ai.BasicAgent;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * Chessboard class is the gui class for chessboard
 * which provides functionality of drawing chessboard,
 * pieces placement reaction, and checking wining
 * condition {@see GomokuObserver}
 *
 * @author Chang ta'z jun
 * @version Version 1.1
 */
public class Chessboard extends JPanel {
    /**
     * Parent gui component
     */
    private Background background;

    /**
     * Coordinates of the cross sight
     */
    private int crossSightXCoordinate = 0;
    private int crossSightYCoordinate = 0;

    /**
     * 2-dimension array for storage the positions of each pieces,
     * 0 for empty, 1 for black and -1 for white
     */
    private int[][] chess;

    /**
     * 1 for black win and -1 for white win
     */
    private int winner = 0;

    private BufferedImage boardImage, blackImage, whiteImage, crossSightImage;

    /**
     * Shows game result
     */
    private GameResultPane resultPane;

    Chessboard(Background background) throws Exception {
        init();
        this.background = background;
        resultPane = new GameResultPane();
    }

    /**
     * Preload images, initializes array, adds mouse listeners and
     * sets gui properties
     *
     * @throws IOException if loading images fails
     */
    private void init() throws IOException {
        this.boardImage = ImageIO.read(new File("src/gui/assets/chessboard.jpg"));
        this.blackImage = ImageIO.read(new File("src/gui/assets/black.png"));
        this.whiteImage = ImageIO.read(new File("src/gui/assets/white.png"));
        this.crossSightImage = ImageIO.read(new File("src/gui/assets/target.png"));

        this.setPreferredSize(new Dimension(GuiConst.BOARD_WIDTH, GuiConst.BOARD_HEIGHT));
        this.chess = new int[GuiConst.TILE_NUM_PER_ROW][GuiConst.TILE_NUM_PER_ROW];
        this.addActionListener();
    }

    /**
     * Adds mouse listeners for clicking mouse and moving cursor, repaint the
     * chessboard when a new piece is placed or the location of cross sight changes.
     */
    private void addActionListener() {
        this.addMouseListener(new MouseAdapter() {

            //Add new piece to the chessboard
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Background.gameOnProgress) {
                    //Calculates indexes of the new piece in the 2-dimensional array
                    int xArrayIndex = (int)Math.round((e.getX() - GuiConst.X_AXIS_OFFSET) / GuiConst.TILE_WIDTH);
                    int yArrayIndex = (int)Math.round((e.getY() - GuiConst.Y_AXIS_OFFSET) / GuiConst.TILE_WIDTH);

                    //If the indexes are valid and the tile is empty, add the new piece to the array
                    if (validateArrayIndex(xArrayIndex) && validateArrayIndex(yArrayIndex)
                        && chess[xArrayIndex][yArrayIndex] == 0) {

                        chess[xArrayIndex][yArrayIndex] = Background.blackTurn ? 1 : -1;
                        System.out.println("placing");

                        //Reverses the flag
                        Background.blackTurn = !Background.blackTurn;

                        //Check is game end
                        checkFiveInLine(chess, xArrayIndex, yArrayIndex);

                        //Repaints the chessboard and outer layer gui

                        new Thread(new Runnable() {
                            public void run() {
                                abMove(chess);
                            }
                        }).start();

                        repaint();
                        background.repaint();
                        /*
                        Runnable aiMove = new Runnable() {
                            public void run() {
                                demoComputerMove(chess);
                            }
                        };
                        SwingUtilities.invokeLater(aiMove);
                        */
                    }
                }
            }

            //Remove the cross sight when the cursor moves out of the chessboard
            @Override public void mouseExited(MouseEvent e) {
                crossSightXCoordinate = -50;
                crossSightYCoordinate = -50;
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            //Changes cross sight location when cursor moves
            @Override
            public void mouseMoved(MouseEvent e) {
                //Gets relative location in the chessboard
                int crossSightRelativeX = (int)Math.round((e.getX() - GuiConst.X_AXIS_OFFSET) / GuiConst.TILE_WIDTH);
                int crossSightRelativeY = (int)Math.round((e.getY() - GuiConst.Y_AXIS_OFFSET) / GuiConst.TILE_WIDTH);
                if (validateArrayIndex(crossSightRelativeX) && validateArrayIndex(crossSightRelativeY)) {
                    int crossSightXCoordinate = (int)(crossSightRelativeX * GuiConst.TILE_WIDTH);
                    int crossSightYCoordinate = (int)(crossSightRelativeY * GuiConst.TILE_WIDTH);

                    //Repaints the chessboard if cross sight moves
                    if (crossSightXCoordinate != Chessboard.this.crossSightXCoordinate
                        || crossSightYCoordinate != Chessboard.this.crossSightYCoordinate) {

                        Chessboard.this.crossSightXCoordinate = crossSightXCoordinate;
                        Chessboard.this.crossSightYCoordinate = crossSightYCoordinate;
                        repaint();
                    }
                }
            }
        });

    }

    /**
     * Draws the chessboard, pieces and cross sight.
     *
     * @param g The java.awt.Graphics class is the abstract base class for drawing components.
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(boardImage, 0, 0, null);
        g.drawImage(crossSightImage, crossSightXCoordinate + 4, crossSightYCoordinate + 3, null);

        //Transverses the array and paint all pieces
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                if (chess[i][j] == 1) {
                    g.drawImage(blackImage, (int)((i * GuiConst.TILE_WIDTH) + 5), (int)((j * GuiConst.TILE_WIDTH) + 5),
                        null);
                } else if (chess[i][j] == -1) {
                    g.drawImage(whiteImage, (int)((i * GuiConst.TILE_WIDTH) + 6), (int)((j * GuiConst.TILE_WIDTH) + 6),
                        null);
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Validates whether the array index within the range.
     *
     * @param index Index of the array
     * @return Return true if the position is valid, vice versa
     */
    public static boolean validateArrayIndex(int index) {
        return index >= 0 && index <= 14;
    }

    /**
     * Resets the game, cleans the array
     */
    void resetGame() {
        System.out.println("reset");
        this.chess = new int[15][15];
        this.remove(resultPane);
        Background.blackTurn = true;
        Background.gameOnProgress = true;
        //validate();
        repaint();
        background.repaint();
    }

    /**
     * Checks whether wining condition is reached
     * @param chess is the 2-dimension array represents the chessboard
     * @param xArrayIndex x-coordinate of the piece
     * @param yArrayIndex y-coordinate of the piece
     */
    private void checkFiveInLine(int[][] chess, int xArrayIndex, int yArrayIndex){
        if (GomokuObserver.isFiveInLine(chess, xArrayIndex, yArrayIndex)) {
            winner = !Background.blackTurn ? 1 : -1;
            System.out.println("WIN!!");
            Background.gameOnProgress = false;
            //@TODO It's too slow to add a new button
            this.add(resultPane);
            validate();
        }
    }

    private void computerMove(int[][] chess){
        //Reverses the flag
        Background.blackTurn = !Background.blackTurn;

        //Check is game end
        int[] result = BasicAgent.nextMove(chess);
        int x = result[0];
        int y = result[1];
        chess[x][y] = -1;
        checkFiveInLine(chess, x, y);


        //Repaints the chessboard and outer layer gui

        repaint();
        background.repaint();
    }

    private void demoComputerMove(int[][] chess){
        //Reverses the flag
        Background.blackTurn = !Background.blackTurn;

        int[] result = AdvancedAgent.startMiniMax(chess);
        int x = result[0];
        int y = result[1];
        this.chess[x][y] = -1;
        checkFiveInLine(chess, x, y);
        //Repaints the chessboard and outer layer gui
        setVisible(true);
        repaint();
        background.repaint();

    }

    private void testMove(int[][] chess){
        //Reverses the flag
        Background.blackTurn = !Background.blackTurn;

        //Check is game end
        int[] result = HeuristicAgent.nextMove(chess);
        int x = result[0];
        int y = result[1];
        chess[x][y] = -1;
        checkFiveInLine(chess, x, y);


        //Repaints the chessboard and outer layer gui

        repaint();
        background.repaint();
    }

    private void abMove(int[][] chess){
        Background.blackTurn = !Background.blackTurn;

        int[] result = AdvancedAgent.startAlphaBetaPruning_preSort(chess);
        int x = result[0];
        int y = result[1];
        this.chess[x][y] = -1;
        checkFiveInLine(chess, x, y);
        //Repaints the chessboard and outer layer gui
        setVisible(true);
        repaint();
        background.repaint();
    }
}