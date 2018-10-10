package ai;

/**
 * This abstract class is the prototype of all AI agents.
 * It pre-implements some universal methods.
 *
 * @author Chang tz'u jun
 */
public abstract class Agent{
    static int maximumSearchDepth = 5;
    static int count = 0;
    public static int aiPieceType = -1;

    public static boolean isFirstLayerMax;

    public static void resetCount() {
        count = 0;
    }

    public static void setMaximumSearchDepth(int depth) {
        maximumSearchDepth = depth;
    }

    public static void setAiPieceType(int pieceType){
        aiPieceType = pieceType;
    }
}
