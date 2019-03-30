package observer;

import java.util.Stack;

/**
 * This class is used to maintain the game history
 *
 * @author Cirun Zhang
 * @version 1.0
 */
public class HistoryObserver {
    private HistoryObserver() {
    }

    /**
     * A stack used to store the history moves
     */
    private static Stack<int[]> history = new Stack<>();

    /**
     * Add new history
     *
     * @param move Position information of the new move
     */
    public static void addHistory(int[] move) {
        history.push(move);
    }

    /**
     * Remove and return the last historical move
     *
     * @return Last move
     */
    public static int[] popHistory() {
        return history.pop();
    }

    /**
     * Get the size of historical moves
     *
     * @return Size of the history stack
     */
    public static int getHistorySize() {
        return history.size();
    }

    /**
     * Reset the history stack
     */
    public static void cleanHistory() {
        history = new Stack<>();
    }

    public static Stack<int[]> getHistory() {
        return history;
    }
}
