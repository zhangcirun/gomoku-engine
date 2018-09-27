package ai;

import ai.constant.AiConst;
import game.GameController;
import gui.Background;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UltraAgent {
    private static int count = 0;
    private static int maximumSearchDepth = 5;
    private static int aiPieceType = -1;

    private static Map<Integer, TranspositionNode> transpositionTable = new HashMap<>(100000);

    private UltraAgent() {
    }

    public static int[] startTranspositionSearch(int[][] chess) {
        //instantiate root node with preset x and y to the center of the chessboard(good for pruning)
        Node root = new Node(-1, -1, -1, chess);
        Node bestMove = transpositionSearch_abpMaximizer(root, 1, -1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        int[] result = bestMove.getCoordinates();
        Background
            .addMessage("Computer move : (x, " + result[0] + ") (y, " + result[1] + ") score " + bestMove.getScore());

        return new int[] {result[0], result[1], aiPieceType};
    }

    private static Node transpositionSearch_abpMaximizer(Node root, int depth, int pieceType, int alpha, int beta) {
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
        int bestScore = Integer.MIN_VALUE;
        Node bestChild = null;

        List<int[]> moves = aiUtils.moveGeneratorWithHeuristicSort(chess);

        //detect five in row
        // @Todo Bad way
        if (depth == 1) {
            Node n = AdvancedAgent.detectFiveInRow(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        if (transpositionTable.containsKey(checkSum) && depth > 1 && depth >= transpositionTable.get(checkSum).getDepth()
            && transpositionTable.get(checkSum).isMaxLayer()) {
            //System.out.println("table");
            bestScore = Math.max(bestScore, transpositionTable.get(checkSum).getEvaluation());
            //@Todo
        } else {
            //abp
            count++;
            for (int[] move : moves) {
                int newX = move[0];
                int newY = move[1];
                int[][] nextMove = aiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
                Node child = new Node(newX, newY, -1, nextMove);

                int score = transpositionSearch_abpMinimizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();
                //int newCheckSum = getCheckSum(nextMove);

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

        //List<int[]> moves = aiUtils.moveGeneratorWithDistanceSort(chess, lastX, lastY);
        List<int[]> moves = aiUtils.moveGeneratorWithHeuristicSort(chess);
        // @Todo Bad way
        if (depth == 1) {
            Node n = AdvancedAgent.detectFiveInRow(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        if (transpositionTable.containsKey(checkSum) && depth > 1 && depth >= transpositionTable.get(checkSum).getDepth()
            && !transpositionTable.get(checkSum).isMaxLayer()) {
            //System.out.println("table");
            bestScore = Math.max(bestScore, transpositionTable.get(checkSum).getEvaluation());
            //@Todo
        }else{
            count++;
            for (int[] move : moves) {
                int newX = move[0];
                int newY = move[1];
                int[][] nextMove = aiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
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
