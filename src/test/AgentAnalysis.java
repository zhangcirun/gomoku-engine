package test;

import game.constant.GameConst;
import gui.constant.GuiConst;
import game.AiAnalyser;

/**
 * This class is used to analyse the performance of the AI agents
 */
public class AgentAnalysis {
    public static void main(String[] args){
        System.out.println("Analysis begin");
        int totalMoveCount = 0;

        for(int i = 0; i < 20; i++){
            int[][] testChess = new int[GuiConst.TILE_NUM_PER_ROW][GuiConst.TILE_NUM_PER_ROW];
            totalMoveCount += AiAnalyser.battle(GameConst.BENCH_MARKER, GameConst.ALPHA_BETA_PRUNING, testChess);
        }
        System.out.println("Analysis over, average move: " + totalMoveCount / 20);
    }
}
