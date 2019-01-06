package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.R;

public class ElevatorBackground extends Thing {

    public ElevatorBackground(Floor floor) {
        super(floor, 50, Elevator.YDIFF);
    }

    @Override
    public int getDrawableId() {
        return R.raw.elevator_open;
    }

    @Override
    public float getHeight() {
        return Elevator.HEIGHT;
    }

    @Override
    public float getWidth() {
        return Elevator.WIDTH;
    }
}
