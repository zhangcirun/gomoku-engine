package ai;

import java.util.Arrays;

public class ToolKit {
    private ToolKit(){}

    static int[][] copyArray(int[][] chess) {
        int[][] a = new int[chess.length][chess[0].length];

        for (int i = 0; i < chess.length; i++) {
            a[i] = Arrays.copyOf(chess[i], chess[i].length);
        }
        return a;
    }
}
