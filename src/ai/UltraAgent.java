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
 * This class is an ai agent uses transposition table applied in alpha
 * beta pruning
 *
 * @author chang tz'u jun
 */

public class UltraAgent extends Agent{

    /**
     * Transposition table implements by a hash map
     */
    private static Map<Integer, TranspositionNode> transpositionTable = new HashMap<>(100000);

    private UltraAgent() {
    }

    public static int[] startTranspositionSearch(int[][] chess) {
        if(isOpenning(chess)){
            return new int[] {7, 7, aiPieceType};
        }else{
            //instantiate root node with preset x and y to the center of the chessboard(good for pruning)
            Node root = new Node(-1, -1, -1, chess);
            Node bestMove;

            bestMove = transpositionSearch_abpMaximizer(root, 1, aiPieceType, Integer.MIN_VALUE, Integer.MAX_VALUE);

            int[] result = bestMove.getCoordinates();
            Background
                .addMessage("Computer move : (x, " + result[0] + ") (y, " + result[1] + ") score " + bestMove.getScore());

            Background.addMessage("Transposition table size: " + transpositionTable.size());
            return new int[] {result[0], result[1], aiPieceType};
        }
    }

    /**
     * Maximizer of alpha beta pruning applied transposition search,
     * each node will be recorded in the transposition table.
     * @param root Current game node
     * @param depth Current depth of the node in the search tree
     * @param pieceType Type of pieces
     * @param alpha alpha value for Max node
     * @param beta beta value for Min node
     * @return The most valuable node
     */
    private static Node transpositionSearch_abpMaximizer(Node root, int depth, int pieceType, int alpha, int beta) {
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

        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess);

        //detect five in row
        // @Todo Bad way
        if (depth == 1) {
            Node n = AdvancedAgent.detectFiveInRow(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        //If checksum is found in transposition table, and current depth is deeper than or
        //equals to the node in the transposition table, and their minMax properties are the same,
        //stop abp and use the score directly
        if (transpositionTable.containsKey(checkSum) && depth >= transpositionTable.get(checkSum).getDepth()
            && transpositionTable.get(checkSum).isMaxLayer()) {
            bestScore = transpositionTable.get(checkSum).getEvaluation();
        } else {
            //abp
            count++;
            for (int[] move : moves) {
                int newX = move[0];
                int newY = move[1];
                int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
                Node child = new Node(newX, newY, -1, nextMove);

                int score = transpositionSearch_abpMinimizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();

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
     * Minimizer of alpha beta pruning applied transposition search,
     * each node will be recorded in the transposition table.
     * @param root Current game node
     * @param depth Current depth of the node in the search tree
     * @param pieceType Type of pieces
     * @param alpha alpha value for Max node
     * @param beta beta value for Min node
     * @return The most valuable node
     */
    private static Node transpositionSearch_abpMinimizer(Node root, int depth, int pieceType, int alpha, int beta) {
        //base case
        if (depth >= maximumSearchDepth) {
            count++;
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        /**
         * checkSum
         */
        int checkSum = getCheckSum(chess);
        int bestScore = Integer.MAX_VALUE;
        Node bestChild = null;

        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess);
        // @Todo Bad way
        if (depth == 1) {
            Node n = AdvancedAgent.detectFiveInRow(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        //If checksum is found in transposition table, and current depth is deeper than or
        //equals to the node in the transposition table, and their minMax properties are the same,
        //stop abp and use the score directly
        if (transpositionTable.containsKey(checkSum) && depth >= transpositionTable.get(checkSum).getDepth()
            && !transpositionTable.get(checkSum).isMaxLayer()) {
            //System.out.println("table");
            bestScore = transpositionTable.get(checkSum).getEvaluation();
        } else {
            count++;
            for (int[] move : moves) {
                int newX = move[0];
                int newY = move[1];
                int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
                Node child = new Node(newX, newY, -1, nextMove);

                int score = transpositionSearch_abpMaximizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();
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
     * Calculates the checksum representing the chessboard
     * @param chess 2-dimensional array represents the chessboard
     * @return Integer checksum
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
 * This class represents the nodes store in the transposition table,
 * each node records it's checksum, score, depth in the tree and
 * miniMax property.
 *
 * @author Chang tz'u jun
 */
class TranspositionNode {
    private int checksum, evaluation, depth;

    private boolean isMaxLayer;

    public TranspositionNode(int checksum, int evaluation, int depth, boolean isMaxLayer) {
        this.checksum = checksum;
        this.evaluation = evaluation;
        this.depth = depth;
        this.isMaxLayer = isMaxLayer;
    }

    public int getChecksum() {
        return checksum;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isMaxLayer() {
        return isMaxLayer;
    }
}
