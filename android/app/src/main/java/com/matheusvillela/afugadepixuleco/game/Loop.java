package com.matheusvillela.afugadepixuleco.game;

import timber.log.Timber;

public abstract class Loop extends Thread {
    private long updateInterval;
    private boolean running = true;

    public Loop(long updateInterval) {
        this.updateInterval = updateInterval;
    }

    @Override
    public final void run() {
        long estimatedTime = System.currentTimeMillis();
        while (running) {
            long currentTime = System.currentTimeMillis();
            long lag = currentTime - estimatedTime;
            step();
            long elapsed = System.currentTimeMillis() - currentTime;
            long sleepTime = updateInterval - lag - elapsed;
            if(sleepTime < 0) {
                Timber.d("sleep: %s", sleepTime);
                sleepTime = 0;
            }
            estimatedTime = System.currentTimeMillis() + sleepTime;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Timber.d(e, "thread interrupted");
                running = false;
            }
        }
    }

    public abstract void step();

    public final void close() {
        this.running = false;
    }
}
