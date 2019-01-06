package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.R;

import java.util.ArrayList;
import java.util.List;

public class Dinheiro extends MovingThing implements MovingThing.RemovableThing {
    private static final float WIDTH = 14f;
    private static final float HEIGHT = 10f;
    private static final float yDiff = -2f;
    private int step = 0;

    private static final List<BoundingBox> NORMAL_BOXES = new ArrayList<BoundingBox>() {{
        add(new BoundingBox(0f, 5f, -2f, 2f));
    }};
    private int stepsRemove = 0;

    public Dinheiro(Logic logic, Floor floor, float x) {
        super(logic, floor, x);
        position.y = floor.getHeight() + yDiff;
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
    public Bitmap getBitmap() {
        Bitmap bitmap = getBitmap(R.raw.dinheiro);
        return bitmap;
    }

    @Override
    public List<BoundingBox> getBoxes() {
        return NORMAL_BOXES;
    }

    @Override
    public int getDrawableId() {
        return R.raw.dinheiro;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public void remove() {
        stepsRemove = Moro.STEPS_TO_BLOCK / 4;
    }

    @Override
    public float getAlpha() {
        if (stepsRemove == 0) {
            return 1;
        } else {
            float v = stepsRemove / (float) (Moro.STEPS_TO_BLOCK / 4);
            return v;
        }
    }

    @Override
    public int getPenaltySteps() {
        return 0;
    }
}
