package ai.utility;

import ai.constant.AiConst;
import gui.Chessboard;
import gui.constant.GuiConst;

/**
 * This class is an utility class for scanning the chessboard
 *
 * @author cirun zhang
 * @version 1.1
 */
public class ChessboardScanUtils {
    private static StringBuilder builder = new StringBuilder();

    private ChessboardScanUtils() {
    }

    /**
     * Returns 8 horizontal pieces surround a specific piece
     *
     * @param chess          The 2-dimension array represents the chessboard
     * @param xArrayPosition X-coordinate of the piece
     * @param yArrayPosition Y-coordinate of the piece
     * @param pieceType      Type of the piece
     * @return A horizontal row of pieces in String format
     */
    public static String horizontalAdjacentPieces8(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType) {
        StringBuilder builder = new StringBuilder();
        //check from left to target
        for (int i = xArrayPosition - 4; i < xArrayPosition; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                //0 for empty, 1 for ally, 2 for opponent
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to right
        for (int i = xArrayPosition + 1; i < xArrayPosition + 5; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    /**
     * Returns 8 vertical pieces surround a specific piece
     *
     * @param chess          The 2-dimension array represents the chessboard
     * @param xArrayPosition X-coordinate of the piece
     * @param yArrayPosition Y-coordinate of the piece
     * @param pieceType      Type of the piece
     * @return A vertical row of pieces in String format
     */
    public static String verticalAdjacentPieces8(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType) {
        StringBuilder builder = new StringBuilder();
        //check from top to target
        for (int i = yArrayPosition - 4; i < yArrayPosition; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to bottom
        for (int i = yArrayPosition + 1; i < yArrayPosition + 5; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    /**
     * Returns 8 diagonal pieces surround a specific piece
     *
     * @param chess          The 2-dimension array represents the chessboard
     * @param xArrayPosition X-coordinate of the piece
     * @param yArrayPosition Y-coordinate of the piece
     * @param pieceType      Type of the piece
     * @return A diagonal row of pieces in String format
     */
    public static String diagonalAdjacentPieces8(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType) {
        StringBuilder builder = new StringBuilder();
        //check from left top to target
        for (int i = 4; i > 0; i--) {
            if (Chessboard.validateArrayIndex(xArrayPosition - i) && Chessboard
                .validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition - i][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to right bottom
        for (int i = 1; i < 5; i++) {
            if (Chessboard.validateArrayIndex(xArrayPosition + i) && Chessboard
                .validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    /**
     * Returns 8 anti-diagonal pieces surround a specific piece
     *
     * @param chess          The 2-dimension array represents the chessboard
     * @param xArrayPosition X-coordinate of the piece
     * @param yArrayPosition Y-coordinate of the piece
     * @param pieceType      Type of the piece
     * @return A anti-diagonal row of pieces in String format
     */
    public static String antiDiagonalAdjacentPieces8(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType) {
        StringBuilder builder = new StringBuilder();
        //check from right top to target
        for (int i = 4; i > 0; i--) {
            if (Chessboard.validateArrayIndex(xArrayPosition + i) && Chessboard
                .validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to left bottom
        for (int i = 1; i < 5; i++) {
            if (Chessboard.validateArrayIndex(xArrayPosition - i) && Chessboard
                .validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition - i][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }



    /**
     * Get the adjacent 10 vertical pieces surround by the target piece
     *
     * @param chess          The chessboard
     * @param xArrayPosition X coordinate of the target piece
     * @param yArrayPosition Y coordinate of the target piece
     * @param pieceType      Piece type of the AI
     * @param c              The char represent the centre piece
     * @return A string represent the nearby pieces
     */
    public static String verticalAdjacentPieces10(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType,
        char c) {
        //check from top to target
        for (int i = xArrayPosition - 5; i < xArrayPosition; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                //0 for empty, 1 for ally, 2 for opponent
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to bottom
        for (int i = xArrayPosition + 1; i < xArrayPosition + 6; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }
        builder.setLength(0);
        return builder.toString();
    }

    /**
     * Get the adjacent 10 horizontal pieces surround by the target piece
     *
     * @param chess          The chessboard
     * @param xArrayPosition X coordinate of the target piece
     * @param yArrayPosition Y coordinate of the target piece
     * @param pieceType      Piece type of the AI
     * @param c              The char represent the centre piece
     * @return A string represent the nearby pieces
     */
    public static String horizontalAdjacentPieces10(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType,
        char c) {
        //check from left to target
        for (int i = yArrayPosition - 5; i < yArrayPosition; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to right
        for (int i = yArrayPosition + 1; i < yArrayPosition + 6; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }
        builder.setLength(0);
        return builder.toString();
    }

    /**
     * Get the adjacent 10 diagonal pieces surround by the target piece
     *
     * @param chess          The chessboard
     * @param xArrayPosition X coordinate of the target piece
     * @param yArrayPosition Y coordinate of the target piece
     * @param pieceType      Piece type of the AI
     * @param c              The char represent the centre piece
     * @return A string represent the nearby pieces
     */
    public static String diagonalAdjacentPieces10(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType,
        char c) {
        //check from left top to target
        for (int i = 5; i > 0; i--) {
            if (Chessboard.validateArrayIndex(xArrayPosition - i) && Chessboard
                .validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition - i][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to right bottom
        for (int i = 1; i < 6; i++) {
            if (Chessboard.validateArrayIndex(xArrayPosition + i) && Chessboard
                .validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.setLength(0);
        return builder.toString();
    }

    /**
     * Get the adjacent 10 anti-diagonal pieces surround by the target piece
     *
     * @param chess          The chessboard
     * @param xArrayPosition X coordinate of the target piece
     * @param yArrayPosition Y coordinate of the target piece
     * @param pieceType      Piece type of the AI
     * @param c              The char represent the centre piece
     * @return A string represent the nearby pieces
     */
    public static String antiDiagonalAdjacentPieces10(int[][] chess, int xArrayPosition, int yArrayPosition,
        int pieceType, char c) {
        //check from right top to target
        for (int i = 5; i > 0; i--) {
            if (Chessboard.validateArrayIndex(xArrayPosition + i) && Chessboard
                .validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to left bottom
        for (int i = 1; i < 6; i++) {
            if (Chessboard.validateArrayIndex(xArrayPosition - i) && Chessboard
                .validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition - i][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.setLength(0);
        return builder.toString();
    }

    /**
     * Get the pieces on the horizontal row which the given two pieces lied on it.
     *
     * @param chess     The chessboard
     * @param x         X coordinate of the first piece
     * @param y         Y coordinate of the first piece
     * @param lastX     X coordinate of the second piece
     * @param lastY     Y coordinate of the second piece
     * @param pieceType Piece type if the AI
     * @return A string represent the pieces in one row
     */
    public static String scanHorizontal(int[][] chess, int x, int y, int lastX, int lastY, int pieceType) {
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            int piece = chess[x][i];
            if (i == y || i == lastY) {
                builder.append("t");
            } else {
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.setLength(0);
        return builder.toString();
    }

    /**
     * Get the pieces on the vertical row which the given two pieces lied on it.
     *
     * @param chess     The chessboard
     * @param x         X coordinate of the first piece
     * @param y         Y coordinate of the first piece
     * @param lastX     X coordinate of the second piece
     * @param lastY     Y coordinate of the second piece
     * @param pieceType Piece type if the AI
     * @return A string represent the pieces in one row
     */
    public static String scanVertical(int[][] chess, int x, int y, int lastX, int lastY, int pieceType) {
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            int piece = chess[i][y];
            if (i == x || i == lastX) {
                builder.append("t");
            } else {
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.setLength(0);
        return builder.toString();
    }

    /**
     * Get the pieces on the diagonal row which the given two pieces lied on it.
     *
     * @param chess     The chessboard
     * @param x         X coordinate of the first piece
     * @param y         Y coordinate of the first piece
     * @param lastX     X coordinate of the second piece
     * @param lastY     Y coordinate of the second piece
     * @param pieceType Piece type if the AI
     * @return A string represent the pieces in one row
     */
    public static String scanDiagonal(int[][] chess, int x, int y, int lastX, int lastY, int pieceType) {
        //Scans the chessboard from right top to left bottom diagonally
        int upperBoundX = x;
        int upperBoundY = y;

        //get the coordinates of right top corner
        while (upperBoundY < 14 && upperBoundX > 0) {
            upperBoundY++;
            upperBoundX--;
        }

        for (int i = upperBoundX, j = upperBoundY; i <= upperBoundY && j >= upperBoundX; i++, j--) {
            if ((i == x && j == y) || (i == lastX && j == lastY)) {
                builder.append("t");
            } else {
                int piece = chess[i][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.setLength(0);
        return builder.toString();
    }

    /**
     * Get the pieces on the anti-diagonal row which the given two pieces lied on it.
     *
     * @param chess     The chessboard
     * @param x         X coordinate of the first piece
     * @param y         Y coordinate of the first piece
     * @param lastX     X coordinate of the second piece
     * @param lastY     Y coordinate of the second piece
     * @param pieceType Piece type if the AI
     * @return A string represent the pieces in one row
     */
    public static String scanAntiDiagonal(int[][] chess, int x, int y, int lastX, int lastY, int pieceType) {
        //Scans the chessboard from left top to right bottom antiDiagonally
        int upperBoundX = x;
        int upperBoundY = y;
        int lowerBoundX = x;
        int lowerBoundY = y;
        while (upperBoundX > 0 && upperBoundY > 0) {
            upperBoundX--;
            upperBoundY--;
        }

        while (lowerBoundX < 14 && lowerBoundY < 14) {
            lowerBoundX++;
            lowerBoundY++;
        }

        for (int i = upperBoundX, j = upperBoundY; i <= lowerBoundX && j <= lowerBoundY; i++, j++) {
            int piece = chess[i][j];
            if ((i == x && j == y) || (i == lastX && j == lastY)) {
                builder.append("t");
            } else {
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }

        }
        builder.setLength(0);
        return builder.toString();
    }

    /**
     * Scans threats of the chessboard from top to bottom vertically
     *
     * @param chess     2-dimension array represents the chessboard
     * @param pieceType Indicates which player moved, 1 for black -1 for white
     * @return A boolean indicates existing a threat or not
     */
    public static boolean scanVerticalThreat(int[][] chess, int pieceType) {
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                int piece = chess[i][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            if (detectThreat(builder.toString())) {
                return true;
            }
            builder.delete(0, builder.length());
        }
        builder.setLength(0);
        return false;
    }

    /**
     * Scans threats of the chessboard from left to right horizontally
     *
     * @param chess     2-dimension array represents the chessboard
     * @param pieceType Indicates which player moved, 1 for black -1 for white
     * @return A boolean indicates existing a threat or not
     */
    public static boolean scanHorizontalThreat(int[][] chess, int pieceType) {

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                int piece = chess[j][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            if (detectThreat(builder.toString())) {
                return true;
            }
            builder.delete(0, builder.length());
        }
        builder.setLength(0);
        return false;
    }

    /**
     * Scans threats of the chessboard from right top to left bottom diagonally
     *
     * @param chess     2-dimension array represents the chessboard
     * @param pieceType Indicates which player moved, 1 for black -1 for white
     * @return A boolean indicates existing a threat or not
     */

    public static boolean scanDiagonalThreat(int[][] chess, int pieceType) {
        //upper half chessboard
        for (int i = 10; i >= 0; i--) {
            int count = 0;
            for (int j = i; j <= 14; j++) {
                int piece = chess[j][count++];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            if (detectThreat(builder.toString())) {
                return true;
            }
            builder.delete(0, builder.length());
        }

        //lower half chessboard
        for (int i = 1; i <= 10; i++) {
            int count = 0;
            for (int j = i; j <= 14; j++) {
                int piece = chess[count++][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            if (detectThreat(builder.toString())) {
                return true;
            }
            builder.delete(0, builder.length());
        }
        builder.setLength(0);
        return false;
    }

    /**
     * Scans threats of the chessboard from left top to right bottom anti-diagonally.
     *
     * @param chess     2-dimension array represents the chessboard
     * @param pieceType Indicates which player moved, 1 for black -1 for white
     * @return A boolean indicates existing a threat or not
     */
    public static boolean scanAntiDiagonalThreat(int[][] chess, int pieceType) {
        //upper half chessboard
        for (int i = 4; i <= 14; i++) {
            int count = 0;
            for (int j = i; j >= 0; j--) {
                int piece = chess[count++][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            if (detectThreat(builder.toString())) {
                return true;
            }
            ;
            builder.delete(0, builder.length());
        }

        //lower half chessboard
        for (int i = 0; i <= 10; i++) {
            int count = 14;
            for (int j = i; j <= 14; j++) {
                int piece = chess[j][count--];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
            if (detectThreat(builder.toString())) {
                return true;
            }
            builder.delete(0, builder.length());
        }
        builder.setLength(0);
        return false;
    }

    /**
     * Detects whether a sequence of pieces contains threat.
     *
     * @param pieces A sequence of pieces
     * @return A boolean indicates existing a threat or not
     */
    private static boolean detectThreat(String pieces) {
        if (pieces.contains(AiConst.IMPLICATE_FOUR_DOUBLE_EMPTY)) {
            return true;
        }

        return pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_A) || pieces
            .contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_B) || pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_C)
            || pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_D) || pieces
            .contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_E) || pieces.contains(AiConst.IMPLICATE_THREE_A) || pieces
            .contains(AiConst.IMPLICATE_THREE_B) || pieces.contains(AiConst.IMPLICATE_THREE_C) || pieces
            .contains(AiConst.IMPLICATE_THREE_D);
    }
}
