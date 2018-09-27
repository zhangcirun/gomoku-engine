package game;

import gui.Background;
import gui.constant.GuiConst;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

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

    /**
     * HashMap as the transposition table
     */
    //public Map<K, V> transpositionTable = new HashMap<>();

    public static int moveCounter = 0;

    public static boolean humanVsComputer = true;

    public static int humanPieceType = 1;

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
}
