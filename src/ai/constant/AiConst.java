package ai.constant;

/**
 * This class manages constants in ai package
 * 1 for ally, 0 for empty, 2 for opponent
 *
 * @author Chang ta'z jun
 * @version Version 1.1
 */
public class AiConst {
    private AiConst(){}

    public static final int EMPTY_STONE = 0;

    public static final int BLACK_STONE = 1;

    public static final int WHITE_STONE = -1;

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

    //public static final String IMPLICATE_THREE_E = "10101";

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

    /**
     * Window size for Aspiration search
     */
    public static final int WINDOW_SIZE_ASPIRATION = 100;

    /**
     * Threat Direction
     */
    public static final int NO_THREAT = 0;

    public static final int HORIZONTAL_THREAT = 1;

    public static final int VERTICAL_THREAT = 2;

    public static final int DIAGONAL_THREAT = 3;

    public static final int ANTIDIAGONAL_THREAT = 4;

    /**
     *
     * Potential Threats
     * 1 for ally, 2 for enemy, t for gain square, 0 for empty
     */
    public static final String POTENTIAL_THREAT_A = "2111t0";

    public static final String POTENTIAL_THREAT_B = "0t1112";

    public static final String POTENTIAL_THREAT_C = "21110t";

    public static final String POTENTIAL_THREAT_D = "t01112";

    public static final String POTENTIAL_THREAT_E = "011t0";

    public static final String POTENTIAL_THREAT_F = "0t110";

    public static final String POTENTIAL_THREAT_G = "01t10";

    public static final String POTENTIAL_THREAT_H = "0110t0";

    public static final String POTENTIAL_THREAT_I = "0t0110";

    /**
     * Dependent Threats
     */
    //The three
    public static final String DEPENDENT_THREAT_A = "0t1t0";

    public static final String DEPENDENT_THREAT_B = "0tt10";

    public static final String DEPENDENT_THREAT_C = "01tt0";

    public static final String DEPENDENT_THREAT_D = "0tt010";

    public static final String DEPENDENT_THREAT_E = "010tt0";

    public static final String DEPENDENT_THREAT_F = "0t10t0";

    public static final String DEPENDENT_THREAT_G = "0t01t0";

    public static final String DEPENDENT_THREAT_H = "01t0t0";

    public static final String DEPENDENT_THREAT_I = "0t0t10";


    //The fours
    public static final String DEPENDENT_THREAT_J = "0tt11";

    public static final String DEPENDENT_THREAT_K = "11tt0";

    public static final String DEPENDENT_THREAT_L = "tt110";

    public static final String DEPENDENT_THREAT_M = "011tt";

    public static final String DEPENDENT_THREAT_O = "t1t10";

    public static final String DEPENDENT_THREAT_P = "01t1t";

    public static final String DEPENDENT_THREAT_Q = "0t1t1";

    public static final String DEPENDENT_THREAT_R = "1t1t0";

    public static final String DEPENDENT_THREAT_S = "t11t0";

    public static final String DEPENDENT_THREAT_T = "0t11t";

    public static final String DEPENDENT_THREAT_U = "t11t0";

    public static final String DEPENDENT_THREAT_V = "01tt1";

    public static final String DEPENDENT_THREAT_W = "1tt10";

    //The three plus one
    public static final String DEPENDENT_THREAT_X = "tt101";

    public static final String DEPENDENT_THREAT_Y = "101tt";

    public static final String DEPENDENT_THREAT_Z = "t1t01";

    public static final String DEPENDENT_THREAT_AA = "10t1t";

    public static final String DEPENDENT_THREAT_AB = "t110t";

    public static final String DEPENDENT_THREAT_AC = "t011t";

    public static final String DEPENDENT_THREAT_AD = "1tt01";

    public static final String DEPENDENT_THREAT_AE = "10tt1";

    public static final String DEPENDENT_THREAT_AF = "1t10t";

    public static final String DEPENDENT_THREAT_AG = "t01t1";

    public static final String DEPENDENT_THREAT_AH = "11t0t";

    public static final String DEPENDENT_THREAT_AI = "t0t11";

    //@Todo add more

    /**
     * Defensive move positions
     */
    public static final String DEFENSE_THREAT_A1 = "t11112";

    public static final String DEFENSE_THREAT_A2 = "21111t";


    public static final String DEFENSE_THREAT_B1 = "111t1";

    public static final String DEFENSE_THREAT_B2 = "1t111";


    public static final String DEFENSE_THREAT_C1 = "01t110";

    public static final String DEFENSE_THREAT_C2 = "011t10";

    public static final String DEFENSE_THREAT_C3 = "t10110";

    public static final String DEFENSE_THREAT_C4 = "01101t";

    public static final String DEFENSE_THREAT_C5 = "01011t";

    public static final String DEFENSE_THREAT_C6 = "t11010";



    //Three
    public static final String DEFENSE_THREAT_D1= "t1110";

    public static final String DEFENSE_THREAT_D2 = "0111t";


}
