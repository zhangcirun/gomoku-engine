package ai;

import ai.constant.AiConst;
import gui.constant.GuiConst;

/**
 * This class provides a basic heuristic function for the game
 *
 * @author  Chang ta'z jun
 * @version 1.0
 */

public class HeuristicAgent {
    private HeuristicAgent() {
    }

    /**
     * Returns the score of the whole chessboard regard to black or white
     *
     * @param chess is the 2 dimension array represents the chessboard
     * @return score of the chessboard
     */
    static int heuristic(int[][] chess){
        int allyScore = scanVertical(chess, -1) +
            scanHorizontal(chess, -1) +
            scanDiagonal(chess, -1) +
            scanAntiDiagonal(chess, -1);

        int opponentScore = scanVertical(chess, 1) +
            scanHorizontal(chess, 1) +
            scanDiagonal(chess, 1) +
            scanAntiDiagonal(chess, 1);
        return allyScore - opponentScore;
    }

    @Deprecated
    static int heuristic_megaMax(int[][] chess, int pieceType){
        int allyScore = scanVertical(chess, pieceType) +
            scanHorizontal(chess, pieceType) +
            scanDiagonal(chess, pieceType) +
            scanAntiDiagonal(chess, pieceType);

        int opponentScore = scanVertical(chess, pieceType * -1) +
            scanHorizontal(chess, pieceType * -1) +
            scanDiagonal(chess, pieceType * -1) +
            scanAntiDiagonal(chess, pieceType * -1);

        return allyScore - opponentScore;
    }

    @Deprecated
    private static int heuristicTest(int [][] chess, int x, int y){
        int score = 0;
        int[][] dummy = ToolKit.copyArray(chess);
        dummy[x][y] = -1;
        score += scanVertical(dummy, -1) +
            scanHorizontal(dummy, -1) +
            scanDiagonal(dummy, -1) +
            scanAntiDiagonal(dummy, -1);
        dummy[x][y] = 1;

        score -= scanVertical(dummy, 1) +
            scanHorizontal(dummy, 1) +
            scanDiagonal(dummy, 1) +
            scanAntiDiagonal(dummy, 1);
        return score;
    }

    /**
     * Scans the chessboard for top to bottom vertically
     *
     * @param chess 2-dimentsion array represents the chessboard
     */
    private static int scanVertical(int[][] chess, int pieceType) {
        StringBuilder builder = new StringBuilder();
        int score = 0;

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                int piece = chess[i][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            score += eval(builder.toString());
            builder.delete(0, builder.length());
        }
        return score;
    }

    /**
     * Scans the chessboard from left to right horizontally
     *
     * @param chess 2-dimentsion array represents the chessboard
     */
    private static int scanHorizontal(int[][] chess, int pieceType) {
        StringBuilder builder = new StringBuilder();
        int score = 0;

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                int piece = chess[j][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            score += eval(builder.toString());
            builder.delete(0, builder.length());
        }
        return score;
    }

    /**
     * Scans the chessboard from right top to left bottom diagonally
     *
     * @param chess 2-dimension array represents the chessboard
     */
    private static int scanDiagonal(int[][] chess, int pieceType) {
        StringBuilder builder = new StringBuilder();
        int score = 0;

        //upper half chessboard
        for (int i = 10; i >= 0; i--) {
            int count = 0;
            for (int j = i; j <= 14; j++) {
                int piece = chess[j][count++];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            score += eval(builder.toString());
            builder.delete(0, builder.length());
        }

        //lower half chessboard
        for (int i = 1; i <= 10; i++) {
            int count = 0;
            for (int j = i; j <= 14; j++) {
                int piece = chess[count++][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            score += eval(builder.toString());
            builder.delete(0, builder.length());
        }
        return score;
    }

    /**
     * Scans the chessboard from left top to right bottom anti-diagonally
     *
     * @param chess 2-dimension array represents the chessboard
     */
    private static int scanAntiDiagonal(int[][] chess, int pieceType) {
        StringBuilder builder = new StringBuilder();
        int score = 0;

        //upper half chessboard
        for (int i = 4; i <= 14; i++) {
            int count = 0;
            for (int j = i; j >= 0; j--) {
                int piece = chess[count++][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            score += eval(builder.toString());
            builder.delete(0, builder.length());
        }

        //lower half chessboard
        for (int i = 0; i <= 10; i++) {
            int count = 14;
            for (int j = i; j <= 14; j++) {
                int piece = chess[j][count--];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            score += eval(builder.toString());
            builder.delete(0, builder.length());
        }
        return score;
    }

    /**
     * Returns the score of one row of pieces
     *
     * @param pieces the string represents a row of pieces
     * @return the score of the pieces
     */
    private static int eval(String pieces){
        if (pieces.contains(AiConst.IMPLICATE_FIVE)) {
            return 50000;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_DOUBLE_EMPTY)) {
            return 4320;
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
            pieces.contains(AiConst.IMPLICATE_THREE_D) ||
            pieces.contains(AiConst.IMPLICATE_THREE_E)){
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

    @Deprecated
    public static int[] nextMove(int[][] chess){
        int currentMaxScore = 0;
        int x = 0;
        int y = 0;
        for(int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++){
            for(int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++){
                //for each empty tiles, calculates their marks
                if(chess[i][j] == 0) {
                    //int[][] dummy = ToolKit.copyArray(chess);
                    //dummy[i][j] = -1;
                    //int score = heuristic(dummy, -1);
                    int score = heuristicTest(chess, x, y);
                    if (score > currentMaxScore) {
                        currentMaxScore = score;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        System.out.println("currentMax: " + currentMaxScore + " " + x + " " + y);
        return new int[]{x, y};
    }
}
