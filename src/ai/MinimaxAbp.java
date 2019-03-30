package ai;

import ai.constant.AiConst;
import ai.utility.AiUtils;
import ai.utility.HeuristicChessboardUtils;
import gui.Background;
import gui.constant.GuiConst;

import java.util.List;

/**
 * This class is an AI agent uses miniMax, alpha beta pruning and aspiration search
 *
 * @author Cirun Zhang
 * @version 1.2
 */
public class MinimaxAbp extends Agent {
    private MinimaxAbp() {
    }

    /**
     * Starts miniMax search
     *
     * @param chess 2-dimension array represents the chessboard
     * @return Coordinates of the best next move for the AI
     */
    public static int[] startMiniMax(int[][] chess) {
        if (isOpening(chess)) {
            return new int[] {7, 7, aiPieceType};
        } else {
            Node root = new Node(-1, -1, -1, chess);
            Node result = miniMax(root, 1, aiPieceType, true);
            System.out.println("x " + result.getX() + "y " + result.getY() + "score " + result.getScore());
            return new int[] {result.getX(), result.getY(), aiPieceType};
        }
    }

    /**
     * Provides depth-first miniMax search for the game tree and returns the most valuable node
     *
     * @param root      Current tree node
     * @param depth     Current Depth of the node
     * @param pieceType Identification for players, 1 for black piece and -1 for white piece
     * @param isMax     Identification for max nodes and min nodes
     * @return The most valuable node
     */
    private static Node miniMax(Node root, int depth, int pieceType, boolean isMax) {
        count++;
        if (depth >= maximumSearchDepth) {
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Node bestChild = null;

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                //if the tile is empty
                if (chess[i][j] == 0) {
                    //create child node and search it
                    int[][] dummy = AiUtils.copyArray(chess);
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
            }
        }
        root.setScore(bestScore);

        if (depth == 1) {
            System.out.println("Minimax total nodes: " + count);
            count = 0;
            return bestChild;
        }

        return root;
    }

    /**
     * Starts depth-first miniMax search with alpha beta pruning technique
     *
     * @param chess 2-dimension array represents the chessboard
     * @return Coordinates of the best next move for the AI
     */
    public static int[] startAlphaBetaPruning(int[][] chess) {
        if (isOpening(chess)) {
            return new int[] {7, 7, aiPieceType};
        } else {
            Node root = new Node(-1, -1, -1, chess);
            Node bestMove;

            bestMove = maximizer(root, 1, aiPieceType, Integer.MIN_VALUE, Integer.MAX_VALUE);

            int[] result = new int[2];
            result[0] = bestMove.getX();
            result[1] = bestMove.getY();
            System.out.println("x " + bestMove.getX() + "y " + bestMove.getY() + "score " + bestMove.getScore());

            return result;
        }
    }

    /**
     * Maximizer of alpha beta pruning, it prunes the current node when the alpha value of current node is greater
     * than or equal to the beta value of its ancient node
     *
     * @param root      Current tree node
     * @param depth     Current depth of the node
     * @param pieceType Identification of players, 1 for black and -1 for white
     * @param alpha     alpha value for Max node
     * @param beta      beta value for Min node
     * @return The most valuable node
     */
    private static Node maximizer(Node root, int depth, int pieceType, int alpha, int beta) {
        count++;
        //base case
        if (depth >= maximumSearchDepth) {
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore = Integer.MIN_VALUE;
        Node bestChild = null;

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                // for each possible moves, do depth first search
                if (chess[i][j] == 0) {
                    int[][] nextMove = AiUtils.nextMoveChessboard(chess, i, j, pieceType);
                    Node child = new Node(i, j, -1, nextMove);

                    int score = minimizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();
                    if (score > bestScore) {
                        bestScore = score;
                        bestChild = child;
                        alpha = score;
                    }
                    //beta pruning
                    if (score >= beta) {
                        root.setScore(bestScore);
                        return root;
                    }
                }
            }
        }

        root.setScore(bestScore);

        if (depth == 1) {
            System.out.println("total nodes: " + count);
            count = 0;
            return bestChild;
        }

