package ai;

import ai.constant.AiConst;
import ai.utility.AiUtils;
import gui.Background;
import gui.constant.GuiConst;
import observer.GameStatusChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is an AI agent uses Monte Carlo tree search
 *
 * @author Cirun Zhang
 * @version 1.1
 */
public class MonteCarlo extends Agent {
    /**
     * Counter for MCTS
     */
    private static int iteration;

    public static void tester(int[][] chess) {
        iteration = 0;
        TreeNode root = new TreeNode(true, aiPieceType * -1, -1, -1, chess, null);
        while (iteration < 30000) {
            selection(root);
        }

        List<TreeNode> children = root.getChildren();
        int maxVisits = Integer.MIN_VALUE;
        int max_x = -1;
        int max_y = -1;
        for (TreeNode child : children) {
            if (child.getVisitsCount() > maxVisits) {
                maxVisits = child.getVisitsCount();
                max_x = child.getX();
                max_y = child.getY();
            }
        }

        System.out.println(max_x + "===" + max_y);
    }

    /**
     * Entrance of MCTS
     *
     * @param chess 2-dimensional array represents the chessboard
     * @return Position of the next move
     */
    public static int[] monteCarloTreeSearch(int[][] chess) {
        Background.addMessage("Doing MCTS, please wait..");
        iteration = 0;

        TreeNode root = new TreeNode(true, aiPieceType * -1, -1, -1, chess, null);
        //execute MCTS for 50000 times
        while (iteration < 50000) {
            selection(root);
        }

        List<TreeNode> children = root.getChildren();
        int maxVisits = Integer.MIN_VALUE;
        int max_x = -1;
        int max_y = -1;
        for (TreeNode child : children) {
            if (child.getVisitsCount() > maxVisits) {
                maxVisits = child.getVisitsCount();
                max_x = child.getX();
                max_y = child.getY();
            }
        }

        System.out.println(root.getReward() + "-" + root.getVisitsCount());
        System.out.println(max_x + "===" + max_y);
        System.out.println(maxVisits);
        return new int[] {max_x, max_y, aiPieceType};
    }

    /**
     * Selection process of MCTS
     *
     * @param root The node for process selection, initially the node is set to the node
     */
    private static void selection(TreeNode root) {
        if (root.isLeaf()) {
            if (root.getVisitsCount() == 0) {
                rollout(root);
            } else {
                expansion(root);
            }
        } else {
            List<TreeNode> children = root.getChildren();
            TreeNode best = ucbSelection(children);
            if (best != null) {
                selection(best);
            } else {
                System.out.println("null");
            }

        }
    }

    /**
     * Expansion process of MCTS
     *
     * @param node The leaf node need to be expanded
     */
    private static void expansion(TreeNode node) {
        List<TreeNode> children = generatesChildren(node);
        node.setChildren(children);
        node.setLeaf(false);
        selection(node);
    }

    /**
     * Rollout process of MCTS. The rollout only stops when the simulated game is terminated
     *
     * @param node The node need to be simulated
     */
    private static void rollout(TreeNode node) {
        iteration++;
        int numOfMoves = 0;
        int[][] chess = AiUtils.copyArray(node.getChess());
        int lastTurnPlayer = node.getThisTurnPlayer();
        PossibleMove randomMove;

        do {
            lastTurnPlayer *= -1;
            numOfMoves++;
            randomMove = getRandomMove(chess);
            if (randomMove == null) {
                System.out.println("randomMove == null");
                break;
            }
            placePiece(chess, randomMove, lastTurnPlayer);
        } while (!GameStatusChecker.isFiveInLine(chess, randomMove.getX(), randomMove.getY()));

        //back propagation
        backPropagation(node, 1, lastTurnPlayer);
    }


    /**
     * Back propagation process of MCTS
     *
     * @param node         The back propagated node
     * @param reward       The reward for winning nodes
     * @param winningPiece Indicates which player wins
     */
    private static void backPropagation(TreeNode node, int reward, int winningPiece) {
        if (node != null) {
            if (node.getThisTurnPlayer() == winningPiece) {
                node.increaseReward(reward);
            } else {
                node.increaseReward(-1);
            }
            node.increaseVisitCount();
            backPropagation(node.getParent(), reward, winningPiece);
        }
    }

    /**
     * UCB-1 function of MCTS, it is used to balance the visit count and win count
     *
     * @param node Calculates the UCB value for this particular node
     * @return UCB value
     */
    private static double ucb1(TreeNode node) {
        //1.1 as the ucb constant
        final double c = 1.1;
        int reward = node.getReward();
        int visitCount = node.getVisitsCount();
        int parentVisitCount = node.getParent().getVisitsCount();
        return AiUtils.safeDivide(reward, visitCount) + c * Math
            .sqrt(AiUtils.safeDivide(Math.log(parentVisitCount), visitCount));
    }

