package ai.utility;

import gui.Chessboard;

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
}
