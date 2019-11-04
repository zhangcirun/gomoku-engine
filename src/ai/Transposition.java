package ai;

import ai.constant.AiConst;
import ai.utility.AiUtils;
import ai.utility.HeuristicChessboardUtils;
import game.GameController;
import gui.Background;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is an ai agent uses transposition table technique
 *
 * @author Cirun Zhang
 * @version 1.1
 */

public class Transposition extends Agent {

    /**
     * Transposition table implemented by a hash map
     */
    private static Map<Integer, TranspositionNode> transpositionTable = new HashMap<>(100000);

    private static int usage;

    private Transposition() {
    }

    /**
     * Entrance of transposition search
     *
     * @param chess The chessboard
     * @return Position of the next move
     */
    public static int[] startTranspositionSearch(int[][] chess) {
        usage = 0;
        if (isOpening(chess)) {
            return new int[] {7, 7, aiPieceType};
        } else {
            //instantiate root node with preset x and y to the center of the chessboard(good for pruning)
            Node root = new Node(-1, -1, -1, chess);
            Node bestMove;

            bestMove = transpositionMaximizer(root, 1, aiPieceType, Integer.MIN_VALUE, Integer.MAX_VALUE);

            int[] result = bestMove.getCoordinates();
            Background.addMessage(
                "Computer move : (x, " + result[0] + ") (y, " + result[1] + ") score " + bestMove.getScore());

            Background.addMessage("Transposition table size: " + transpositionTable.size());
            Background.addMessage("Transposition usage: " + usage);
            return new int[] {result[0], result[1], aiPieceType};
        }
    }

    /**
     * Maximizer of alpha beta pruning applied transposition search,
     * each node will be recorded in the transposition table.
     *
     * @param root      Current game node
     * @param depth     Current depth of the node in the search tree
     * @param pieceType Type of pieces
     * @param alpha     Alpha value for Max node
     * @param beta      Beta value for Min node
     * @return The most valuable node
     */
    private static Node transpositionMaximizer(Node root, int depth, int pieceType, int alpha, int beta) {
        //base case
        if (depth >= maximumSearchDepth) {
            count++;
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();

        //calculate the checksum for the chessboard
        int checkSum = getCheckSum(chess);

        int bestScore = Integer.MIN_VALUE;
        Node bestChild = null;

        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess, 24);

        //terminal check
        if (depth == 1) {
            Node n = MinimaxAbp.terminalCheck(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        /*If checksum is found in transposition table, and current depth is deeper than or equals to the
        node in the transposition table, and their minMax properties are the same, stop abp and use the
        score directly*/
        if (transpositionTable.containsKey(checkSum) && depth >= transpositionTable.get(checkSum).getDepth()
            && transpositionTable.get(checkSum).isMaxLayer()) {
            usage++;
            bestScore = transpositionTable.get(checkSum).getEvaluation();

        } else {
            //abp
            count++;
            for (int[] move : moves) {
                int newX = move[0];
                int newY = move[1];
                int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
                Node child = new Node(newX, newY, -1, nextMove);

                int score = transpositionMinimizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();

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
            //Stores the new record to the transposition table
            transpositionTable.put(checkSum, new TranspositionNode(checkSum, bestScore, depth, true));
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
     * Minimizer of alpha beta pruning with transposition search technique, each node will be hashed into the
     * transposition table
     *
     * @param root      Current game node
     * @param depth     Current depth of the node in the search tree
     * @param pieceType Type of pieces
     * @param alpha     alpha value for Max node
     * @param beta      beta value for Min node
     * @return The most valuable node
     */
    private static Node transpositionMinimizer(Node root, int depth, int pieceType, int alpha, int beta) {
        //base case
        if (depth >= maximumSearchDepth) {
            count++;
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        int checkSum = getCheckSum(chess);
        int bestScore = Integer.MAX_VALUE;
        Node bestChild = null;

        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess, 24);
        //Terminal check
        if (depth == 1) {
            Node n = MinimaxAbp.terminalCheck(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        /*If checksum is found in transposition table, and current depth is deeper than or equals to the node
        in the transposition table, and their minMax properties are the same, stop abp and use the
        score directly*/
        if (transpositionTable.containsKey(checkSum) && depth >= transpositionTable.get(checkSum).getDepth()
            && !transpositionTable.get(checkSum).isMaxLayer()) {
            usage++;
            bestScore = transpositionTable.get(checkSum).getEvaluation();
        } else {
            count++;
            for (int[] move : moves) {
                int newX = move[0];
                int newY = move[1];
                int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
                Node child = new Node(newX, newY, -1, nextMove);

                int score = transpositionMaximizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();
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
            //Stores the new record to the transposition table
            transpositionTable.put(checkSum, new TranspositionNode(checkSum, bestScore, depth, false));
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
     * Calculates the checksum of the chessboard
     *
     * @param chess 2-dimensional array represents the chessboard
     * @return Hash value
     */
    public static int getCheckSum(int[][] chess) {
        int checkSum = 0;
        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[0].length; j++) {
                int pieceType = chess[i][j];
                switch (pieceType) {
                    case AiConst.BLACK_STONE:
                        checkSum ^= GameController.zobrist[0][i][j];
                        break;
                    case AiConst.WHITE_STONE:
                        checkSum ^= GameController.zobrist[1][i][j];
                        break;
                    default:
                        break;
                }
            }
        }
        return checkSum;
    }
}

/**
 * This class represents the node stored in the transposition table, each node encapsulates the checksum,
 * score, Min&Max and depth in the tree
 *
 * @author Cirun Zhang
 * @version 1.1
 */
class TranspositionNode {
    private int checksum, evaluation, depth;

    private boolean isMaxLayer;

    TranspositionNode(int checksum, int evaluation, int depth, boolean isMaxLayer) {
        this.checksum = checksum;
        this.evaluation = evaluation;
        this.depth = depth;
        this.isMaxLayer = isMaxLayer;
    }

    int getChecksum() {
        return checksum;
    }

    int getEvaluation() {
        return evaluation;
    }

    int getDepth() {
        return depth;
    }

    boolean isMaxLayer() {
        return isMaxLayer;
    }
}
