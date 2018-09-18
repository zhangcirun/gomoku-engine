package observer;

import java.util.Stack;

/**
 * This class stores the moving histories of the game into a stack
 *
 * @author chang ta'z jun
 */
public class HistoryObserver {
    private HistoryObserver() {
    }

    private static int lastScoreAspiration = 0;

    /**
     * Each move is store in an array []
     */
    private static Stack<int[]> history = new Stack<>();

    public static void addHistory(int[] move){
        history.push(move);
    }

    public static int[] popHistory(){
        return history.pop();
    }

    public static int getHistorySize(){
        return history.size();
    }

    public static int getLastScoreAspiration() {
        return lastScoreAspiration;
    }

    public static void updateLastScoreAspiration(int newScore) {
        lastScoreAspiration = newScore;
    }

    public static void cleanHistory(){
        history = new Stack<>();
    }

    public static Stack<int[]> getHistory(){
        return history;
    }
}
