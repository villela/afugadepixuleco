package com.matheusvillela.afugadepixuleco.game;

public interface LevelFinishedListener {
    void onLevelWon(int level, long totalTimeMillis, int pixulecoStringId);
    void onEscaped(int level);
}
