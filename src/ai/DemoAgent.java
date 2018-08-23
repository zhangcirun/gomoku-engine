package ai;

import gui.constant.GuiConst;

import java.util.Arrays;

public class DemoAgent {
    private DemoAgent(){}

    public static int[][] startMiniMax(int[][] chess){
        Node root = new Node(-1, -1, -1, null, chess);
        Node result = miniMax(root, 0, -1, true).getChild();
        System.out.println("x " + result.getX() + "y " + result.getY() + "score " + result.getScore());
        return result.getChess();
    }

    public static Node miniMax(Node root, int depth, int pieceType, boolean isMax){
        if(depth >= 2){
            //@Todo
            root.setScore(evaluation(root.getChess(), pieceType));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore;
        Node bestChild = null;

        if(isMax){
            bestScore = Integer.MIN_VALUE;
        }else {
            bestScore = Integer.MAX_VALUE;
        }

        for(int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++){
            for(int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++){
                //if the tile is empty
                if(chess[i][j] == 0){
                    //create child node and search it
                    int[][] dummy = copyArray(chess);
                    dummy[i][j] = pieceType;
                    Node child = new Node(i, j, 0, null, dummy);

                    int score = miniMax(child, depth + 1, pieceType * -1, !isMax).getScore();

                    if(isMax){
                        if(score > bestScore){
                            bestScore = score;
                            bestChild = child;
                        }
                    }else{
                        if(score < bestScore){
                            bestScore = score;
                            bestChild = child;
                        }
                    }
                }
                //
            }
        }

        root.setScore(bestScore);
        root.setChild(bestChild);
        return root;
    }

    private static int[][] copyArray(int[][] chess){
        int[][] a = new int[chess.length][chess[0].length];

        for(int i = 0; i < chess.length; i++){
            a[i] = Arrays.copyOf(chess[i], chess[i].length);
        }
        return a;
    }

    public static int evaluation(int[][] chess, int pieceType){
        int scoreAlly = 0;
        int scoreOppo = 0;
        for(int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++){
            for(int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++){
                if(chess[i][j] == pieceType){
                    scoreAlly += BasicAgent.markPiece(chess, i, j, pieceType);
                }
                if(chess[i][j] == pieceType * -1){
                    scoreOppo+= BasicAgent.markPiece(chess, i, j, pieceType * -1);
                }
            }
        }

        return scoreAlly - scoreOppo;
    }
}


class Node {
    private int x;
    private int y;
    private int score;
    private Node child;
    private int[][] chess;

    Node(int x, int y, int score, Node child, int[][] chess) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.child = child;
        this.chess = chess;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getScore() {
        return this.score;
    }

    public int[][] getChess() {
        return this.chess;
    }

    public Node getChild(){
        return this.child;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setChild(Node child){
        this.child = child;
    }
}
