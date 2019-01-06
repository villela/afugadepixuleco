package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.SectionController;

import java.util.ArrayList;
import java.util.List;

public class Mortadela extends MovingThing implements MovingThing.RemovableThing {
    private final float speedX;
    private final float baseY;
    private static final float WIDTH = 6f;
    private static final float yDiff = 6f;
    private final SectionController.Side side;
    private int step = 0;
    private static final List<BoundingBox> NORMAL_BOXES = new ArrayList<BoundingBox>() {{
        add(new BoundingBox(1f, 4f, -1.2f, 1.2f));
    }};
    private Bitmap invertedBitmap;
    private int stepsRemove = 0;

    public Mortadela(Logic logic, Floor floor, float speedX, SectionController.Side side) {
        super(logic, floor, side == SectionController.Side.RIGHT ? -WIDTH : (100 + WIDTH));
        this.speedX = side == SectionController.Side.RIGHT ? speedX : -speedX;
        this.baseY = floor.getHeight() + 2f;
        this.side = side;
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
        position.x += speedX;
        position.y = baseY + (yDiff / 2f) + ((float) Math.sin(step / 60.f) * yDiff);
        if (position.x > 100f + WIDTH || position.x < 0.0f - WIDTH) {
            return false;
        }
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {

    }

    @Override
    public Bitmap getBitmap() {
        if (side == SectionController.Side.LEFT) {
            return super.getBitmap();
        } else {
            if (this.invertedBitmap == null) {
                this.invertedBitmap = getInvertedBitmap(getDrawableId());
            }
            return this.invertedBitmap;
        }
    }

    @Override
    public List<BoundingBox> getBoxes() {
        return NORMAL_BOXES;
    }

    @Override
    public int getDrawableId() {
        return R.raw.paomortadela;
    }

    @Override
    public float getHeight() {
        return 5;
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
        return 50;
    }
}
