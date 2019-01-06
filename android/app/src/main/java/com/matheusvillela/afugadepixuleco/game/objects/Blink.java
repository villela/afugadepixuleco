package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;

import java.util.ArrayList;
import java.util.List;

public abstract class Blink extends MovingThing implements MovingThing.RemovableThing {

    private static final List<BoundingBox> BOXES = new ArrayList<>();
    private int stepsRemove = 50;

    public Blink(Logic logic) {
        super(logic, Floor.ZERO, 50, 0);
    }

    @Override
    public float getHeight() {
        return 100;
    }

    @Override
    public float getWidth() {
        return 100;
    }

    @Override
    public boolean step() {
        if (stepsRemove == 1) {
            return false;
        } else if (stepsRemove > 1) {
            stepsRemove--;
            return true;
        }
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {

    }

    @Override
    public List<BoundingBox> getBoxes() {
        return BOXES;
    }

    @Override
    public void remove() {
        stepsRemove = Moro.STEPS_TO_BLOCK;
    }

    @Override
    public float getAlpha() {
        if (stepsRemove == 0) {
            return 1;
        } else {
            float v = stepsRemove / (float) Moro.STEPS_TO_BLOCK;
            v = v / 0.7f;
            return v;
        }
    }

    @Override
    public int getPenaltySteps() {
        return 0;
    }
}
