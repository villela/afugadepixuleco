package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.Position;
import com.matheusvillela.afugadepixuleco.game.ServiceLoop;

public class Clock {
    private final Position position;
    private final Logic logic;
    private final int totalSteps;
    private int step;
    private static final int ONE_SECOND_STEPS = (int) (1000 / ServiceLoop.UPDATE_INTERVAL);
    private static final float ALPHA_INTERVAL =  ONE_SECOND_STEPS / 3.0f;

    public Clock(Logic logic, int totalSeconds) {
        this.step = (int) (totalSeconds * 1000 / ServiceLoop.UPDATE_INTERVAL);
        this.totalSteps = this.step;
        this.position = new Position(27, 90);
        this.logic = logic;
    }

    public void step() {
        if (step == 0) {
            logic.onPixulecoEscaped();
        } else {
            step--;
        }
    }

    public Position getPosition() {
        return position;
    }

    public String getText() {
        int time = (int) (ServiceLoop.UPDATE_INTERVAL * step / 1000);
        return "" + time;
    }

    public Float getAlpha() {
        if(ServiceLoop.UPDATE_INTERVAL * step > 10000 || step == 0) {
            return null;
        }
        int i = step % ONE_SECOND_STEPS;
        if(i > ALPHA_INTERVAL) {
            return null;
        }
        return i / ALPHA_INTERVAL;
    }

    public long getTimeLeftMillis() {
        return step * ServiceLoop.UPDATE_INTERVAL;
    }

    public long getRunTimeMillis() {
        return (totalSteps - step) * ServiceLoop.UPDATE_INTERVAL;
    }

    public void removeSteps(int stepsToRemove) {
        step -= stepsToRemove;
        if(step < 0) {
            step = 0;
        }
    }
}
