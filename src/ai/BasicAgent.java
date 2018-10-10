package ai;

import ai.constant.AiConst;
import gui.Chessboard;
import gui.constant.GuiConst;

/**
 * This class is a simple AI agent using simple heuristic function to
 * evaluate each empty tiles, and return the coordinates of the tile which
 * has highest score.
 *
 * @author Chang ta'z jun
 * @version Version 1.0
 */
public class BasicAgent extends Agent{
    //@Todo agent cannot plays correctly sometimes when both computer and human have a potential 5
    private BasicAgent() {}

    /**
     * Evaluate each empty tiles and return the best tile
     * for the next move.
     *
     * @param chess is the 2-dimension array represents the chessboard
     * @return An array contains the coordinates of the tile
     */
    public static int[] nextMove(int[][] chess){
        int currentMaxScore = 0;
        int x = 0;
        int y = 0;
        for(int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++){
            for(int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++){
                //for each empty tiles, calculates their marks
                if(chess[i][j] == 0) {
                    //-1 for white piece
                    int score = totalmark(chess, i, j);
                    if (score > currentMaxScore) {
                        currentMaxScore = score;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        System.out.println("currentMax: " + currentMaxScore + " " + x + " " + y);
        return new int[]{x, y, aiPieceType};
    }

    /**
     * Calculate the total score of one specific tile
     *
     * @param chess is the 2-dimension array represents the chessboard
     * @param x is the coordinate of the tile in x-axis
     * @param y is the coordinate of the tile in y-axis
     * @return the total score
     */
    public static int totalmark(int[][] chess, int x, int y){
        return markPiece(chess, x, y, -1) + markPiece(chess, x, y, 1);
    }

    public static int markPiece(int[][] chess, int x, int y, int pieceType){
        return marking(horizontalPieces(chess, x, y, pieceType)) +
                marking(verticalPieces(chess, x, y, pieceType)) +
                marking(diagonalPieces(chess, x, y, pieceType)) +
                marking(antiDiagonalPieces(chess, x, y, pieceType));
    }
    /**
     * Pattern matches a row of pieces with pre-defined patterns
     * and return the corresponding score
     *
     * @param pieces is a row of pieces in String format
     * @return the mark of the row of pieces
     */
    private static int marking(String pieces) {
        if (pieces.contains(AiConst.IMPLICATE_FIVE)) {
            return 500000;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_DOUBLE_EMPTY)) {
            return 10000;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_A) ||
            pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_B) ||
            pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_C) ||
            pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_D) ||
            pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_E)) {
            return 720;
        }

        if (pieces.contains(AiConst.IMPLICATE_THREE_A) ||
            pieces.contains(AiConst.IMPLICATE_THREE_B) ||
            pieces.contains(AiConst.IMPLICATE_THREE_C) ||
            pieces.contains(AiConst.IMPLICATE_THREE_D)){
            return 720;
        }

        if (pieces.contains(AiConst.IMPLICATE_TWO_A) ||
            pieces.contains(AiConst.IMPLICATE_TWO_B) ||
            pieces.contains(AiConst.IMPLICATE_TWO_C)){
            return 120;
        }

        if (pieces.contains(AiConst.IMPLICATE_ONE_A) ||
            pieces.contains(AiConst.IMPLICATE_ONE_B)){
            return 20;
        }

        return 0;

    }

    /**
     * Returns 8 pieces surround a specific piece horizontally
     *
     * @param chess is the 2-dimension array represents the chessboard
     * @param xArrayPosition is the coordinate of the tile in x-axis
     * @param yArrayPosition is the coordinate of the tile in y-axis
     * @param pieceType represents the type of the piece will be placed in the tile
     * @return the row of pieces horizontally in String format
     */
    private static String horizontalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        StringBuilder builder = new StringBuilder();
        //check from left to target
        for(int i = xArrayPosition - 4; i < xArrayPosition ; i++){
            if(Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                //0 for empty, 1 for ally, 2 for opponent
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to right
        for(int i = xArrayPosition + 1; i < xArrayPosition + 5 ; i++){
            if(Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    /**
     * Returns 8 pieces surround a specific piece vertically
     *
     * @param chess is the 2-dimension array represents the chessboard
     * @param xArrayPosition is the coordinate of the tile in x-axis
     * @param yArrayPosition is the coordinate of the tile in y-axis
     * @param pieceType represents the type of the piece will be placed in the tile
     * @return the row of pieces vertically in String format
     */
    private static String verticalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        StringBuilder builder = new StringBuilder();
        //check from top to target
        for(int i = yArrayPosition - 4; i < yArrayPosition ; i++){
            if(Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to bottom
        for(int i = yArrayPosition + 1; i < yArrayPosition + 5 ; i++){
            if(Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    /**
     * Returns 8 pieces surround a specific piece diagonally
     *
     * @param chess is the 2-dimension array represents the chessboard
     * @param xArrayPosition is the coordinate of the tile in x-axis
     * @param yArrayPosition is the coordinate of the tile in y-axis
     * @param pieceType represents the type of the piece will be placed in the tile
     * @return the row of pieces diagonally in String format
     */
    private static String diagonalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        StringBuilder builder = new StringBuilder();
        //check from left top to target
        for(int i = 4; i > 0; i--){
            if(Chessboard.validateArrayIndex(xArrayPosition - i) &&
                Chessboard.validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition - i ][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to right bottom
        for(int i = 1; i < 5 ; i++){
            if(Chessboard.validateArrayIndex(xArrayPosition + i) &&
                Chessboard.validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    /**
     * Returns 8 pieces surround a specific piece anti-diagonally
     *
     * @param chess is the 2-dimension array represents the chessboard
     * @param xArrayPosition is the coordinate of the tile in x-axis
     * @param yArrayPosition is the coordinate of the tile in y-axis
     * @param pieceType represents the type of the piece will be placed in the tile
     * @return the row of pieces anti-diagonally in String format
     */
    private static String antiDiagonalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        StringBuilder builder = new StringBuilder();
        //check from right top to target
        for(int i = 4; i > 0 ; i--){
            if(Chessboard.validateArrayIndex(xArrayPosition + i) &&
                Chessboard.validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to left bottom
        for(int i = 1; i < 5; i++){
            if(Chessboard.validateArrayIndex(xArrayPosition - i) &&
                Chessboard.validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition - i ][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }
}
