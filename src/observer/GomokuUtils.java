package observer;

/**
 * This class is a tool class which provides some useful methods
 *
 * @author  Chang ta'z jun
 * @version Version 1.0
 */
public class GomokuUtils {
    private GomokuUtils(){}

    /**
     * Get manhattan distance between two sets of coordinates
     * @param x1 x coordinate of first piece
     * @param y1 y coordinate of first piece
     * @param x2 x coordinate of second piece
     * @param y2 y coordinate of second piece
     * @return distance
     */
    public static int ManhattanDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
