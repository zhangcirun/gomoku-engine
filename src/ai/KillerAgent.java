package ai;

import ai.constant.AiConst;
import gui.Background;

import java.util.List;

/**
 * The killer heuristic attempts to produce a cutoff by assuming that a move that produced
 * a cutoff in another branch of the game tree at the same depth is likely to produce a
 * cutoff in the present position.
 *
 * @author chang tu'z jun
 */
public class KillerAgent extends Agent {
    private KillerAgent(){}

    private static int[][][] killerMoves = new int[maximumSearchDepth][2][];

    public static int[] startAlphaBetaPruning_killer(int[][] chess) {
        //instantiate root node with preset x and y to the center of the chessboard(good for pruning)
        resetKillerMoves();
        Node root = new Node(-1, -1, -1, chess);
        Node bestMove;
        if(isFirstLayerMax){
            bestMove = alphaBetaPruning_Maximizer_preSort(root, 1, aiPieceType, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }else {
            bestMove = alphaBetaPruning_Minimizer_preSort(root, 1, aiPieceType, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        int[] result = bestMove.getCoordinates();
        Background
            .addMessage("Computer move : (x, " + result[0] + ") (y, " + result[1] + ") score " + bestMove.getScore());

        return new int[]{result[0], result[1], aiPieceType};
    }

    /**
     * This methods is the maximizer of alpha beta pruning, it prunes the current node
     * when the alpha value of current node is greater than or equal to the beta value
     * of its ancient node. Every possible moves are sorted by a heuristic function
     * to improve pruning
     *
     * @param root      Current tree node
     * @param depth     Current depth of the node
     * @param pieceType Initializes to the piece type of AI
     * @param alpha     alpha value for Max node
     * @param beta      beta value for Min node
     * @return The most valuable node
     */
    private static Node alphaBetaPruning_Maximizer_preSort(Node root, int depth, int pieceType, int alpha, int beta) {
        count++;
        //base case
        if (depth >= maximumSearchDepth) {
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore = Integer.MIN_VALUE;
        Node bestChild = null;

        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess);

        //merge killer moves with all possible moves
        if(depth == 2 && killerMoves[depth][0] != null && isKillerMoveLegal(chess, killerMoves[depth][0])){
            System.out.println("Added");
            //merge
            moves.add(0, killerMoves[depth][0]);
        }

        /*
        if(depth == 2 && killerMoves[depth - 1][1] != null && isKillerMoveLegal(chess, killerMoves[depth - 1][1])){
            //merge
            moves.add(0, killerMoves[depth][1]);
        }
        */

        //detect five in row
        // @Todo Bad way
        if (depth == 1) {
            Node n = AdvancedAgent.detectFiveInRow(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        for(int i = 0; i < moves.size(); i++){
            int newX = moves.get(i)[0];
            int newY = moves.get(i)[1];
            int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
            Node child = new Node(newX, newY, -1, nextMove);

            int score = alphaBetaPruning_Minimizer_preSort(child, depth + 1, pieceType * -1, alpha, beta).getScore();
            if (score > bestScore) {
                bestScore = score;
                bestChild = child;
                alpha = score;
            }
            //beta pruning
            if (score >= beta) {
                //since depth start at 1 so the decrement is necessary
                //@Todo wrong pruning number
                //@Todo legal check
                addKillerMove(depth, new int[]{newX, newY, (moves.size() - i - 1) * (int)Math.pow(24, maximumSearchDepth - depth - 1)});
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
     * This methods is the minimizer of alpha beta pruning, it prunes the current node
     * when the beta value of current node is less than or equal to the alpha value
     * of its ancient node. Every possible moves are sorted by a heuristic function
     * to improve pruning
     *
     * @param root      Current tree node
     * @param depth     Current depth of the node
     * @param pieceType Identification of players, 1 for black piece and -1 white piece
     * @param alpha     Alpha value for Max node
     * @param beta      Beta value for Min node
     * @return The most valuable node
     */
    private static Node alphaBetaPruning_Minimizer_preSort(Node root, int depth, int pieceType, int alpha, int beta) {
        count++;
        //base case
        if (depth >= maximumSearchDepth) {
            root.setScore(HeuristicChessboardUtils.heuristic(root.getChess()));
            return root;
        }

        int[][] chess = root.getChess();
        int bestScore = Integer.MAX_VALUE;
        Node bestChild = null;

        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess);

        //merge killer moves with all possible moves
        if(depth == 2 && killerMoves[depth][0] != null && isKillerMoveLegal(chess, killerMoves[depth][0])){
            //merge
            System.out.println("Added");
            moves.add(0, killerMoves[depth][0]);
        }

        /*
        if(depth == 2 && killerMoves[depth - 1][1] != null){
            //merge
            moves.add(0, killerMoves[depth - 1][1]);
        }
        */

        // @Todo Bad way
        if (depth == 1) {
            Node n = AdvancedAgent.detectFiveInRow(chess, moves, pieceType);
            if (n != null) {
                return n;
            }
        }

        //for each child
        for(int i = 0; i < moves.size(); i++){
            int newX = moves.get(i)[0];
            int newY = moves.get(i)[1];
            int[][] nextMove = AiUtils.nextMoveChessboard(chess, newX, newY, pieceType);
            Node child = new Node(newX, newY, -1, nextMove);

            int score = alphaBetaPruning_Maximizer_preSort(child, depth + 1, pieceType * -1, alpha, beta).getScore();
            if (score < bestScore) {
                bestScore = score;
                bestChild = child;
                beta = score;
            }
            //alpha pruning
            if (score <= alpha) {
                //since depth start at 1 so the decrement is necessary
                addKillerMove(depth, new int[]{newX, newY, (moves.size() - i - 1) * (int)Math.pow(24, maximumSearchDepth - depth - 1)});
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

    private static void resetKillerMoves(){
        killerMoves = new int[maximumSearchDepth][2][];
    }

    private static void addKillerMove(int depth, int[] move){
        if(killerMoves[depth][0] == null){
            killerMoves[depth][0] = move;
        }else if(killerMoves[depth][0][2] < move[2]){
            //compare how many nodes are pruned
            killerMoves[depth][0] = move;
        }
    }

    private static boolean isKillerMoveLegal(int[][] chess, int[] killerMove){
        System.out.println("coor " + killerMove[0] + " " + killerMove[1] + (chess[killerMove[0]][killerMove[1]] == AiConst.EMPTY_STONE));
        return chess[killerMove[0]][killerMove[1]] == AiConst.EMPTY_STONE;
    }
}
