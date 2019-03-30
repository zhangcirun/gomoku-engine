package ai.utility;

import ai.Agent;
import ai.constant.AiConst;
import gui.constant.GuiConst;

/**
 * This class provides an utility class for providing heuristic function of the whole game state(h1).
 *
 * @author Cirun Zhang
 * @version 1.1
 */

public class HeuristicChessboardUtils {
    private static StringBuilder builder = new StringBuilder();
    private HeuristicChessboardUtils() {
    }

    /**
     * Returns the score of the whole chessboard
     *
     * @param chess is the 2 dimension array represents the chessboard
     * @return score of the chessboard
     */
    public static int heuristic(int[][] chess) {
        int allyScore =
            scanVertical(chess, Agent.aiPieceType) + scanHorizontal(chess, Agent.aiPieceType) + scanDiagonal(chess,
                Agent.aiPieceType) + scanAntiDiagonal(chess, Agent.aiPieceType);

        int opponentScore =
            scanVertical(chess, Agent.aiPieceType * -1) + scanHorizontal(chess, Agent.aiPieceType * -1) + scanDiagonal(
                chess, Agent.aiPieceType * -1) + scanAntiDiagonal(chess, Agent.aiPieceType * -1);
        return allyScore - opponentScore;
    }

    @Deprecated
    public static int heuristic_megaMax(int[][] chess, int pieceType) {
        int allyScore =
            scanVertical(chess, pieceType) + scanHorizontal(chess, pieceType) + scanDiagonal(chess, pieceType)
                + scanAntiDiagonal(chess, pieceType);

        int opponentScore =
            scanVertical(chess, pieceType * -1) + scanHorizontal(chess, pieceType * -1) + scanDiagonal(chess,
                pieceType * -1) + scanAntiDiagonal(chess, pieceType * -1);

        return allyScore - opponentScore;
    }

    /**
     * Scans the chessboard from top to bottom vertically
     *
     * @param chess     2-dimension array represents the chessboard
     * @param pieceType Indicates which player moved, 1 for black -1 for white
     * @return Score of vertical rows
     */
    private static int scanVertical(int[][] chess, int pieceType) {
        int score = 0;

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                int piece = chess[i][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            score += eval(builder.toString());
            builder.setLength(0);
        }
        builder.setLength(0);
        return score;
    }

    /**
     * Scans the chessboard from left to right horizontally
     *
     * @param chess     2-dimension array represents the chessboard
     * @param pieceType Indicates which player moved, 1 for black -1 for white
     * @return Score of horizontal rows
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
            builder.setLength(0);
        }
        builder.setLength(0);
        return score;
    }

    /**
     * Scans the chessboard from right top to left bottom diagonally
     *
     * @param chess     2-dimension array represents the chessboard
     * @param pieceType Indicates which player moved, 1 for black -1 for white
     * @return Score of diagonal rows
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
            builder.setLength(0);
        }
        builder.setLength(0);
        return score;
    }

    /**
     * Scans the chessboard from left top to right bottom anti-diagonally
     *
     * @param chess     2-dimension array represents the chessboard
     * @param pieceType Indicates which player moved, 1 for black -1 for white
     * @return Score of anti-diagonal rows
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
            builder.setLength(0);
        }
        builder.setLength(0);
        return score;
    }

    /**
     * Returns the score of one row of pieces
     *
     * @param pieces the string represents a row of pieces
     * @return the score of the pieces
     */
    public static int eval(String pieces) {
        if (pieces.contains(AiConst.IMPLICATE_FIVE)) {
            return 500000;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_DOUBLE_EMPTY)) {
            return 10000;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_A) || pieces
            .contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_B) || pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_C)
            || pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_D) || pieces
            .contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_E)) {
            return 820;
        }

        if (pieces.contains(AiConst.IMPLICATE_THREE_A) || pieces.contains(AiConst.IMPLICATE_THREE_B) || pieces
            .contains(AiConst.IMPLICATE_THREE_C) || pieces.contains(AiConst.IMPLICATE_THREE_D)) {
            return 720;
        }

        if (pieces.contains(AiConst.IMPLICATE_TWO_A) || pieces.contains(AiConst.IMPLICATE_TWO_B) || pieces
            .contains(AiConst.IMPLICATE_TWO_C)) {
            return 120;
        }

        if (pieces.contains(AiConst.IMPLICATE_ONE_A) || pieces.contains(AiConst.IMPLICATE_ONE_B)) {
            return 20;
        }

        return 0;
    }

}
