package ai.utility;

import ai.constant.PieceConst;

/**
 * This class is an utility class for providing heuristic function for a specific piece(h2)
 *
 * @author Cirun Zhang
 * @version 1.1
 */
public class HeuristicPieceUtils {
    private HeuristicPieceUtils() {
    }

    /**
     * Returns the score of a specific piece
     *
     * @param chess     The chessboard
     * @param x         X-coordinate of the piece
     * @param y         Y-coordinate of the piece
     * @param pieceType Type of the piece
     * @return The total evaluation score
     */
    public static int eval(int[][] chess, int x, int y, int pieceType) {
        int score = 0;
        int[] strategy = new int[7];

        heuristic(ChessboardScanUtils.horizontalAdjacentPieces10(chess, x, y, pieceType, '1'), strategy);
        heuristic(ChessboardScanUtils.verticalAdjacentPieces10(chess, x, y, pieceType, '1'), strategy);
        heuristic(ChessboardScanUtils.diagonalAdjacentPieces10(chess, x, y, pieceType, '1'), strategy);
        heuristic(ChessboardScanUtils.antiDiagonalAdjacentPieces10(chess, x, y, pieceType, '1'), strategy);

        int num_implicate_five = strategy[0];
        int num_implicate_four = strategy[1];
        int num_implicate_four_block = strategy[2];
        int num_implicate_three = strategy[3];
        int num_implicate_three_block = strategy[4];
        int num_implicate_two = strategy[5];
        int num_implicate_one = strategy[6];

        if (num_implicate_four > 1) {
            score += 10000;
        }

        if (num_implicate_four_block > 1) {
            score += 10000;
        }

        if (num_implicate_three > 1) {
            score += 10000;
        }

        if (num_implicate_three + num_implicate_four_block > 1) {
            score += 10000;
        }
        score += num_implicate_five * 50000 + num_implicate_four * 10000 + num_implicate_four_block * 720
            + num_implicate_three * 720 + num_implicate_three_block * 380 + num_implicate_two * 380
            + num_implicate_one * 20;
        return score;
    }

    /**
     * Does pattern matching and statistics.
     *
     * @param row      A sequence of pieces
     * @param strategy A array for recording the pattern count
     */
    private static void heuristic(String row, int[] strategy) {
        if (row.contains(PieceConst.IMPLICATE_FIVE)) {
            strategy[0]++;
            return;
        }

        if (row.contains(PieceConst.IMPLICATE_FOUR_A)) {
            strategy[1]++;
            return;
        }

        if (row.contains(PieceConst.IMPLICATE_FOUR_BLOCK_A) || row.contains(PieceConst.IMPLICATE_FOUR_BLOCK_B) || row
            .contains(PieceConst.IMPLICATE_FOUR_BLOCK_C) || row.contains(PieceConst.IMPLICATE_FOUR_BLOCK_D) || row
            .contains(PieceConst.IMPLICATE_FOUR_BLOCK_E)) {
            strategy[2]++;
            return;
        }

        if (row.contains(PieceConst.IMPLICATE_THREE_A) || row.contains(PieceConst.IMPLICATE_THREE_B) || row
            .contains(PieceConst.IMPLICATE_THREE_C) || row.contains(PieceConst.IMPLICATE_THREE_D)) {
            strategy[3]++;
            return;
        }

        if (row.contains(PieceConst.IMPLICATE_THREE_BLOCK_A) || row.contains(PieceConst.IMPLICATE_THREE_BLOCK_B) || row
            .contains(PieceConst.IMPLICATE_THREE_BLOCK_C) || row.contains(PieceConst.IMPLICATE_THREE_BLOCK_D) || row
            .contains(PieceConst.IMPLICATE_THREE_BLOCK_E) || row.contains(PieceConst.IMPLICATE_THREE_BLOCK_F) || row
            .contains(PieceConst.IMPLICATE_THREE_BLOCK_G) || row.contains(PieceConst.IMPLICATE_THREE_BLOCK_H) || row
            .contains(PieceConst.IMPLICATE_THREE_BLOCK_I) || row.contains(PieceConst.IMPLICATE_THREE_BLOCK_J)) {
            strategy[4]++;
            return;
        }

        if (row.contains(PieceConst.IMPLICATE_TWO_A) || row.contains(PieceConst.IMPLICATE_TWO_B) || row
            .contains(PieceConst.IMPLICATE_TWO_C) || row.contains(PieceConst.IMPLICATE_TWO_D)) {
            strategy[5]++;
            return;
        }

        if (row.contains(PieceConst.IMPLICATE_ONE_A) || row.contains(PieceConst.IMPLICATE_ONE_B)) {
            strategy[6]++;
        }
    }
}
