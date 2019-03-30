package gui;

import ai.*;
import game.GameController;
import game.constant.GameConst;
import observer.HistoryObserver;
import observer.GameStatusChecker;
import gui.constant.GuiConst;

import java.awt.Image;
import java.io.IOException;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * This class is a GUI component, which provides functionality of drawing chessboard, placing pieces and invoking
 * AI agents
 *
 * @author Cirun Zhang
 * @version Version 1.4
 */
public class Chessboard extends JPanel {
    /**
     * Parent gui component
     */
    private Background background;

    /**
     * Coordinates of the cross sight
     */
    private int crossSightXCoordinate = -50;
    private int crossSightYCoordinate = -50;

    /**
     * A flag controls piece placing
     */
    private boolean placing = true;

    private Image boardImage, blackImage, whiteImage, crossSightImage;

    /**
     * Shows game result
     */
    private GameResultPane resultPane;

    Chessboard(Background background) {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.background = background;

        try {
            resultPane = new GameResultPane();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Preload images, initializes array, adds mouse listeners and
     * sets gui properties
     *
     * @throws IOException if loading images fails
     */
    private void init() throws IOException {

        this.boardImage = new ImageIcon(this.getClass().getResource("/assets/chessboard.jpg")).getImage();
        this.blackImage = new ImageIcon(this.getClass().getResource("/assets/black.png")).getImage();
        this.whiteImage = new ImageIcon(this.getClass().getResource("/assets/white.png")).getImage();
        this.crossSightImage = new ImageIcon(this.getClass().getResource("/assets/target.png")).getImage();
        this.setPreferredSize(new Dimension(GuiConst.BOARD_WIDTH, GuiConst.BOARD_HEIGHT));
        GameController.initGame();
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
                if (GameController.isGameInProgress() && placing) {
                    //Calculates indexes of the new piece in the 2-dimensional array
                    int xArrayIndex = (int)Math.round((e.getX() - GuiConst.X_AXIS_OFFSET) / GuiConst.TILE_WIDTH);
                    int yArrayIndex = (int)Math.round((e.getY() - GuiConst.Y_AXIS_OFFSET) / GuiConst.TILE_WIDTH);

                    //If the indexes are valid and the tile is empty, add the new piece to the array
                    if (validateArrayIndex(xArrayIndex) && validateArrayIndex(yArrayIndex)
                        && GameController.chess[xArrayIndex][yArrayIndex] == 0) {

                        GameController.chess[xArrayIndex][yArrayIndex] = Agent.aiPieceType * -1;

                        Background.addMessage("Human Move: (x," + xArrayIndex + "), (y," + yArrayIndex + ")");

                        //Add history
                        HistoryObserver.addHistory(new int[] {xArrayIndex, yArrayIndex, Agent.aiPieceType * -1});

                        //Check is game end
                        checkFiveInLine(GameController.chess, xArrayIndex, yArrayIndex);

                        placing = false;

                        //calculate computer move in a new thread
                        new Thread(new Runnable() {
                            public void run() {
                                computerMove(GameController.chess);
                            }
                        }).start();

                        //Repaints the chessboard and outer layer gui
                        repaint();
                        background.repaint();
                    }
                }
            }

            //Remove the cross sight when the cursor moves out of the chessboard
            @Override
            public void mouseExited(MouseEvent e) {
                crossSightXCoordinate = -50;
                crossSightYCoordinate = -50;
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            //Changes cross sight location when cursor moves
            @Override public void mouseMoved(MouseEvent e) {
                if (GameController.isGameInProgress()) {
                    //Gets relative location in the chessboard
                    int crossSightRelativeX =
                        (int)Math.round((e.getX() - GuiConst.X_AXIS_OFFSET) / GuiConst.TILE_WIDTH);
                    int crossSightRelativeY =
                        (int)Math.round((e.getY() - GuiConst.Y_AXIS_OFFSET) / GuiConst.TILE_WIDTH);
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
                if (GameController.chess[i][j] == 1) {
                    g.drawImage(blackImage, (int)((i * GuiConst.TILE_WIDTH) + 5), (int)((j * GuiConst.TILE_WIDTH) + 5),
                        null);
                } else if (GameController.chess[i][j] == -1) {
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
        GameController.resetChessboard();
        //GameController.chess = TestUtility.dummyChess3;
        if (resultPane != null) {
            this.remove(resultPane);
        }
        HistoryObserver.cleanHistory();

        if (!GameController.isHumanFirst()) {
            System.out.println("ai move first");
            computerMove(GameController.chess);
        }

        //validate();
        repaint();
        background.repaint();
    }

    /**
     * Revert the last move
     */
    void revertHistory() {
        if (HistoryObserver.getHistorySize() >= 2) {
            int[] lastMove1 = HistoryObserver.popHistory();
            int[] lastMove2 = HistoryObserver.popHistory();
            GameController.chess[lastMove1[0]][lastMove1[1]] = 0;
            GameController.chess[lastMove2[0]][lastMove2[1]] = 0;
            if (resultPane != null) {
                this.remove(resultPane);
            }
            GameController.setGameInProgress(true);
            repaint();
            System.out.println("revert");
        }
    }

    /**
     * Checks whether wining condition is reached
     *
     * @param chess       2-dimension array represents the chessboard
     * @param xArrayIndex X-coordinate of the piece
     * @param yArrayIndex Y-coordinate of the piece
     */
    private void checkFiveInLine(int[][] chess, int xArrayIndex, int yArrayIndex) {
        if (GameStatusChecker.isFiveInLine(chess, xArrayIndex, yArrayIndex)) {
            System.out.println("WIN");
            GameController.setGameInProgress(false);
            this.add(resultPane);
            validate();
        }
    }

    private void computerMove(int[][] chess) {
        int[] result;

        switch (GameController.getAiIndex()) {
            case GameConst.BEST_FIRST:
                result = GreedyBestFirst.nextMove(chess);
                break;
            case GameConst.MINIMAX:
                result = MinimaxAbp.startMiniMax(chess);
                break;
            case GameConst.ALPHA_BETA_PRUNING:
                result = MinimaxAbp.startAlphaBetaPruningWithSort(chess);
                break;
            case GameConst.TRANSPOSITION_SEARCH:
                result = Transposition.startTranspositionSearch(chess);
                break;
            case GameConst.KILLER_HEURISTIC:
                result = KillerHeuristic.killerAbp(chess);
                break;
            case GameConst.THREAT_SPACE_SEARCH:
                result = ThreatSpace.startThreatSpaceSearch(chess);
                break;
            case GameConst.MONTE_CARLO_TREE_SEARCH:
                result = MonteCarlo.monteCarloTreeSearch(chess);
                break;
            default:
                System.err.println("Invalid Ai Index");
                return;
        }
        int x = result[0];
        int y = result[1];
        int pieceType = result[2];
        chess[x][y] = pieceType;
        //Check whether the wining case is reached
        checkFiveInLine(chess, x, y);
        HistoryObserver.addHistory(result);
        //Repaints the GUI components
        setVisible(true);
        repaint();
        background.repaint();
        //Reverse the flag
        placing = true;
    }
}