        return root;
    }

    /**
     * Minimizer of alpha beta pruning, it prunes the current node when the beta value of current node is less than
     * or equal to the alpha value of its ancient node
     *
     * @param root      Current tree node
     * @param depth     Current depth of the node
     * @param pieceType Identification of players, 1 for black and -1 for white
     * @param alpha     Alpha value for Max node
     * @param beta      Beta value for Min node
     * @return The most valuable node
     */
    private static Node minimizer(Node root, int depth, int pieceType, int alpha, int beta) {
        count++;
        //base case
        if (depth >= maximumSearchDepth) {
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore = Integer.MAX_VALUE;
        Node bestChild = null;

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                // for each possible moves, do depth first search
                if (chess[i][j] == 0) {
                    int[][] nextMove = AiUtils.nextMoveChessboard(chess, i, j, pieceType);
                    Node child = new Node(i, j, -1, nextMove);

                    int score = maximizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();
                    if (score < bestScore) {
                        bestScore = score;
                        bestChild = child;
                        beta = score;
                    }
                    //alpha pruning
                    if (score <= alpha) {
                        root.setScore(bestScore);
                        return root;
                    }
                }
            }
        }

        root.setScore(bestScore);

        if (depth == 1) {
            return bestChild;
        }

        return root;
    }

    /**
     * Starts depth first miniMax search with alpha beta pruning, and the nodes are
     * pre-sorted by the heuristic function(h2).
     *
     * @param chess 2-dimension array represents the chessboard
     * @return most valuable node
     */
    public static int[] startAlphaBetaPruningWithSort(int[][] chess) {
        if (isOpening(chess)) {
            return new int[] {7, 7, aiPieceType};
        } else {
            //instantiate root node with preset x and y to the center of the chessboard(good for pruning)
            Node root = new Node(-1, -1, -1, chess);
            Node bestMove;

            bestMove = maximizerWithSort(root, 1, aiPieceType, Integer.MIN_VALUE, Integer.MAX_VALUE);

            int[] result = bestMove.getCoordinates();
            Background.addMessage(
                "Computer move : (x, " + result[0] + ") (y, " + result[1] + ") score " + bestMove.getScore());

            return new int[] {result[0], result[1], aiPieceType};
        }
    }

    /**
     * Maximizer of alpha beta pruning, every possible moves are sorted by the h2 heuristic function
     *
     * @param root      Current tree node
     * @param depth     Current depth of the node
     * @param pieceType Identification of players, 1 for black piece and -1 for white
     * @param alpha     Alpha value for Max node
     * @param beta      Beta value for Min node
     * @return The most valuable node
     */
    private static Node maximizerWithSort(Node root, int depth, int pieceType, int alpha, int beta) {
        count++;
        //base case
        if (depth >= maximumSearchDepth) {
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore = Integer.MIN_VALUE;
        Node bestChild = null;

        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess, 24);

        //detect five in row
        if (depth == 1) {
            Node n = terminalCheck(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        for (int[] move : moves) {
            int newX = move[0];
            int newY = move[1];
            int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
            Node child = new Node(newX, newY, -1, nextMove);

            int score = minimizerWithSort(child, depth + 1, pieceType * -1, alpha, beta).getScore();
            if (score > bestScore) {
                bestScore = score;
                bestChild = child;
                alpha = score;
            }
            //beta pruning
            if (score >= beta) {
                break;
            }
        }

        root.setScore(bestScore);

        if (depth == 1) {
            System.out.println("total nodes: " + count);
            Background.addMessage("Total nodes: " + count);
            count = 0;
            return bestChild;
        }

        return root;
    }

    /**
     * Minimizer of alpha beta pruning, every possible moves are sorted by the h2 heuristic function
     *
     * @param root      Current tree node
     * @param depth     Current depth of the node
     * @param pieceType Identification of players, 1 for black piece and -1 for white
     * @param alpha     Alpha value for Max node
     * @param beta      Beta value for Min node
     * @return The most valuable node
     */
    private static Node minimizerWithSort(Node root, int depth, int pieceType, int alpha, int beta) {
        count++;
        //base case
        if (depth >= maximumSearchDepth) {
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore = Integer.MAX_VALUE;
        Node bestChild = null;

        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess, 24);
        if (depth == 1) {
            Node n = terminalCheck(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        for (int[] move : moves) {
            int newX = move[0];
            int newY = move[1];
            int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
            Node child = new Node(newX, newY, -1, nextMove);

            int score = maximizerWithSort(child, depth + 1, pieceType * -1, alpha, beta).getScore();
            if (score < bestScore) {
                bestScore = score;
                bestChild = child;
                beta = score;
            }
            //alpha pruning
            if (score <= alpha) {
                break;
            }
        }

        root.setScore(bestScore);

        if (depth == 1) {
            System.out.println("total nodes: " + count);
            Background.addMessage("Total nodes: " + count);
            count = 0;
            return bestChild;
        }

        return root;
    }

    /**
     * Starts aspiration search
     *
     * @param chess 2-dimension array represents the chessboard
     * @return Coordinates of the best next move for the AI
     */
    public static int[] aspirationSearch(int[][] chess, int expectScore) {
        int expectedLowerBound = expectScore - AiConst.WINDOW_SIZE_ASPIRATION;
        int expectedUpperBound = expectScore + AiConst.WINDOW_SIZE_ASPIRATION;
        Node root = new Node(-1, -1, -1, chess);
        Node bestMove = maximizerWithSort(root, 0, -1, expectedLowerBound, expectedUpperBound);
        int resultScore = bestMove.getScore();

        if (resultScore > expectedLowerBound && resultScore < expectedUpperBound) {
            //expected
            System.out.println("expected");
            return bestMove.getCoordinatesAndScore();
        }

        if (resultScore >= expectedUpperBound) {
            //fail high
            System.out.println("fail high");
            Node failHighNode = maximizerWithSort(root, 0, -1, resultScore - 1, Integer.MAX_VALUE);
            return failHighNode.getCoordinatesAndScore();
        }

        if (resultScore <= expectedLowerBound) {
            //fail low
            System.out.println("fail low");
            Node failLowNode = maximizerWithSort(root, 0, -1, Integer.MIN_VALUE, resultScore + 1);

            return failLowNode.getCoordinatesAndScore();
        }

        return null;
    }
}

/**
 * This class represents the node of the search tree
 *
 * @author Cirun Zhang
 * @version 1.1
 */
class Node {
    /**
     * X coordinate of the last move
     */
    private int x;

    /**
     * Y coordinate of the last move
     */
    private int y;

    /**
     * Score of the node
     */
    private int score;

    /**
     * Chessboard of the board
     */
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

    int[] getCoordinates() {
        return new int[] {this.x, this.y};
    }

    int[] getCoordinatesAndScore() {
        return new int[] {this.x, this.y, this.score};
    }
}