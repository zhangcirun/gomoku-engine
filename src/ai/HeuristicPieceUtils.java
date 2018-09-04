package ai;

import ai.constant.DemoConst;
import gui.Chessboard;

/**
 * This class provides a heuristic function for pieces
 *
 * @author Chang ta'z jun
 * @version 1.0
 */
public class HeuristicPieceUtils {
    private HeuristicPieceUtils(){}

    static int evalAll(int[][] chess, int x, int y){
        return eval(chess, x, y, -1) + eval(chess, x, y, 1);
    }

    static int eval(int[][] chess, int x, int y, int pieceType){
        int score = 0;
        int[] strategy = new int[7];

        herustic(horizontalPieces(chess, x, y, pieceType), strategy);
        herustic(verticalPieces(chess, x, y, pieceType), strategy);
        herustic(diagonalPieces(chess, x, y, pieceType), strategy);
        herustic(antiDiagonalPieces(chess, x, y, pieceType), strategy);

        int num_implicate_five = strategy[0];
        int num_implicate_four = strategy[1];
        int num_implicate_four_block = strategy[2];
        int num_implicate_three = strategy[3];
        int num_implicate_three_block = strategy[4];
        int num_implicate_two = strategy[5];
        int num_implicate_one = strategy[6];

        if(num_implicate_four > 1){
            score += 10000;
        }

        if(num_implicate_four_block > 1){
            score += 10000;
        }

        if(num_implicate_three > 1){
            score += 10000;
        }

        if(num_implicate_three + num_implicate_four_block > 1){
            score += 10000;
        }
        score += num_implicate_five * 50000 +
            num_implicate_four * 10000 +
            num_implicate_four_block * 720 +
            num_implicate_three * 720 +
            num_implicate_three_block * 380 +
            num_implicate_two * 380 +
            num_implicate_one * 20;
        return score;
    }

    private static void herustic(String row, int[] strategy){
        if (row.contains(DemoConst.IMPLICATE_FIVE)) {
            strategy[0]++;
            return;
        }

        if (row.contains(DemoConst.IMPLICATE_FOUR_A)) {
            strategy[1]++;
            return;
        }

        if (row.contains(DemoConst.IMPLICATE_FOUR_BLOCK_A) ||
            row.contains(DemoConst.IMPLICATE_FOUR_BLOCK_B) ||
            row.contains(DemoConst.IMPLICATE_FOUR_BLOCK_C) ||
            row.contains(DemoConst.IMPLICATE_FOUR_BLOCK_D) ||
            row.contains(DemoConst.IMPLICATE_FOUR_BLOCK_E)) {
            strategy[2]++;
            return;
        }

        if (row.contains(DemoConst.IMPLICATE_THREE_A) ||
            row.contains(DemoConst.IMPLICATE_THREE_B) ||
            row.contains(DemoConst.IMPLICATE_THREE_C) ||
            row.contains(DemoConst.IMPLICATE_THREE_D)) {
            strategy[3]++;
            return;
        }

        if (row.contains(DemoConst.IMPLICATE_THREE_BLOCK_A) ||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_B)||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_C)||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_D)||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_E)||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_F)||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_G)||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_H)||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_I)||
            row.contains(DemoConst.IMPLICATE_THREE_BLOCK_J)) {
            strategy[4]++;
            return;
        }

        if (row.contains(DemoConst.IMPLICATE_TWO_A) ||
            row.contains(DemoConst.IMPLICATE_TWO_B) ||
            row.contains(DemoConst.IMPLICATE_TWO_C) ||
            row.contains(DemoConst.IMPLICATE_TWO_D)){
            strategy[5]++;
            return;
        }

        if (row.contains(DemoConst.IMPLICATE_ONE_A) ||
            row.contains(DemoConst.IMPLICATE_ONE_B)){
            strategy[6]++;
        }
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
