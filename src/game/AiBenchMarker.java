package game;

import ai.Agent;
import ai.constant.AiConst;
import ai.utility.ChessboardScanUtils;
import ai.utility.HeuristicChessboardUtils;
import gui.constant.GuiConst;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is an AI agent for undergoing agent performance test
 *
 * @author Cirun Zhang
 * @version 1.0
 */
public class AiBenchMarker extends Agent {
    private static List<int[]> moveCandidates = new ArrayList<>();

    /**
     * Return the next move
     *
     * @param chess The chessboard
     * @return Position information of the next move
     */
    public static int[] nextMove(int[][] chess) {
        moveCandidates.clear();

        if (isOpening(chess)) {
            return openingStrategy();
        } else {
            int currentMaxScore = Integer.MIN_VALUE;
            for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
                for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                    //for each empty tiles, calculates their marks
                    if (chess[i][j] == AiConst.EMPTY_STONE) {
                        int score = totalMark(chess, i, j);
                        if (score > currentMaxScore) {
                            currentMaxScore = score;
                            //Add to the head of the candidate list
                            moveCandidates.add(0, new int[] {i, j});
                        }
                    }
                }
            }
            if (moveCandidates.size() == 0) {
                System.err.println("cant");
            }
            int randomIndex = ThreadLocalRandom.current().nextInt(0, (moveCandidates.size() / 3) + 1);
            int[] randomCandidate = moveCandidates.get(randomIndex);
            return new int[] {randomCandidate[0], randomCandidate[1], aiPieceType};
        }
    }

    /**
     * Calculate the total mark for both AI and human
     *
     * @param chess The chessboard
     * @param x     X-coordinate of the piece
     * @param y     Y-coordinate of the piece
     * @return Evaluation score
     */
    private static int totalMark(int[][] chess, int x, int y) {
        return markPiece(chess, x, y, -1) + markPiece(chess, x, y, 1);
    }

    private static int markPiece(int[][] chess, int x, int y, int pieceType) {
        return
            HeuristicChessboardUtils.eval(ChessboardScanUtils.horizontalAdjacentPieces10(chess, x, y, pieceType, '1'))
                + HeuristicChessboardUtils
                .eval(ChessboardScanUtils.verticalAdjacentPieces10(chess, x, y, pieceType, '1'))
                + HeuristicChessboardUtils
                .eval(ChessboardScanUtils.diagonalAdjacentPieces10(chess, x, y, pieceType, '1'))
                + HeuristicChessboardUtils
                .eval(ChessboardScanUtils.antiDiagonalAdjacentPieces10(chess, x, y, pieceType, '1'));
    }

    /**
     * Generates opening strategy randomly
     *
     * @return A random opening move
     */
    private static int[] openingStrategy() {
        //random integer between [min, max]
        int randomX = ThreadLocalRandom.current().nextInt(5, 8 + 1);
        int randomY = ThreadLocalRandom.current().nextInt(5, 8 + 1);
        return new int[] {randomX, randomY, aiPieceType};
    }
}
