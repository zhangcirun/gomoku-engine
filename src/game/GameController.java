package game;

import ai.Agent;
import ai.constant.AiConst;
import game.constant.GameConst;
import gui.constant.GuiConst;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used for game controlling, it controls
 * and preserve the important data and parameters of the game
 *
 * @author Chang tz'u jun
 */
public class GameController {
    private GameController(){}

    /**
     * 2-dimension array for storing the positions of each pieces,
     * 0 for empty, 1 for black and -1 for white
     */
    public static int[][] chess;

    /**
     * Stores Zobrist key values
     */
    public static int[][][] zobrist;

    private static int aiIndex;

    public static int moveCounter = 0;

    private static boolean gameInProgress = false;
    //public static boolean humanVsComputer = true;

    private static boolean humanFirst = false;

    public static void resetChessboard(){
        chess = new int[GuiConst.TILE_NUM_PER_ROW][GuiConst.TILE_NUM_PER_ROW];
        moveCounter = 0;
    }

    public static void resetZobrist(){
        zobrist = new int[2][GuiConst.TILE_NUM_PER_ROW][GuiConst.TILE_NUM_PER_ROW];
        for(int i = 0; i < zobrist.length; i++){
            for(int j = 0; j < zobrist[0].length; j++){
                for(int k = 0; k < zobrist[0][0].length; k++){
                    zobrist[i][j][k] = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
                }
            }
        }
    }

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
