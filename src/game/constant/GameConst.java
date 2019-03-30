package game.constant;

/**
 * This class manages constants in game package
 *
 * @author Cirun Zhang
 * @version 1.1
 */
public class GameConst {
    private GameConst() {
    }

    /**
     * Code for bench marker
     */
    public static final int BENCH_MARKER = -1;

    /**
     * Code for greedy best-first search
     */
    public static final int BEST_FIRST = 0;

    /**
     * Code for minimax
     */
    public static final int MINIMAX = 1;

    /**
     * Code for alpha beta pruning
     */
    public static final int ALPHA_BETA_PRUNING = 2;

    /**
     * Code for minimax applies alpha beta pruning and transposition table
     */
    public static final int TRANSPOSITION_SEARCH = 3;

    /**
     * Code for minimax applies alpha beta pruning with killer heuristic
     */
    public static final int KILLER_HEURISTIC = 4;

    /**
     * Code for threat space search
     */
    public static final int THREAT_SPACE_SEARCH = 5;

    /**
     * Code for monte carlo tree search
     */
    public static final int MONTE_CARLO_TREE_SEARCH = 6;

    /**
     * Code for human move first
     */
    public static final int HUMAN_MOVE_FIRST = 0;
}
