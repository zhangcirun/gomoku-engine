package ai;

import ai.constant.AiConst;
import gui.constant.GuiConst;

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

    public static void resetCount() {
        count = 0;
    }

    public static boolean isOpenning(int[][] chess){
        if(chess[7][7] != AiConst.EMPTY_STONE){
            return false;
        }
        for(int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++){
            for(int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++){
                if(chess[i][j] != AiConst.EMPTY_STONE){
                    return false;
                }
            }
        }
        return true;
    }

    public static void setMaximumSearchDepth(int depth) {
        maximumSearchDepth = depth;
    }
}
