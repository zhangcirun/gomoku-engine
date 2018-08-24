package ai.constant;

/**
 * 1 for ally, 0 for empty, 2 for opponent
 *
 * @author Chang ta'z jun
 * @version Version 1.0
 */
public class AiConst {
    private AiConst(){}


    public static final String IMPLICATE_FIVE  = "11111";

    /**
     * Four pieces cases
     */
    public static final String IMPLICATE_FOUR_DOUBLE_EMPTY = "011110";


    public static final String IMPLICATE_FOUR_SINGLE_EMPTY_A = "01111";

    public static final String IMPLICATE_FOUR_SINGLE_EMPTY_B = "11110";

    public static final String IMPLICATE_FOUR_SINGLE_EMPTY_C = "10111";

    public static final String IMPLICATE_FOUR_SINGLE_EMPTY_D = "11101";

    public static final String IMPLICATE_FOUR_SINGLE_EMPTY_E = "11011";

    /**
     * Three pieces cases
     */
    public static final String IMPLICATE_THREE_A = "011100";

    public static final String IMPLICATE_THREE_B = "001110";

    public static final String IMPLICATE_THREE_C = "011010";

    public static final String IMPLICATE_THREE_D = "010110";

    public static final String IMPLICATE_THREE_E = "10101";

    /**
     * Two pieces cases
     */
    public static final String IMPLICATE_TWO_A = "001100";

    public static final String IMPLICATE_TWO_B = "001010";

    public static final String IMPLICATE_TWO_C = "010100";

    /**
     * One piece cases
     */
    public static final String IMPLICATE_ONE_A = "000100";

    public static final String IMPLICATE_ONE_B = "001000";

}
