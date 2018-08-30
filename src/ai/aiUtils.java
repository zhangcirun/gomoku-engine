package ai;

import gui.constant.GuiConst;
import observer.GomokuUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This class is a tool class which provides some useful methods
 * for ai package
 *
 * @author  Chang ta'z jun
 * @version Version 1.0
 */
public class aiUtils {
    private aiUtils() {
    }

    private static Comparator<int[]> moveComparator = new Comparator<int[]>() {
        @Override public int compare(int[] moveOne, int[] moveTwo) {
            return moveOne[2] - moveTwo[2];
        }
    };

    /**
     * Returns new array with same contents
     *
     * @param array Original 2-dimension array
     * @return Copy of the array
     */
    static int[][] copyArray(int[][] array) {
        int[][] a = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            a[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return a;
    }

    /**
     * Returns new array after next move
     *
     * @param chess     Original chessboard
     * @param x         X coordinate of next move
     * @param y         Y coordinate of next move
     * @param pieceType Type of piece, 1 for black and -1 for white
     * @return new array after next move
     */
    static int[][] nextMoveChessboard(int[][] chess, int x, int y, int pieceType) {
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
     * @return List contains all possible moves store in an array [x, y, dist] individually
     */
    public static List<int[]> moveGeneratorWithSort(int[][] chess, int x, int y) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                if (chess[i][j] == 0) {
                    moves.add(new int[] {i, j, GomokuUtils.ManhattanDistance(i, j, x, y)});
                }
            }
        }
        moves.sort(moveComparator);
        return moves;
    }

}
