/**
 * This class is used to determine whether the game is over or not
 *
 * @author Chang ta'z jun
 * @version Version 1.1
 */
package controller;

import gui.Chessboard;

public class GomokuController {
    /**
     * Determines whether five pieces are in one row or not
     *
     * @param chess The 2-dimensional array represents pieces locations
     * @param xArrayIndex The x axis coordinate of the new piece
     * @param yArrayIndex The y axis coordinate of the new piece
     * @return Returns true if the game is over and returns false if the game is not over
     */
    public static boolean isFiveInLine(int[][] chess, int xArrayIndex, int yArrayIndex){
        int pieceType = chess[xArrayIndex][yArrayIndex];

        return verticalCheck(chess, xArrayIndex, yArrayIndex, pieceType) ||
                horizontalCheck(chess, xArrayIndex, yArrayIndex, pieceType) ||
                diagonalCheck(chess, xArrayIndex, yArrayIndex, pieceType)||
                antiDiagonalCheck(chess, xArrayIndex, yArrayIndex, pieceType);
    }

    /**
     * Determine whether five pieces are in the same vertical row
     *
     * @param chess The 2-dimensional array represents pieces locations
     * @param xArrayPosition The x axis coordinate of the new piece
     * @param yArrayPosition The y axis coordinate of the new piece
     * @param pieceType 1 for black piece and -1 for white piece
     * @return Returns true if five pieces are in the same vertical row
     */
    private static boolean verticalCheck(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        int numOfPiecesInLine = 1;
        //check upward
        for(int i = 1; i < 5 ; i++){
            if(Chessboard.validateArrayIndex(yArrayPosition + i) &&
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
            if(Chessboard.validateArrayIndex(yArrayPosition - i) &&
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

    /**
     * Determine whether five pieces are in the same horizontal row
     *
     * @param chess The 2-dimensional array represents pieces locations
     * @param xArrayPosition The x axis coordinate of the new piece
     * @param yArrayPosition The y axis coordinate of the new piece
     * @param pieceType 1 for black piece and -1 for white piece
     * @return Returns true if five pieces are in the same horizontal row
     */
    private static boolean horizontalCheck(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        int numOfPiecesInLine = 1;
        //check right
        for(int i = 1; i < 5 ; i++){
            if(Chessboard.validateArrayIndex(xArrayPosition + i) &&
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
            if(Chessboard.validateArrayIndex(xArrayPosition - i) &&
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

    /**
     * Determine whether five pieces are in the same line from right bottom to left top
     *
     * @param chess The 2-dimensional array represents pieces locations
     * @param xArrayPosition The x axis coordinate of the new piece
     * @param yArrayPosition The y axis coordinate of the new piece
     * @param pieceType 1 for black piece and -1 for white piece
     * @return Returns true if five pieces are in the same line from right bottom to left top
     */
    private static boolean diagonalCheck(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        int numOfPiecesInLine = 1;

        //check piece to left top
        for(int i = 1; i < 5; i++){
            if(Chessboard.validateArrayIndex(xArrayPosition - i) &&
                    Chessboard.validateArrayIndex(yArrayPosition - i) &&
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
            if(Chessboard.validateArrayIndex(xArrayPosition + i) &&
                    Chessboard.validateArrayIndex(yArrayPosition + i) &&
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

    /**
     * Determine whether five pieces are in the same line from left bottom to right top
     *
     * @param chess The 2-dimensional array represents pieces locations
     * @param xArrayPosition The x axis coordinate of the new piece
     * @param yArrayPosition The y axis coordinate of the new piece
     * @param pieceType 1 for black piece and -1 for white piece
     * @return Returns true if five pieces are in the same line from left bottom to right top
     */
    private static boolean antiDiagonalCheck(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType){
        int numOfPiecesInLine = 1;

        //check piece to right top
        for(int i = 1; i < 5; i++){
            if(Chessboard.validateArrayIndex(xArrayPosition + i) &&
                    Chessboard.validateArrayIndex(yArrayPosition - i) &&
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
            if(Chessboard.validateArrayIndex(xArrayPosition - i) &&
                    Chessboard.validateArrayIndex(yArrayPosition + i) &&
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
