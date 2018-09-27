package test;

import ai.UltraAgent;
import ai.aiUtils;
import game.GameController;
import observer.ReportGenerator;

import java.util.List;

public class TestUtil {
    private static int[][] dummyChess = new int[][]
        {
            {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,0,0,0,0,0,0,-1,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
        };

    public static void main(String[] args){
        //new ReportGenerator().createReport("/Users/cirun/Desktop");
        GameController.initGame();

        System.out.println(UltraAgent.getCheckSum(dummyChess));
    }
}
