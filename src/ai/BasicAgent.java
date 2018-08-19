package ai;

import ai.constant.AiConst;
import gui.Chessboard;
import java.util.HashMap;
import java.util.Map;

public class BasicAgent {
    private BasicAgent() {
    }

    public static int totalmark(int[][] chess, int x, int y, int pieceType){
        return marking(horizontalPieces(chess, x, y, pieceType)) +
            marking(verticalPieces(chess, x, y, pieceType)) +
            marking(diagonalPieces(chess, x, y, pieceType)) +
            marking(antiDiagonalPieces(chess, x, y, pieceType));
    }

    private static int marking(String pieces) {
        if (pieces.contains(AiConst.IMPLICATE_FIVE)) {
            return 50000;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_DOUBLE_EMPTY)) {
            return 4320;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_A) ||
            pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_B) ||
            pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_C) ||
            pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_D) ||
            pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_E)) {
            return 720;
        }

        if (pieces.contains(AiConst.IMPLICATE_THREE_A) ||
            pieces.contains(AiConst.IMPLICATE_THREE_B) ||
            pieces.contains(AiConst.IMPLICATE_THREE_C) ||
            pieces.contains(AiConst.IMPLICATE_THREE_D)){
            return 720;
        }

        if (pieces.contains(AiConst.IMPLICATE_TWO_A) ||
            pieces.contains(AiConst.IMPLICATE_TWO_B) ||
            pieces.contains(AiConst.IMPLICATE_TWO_C)){
            return 120;
        }

        if (pieces.contains(AiConst.IMPLICATE_ONE_A) ||
            pieces.contains(AiConst.IMPLICATE_ONE_B)){
            return 20;
        }

        return 0;

    }

    public static String horizontalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        StringBuilder builder = new StringBuilder();
        //check from left to target
        for(int i = xArrayPosition - 4; i < xArrayPosition ; i++){
            if(Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to right
        for(int i = xArrayPosition + 1; i < xArrayPosition + 5 ; i++){
            if(Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    public static String verticalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        StringBuilder builder = new StringBuilder();
        //check from top to target
        for(int i = yArrayPosition - 4; i < yArrayPosition ; i++){
            if(Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to bottom
        for(int i = yArrayPosition + 1; i < yArrayPosition + 5 ; i++){
            if(Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    public static String diagonalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        StringBuilder builder = new StringBuilder();
        //check from left top to target
        for(int i = 4; i > 0; i--){
            if(Chessboard.validateArrayIndex(xArrayPosition - i) &&
                Chessboard.validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition - i ][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to right bottom
        for(int i = 1; i < 5 ; i++){
            if(Chessboard.validateArrayIndex(xArrayPosition + i) &&
                Chessboard.validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    public static String antiDiagonalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        StringBuilder builder = new StringBuilder();
        //check from right top to target
        for(int i = 4; i > 0 ; i--){
            if(Chessboard.validateArrayIndex(xArrayPosition + i) &&
                Chessboard.validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append("1");

        //check from target to left bottom
        for(int i = 1; i < 5; i++){
            if(Chessboard.validateArrayIndex(xArrayPosition - i) &&
                Chessboard.validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition - i ][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }
}
