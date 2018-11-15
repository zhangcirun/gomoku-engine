package test;

import game.constant.GameConst;
import gui.constant.GuiConst;
import game.AiAnalyser;
import observer.ReportGenerator;

public class TestBattle {
    public static void main(String[] args){
        System.out.println("Analysis begin");
        int totalMoveCount = 0;
        //AiAnalyser.battle(GameConst.PURE_HEURISTIC, GameConst.ALPHA_BETA_PRUNING, testChess);

        //AiAnalyser.battle(GameConst.ALPHA_BETA_PRUNING, GameConst.PURE_HEURISTIC, testChess);

        //AiAnalyser.battle(GameConst.THREAT_SPACE_SEARCH, GameConst.PURE_HEURISTIC, testChess);

        //AiAnalyser.battle(GameConst.PURE_HEURISTIC, GameConst.ALPHA_BETA_PRUNING, testChess);

        for(int i = 0; i < 50; i++){
            int[][] testChess = new int[GuiConst.TILE_NUM_PER_ROW][GuiConst.TILE_NUM_PER_ROW];
            totalMoveCount += AiAnalyser.battle(GameConst.BENCH_MARKER, GameConst.ALPHA_BETA_PRUNING, testChess);
        }



        //new ReportGenerator().createReport("/Users/cirun/Desktop/duo");
        System.out.println("Analysis over, average move: " + totalMoveCount / 50);
    }
}
