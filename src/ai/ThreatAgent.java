package ai;

import ai.constant.AiConst;
import ai.utility.AiUtils;
import ai.utility.ChessboardScanUtils;
import gui.Background;
import gui.Chessboard;
import gui.constant.GuiConst;

import java.util.ArrayList;

/**
 * This class is an Ai agent using threat space search.
 */
public class ThreatAgent extends Agent {
    //[[lastX, lastY], [x, y]]

    private static ArrayList<int[]> threatSequence = new ArrayList<>(4);

    private ThreatAgent() {
    }

    public static int[] startThreatSpaceSearch(int[][] chess) {
        if (BasicAgent.detectThreats(chess, aiPieceType)) {
            Background.addMessage("Detected emergent threat, abp");
            return AdvancedAgent.startAlphaBetaPruning_preSort(chess);
        }
        if (!threatSequence.isEmpty()) {
            if (isMoveValid(threatSequence.get(0), chess)) {
                System.out.println("Wining sequence found, second move");
                Background.addMessage("Wining sequence found, second move");
                return threatSequence.remove(0);
            } else {
                threatSequence.clear();
                Background.addMessage("threat move is blocked");
                return AdvancedAgent.startAlphaBetaPruning_preSort(chess);
            }

        } else if (threatSpaceSearch(chess, 0, -1, -1)) {
            Background.addMessage("Wining sequence found, first move");
            System.out.println("Wining sequence found, first move");
            return threatSequence.remove(0);
        } else {
            Background.addMessage("No threat sequence found, abp");
            System.out.println("No threat sequence found, abp");
            return AdvancedAgent.startAlphaBetaPruning_preSort(chess);
        }
    }

    @Deprecated public static void startThreatSpaceSearchTest(int[][] chess) {
        System.out.println("SUCCESS? " + threatSpaceSearch(chess, 0, -1, -1));
        System.out.println("size " + threatSequence.size());
    }

