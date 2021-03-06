package engine;

import java.util.concurrent.TimeUnit;

public class GameTime {

    private static final int FPS_TARGET = 60;

    private static GameTime instance;
    private static  int currentFPS;
    private static int fpsCount;
    private static long fpsTimeDelta;
    private static long gameStartTime;
    private long syncTime;


    public static Long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static int getCurrentFPS() {
        return (currentFPS > 0) ? currentFPS : fpsCount;
    }

    public static long getElapsedTime() {
        return System.currentTimeMillis() - gameStartTime;
    }

    public static String getElapsedFormattedTime() {
        long time = System.currentTimeMillis() - gameStartTime;

        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);

        long minutes= TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);

        return String.format("Time : %02d:%02d:%02d", hours, minutes, seconds);
    }

    protected static GameTime getInstance() {
        if (null == instance) {
            instance = new GameTime();
        }
        return instance;
    }


    protected void synchronize() {
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        updateSynchronizationTime();
    }

    public long getSleepTime() {
        long targetTime = 1000L/FPS_TARGET;
        long sleep = targetTime -
                (System.currentTimeMillis() - syncTime);
        if (sleep < 0) {
            sleep = 4;
        }
        return sleep;
    }


    public void updateSynchronizationTime() {
        syncTime = System.currentTimeMillis();
    }


    private void update() {
        fpsCount ++ ;
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(getElapsedTime());
        if (fpsTimeDelta != currentSecond) {
            currentFPS = fpsCount;
            fpsCount = 0;
        }
        fpsTimeDelta = currentSecond;
    }


    private GameTime() {
        updateSynchronizationTime();
        gameStartTime = System.currentTimeMillis();
        fpsTimeDelta = 0;
        currentFPS = 0;
    }
}
