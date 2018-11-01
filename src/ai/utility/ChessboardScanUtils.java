package ai.utility;

import gui.Chessboard;
import gui.constant.GuiConst;

/**
 * This class is an utility class for scanning the chessboard
 *
 * @author Chang tz'u jun
 */
public class ChessboardScanUtils {
    private ChessboardScanUtils() {
    }

    /**
     * Get the adjacent 10 pieces surround by the target piece vertically
     *
     * @param chess          The chessboard
     * @param xArrayPosition X coordinate of the target piece
     * @param yArrayPosition Y coordinate of the target piece
     * @param pieceType      Piece type of the AI
     * @param c              The char represent the centre piece
     * @return A string represent the nearby pieces
     */
    public static String verticalAdjacentPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType,
        char c) {
        StringBuilder builder = new StringBuilder();
        //check from left to target
        for (int i = xArrayPosition - 5; i < xArrayPosition; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                //0 for empty, 1 for ally, 2 for opponent
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to right
        for (int i = xArrayPosition + 1; i < xArrayPosition + 6; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    /**
     * Get the adjacent 10 pieces surround by the target piece horizontally
     *
     * @param chess          The chessboard
     * @param xArrayPosition X coordinate of the target piece
     * @param yArrayPosition Y coordinate of the target piece
     * @param pieceType      Piece type of the AI
     * @param c              The char represent the centre piece
     * @return A string represent the nearby pieces
     */
    public static String horizontalAdjacentPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType,
        char c) {
        StringBuilder builder = new StringBuilder();
        //check from top to target
        for (int i = yArrayPosition - 5; i < yArrayPosition; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to bottom
        for (int i = yArrayPosition + 1; i < yArrayPosition + 6; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    /**
     * Get the adjacent 10 pieces surround by the target piece diagonally
     *
     * @param chess          The chessboard
     * @param xArrayPosition X coordinate of the target piece
     * @param yArrayPosition Y coordinate of the target piece
     * @param pieceType      Piece type of the AI
     * @param c              The char represent the centre piece
     * @return A string represent the nearby pieces
     */
    public static String diagonalAdjacentPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType,
        char c) {
        StringBuilder builder = new StringBuilder();
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

        return builder.toString();
    }

    /**
     * Get the adjacent 10 pieces surround by the target piece anti-diagonally
     *
     * @param chess          The chessboard
     * @param xArrayPosition X coordinate of the target piece
     * @param yArrayPosition Y coordinate of the target piece
     * @param pieceType      Piece type of the AI
     * @param c              The char represent the centre piece
     * @return A string represent the nearby pieces
     */
    public static String antiDiagonalAdjacentPieces(int[][] chess, int xArrayPosition, int yArrayPosition,
        int pieceType, char c) {
        StringBuilder builder = new StringBuilder();
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
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            int piece = chess[x][i];
            if (i == y || i == lastY) {
                builder.append("t");
            } else {
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }
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
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            int piece = chess[i][y];
            if (i == x || i == lastX) {
                builder.append("t");
            } else {
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }
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
        StringBuilder builder = new StringBuilder();
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
        StringBuilder builder = new StringBuilder();
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
        return builder.toString();
    }
}
