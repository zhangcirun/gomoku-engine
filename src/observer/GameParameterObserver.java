package observer;

public class GameParameterObserver {
    private GameParameterObserver() {
    }

    private static int lastScoreAspiration = 0;

    public static int getLastScoreAspiration() {
        return lastScoreAspiration;
    }

    public static void updateLastScoreAspiration(int newScore) {
        lastScoreAspiration = newScore;
    }
}
