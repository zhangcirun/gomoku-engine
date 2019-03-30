package game;

import ai.Agent;
import ai.constant.AiConst;
import gui.constant.GuiConst;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used for game controlling, it controls and preserve some important functions and data
 * of the game
 *
 * @author Cirun Zhang
 * @version 1.1
 */
public class GameController {
    private GameController(){}

    /**
     * 2-dimension array represents the chessboard, 0 for empty tile, 1 for black piece, and -1 for white piece
     */
    public static int[][] chess;

    /**
     * Stores Zobrist key-values
     */
    public static int[][][] zobrist;

    /**
     * Index of the current AI agent
     */
    private static int aiIndex;

    /**
     * A boolean indicates whether the game is in progress or not
     */
    private static boolean gameInProgress = false;

    /**
     * A boolean indicates whether the human moves first or not
     */
    private static boolean humanFirst = false;

    /**
     * Resets the chessboard
     */
    public static void resetChessboard(){
        chess = new int[GuiConst.TILE_NUM_PER_ROW][GuiConst.TILE_NUM_PER_ROW];
    }

    /**
     * Regenerates all Zobrist key-values
     */
    private static void resetZobrist(){
        zobrist = new int[2][GuiConst.TILE_NUM_PER_ROW][GuiConst.TILE_NUM_PER_ROW];
        for(int i = 0; i < zobrist.length; i++){
            for(int j = 0; j < zobrist[0].length; j++){
                for(int k = 0; k < zobrist[0][0].length; k++){
                    zobrist[i][j][k] = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
                }
            }
        }
    }

    /**
     * Initialize the game
     */
    public static void initGame(){
        resetChessboard();
        resetZobrist();
    }

    public static int getAiIndex(){
        return aiIndex;
    }

    public static void setAiIndex(int index){
        aiIndex = index;
    }

    public static boolean isGameInProgress(){
        return gameInProgress;
    }

    public static void setGameInProgress(boolean status){
        gameInProgress = status;
    }

    public static boolean isHumanFirst(){
        return humanFirst;
    }

    public static void setHumanFirst(boolean isHumanFirst){
        humanFirst = isHumanFirst;
        Agent.aiPieceType = isHumanFirst ? AiConst.WHITE_STONE : AiConst.BLACK_STONE;
    }
}
