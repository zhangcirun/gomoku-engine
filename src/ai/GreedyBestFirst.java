package ai;

import ai.utility.ChessboardScanUtils;
import ai.utility.HeuristicChessboardUtils;
import gui.constant.GuiConst;

/**
 * This class is an AI agent uses greedy best-first search
 *
 * @author Chang ta'z jun
 * @version Version 1.0
 */
public class GreedyBestFirst extends Agent {
    private GreedyBestFirst() {
    }

    /**
     * Evaluates each empty tiles and return the best move for the next move.
     *
     * @param chess The chessboard
     * @return Position of next move
     */
    public static int[] nextMove(int[][] chess) {
        if (isOpening(chess)) {
            return new int[] {7, 7, aiPieceType};
        } else {
            int currentMaxScore = 0;
            int x = 0;
            int y = 0;
            for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
                for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                    //for each empty tiles, calculates their marks
                    if (chess[i][j] == 0) {
                        //-1 for white piece
                        int score = totalMark(chess, i, j);
                        if (score > currentMaxScore) {
                            currentMaxScore = score;
                            x = i;
                            y = j;
                        }
                    }
                }
            }
            System.out.println("currentMax: " + currentMaxScore + " " + x + " " + y);
            return new int[] {x, y, aiPieceType};
        }
    }

    /**
     * Calculate the total score for AI and human
     *
     * @param chess The chessboard
     * @param x     X-coordinate of the piece
     * @param y     Y-coordinate of the piece
     * @return The total score
     */
    public static int totalMark(int[][] chess, int x, int y) {
        return markPiece(chess, x, y, -1) + markPiece(chess, x, y, 1);
    }

    /**
     * Calculate the total score of a specific piece
     *
     * @param chess     The chess board
     * @param x         X-coordinate of the piece
     * @param y         Y-coordinate of the piece
     * @param pieceType Piece type for scoring
     * @return Score
     */
    private static int markPiece(int[][] chess, int x, int y, int pieceType) {
        return HeuristicChessboardUtils.eval(ChessboardScanUtils.horizontalAdjacentPieces8(chess, x, y, pieceType))
            + HeuristicChessboardUtils.eval(ChessboardScanUtils.verticalAdjacentPieces8(chess, x, y, pieceType))
            + HeuristicChessboardUtils.eval(ChessboardScanUtils.diagonalAdjacentPieces8(chess, x, y, pieceType))
            + HeuristicChessboardUtils.eval(ChessboardScanUtils.antiDiagonalAdjacentPieces8(chess, x, y, pieceType));
    }
}
