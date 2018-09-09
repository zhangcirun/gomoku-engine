package observer;

import java.util.Stack;

public class GameObserver {
    private GameObserver() {
    }

    private static int lastScoreAspiration = 0;

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
}