    /**
     * Method of depth-first threat space search.
     * Scans each possible gain squares and if it forms a dependent threat with it's child node.
     *
     * @param chess
     * @param depth
     * @param lastThreatX
     * @param lastThreatY
     * @return
     */
    public static boolean threatSpaceSearch(int[][] chess, int depth, int lastThreatX, int lastThreatY) {
        boolean success = false;
        if (depth >= 2) {
            //System.out.println("finish");
            return false;
        }

        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                //for each empty tiles

                int threatDirection;

                if (chess[i][j] == 0 && ((threatDirection = detectPotentialThreatWithDirection(i, j, chess))
                    != AiConst.NO_THREAT)) {
                    System.out.println("X " + i + "Y " + j + " depth" + depth);
                    if (detectWiningThreatSequence(i, j, lastThreatX, lastThreatY, chess)) {
                        //@Todo detect the winning threat e.g. -1-1-1-1 or -1-1-1, composed with these two threats.
                        System.out.println("first move: " + lastThreatX + " - " + lastThreatY);
                        System.out.println("Next move: " + i + " - " + j);
                        threatSequence.add(new int[] {lastThreatX, lastThreatY, aiPieceType});
                        threatSequence.add(new int[] {i, j, aiPieceType});
                        return true;
                    } else {
                        //NOTE: | or || do effect the recursion!
                        //lazy Operator(||) would stop the recursion after the first wining sequence is found.
                        //Todo 模拟对手防御堵子
                        int[][] nextChess = AiUtils.nextMoveChessboard(chess, i, j, aiPieceType);
                        //Defense the threat.
                        defenseSimulation(nextChess , i, j, aiPieceType ,threatDirection);

                        success = success || threatSpaceSearch(nextChess, depth + 1, i, j);
                    }
                }
            }
        }
        return success;
    }

    @Deprecated public static void findAllThreat(int[][] chess) {
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            for (int j = 0; j < GuiConst.TILE_NUM_PER_ROW; j++) {
                //for each empty tiles, calculates their marks
                if (chess[i][j] == 0) {
                    if (detectPotentialThreat(i, j, chess)) {
                        System.out.println("threat: " + i + "--" + j);
                    }
                }
            }
        }
        System.out.println("done");
    }

    public static boolean detectWiningThreatSequence(int x, int y, int lastX, int lastY, int[][] chess) {
        if (x == lastX) {
            //同一个数组 在同一行 scanHorizontal
            String pieces = scanHorizontal(chess, x, y, lastX, lastY, aiPieceType);
            if (isDependentThreat(pieces) && numOfNearbyThreats(x, y, chess, aiPieceType) >= 2) {
                //@Todo (x, y) has nearby threats >= 2
                return true;
            }
            //System.out.println(pieces + " HHH");
        } else if (y == lastY) {
            //在同一列，不同数组的相同位置
            String pieces = scanVertical(chess, x, y, lastX, lastY, aiPieceType);
            if (isDependentThreat(pieces) && numOfNearbyThreats(x, y, chess, aiPieceType) >= 2) {

                return true;
            }
            //System.out.println(pieces + " VVV");
        } else if (x - lastX == -(y - lastY)) {
            //diagonal
            String pieces = scanDiagonal(chess, x, y, lastX, lastY, aiPieceType);
            if (isDependentThreat(pieces) && numOfNearbyThreats(x, y, chess, aiPieceType) >= 2) {

                return true;
            }
            //System.out.println(pieces + " DDD");
        } else if (x - lastX == y - lastY) {
            //anti-diagonal
            String pieces = scanAntiDiagonal(chess, x, y, lastX, lastY, aiPieceType);
            if (isDependentThreat(pieces) && numOfNearbyThreats(x, y, chess, aiPieceType) >= 2) {
                return true;
            }
            //System.out.println(pieces + " AAA");
        }
        return false;
    }

    public static boolean detectPotentialThreat(int x, int y, int[][] chess) {
        String horizontal = ChessboardScanUtils.verticalAdjacentPieces(chess, x, y, aiPieceType, 't');
        String vertical = ChessboardScanUtils.horizontalAdjacentPieces(chess, x, y, aiPieceType, 't');
        String diagonal = ChessboardScanUtils.diagonalAdjacentPieces(chess, x, y, aiPieceType, 't');
        String antiDiagonal = ChessboardScanUtils.antiDiagonalAdjacentPieces(chess, x, y, aiPieceType, 't');

        if (horizontal.contains(AiConst.POTENTIAL_THREAT_A) || horizontal.contains(AiConst.POTENTIAL_THREAT_B)
            || horizontal.contains(AiConst.POTENTIAL_THREAT_C) || horizontal.contains(AiConst.POTENTIAL_THREAT_D)
            || horizontal.contains(AiConst.POTENTIAL_THREAT_E) || horizontal.contains(AiConst.POTENTIAL_THREAT_F)
            || horizontal.contains(AiConst.POTENTIAL_THREAT_G) || horizontal.contains(AiConst.POTENTIAL_THREAT_H)
            || horizontal.contains(AiConst.POTENTIAL_THREAT_I)) {
            return true;
        } else if (vertical.contains(AiConst.POTENTIAL_THREAT_A) || vertical.contains(AiConst.POTENTIAL_THREAT_B)
            || vertical.contains(AiConst.POTENTIAL_THREAT_C) || vertical.contains(AiConst.POTENTIAL_THREAT_D)
            || vertical.contains(AiConst.POTENTIAL_THREAT_E) || vertical.contains(AiConst.POTENTIAL_THREAT_F)
            || vertical.contains(AiConst.POTENTIAL_THREAT_G) || vertical.contains(AiConst.POTENTIAL_THREAT_H)
            || vertical.contains(AiConst.POTENTIAL_THREAT_I)) {
            return true;
        } else if (diagonal.contains(AiConst.POTENTIAL_THREAT_A) || diagonal.contains(AiConst.POTENTIAL_THREAT_B)
            || diagonal.contains(AiConst.POTENTIAL_THREAT_C) || diagonal.contains(AiConst.POTENTIAL_THREAT_D)
            || diagonal.contains(AiConst.POTENTIAL_THREAT_E) || diagonal.contains(AiConst.POTENTIAL_THREAT_F)
            || diagonal.contains(AiConst.POTENTIAL_THREAT_G) || diagonal.contains(AiConst.POTENTIAL_THREAT_H)
            || diagonal.contains(AiConst.POTENTIAL_THREAT_I)) {
            return true;
        } else if (antiDiagonal.contains(AiConst.POTENTIAL_THREAT_A) || antiDiagonal
            .contains(AiConst.POTENTIAL_THREAT_B) || antiDiagonal.contains(AiConst.POTENTIAL_THREAT_C) || antiDiagonal
            .contains(AiConst.POTENTIAL_THREAT_D) || antiDiagonal.contains(AiConst.POTENTIAL_THREAT_E) || antiDiagonal
            .contains(AiConst.POTENTIAL_THREAT_F) || antiDiagonal.contains(AiConst.POTENTIAL_THREAT_G) || antiDiagonal
            .contains(AiConst.POTENTIAL_THREAT_H) || antiDiagonal.contains(AiConst.POTENTIAL_THREAT_I)) {
            return true;
        }
        return false;
    }
    //Determine whether or not a position is a gain square, if yes, return the direction of the threat
    public static int detectPotentialThreatWithDirection(int x, int y, int[][] chess) {
        String vertical = ChessboardScanUtils.verticalAdjacentPieces(chess, x, y, aiPieceType, 't');
        String horizontal = ChessboardScanUtils.horizontalAdjacentPieces(chess, x, y, aiPieceType, 't');
        String diagonal = ChessboardScanUtils.diagonalAdjacentPieces(chess, x, y, aiPieceType, 't');
        String antiDiagonal = ChessboardScanUtils.antiDiagonalAdjacentPieces(chess, x, y, aiPieceType, 't');

        if (horizontal.contains(AiConst.POTENTIAL_THREAT_A) || horizontal.contains(AiConst.POTENTIAL_THREAT_B)
            || horizontal.contains(AiConst.POTENTIAL_THREAT_C) || horizontal.contains(AiConst.POTENTIAL_THREAT_D)
            || horizontal.contains(AiConst.POTENTIAL_THREAT_E) || horizontal.contains(AiConst.POTENTIAL_THREAT_F)
            || horizontal.contains(AiConst.POTENTIAL_THREAT_G) || horizontal.contains(AiConst.POTENTIAL_THREAT_H)
            || horizontal.contains(AiConst.POTENTIAL_THREAT_I)) {
            return AiConst.HORIZONTAL_THREAT;
        } else if (vertical.contains(AiConst.POTENTIAL_THREAT_A) || vertical.contains(AiConst.POTENTIAL_THREAT_B)
            || vertical.contains(AiConst.POTENTIAL_THREAT_C) || vertical.contains(AiConst.POTENTIAL_THREAT_D)
            || vertical.contains(AiConst.POTENTIAL_THREAT_E) || vertical.contains(AiConst.POTENTIAL_THREAT_F)
            || vertical.contains(AiConst.POTENTIAL_THREAT_G) || vertical.contains(AiConst.POTENTIAL_THREAT_H)
            || vertical.contains(AiConst.POTENTIAL_THREAT_I)) {
            return AiConst.VERTICAL_THREAT;
        } else if (diagonal.contains(AiConst.POTENTIAL_THREAT_A) || diagonal.contains(AiConst.POTENTIAL_THREAT_B)
            || diagonal.contains(AiConst.POTENTIAL_THREAT_C) || diagonal.contains(AiConst.POTENTIAL_THREAT_D)
            || diagonal.contains(AiConst.POTENTIAL_THREAT_E) || diagonal.contains(AiConst.POTENTIAL_THREAT_F)
            || diagonal.contains(AiConst.POTENTIAL_THREAT_G) || diagonal.contains(AiConst.POTENTIAL_THREAT_H)
            || diagonal.contains(AiConst.POTENTIAL_THREAT_I)) {
            return AiConst.DIAGONAL_THREAT;
        } else if (antiDiagonal.contains(AiConst.POTENTIAL_THREAT_A) || antiDiagonal
            .contains(AiConst.POTENTIAL_THREAT_B) || antiDiagonal.contains(AiConst.POTENTIAL_THREAT_C) || antiDiagonal
            .contains(AiConst.POTENTIAL_THREAT_D) || antiDiagonal.contains(AiConst.POTENTIAL_THREAT_E) || antiDiagonal
            .contains(AiConst.POTENTIAL_THREAT_F) || antiDiagonal.contains(AiConst.POTENTIAL_THREAT_G) || antiDiagonal
            .contains(AiConst.POTENTIAL_THREAT_H) || antiDiagonal.contains(AiConst.POTENTIAL_THREAT_I)) {
            return AiConst.ANTIDIAGONAL_THREAT;
        }
        return AiConst.NO_THREAT;
    }

    //Scan the whole row
    public static String scanHorizontal(int[][] chess, int x, int y, int lastX, int lastY, int pieceType) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            int piece = chess[x][i];
            if (i == y || i == lastY) {
                builder.append("t");
            } else {
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }
        return builder.toString();
    }

    public static String scanVertical(int[][] chess, int x, int y, int lastX, int lastY, int pieceType) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < GuiConst.TILE_NUM_PER_ROW; i++) {
            int piece = chess[i][y];
            if (i == x || i == lastX) {
                builder.append("t");
            } else {
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }
        return builder.toString();
    }

    public static String scanDiagonal(int[][] chess, int x, int y, int lastX, int lastY, int pieceType) {
        //Scans the chessboard from right top to left bottom diagonally
        StringBuilder builder = new StringBuilder();
        int upperBoundX = x;
        int upperBoundY = y;

        //get the coordinates of right top corner
        while (upperBoundY < 14 && upperBoundX > 0) {
            upperBoundY++;
            upperBoundX--;
        }

        for (int i = upperBoundX, j = upperBoundY; i <= upperBoundY && j >= upperBoundX; i++, j--) {
            if ((i == x && j == y) || (i == lastX && j == lastY)) {
                builder.append("t");
            } else {
                int piece = chess[i][j];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }
        return builder.toString();
    }

    public static String scanAntiDiagonal(int[][] chess, int x, int y, int lastX, int lastY, int pieceType) {
        //Scans the chessboard from left top to right bottom antiDiagonally
        StringBuilder builder = new StringBuilder();
        int upperBoundX = x;
        int upperBoundY = y;
        int lowerBoundX = x;
        int lowerBoundY = y;
        while (upperBoundX > 0 && upperBoundY > 0) {
            upperBoundX--;
            upperBoundY--;
        }

        while (lowerBoundX < 14 && lowerBoundY < 14) {
            lowerBoundX++;
            lowerBoundY++;
        }

        for (int i = upperBoundX, j = upperBoundY; i <= lowerBoundX && j <= lowerBoundY; i++, j++) {
            int piece = chess[i][j];
            if ((i == x && j == y) || (i == lastX && j == lastY)) {
                builder.append("t");
            } else {
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }

        }
        return builder.toString();
    }

    private static boolean isDependentThreat(String threat) {
        if (threat.contains(AiConst.DEPENDENT_THREAT_A) || threat.contains(AiConst.DEPENDENT_THREAT_B) || threat
            .contains(AiConst.DEPENDENT_THREAT_C) || threat.contains(AiConst.DEPENDENT_THREAT_D) || threat
            .contains(AiConst.DEPENDENT_THREAT_E) || threat.contains(AiConst.DEPENDENT_THREAT_F) || threat
            .contains(AiConst.DEPENDENT_THREAT_G) || threat.contains(AiConst.DEPENDENT_THREAT_H) || threat
            .contains(AiConst.DEPENDENT_THREAT_I) || threat.contains(AiConst.DEPENDENT_THREAT_J) || threat
            .contains(AiConst.DEPENDENT_THREAT_K) || threat.contains(AiConst.DEPENDENT_THREAT_L) || threat
            .contains(AiConst.DEPENDENT_THREAT_M) || threat.contains(AiConst.DEPENDENT_THREAT_O) || threat
            .contains(AiConst.DEPENDENT_THREAT_P) || threat.contains(AiConst.DEPENDENT_THREAT_Q) || threat
            .contains(AiConst.DEPENDENT_THREAT_R) || threat.contains(AiConst.DEPENDENT_THREAT_S) || threat
            .contains(AiConst.DEPENDENT_THREAT_T) || threat.contains(AiConst.DEPENDENT_THREAT_U) || threat
            .contains(AiConst.DEPENDENT_THREAT_V) || threat.contains(AiConst.DEPENDENT_THREAT_W) || threat
            .contains(AiConst.DEPENDENT_THREAT_X) || threat.contains(AiConst.DEPENDENT_THREAT_Y) || threat
            .contains(AiConst.DEPENDENT_THREAT_Z) || threat.contains(AiConst.DEPENDENT_THREAT_AA) || threat
            .contains(AiConst.DEPENDENT_THREAT_AB) || threat.contains(AiConst.DEPENDENT_THREAT_AC) || threat
            .contains(AiConst.DEPENDENT_THREAT_AD) || threat.contains(AiConst.DEPENDENT_THREAT_AE) || threat
            .contains(AiConst.DEPENDENT_THREAT_AF) || threat.contains(AiConst.DEPENDENT_THREAT_AG) || threat
            .contains(AiConst.DEPENDENT_THREAT_AH) || threat.contains(AiConst.DEPENDENT_THREAT_AI)) {

            System.out.println("Found Dependent Sequence.");
            return true;
        }
        return false;
    }

    public static int numOfNearbyThreats(int x, int y, int[][] chess, int pieceType) {
        String horizontal = ChessboardScanUtils.verticalAdjacentPieces(chess, x, y, pieceType, '1');
        String vertical = ChessboardScanUtils.horizontalAdjacentPieces(chess, x, y, pieceType, '1');
        String diagonal = ChessboardScanUtils.diagonalAdjacentPieces(chess, x, y, pieceType, '1');
        String antiDiagonal = ChessboardScanUtils.antiDiagonalAdjacentPieces(chess, x, y, pieceType, '1');

        return detectOneRowThreats(horizontal, chess) + detectOneRowThreats(vertical, chess)
            + detectOneRowThreats(diagonal, chess) + detectOneRowThreats(antiDiagonal, chess);
    }

    public static int detectOneRowThreats(String pieces, int[][] chess) {
        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_A) || pieces
            .contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_B) || pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_C)
            || pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_D) || pieces
            .contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_E) || pieces.contains(AiConst.IMPLICATE_THREE_A) || pieces
            .contains(AiConst.IMPLICATE_THREE_B) || pieces.contains(AiConst.IMPLICATE_THREE_C) || pieces
            .contains(AiConst.IMPLICATE_THREE_D)) {
            return 1;
        }
        return 0;
    }

    private static boolean isMoveValid(int[] threatMove, int[][] chess) {
        return chess[threatMove[0]][threatMove[1]] == AiConst.EMPTY_STONE;
    }

    public static void defenseSimulation(int[][] chess, int x, int y, int pieceType, int threatDirection) {
        switch (threatDirection) {
            case AiConst.HORIZONTAL_THREAT:
                defenseHorizontal(chess, x, y, pieceType);
                break;
            case AiConst.VERTICAL_THREAT:
                defenseVertical(chess, x, y, pieceType);
                break;
            case AiConst.DIAGONAL_THREAT:
                defenseDiagonal(chess, x, y, pieceType);
                break;
            case AiConst.ANTIDIAGONAL_THREAT:
                defenseAntiDiagonal(chess, x, y, pieceType);
                default:
                    System.out.println("this should never happen");
        }
    }

    public static void defenseVertical(int[][] chess, int x, int y, int pieceType) {
        ArrayList<DefensiveMove> defensiveMoves = new ArrayList<>();
        ArrayList<DefensiveMove> possibleMoves = new ArrayList<>();

        for (int i = x - 4; i < x; i++) {
            if (Chessboard.validateArrayIndex(i) && chess[i][y] == AiConst.EMPTY_STONE) {
                String pieces = ChessboardScanUtils.verticalAdjacentPieces(chess, i, y, pieceType, 't');
                possibleMoves.add(new DefensiveMove(i, y, pieces));
            }
        }

        for (int i = x + 1; i < x + 5; i++) {
            if (Chessboard.validateArrayIndex(i) && chess[i][y] == AiConst.EMPTY_STONE) {
                String pieces = ChessboardScanUtils.verticalAdjacentPieces(chess, i, y, pieceType, 't');
                possibleMoves.add(new DefensiveMove(i, y, pieces));
            }
        }

        generateDefensiveMoves(defensiveMoves, possibleMoves);

        for (DefensiveMove move : defensiveMoves) {
            chess[move.x][move.y] = pieceType * -1;
        }

    }

    public static void defenseHorizontal(int[][] chess, int x, int y, int pieceType) {
        ArrayList<DefensiveMove> defensiveMoves = new ArrayList<>();
        ArrayList<DefensiveMove> possibleMoves = new ArrayList<>();

        for (int i = y - 4; i < y; i++) {
            if (Chessboard.validateArrayIndex(i) && chess[x][i] == AiConst.EMPTY_STONE) {
                String pieces = ChessboardScanUtils.horizontalAdjacentPieces(chess, x, i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x, i, pieces));
            }
        }

        for (int i = y + 1; i < y + 5; i++) {
            if (Chessboard.validateArrayIndex(i) && chess[x][i] == AiConst.EMPTY_STONE) {
                String pieces = ChessboardScanUtils.horizontalAdjacentPieces(chess, x, i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x, i, pieces));
            }
        }

        generateDefensiveMoves(defensiveMoves, possibleMoves);

        for (DefensiveMove move : defensiveMoves) {
            chess[move.x][move.y] = pieceType * -1;
        }
    }

    public static void defenseDiagonal(int[][] chess, int x, int y, int pieceType) {
        ArrayList<DefensiveMove> defensiveMoves = new ArrayList<>();
        ArrayList<DefensiveMove> possibleMoves = new ArrayList<>();

        for (int i = 4; i > 0; i--) {
            if (Chessboard.validateArrayIndex(x - i) && Chessboard.validateArrayIndex(y - i)
                && chess[x - i][y - i] == AiConst.EMPTY_STONE) {
                String pieces = ChessboardScanUtils.diagonalAdjacentPieces(chess, x - i, y - i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x - i, y - i, pieces));
            }
        }
        //check from target to right bottom
        for (int i = 1; i < 5; i++) {
            if (Chessboard.validateArrayIndex(x + i) && Chessboard.validateArrayIndex(y + i)
                && chess[x + i][y + i] == AiConst.EMPTY_STONE) {
                String pieces = ChessboardScanUtils.diagonalAdjacentPieces(chess, x + i, y + i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x + i, y + i, pieces));
            }
        }

        generateDefensiveMoves(defensiveMoves, possibleMoves);

        for (DefensiveMove move : defensiveMoves) {
            chess[move.x][move.y] = pieceType * -1;
        }
    }

    public static void defenseAntiDiagonal(int[][] chess, int x, int y, int pieceType) {
        ArrayList<DefensiveMove> defensiveMoves = new ArrayList<>();
        ArrayList<DefensiveMove> possibleMoves = new ArrayList<>();

        for (int i = 4; i > 0; i--) {
            if (Chessboard.validateArrayIndex(x + i) && Chessboard.validateArrayIndex(y - i)
                && chess[x + i][y - i] == AiConst.EMPTY_STONE) {

                String pieces = ChessboardScanUtils.antiDiagonalAdjacentPieces(chess, x + i, y - i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x + i, y - i, pieces));
            }
        }

        //check from target to left bottom
        for (int i = 1; i < 5; i++) {
            if (Chessboard.validateArrayIndex(x - i) && Chessboard.validateArrayIndex(y + i)
                && chess[x - i][y + i] == AiConst.EMPTY_STONE) {

                String pieces = ChessboardScanUtils.antiDiagonalAdjacentPieces(chess, x - i, y + i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x - i, y + i, pieces));
            }
        }

        generateDefensiveMoves(defensiveMoves, possibleMoves);

        for (DefensiveMove move : defensiveMoves) {
            chess[move.x][move.y] = pieceType * -1;
        }
    }

    private static void generateDefensiveMoves(ArrayList<DefensiveMove> defensiveMoves,
        ArrayList<DefensiveMove> possibleMoves) {
        if (defensiveMoves.isEmpty()) {
            for (DefensiveMove move : possibleMoves) {
                if (move.pieces.contains(AiConst.DEFENSE_THREAT_A1) || move.pieces
                    .contains(AiConst.DEFENSE_THREAT_A2)) {
                    defensiveMoves.add(move);
                }
            }
        }

        if (defensiveMoves.isEmpty()) {
            for (DefensiveMove move : possibleMoves) {
                if (move.pieces.contains(AiConst.DEFENSE_THREAT_B1) || move.pieces
                    .contains(AiConst.DEFENSE_THREAT_B2)) {
                    defensiveMoves.add(move);
                }
            }
        }

        if (defensiveMoves.isEmpty()) {
            for (DefensiveMove move : possibleMoves) {
                if (move.pieces.contains(AiConst.DEFENSE_THREAT_C1) || move.pieces.contains(AiConst.DEFENSE_THREAT_C2)
                    || move.pieces.contains(AiConst.DEFENSE_THREAT_C3) || move.pieces
                    .contains(AiConst.DEFENSE_THREAT_C4) || move.pieces.contains(AiConst.DEFENSE_THREAT_C5)
                    || move.pieces.contains(AiConst.DEFENSE_THREAT_C6)) {
                    defensiveMoves.add(move);
                }
            }
        }

        if (defensiveMoves.isEmpty()) {
            for (DefensiveMove move : possibleMoves) {
                if (move.pieces.contains(AiConst.DEFENSE_THREAT_D1) || move.pieces
                    .contains(AiConst.DEFENSE_THREAT_D2)) {
                    defensiveMoves.add(move);
                }
            }
        }
    }
}

class DefensiveMove {
    int x;
    int y;
    String pieces;

    public DefensiveMove(int x, int y, String pieces) {
        this.x = x;
        this.y = y;
        this.pieces = pieces;
    }
}