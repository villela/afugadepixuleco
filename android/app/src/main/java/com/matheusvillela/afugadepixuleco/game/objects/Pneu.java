package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.R;

import java.util.ArrayList;
import java.util.List;

public class Pneu extends MovingThing implements MovingThing.RemovableThing {
    private static final float WIDTH = 5f;
    private static final float HEIGHT = 10f;
    private static final float yDiff = 0f;
    private int step = 0;

    private int[] sprites = new int[]{R.raw.pneu1, R.raw.pneu2, R.raw.pneu3};

    private static final List<BoundingBox> NORMAL_BOXES = new ArrayList<BoundingBox>() {{
        add(new BoundingBox(0f, 5f, -2f, 2f));
        add(new BoundingBox(5f, 6f, -1f, 1f));
    }};
    private int stepsRemove = 0;

    public Pneu(Logic logic, Floor floor, float x) {
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
        step++;
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {

    }

    @Override
    public Bitmap getBitmap() {
        int variationIndex = (step % 30) / 10;
        Bitmap bitmap = getBitmap(sprites[variationIndex]);
        return bitmap;
    }

    @Override
    public List<BoundingBox> getBoxes() {
        return NORMAL_BOXES;
    }

    @Override
    public int getDrawableId() {
        return R.raw.pneu1;
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
        stepsRemove = Moro.STEPS_TO_BLOCK;
    }

    @Override
    public float getAlpha() {
        if (stepsRemove == 0) {
            return 1;
        } else {
            float v = stepsRemove / (float) Moro.STEPS_TO_BLOCK;
            return v;
        }
    }

    @Override
    public int getPenaltySteps() {
        return 150;
    }
}