    /**
     * Selects the child node with the highest UCB value
     *
     * @param children The child nodes
     * @return The best node
     */
    private static TreeNode ucbSelection(List<TreeNode> children) {
        double max = Double.NEGATIVE_INFINITY;
        TreeNode best = null;

        for (TreeNode child : children) {
            double ucbVal = ucb1(child);
            if (ucbVal > max) {
                max = ucbVal;
                best = child;
            }

            if (max == Double.POSITIVE_INFINITY) {
                return best;
            }
        }

        if (best == null) {
            System.out.println(ucb1(children.get(0)));
        }
        return best;
    }

    /**
     * Generates 10 child nodes for a parent node
     * @param node Parent node
     * @return  Child nodes
     */
    private static List<TreeNode> generatesChildren(TreeNode node) {
        List<TreeNode> children = new ArrayList<>();

        int nextTurnPlayer = node.getThisTurnPlayer() * -1;
        int[][] chess = node.getChess();

        //Generates 10 child nodes
        List<int[]> moves = AiUtils.moveGeneratorWithHeuristicSort(chess, 10);

        for (int[] move : moves) {
            int x = move[0];
            int y = move[1];
            int[][] nextChess = AiUtils.nextMoveChessboard(chess, x, y, nextTurnPlayer);
            boolean isTerminal = GameStatusChecker.isFiveInLine(nextChess, x, y);

            if(!isTerminal){
                children.add(new TreeNode(true, nextTurnPlayer, x, y, nextChess, node));
            }else{
                backPropagation(node, 1, nextTurnPlayer);
            }
        }

        return children;
    }

    /**
     * Randomly choose a move
     * @param chess The chessboard
     * @return A randomly chosen move
     */
    private static PossibleMove getRandomMove(int[][] chess) {
        List<PossibleMove> possibleMoves = generatesMoves(chess);
        int size = possibleMoves.size();

        if (size == 0) {
            System.out.println("Chess board full");
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(0, size);
        return possibleMoves.get(randomIndex);
    }

    /**
     * Generates all possible legal game moves.
     * @param chess The chessboard
     * @return All possible moves.
     */
    private static List<PossibleMove> generatesMoves(int[][] chess) {
        List<PossibleMove> possibleMoves = new ArrayList<>();
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                if (chess[i][j] == AiConst.EMPTY_STONE) {
                    possibleMoves.add(new PossibleMove(i, j));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Place piece on the chessboard
     * @param chess The chessboard
     * @param move The location of placing place
     * @param pieceType Type of placed piece
     */
    private static void placePiece(int[][] chess, PossibleMove move, int pieceType) {
        chess[move.getX()][move.getY()] = pieceType;
    }

}

/**
 * This class is used to encapsulate the piece-placing location information
 *
 * @author Cirun Zhang
 * @version 1.0
 */
class PossibleMove {
    private int x;
    private int y;

    PossibleMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

/**
 * This class represents the node of MCT
 *
 * @author Cirun Zhang
 * @version 1.0
 */
class TreeNode {
    private boolean isLeaf;

    private boolean isTerminal;

    private int thisTurnPlayer;

    private int x;

    private int y;

    private int[][] chess;

    private int reward = 0;

    private int visitsCount = 0;

    private TreeNode parent;

    private List<TreeNode> children;

    public TreeNode(int[][] chess) {
        this.chess = chess;
    }

    public TreeNode(boolean isLeaf, int[][] chess) {
        this.isLeaf = isLeaf;
        this.chess = chess;
    }

    public TreeNode(boolean isLeaf, int thisTurnPlayer, int x, int y, int[][] chess, TreeNode parent) {
        this.isLeaf = isLeaf;
        this.thisTurnPlayer = thisTurnPlayer;
        this.x = x;
        this.y = y;
        this.chess = chess;
        this.parent = parent;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int[][] getChess() {
        return chess;
    }

    public void setChess(int[][] chess) {
        this.chess = chess;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getVisitsCount() {
        return visitsCount;
    }

    public void setVisitsCount(int visitsCount) {
        this.visitsCount = visitsCount;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public void setTerminal(boolean terminal) {
        isTerminal = terminal;
    }

    public int getThisTurnPlayer() {
        return thisTurnPlayer;
    }

    public void setThisTurnPlayer(int thisTurnPlayer) {
        this.thisTurnPlayer = thisTurnPlayer;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void increaseReward(int reward) {
        this.reward += reward;
    }

    public void increaseVisitCount() {
        this.visitsCount += 1;
    }
}
