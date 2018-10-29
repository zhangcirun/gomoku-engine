package ai;

import ai.constant.AiConst;
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
                        success = success || threatSpaceSearch(AiUtils.nextMoveChessboard(chess, i, j, aiPieceType),
                            depth + 1, i, j);
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
        int numOfThreat = 0;
        if (x == lastX) {
            //同一个数组 在同一行 scanHorizontal
            String pieces = scanHorizontal(chess, x, y, lastX, lastY, aiPieceType);
            if (isDependentThreat(pieces) && detectNearbyThreats(x, y, chess, aiPieceType) >= 2) {
                //@Todo (x, y) has nearby threats >= 2
                return true;
            }
            //System.out.println(pieces + " HHH");
        } else if (y == lastY) {
            //在同一列，不同数组的相同位置
            String pieces = scanVertical(chess, x, y, lastX, lastY, aiPieceType);
            if (isDependentThreat(pieces) && detectNearbyThreats(x, y, chess, aiPieceType) >= 2) {

                return true;
            }
            //System.out.println(pieces + " VVV");
        } else if (x - lastX == -(y - lastY)) {
            //diagonal
            String pieces = scanDiagonal(chess, x, y, lastX, lastY, aiPieceType);
            if (isDependentThreat(pieces) && detectNearbyThreats(x, y, chess, aiPieceType) >= 2) {

                return true;
            }
            //System.out.println(pieces + " DDD");
        } else if (x - lastX == y - lastY) {
            //anti-diagonal
            String pieces = scanAntiDiagonal(chess, x, y, lastX, lastY, aiPieceType);
            if (isDependentThreat(pieces) && detectNearbyThreats(x, y, chess, aiPieceType) >= 2) {
                return true;
            }
            //System.out.println(pieces + " AAA");
        }
        return false;
    }

    public static boolean detectPotentialThreat(int x, int y, int[][] chess) {
        String horizontal = verticalPieces(chess, x, y, aiPieceType, 't');
        String vertical = horizontalPieces(chess, x, y, aiPieceType, 't');
        String diagonal = diagonalPieces(chess, x, y, aiPieceType, 't');
        String antiDiagonal = antiDiagonalPieces(chess, x, y, aiPieceType, 't');

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

    public static int detectPotentialThreatWithDirection(int x, int y, int[][] chess) {
        String horizontal = verticalPieces(chess, x, y, aiPieceType, 't');
        String vertical = horizontalPieces(chess, x, y, aiPieceType, 't');
        String diagonal = diagonalPieces(chess, x, y, aiPieceType, 't');
        String antiDiagonal = antiDiagonalPieces(chess, x, y, aiPieceType, 't');

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
            return AiConst.ANTIDOAGONAL_THREAT;
        }
        return AiConst.NO_THREAT;
    }

    private static String verticalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType, char c) {
        StringBuilder builder = new StringBuilder();
        //check from left to target
        for (int i = xArrayPosition - 5; i < xArrayPosition; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                //0 for empty, 1 for ally, 2 for opponent
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to right
        for (int i = xArrayPosition + 1; i < xArrayPosition + 6; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[i][yArrayPosition];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    private static String horizontalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType,
        char c) {
        StringBuilder builder = new StringBuilder();
        //check from top to target
        for (int i = yArrayPosition - 5; i < yArrayPosition; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to bottom
        for (int i = yArrayPosition + 1; i < yArrayPosition + 6; i++) {
            if (Chessboard.validateArrayIndex(i)) {
                int piece = chess[xArrayPosition][i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    private static String diagonalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType, char c) {
        StringBuilder builder = new StringBuilder();
        //check from left top to target
        for (int i = 5; i > 0; i--) {
            if (Chessboard.validateArrayIndex(xArrayPosition - i) && Chessboard
                .validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition - i][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to right bottom
        for (int i = 1; i < 6; i++) {
            if (Chessboard.validateArrayIndex(xArrayPosition + i) && Chessboard
                .validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
    }

    private static String antiDiagonalPieces(int[][] chess, int xArrayPosition, int yArrayPosition, int pieceType,
        char c) {
        StringBuilder builder = new StringBuilder();
        //check from right top to target
        for (int i = 5; i > 0; i--) {
            if (Chessboard.validateArrayIndex(xArrayPosition + i) && Chessboard
                .validateArrayIndex(yArrayPosition - i)) {
                int piece = chess[xArrayPosition + i][yArrayPosition - i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        builder.append(c);

        //check from target to left bottom
        for (int i = 1; i < 6; i++) {
            if (Chessboard.validateArrayIndex(xArrayPosition - i) && Chessboard
                .validateArrayIndex(yArrayPosition + i)) {
                int piece = chess[xArrayPosition - i][yArrayPosition + i];
                builder.append(piece == 0 ? "0" : piece == pieceType ? "1" : "2");
            }
        }

        return builder.toString();
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

    public static int detectNearbyThreats(int x, int y, int[][] chess, int pieceType) {
        String horizontal = verticalPieces(chess, x, y, pieceType, '1');
        String vertical = horizontalPieces(chess, x, y, pieceType, '1');
        String diagonal = diagonalPieces(chess, x, y, pieceType, '1');
        String antiDiagonal = antiDiagonalPieces(chess, x, y, pieceType, '1');

        return detectOneRowThreatsTest(horizontal, chess) + detectOneRowThreatsTest(vertical, chess)
            + detectOneRowThreatsTest(diagonal, chess) + detectOneRowThreatsTest(antiDiagonal, chess);
    }

    public static int detectOneRowThreats(String pieces, int[][] chess) {
        int threatCount = 0;
        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_A)) {
            threatCount++;
        } else if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_B)) {
            threatCount++;
        }

        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_C)) {
            threatCount++;
        }
        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_D)) {
            threatCount++;
        }
        if (pieces.contains(AiConst.IMPLICATE_FOUR_SINGLE_EMPTY_E)) {
            threatCount++;
        }

        if (threatCount < 1) {
            if (pieces.contains(AiConst.IMPLICATE_THREE_A)) {
                threatCount++;
            } else if (pieces.contains(AiConst.IMPLICATE_THREE_B)) {
                threatCount++;
            }

            if (pieces.contains(AiConst.IMPLICATE_THREE_C)) {
                threatCount++;
            }
            if (pieces.contains(AiConst.IMPLICATE_THREE_D)) {
                threatCount++;
            }
        }

        return threatCount;
    }

    public static int detectOneRowThreatsTest(String pieces, int[][] chess) {
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

    private static void defenseSimulation(int[][] chess, int x, int y, int enemyPieceType, int threatDirection) {
        switch (threatDirection) {
            case AiConst.HORIZONTAL_THREAT:
                //
        }
    }

    public static void defenseVertical(int[][] chess, int x, int y, int pieceType) {
        ArrayList<DefensiveMove> defensiveMoves = new ArrayList<>();
        ArrayList<DefensiveMove> possibleMoves = new ArrayList<>();

        for (int i = x - 4; i < x; i++) {
            if (Chessboard.validateArrayIndex(i) && chess[i][y] == AiConst.EMPTY_STONE) {
                String pieces = verticalPieces(chess, i, y, pieceType, 't');
                possibleMoves.add(new DefensiveMove(i, y, pieces));
            }
        }

        for (int i = x + 1; i < x + 5; i++) {
            if (Chessboard.validateArrayIndex(i) && chess[i][y] == AiConst.EMPTY_STONE) {
                String pieces = verticalPieces(chess, i, y, pieceType, 't');
                possibleMoves.add(new DefensiveMove(i, y, pieces));
            }
        }

        generateDefensiveMoves(defensiveMoves, possibleMoves);

        for (DefensiveMove move : defensiveMoves) {
            System.out.println("defense: " + move.x + " " + move.y);
            chess[move.x][move.y] = pieceType * -1;
        }

    }

    public static void defenseHorizontal(int[][] chess, int x, int y, int pieceType) {
        ArrayList<DefensiveMove> defensiveMoves = new ArrayList<>();
        ArrayList<DefensiveMove> possibleMoves = new ArrayList<>();

        for (int i = y - 4; i < y; i++) {
            if (Chessboard.validateArrayIndex(i) && chess[x][i] == AiConst.EMPTY_STONE) {
                String pieces = horizontalPieces(chess, x, i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x, i, pieces));
            }
        }

        for (int i = y + 1; i < y + 5; i++) {
            if (Chessboard.validateArrayIndex(i) && chess[x][i] == AiConst.EMPTY_STONE) {
                String pieces = horizontalPieces(chess, x, i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x, i, pieces));
            }
        }

        generateDefensiveMoves(defensiveMoves, possibleMoves);

        for (DefensiveMove move : defensiveMoves) {
            System.out.println("defense: " + move.x + " " + move.y);
            chess[move.x][move.y] = pieceType * -1;
        }
    }

    public static void defenseDiagonal(int[][] chess, int x, int y, int pieceType) {
        ArrayList<DefensiveMove> defensiveMoves = new ArrayList<>();
        ArrayList<DefensiveMove> possibleMoves = new ArrayList<>();

        for (int i = 4; i > 0; i--) {
            if (Chessboard.validateArrayIndex(x - i) && Chessboard.validateArrayIndex(y - i)
                && chess[x - i][y - i] == AiConst.EMPTY_STONE) {
                String pieces = diagonalPieces(chess, x - i, y - i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x - i, y - i, pieces));
            }
        }
        //check from target to right bottom
        for (int i = 1; i < 5; i++) {
            if (Chessboard.validateArrayIndex(x + i) && Chessboard.validateArrayIndex(y + i)
                && chess[x + i][y + i] == AiConst.EMPTY_STONE) {
                String pieces = diagonalPieces(chess, x + i, y + i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x + i, y + i, pieces));
            }
        }

        generateDefensiveMoves(defensiveMoves, possibleMoves);

        for (DefensiveMove move : defensiveMoves) {
            System.out.println("defense: " + move.x + " " + move.y);
            chess[move.x][move.y] = pieceType * -1;
        }
    }

    public static void defenseAntiDiagonal(int[][] chess, int x, int y, int pieceType) {
        ArrayList<DefensiveMove> defensiveMoves = new ArrayList<>();
        ArrayList<DefensiveMove> possibleMoves = new ArrayList<>();

        for (int i = 4; i > 0; i--) {
            if (Chessboard.validateArrayIndex(x + i) && Chessboard.validateArrayIndex(y - i)
                && chess[x + i][y - i] == AiConst.EMPTY_STONE) {

                String pieces = antiDiagonalPieces(chess, x + i, y - i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x + i, y - i, pieces));
            }
        }

        //check from target to left bottom
        for (int i = 1; i < 5; i++) {
            if (Chessboard.validateArrayIndex(x - i) && Chessboard.validateArrayIndex(y + i)
                && chess[x - i][y + i] == AiConst.EMPTY_STONE) {

                String pieces = antiDiagonalPieces(chess, x - i, y + i, pieceType, 't');
                possibleMoves.add(new DefensiveMove(x - i, y + i, pieces));
            }
        }

        generateDefensiveMoves(defensiveMoves, possibleMoves);

        for (DefensiveMove move : defensiveMoves) {
            System.out.println("defense: " + move.x + " " + move.y);
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