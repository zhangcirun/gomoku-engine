package game;

import ai.Agent;
import ai.constant.AiConst;
import ai.utility.ChessboardScanUtils;
import gui.constant.GuiConst;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AiBenchMarker extends Agent {
    private static List<int[]> moveCandidates = new ArrayList<>();
    public static int[] nextMove(int[][] chess) {
        moveCandidates.clear();

        if(isOpenning(chess)){
            return openingStrategy();
        }else{
            int currentMaxScore = -1000000;
            for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
                for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                    //for each empty tiles, calculates their marks
                    if (chess[i][j] == AiConst.EMPTY_STONE) {
                        int score = totalMark(chess, i, j);
                        if (score > currentMaxScore) {
                            currentMaxScore = score;
                            //Add to the head of the candidate list
                            moveCandidates.add(0, new int[] {i, j});
                        }
                    }
                }
            }
            if(moveCandidates.size() == 0){
                System.err.println("cant");
            }
            int randomIndex = ThreadLocalRandom.current().nextInt(0, (moveCandidates.size() / 3) + 1);
            int[] randomCandidate = moveCandidates.get(randomIndex);
            return new int[] {randomCandidate[0], randomCandidate[1], aiPieceType};
        }
    }

    private static int totalMark(int[][] chess, int x, int y) {
        return markPiece(chess, x, y, -1) + markPiece(chess, x, y, 1);
    }

    private static int markPiece(int[][] chess, int x, int y, int pieceType) {
        return marking(ChessboardScanUtils.horizontalAdjacentPieces(chess, x, y, pieceType, '1')) + marking(ChessboardScanUtils.verticalAdjacentPieces(chess, x, y, pieceType, '1'))
            + marking(ChessboardScanUtils.diagonalAdjacentPieces(chess, x, y, pieceType, '1')) + marking(ChessboardScanUtils.antiDiagonalAdjacentPieces(chess, x, y, pieceType, '1'));
    }

    private static int marking(String pieces) {
        if (pieces.contains(AiConst.IMPLICATE_FIVE)) {
            return 500000;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_DOUBLE_EMPTY)) {
            return 10000;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_A) || pieces
            .contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_B) || pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_C)
            || pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_D) || pieces
            .contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_E)) {
            return 720;
        }

        if (pieces.contains(AiConst.IMPLICATE_THREE_A) || pieces.contains(AiConst.IMPLICATE_THREE_B) || pieces
            .contains(AiConst.IMPLICATE_THREE_C) || pieces.contains(AiConst.IMPLICATE_THREE_D)) {
            return 720;
        }

        if (pieces.contains(AiConst.IMPLICATE_TWO_A) || pieces.contains(AiConst.IMPLICATE_TWO_B) || pieces
            .contains(AiConst.IMPLICATE_TWO_C)) {
            return 120;
        }

        if (pieces.contains(AiConst.IMPLICATE_ONE_A) || pieces.contains(AiConst.IMPLICATE_ONE_B)) {
            return 20;
        }

        return 0;

    }

    /**
     * Opening strategy with randomness
     * @return A random opening move
     */
    private static int[] openingStrategy(){
        //random integer between [min, max]
        int randomX = ThreadLocalRandom.current().nextInt(5, 8 + 1);
        int randomY = ThreadLocalRandom.current().nextInt(5, 8 + 1);
        return new int[] {randomX, randomY, aiPieceType};
    }
}
