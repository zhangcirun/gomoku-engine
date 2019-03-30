package ai.utility;

import ai.GreedyBestFirst;
import gui.constant.GuiConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class is an utility class for providing some array manipulation functions
 *
 * @author cirun zhang
 * @version Version 1.0
 */

public class AiUtils {
    private AiUtils() {
    }

    private static Comparator<int[]> moveComparator_asc = new Comparator<int[]>() {
        @Override
        public int compare(int[] moveOne, int[] moveTwo) {
            return moveOne[2] - moveTwo[2];
        }
    };

    private static Comparator<int[]> moveComparator_desc = new Comparator<int[]>() {
        @Override
        public int compare(int[] moveOne, int[] moveTwo) {
            return moveTwo[2] - moveOne[2];
        }
    };

    /**
     * Returns new 2-dimensional array with same contents (deep copy)
     *
     * @param array Original 2-dimension array
     * @return      Deep Copy of the array
     */
    public static int[][] copyArray(int[][] array) {
        int[][] a = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            a[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return a;
    }

    /**
     * Returns the new chessboard after next move
     *
     * @param chess     Original chessboard
     * @param x         X coordinate of next move
     * @param y         Y coordinate of next move
     * @param pieceType Type of piece, 1 for black and -1 for white
     * @return          new array after next move
     */
    public static int[][] nextMoveChessboard(int[][] chess, int x, int y, int pieceType) {
        int[][] a = copyArray(chess);
        a[x][y] = pieceType;
        return a;
    }

    /**
     * Generates all possible moves and sorted by their manhattan distances
     * to the last move
     *
     * @param chess 2-dimension array represents the chessboard
     * @param x     X coordinate of the last move
     * @param y     Y coordinate of the last move
     * @return      A list contains all possible moves with the format of [x, y, dist]
     */
    public static List<int[]> moveGeneratorWithDistanceSort(int[][] chess, int x, int y) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                if (chess[i][j] == 0) {
                    moves.add(new int[] {i, j, AiUtils.ManhattanDistance(i, j, x, y)});
                }
            }
        }
        moves.sort(moveComparator_asc);
        return moves;

    }

    /**
     * Generates all possible moves and sorted by a heuristic function{@see GreedyBestFirst}
     *
     * @param chess 2-dimension array represents the chessboard
     * @param n     Number of required moves
     * @return      A List contains the best n moves with the format of [x, y, value]
     */
    public static List<int[]> moveGeneratorWithHeuristicSort(int[][] chess, int n) {
        List<int[]> moves = new ArrayList<>(100);
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                if (chess[i][j] == 0) {
                    moves.add(new int[] {i, j, GreedyBestFirst.totalMark(chess, i, j)});
                }
            }
        }

        moves.sort(moveComparator_desc);

        if (moves.size() > n) {
            return moves.subList(0, n);
        } else {
            return moves;
        }
    }

    public static double safeDivide(double a, double b) {
        if (b == 0 && a >= 0) {
            return Double.POSITIVE_INFINITY;
        }

        if (b == 0 && a < 0) {
            return Double.NEGATIVE_INFINITY;
        }
        return a / b;
    }

    /**
     * Get manhattan distance between two sets of coordinates
     * @param x1 x coordinate of first piece
     * @param y1 y coordinate of first piece
     * @param x2 x coordinate of second piece
     * @param y2 y coordinate of second piece
     * @return distance
     */
    public static int ManhattanDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
