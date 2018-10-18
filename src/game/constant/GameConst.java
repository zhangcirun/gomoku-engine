package game.constant;

/**
 * This class manages constants in game package
 * @author Chang tz'u jun
 */
public class GameConst {
    private GameConst(){}

    /**
     * Code for pure heuristic strategy
     */
    public static final int PURE_HEURISTIC = 0;

    /**
     * Code for minimax strategy
     */
    public static final int MINIMAX = 1;

    /**
     * Code for minimax applies alpha beta pruning
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

    public static final int HUMAN_MOVE_FIRST = 0;

    public static final int COMPUTER_MOVE_FIRST = 1;
}
