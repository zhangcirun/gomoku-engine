package ai;

import ai.constant.AiConst;
import ai.utility.AiUtils;
import ai.utility.HeuristicChessboardUtils;
import gui.Background;

import java.util.List;

/**
 * This class is an AI agent uses killer heuristics
 * <p>
 * The killer heuristic attempts to produce a cutoff by assuming that a move that produced
 * a cutoff in another branch of the game tree at the same depth is likely to produce a
 * cutoff in the present position.
 *
 * @author Cirun Zhang
 * @version 1.2
 */
public class KillerHeuristic extends Agent {
    private KillerHeuristic() {
    }

    /**
     * A 3-dimensional array used to store killer moves with the format of [depth][index][location]
     */
    private static int[][][] killerMoves = new int[maximumSearchDepth][2][];

    /**
     * Start Alpha-beta pruning with killer heuristic
     *
     * @param chess The chessboard
     * @return The position of the next move
     */
    public static int[] killerAbp(int[][] chess) {
        if (isOpening(chess)) {
            return new int[] {7, 7, aiPieceType};
        } else {
            //instantiate root node with preset x and y to the center of the chessboard(good for pruning)
            resetKillerMoves();
            Node root = new Node(-1, -1, -1, chess);
            Node bestMove;

            bestMove = killerMaximizer(root, 1, aiPieceType, Integer.MIN_VALUE, Integer.MAX_VALUE);

            int[] result = bestMove.getCoordinates();
            Background.addMessage(
                "Computer move : (x, " + result[0] + ") (y, " + result[1] + ") score " + bestMove.getScore());

            return new int[] {result[0], result[1], aiPieceType};
        }
    }

    /**
     * This methods is the maximizer of alpha beta pruning with killer heuristic
     *
     * @param root      Current tree node
     * @param depth     Current depth of the node
     * @param pieceType Initializes to the piece type of AI
     * @param alpha     Alpha value for Max node
     * @param beta      Beta value for Min node
     * @return The most valuable node
     */
    private static Node killerMaximizer(Node root, int depth, int pieceType, int alpha, int beta) {
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

        //merge killer moves with all possible moves
        if (depth == 2 && killerMoves[depth][0] != null && isKillerMoveLegal(chess, killerMoves[depth][0])) {
            System.out.println("Added");
            //merge
            moves.add(0, killerMoves[depth][0]);
        }

        //goes terminal check
        if (depth == 1) {
            Node n = MinimaxAbp.terminalCheck(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        for (int i = 0; i < moves.size(); i++) {
            int newX = moves.get(i)[0];
            int newY = moves.get(i)[1];
            int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
            Node child = new Node(newX, newY, -1, nextMove);

            int score = killerMinimizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();
            if (score > bestScore) {
                bestScore = score;
                bestChild = child;
                alpha = score;
            }
            //beta pruning
            if (score >= beta) {
                //since depth start at 1 so the decrement is necessary
                addKillerMove(depth,
                    new int[] {newX, newY, (moves.size() - i - 1) * (int)Math.pow(24, maximumSearchDepth - depth - 1)});
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
     * This methods is the minimizer of alpha beta pruning with killer heuristic
     *
     * @param root      Current tree node
     * @param depth     Current depth of the node
     * @param pieceType Identification of players, 1 for black piece and -1 white piece
     * @param alpha     Alpha value for Max node
     * @param beta      Beta value for Min node
     * @return The most valuable node
     */
    private static Node killerMinimizer(Node root, int depth, int pieceType, int alpha, int beta) {
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

        //merge killer moves with all possible moves
        if (depth == 2 && killerMoves[depth][0] != null && isKillerMoveLegal(chess, killerMoves[depth][0])) {
            //merge
            System.out.println("Added");
            moves.add(0, killerMoves[depth][0]);
        }

        if (depth == 1) {
            Node n = MinimaxAbp.terminalCheck(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        //for each child
        for (int i = 0; i < moves.size(); i++) {
            int newX = moves.get(i)[0];
            int newY = moves.get(i)[1];
            int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
            Node child = new Node(newX, newY, -1, nextMove);

            int score = killerMaximizer(child, depth + 1, pieceType * -1, alpha, beta).getScore();
            if (score < bestScore) {
                bestScore = score;
                bestChild = child;
                beta = score;
            }
            //alpha pruning
            if (score <= alpha) {
                //since depth start at 1 so the decrement is necessary
                addKillerMove(depth,
                    new int[] {newX, newY, (moves.size() - i - 1) * (int)Math.pow(24, maximumSearchDepth - depth - 1)});
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
     * Reset all the killer moves
     */
    private static void resetKillerMoves() {
        killerMoves = new int[maximumSearchDepth][2][];
    }

    /**
     * Add new killer moves to the array
     *
     * @param depth Depth of occurrence of pruning
     * @param move  Killer move
     */
    private static void addKillerMove(int depth, int[] move) {
        if (killerMoves[depth][0] == null) {
            killerMoves[depth][0] = move;
        } else if (killerMoves[depth][0][2] < move[2]) {
            //compare how many nodes are pruned
            killerMoves[depth][0] = move;
        }
    }

    /**
     * Check the legality of the killer move
     *
     * @param chess      The chessboard
     * @param killerMove Killer move
     * @return A boolean indicates whether the killer move is legal or not
     */
    private static boolean isKillerMoveLegal(int[][] chess, int[] killerMove) {
        System.out.println("coor " + killerMove[0] + " " + killerMove[1] + (chess[killerMove[0]][killerMove[1]]
            == AiConst.EMPTY_STONE));
        return chess[killerMove[0]][killerMove[1]] == AiConst.EMPTY_STONE;
    }
}
