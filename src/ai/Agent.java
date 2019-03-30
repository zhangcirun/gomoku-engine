package ai;

import ai.constant.AiConst;
import ai.utility.AiUtils;
import gui.constant.GuiConst;
import observer.GameStatusChecker;

import java.util.List;

/**
 * This abstract class is the prototype of all AI agents.
 *
 * @author Cirun Zhang
 * @version 1.1
 */
public abstract class Agent {
    static int maximumSearchDepth = 5;

    static int count = 0;

    public static int aiPieceType = -1;

    /**
     * Check whether the chessboard is empty or not
     *
     * @param chess The chessboard
     * @return A boolean indicates whether the chessboard is empty or not
     */
    protected static boolean isOpening(int[][] chess) {
        if (chess[7][7] != AiConst.EMPTY_STONE) {
            return false;
        }
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                if (chess[i][j] != AiConst.EMPTY_STONE) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Detects whether next move can win the game or not, if next move wins then returns that node,
     *
     * @param chess     2-dimensional array represents the chessboard
     * @param moves     List contains all possible move represents as an array [x, y, score]
     * @param pieceType Identification of black(1) and white(-1)
     * @return Node leeds to win or null if no matched situation
     */
    static Node terminalCheck(int[][] chess, List<int[]> moves, int pieceType) {
        //detects if next move can win directly
        for (int[] move : moves) {
            int newX = move[0];
            int newY = move[1];
            int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
            if (GameStatusChecker.isFiveInLine(nextMove, newX, newY)) {
                return new Node(move[0], move[1], 500000, nextMove);
            }
        }

        //prevents opponent's direct win
        for (int[] move : moves) {
            int newX = move[0];
            int newY = move[1];
            int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType * -1);
            if (GameStatusChecker.isFiveInLine(nextMove, newX, newY)) {
                return new Node(move[0], move[1], 500000, nextMove);
            }
        }

        return null;
    }

    public static void setMaximumSearchDepth(int depth) {
        maximumSearchDepth = depth;
    }
}
