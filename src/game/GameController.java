package game;

import gui.constant.GuiConst;

public class GameController {
    private GameController(){}

    /**
     * 2-dimension array for storing the positions of each pieces,
     * 0 for empty, 1 for black and -1 for white
     */
    public static int[][] chess;

    public static int moveCounter = 0;

    public static boolean humanVsComputer = true;

    public static int humanPieceType = 1;

    public static void resetChessboard(){
        chess = new int[GuiConst.TILE_NUM_PER_ROW][GuiConst.TILE_NUM_PER_ROW];
        moveCounter = 0;
    }
}
