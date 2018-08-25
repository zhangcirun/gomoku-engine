package ai;

import gui.constant.GuiConst;

import java.util.Arrays;

public class DemoAgent {
    private DemoAgent() {
    }

    public static int[] startMiniMax(int[][] chess) {
        Node root = new Node(-1, -1, -1, chess);
        Node result = miniMax(root, 0, -1, true);
        System.out.println("x " + result.getX() + "y " + result.getY() + "score " + result.getScore());
        return new int[]{result.getX(), result.getY()};
    }

    public static Node miniMax(Node root, int depth, int pieceType, boolean isMax) {
        if (depth >= 2) {
            //@Todo
            root.setScore(HeuristicAgent.heuristic(root.getChess(), pieceType));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore;
        Node bestChild = null;

        if (isMax) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                //if the tile is empty
                if (chess[i][j] == 0) {
                    //create child node and search it
                    int[][] dummy = ToolKit.copyArray(chess);
                    dummy[i][j] = pieceType;
                    Node child = new Node(i, j, 0, dummy);

                    int score = miniMax(child, depth + 1, pieceType * -1, !isMax).getScore();

                    if (isMax) {
                        if (score > bestScore) {
                            bestScore = score;
                            bestChild = child;
                        }
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            bestChild = child;
                        }
                    }
                }
                //
            }
        }

        root.setScore(bestScore);

        if(depth == 0){
            return bestChild;
        }

        return root;
    }

    public static int evaluation(int[][] chess, int pieceType) {
        int scoreAlly = 0;
        int scoreOppo = 0;
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                if (chess[i][j] == pieceType) {
                    //scoreAlly += BasicAgent.markPiece(chess, i, j, pieceType);
                    scoreAlly += EvalAgent.eval(chess, i, j, pieceType);
                }
                if (chess[i][j] == pieceType * -1) {
                    //scoreOppo += BasicAgent.markPiece(chess, i, j, pieceType * -1);
                    scoreOppo += EvalAgent.eval(chess, i, j, pieceType * -1);
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
    private int[][] chess;

    Node(int x, int y, int score, int[][] chess) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.chess = chess;
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    int getScore() {
        return this.score;
    }

    int[][] getChess() {
        return this.chess;
    }

    void setScore(int score) {
        this.score = score;
    }
}
