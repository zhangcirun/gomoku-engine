package controller;

import gui.Chessboard;

public class GomokuController {
    public boolean isFiveInLine(int[][] chess, int xArrayPosition, int yArrayPosition){
        int pieceType = chess[xArrayPosition][yArrayPosition];

        return verticalCheck(chess, xArrayPosition, yArrayPosition, pieceType) ||
                horizontalCheck(chess, xArrayPosition, yArrayPosition, pieceType) ||
                diagonalCheck(chess, xArrayPosition, yArrayPosition, pieceType)||
                antiDiagonalCheck(chess, xArrayPosition, yArrayPosition, pieceType);
    }

    private boolean verticalCheck(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        int numOfPiecesInLine = 1;
        //check upward
        for(int i = 1; i < 5 ; i++){
            if(Chessboard.validateArrayPosition(yArrayPosition + i) &&
                    chess[xArrayPosition][yArrayPosition + i] == pieceType)
            {
                numOfPiecesInLine++;
            }
            else{
                break;
            }
        }

        //check downward
        for(int i = 1; i < 5 ; i++){
            if(Chessboard.validateArrayPosition(yArrayPosition - i) &&
                    chess[xArrayPosition][yArrayPosition - i] == pieceType)
            {
                numOfPiecesInLine++;
            }
            else{
                break;
            }
        }
        return numOfPiecesInLine >= 5;
    }

    private boolean horizontalCheck(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        int numOfPiecesInLine = 1;
        //check right
        for(int i = 1; i < 5 ; i++){
            if(Chessboard.validateArrayPosition(xArrayPosition + i) &&
                    chess[xArrayPosition + i][yArrayPosition] == pieceType)
            {
                numOfPiecesInLine++;
            }
            else{
                break;
            }
        }

        //check left
        for(int i = 1; i < 5 ; i++){
            if(Chessboard.validateArrayPosition(xArrayPosition - i) &&
                    chess[xArrayPosition - i][yArrayPosition] == pieceType)
            {
                numOfPiecesInLine++;
            }
            else{
                break;
            }
        }
        return numOfPiecesInLine >= 5;
    }

    private boolean diagonalCheck(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        int numOfPiecesInLine = 1;

        //check piece to left top
        for(int i = 1; i < 5; i++){
            if(Chessboard.validateArrayPosition(xArrayPosition - i) &&
                    Chessboard.validateArrayPosition(yArrayPosition - i) &&
                    chess[xArrayPosition - i][yArrayPosition - i] == pieceType)
            {
                numOfPiecesInLine++;
            }
            else{
                break;
            }
        }

        //check piece to right bottom
        for(int i = 1; i < 5; i++){
            if(Chessboard.validateArrayPosition(xArrayPosition + i) &&
                    Chessboard.validateArrayPosition(yArrayPosition + i) &&
                    chess[xArrayPosition + i][yArrayPosition + i] == pieceType)
            {
                numOfPiecesInLine++;
            }
            else{
                break;
            }
        }

        return numOfPiecesInLine >= 5;
    }

    private boolean antiDiagonalCheck(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        int numOfPiecesInLine = 1;

        //check piece to right top
        for(int i = 1; i < 5; i++){
            if(Chessboard.validateArrayPosition(xArrayPosition + i) &&
                    Chessboard.validateArrayPosition(yArrayPosition - i) &&
                    chess[xArrayPosition + i][yArrayPosition - i] == pieceType)
            {
                numOfPiecesInLine++;
            }
            else{
                break;
            }
        }

        //check piece to left bottom
        for(int i = 1; i < 5; i++){
            if(Chessboard.validateArrayPosition(xArrayPosition - i) &&
                    Chessboard.validateArrayPosition(yArrayPosition + i) &&
                    chess[xArrayPosition - i][yArrayPosition + i] == pieceType)
            {
                numOfPiecesInLine++;
            }
            else{
                break;
            }
        }

        return numOfPiecesInLine >= 5;
    }
}
