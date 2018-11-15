package game;

import ai.*;
import ai.constant.AiConst;
import game.constant.GameConst;
import gui.Chessboard;
import observer.GameStatuChecker;
import observer.HistoryObserver;

public class AiAnalyser {
    private AiAnalyser() {
    }

    /**
     * Starts game battle with AI agent A(black) and AI agent B(white), agent A would move first
     * @param agentA Index of agent A
     * @param agentB Index of agent B
     * @param chess The chessboard of the battle
     * @return Game moves
     */
    public static int battle(int agentA, int agentB, int[][] chess) {
        //Agent A move first
        int moveCount = 0;
        while (moveCount < 120) {
            System.out.println("Move " + moveCount++);
            Agent.aiPieceType = AiConst.BLACK_STONE;
            if (aiMove(agentA, chess)) {
                printBattleInfo(agentA, true);
                return moveCount;
            }

            System.out.println("Move " + moveCount++);
            Agent.aiPieceType = AiConst.WHITE_STONE;
            if (aiMove(agentB, chess)) {
                printBattleInfo(agentB, false);
                return moveCount;
            }
        }
        return -1;
    }

    //Todo need to fix the threading
    public static void battleOnScreen(int agentA, int agentB, Chessboard chessboard) {
        //Agent A move first
        int moveCount = 0;
        GameController.resetChessboard();
        while (moveCount < 90) {
            chessboard.repaint();
            chessboard.validate();

            System.out.println("Move " + moveCount++);
            Agent.aiPieceType = AiConst.BLACK_STONE;
            if (aiMove(agentA, GameController.chess)) {
                printBattleInfo(agentA, true);
                return;
            }
            /*
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }*/

            chessboard.repaint();
            chessboard.validate();
            System.out.println("Move " + moveCount++);
            Agent.aiPieceType = AiConst.WHITE_STONE;
            if (aiMove(agentB, GameController.chess)) {
                printBattleInfo(agentB, false);
                return;
            }
        }
    }

    private static boolean aiMove(int agent, int[][] chess) {
        int[] result;
        switch (agent) {
            case GameConst.BENCH_MARKER:
                result = AiBenchMarker.nextMove(chess);
                break;
            case GameConst.PURE_HEURISTIC:
                result = BasicAgent.nextMove(chess);
                break;
            case GameConst.MINIMAX:
                result = AdvancedAgent.startMiniMax(chess);
                break;
            case GameConst.ALPHA_BETA_PRUNING:
                result = AdvancedAgent.startAlphaBetaPruning_preSort(chess);
                break;
            case GameConst.TRANSPOSITION_SEARCH:
                result = UltraAgent.startTranspositionSearch(chess);
                break;
            case GameConst.KILLER_HEURISTIC:
                result = KillerAgent.startAlphaBetaPruning_killer(chess);
                break;
            case GameConst.THREAT_SPACE_SEARCH:
                result = ThreatAgent.startThreatSpaceSearch(chess);
                break;
            default:
                System.err.println("Invalid Ai Index");
                return false;
        }
        int x = result[0];
        int y = result[1];
        int place = result[2];
        chess[x][y] = place;
        HistoryObserver.addHistory(result);
        return GameStatuChecker.isFiveInLine(chess, x, y);
    }

    private static void printBattleInfo(int winingAgent, boolean isMoveFirst){
        System.out.println("==================================");
        switch (winingAgent) {
            case  GameConst.BENCH_MARKER:
                System.out.println("BENCH_MARKER wins");
                System.out.println("First move: " + isMoveFirst);
                break;
            case GameConst.PURE_HEURISTIC:
                System.out.println("PURE_HEURISTIC wins");
                System.out.println("First move: " + isMoveFirst);
                break;
            case GameConst.MINIMAX:
                System.out.println("MINIMAX wins");
                System.out.println("First move: " + isMoveFirst);
                break;
            case GameConst.ALPHA_BETA_PRUNING:
                System.out.println("ABP wins");
                System.out.println("First move: " + isMoveFirst);
                break;
            case GameConst.TRANSPOSITION_SEARCH:
                System.out.println("TRANSPOSITION_SEARCH wins");
                System.out.println("First move: " + isMoveFirst);
                break;
            case GameConst.KILLER_HEURISTIC:
                System.out.println("KILLER_HEURISTIC ABP wins");
                System.out.println("First move: " + isMoveFirst);
                break;
            case GameConst.THREAT_SPACE_SEARCH:
                System.out.println("THREAT_SPACE_SEARCH wins");
                System.out.println("First move: " + isMoveFirst);
                break;
            default:
                System.err.println("Invalid Ai Index");
        }
        System.out.println("==================================");
    }
}
