package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.SectionController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Foguete extends MovingThing  implements MovingThing.RemovableThing{
    private static final float WIDTH = 8f;
    private static final float HEIGHT = 4f;
    private final float speedX;
    private final float baseY;
    private static final float yDiff = 0f;
    private final SectionController.Side side;
    private int step = 0;
    private HashMap<SectionController.Side, HashMap<Integer, Integer>> bitmapsMap = new HashMap<>();

    private final static HashMap<SectionController.Side, List<BoundingBox>> BOXES = new HashMap<SectionController.Side, List<BoundingBox>>() {
        {
            put(SectionController.Side.LEFT, new ArrayList<BoundingBox>() {{
                add(new BoundingBox(0.5f, 3.5f, -3.0f, 1.0f));
                add(new BoundingBox(1.5f, 2.5f, -4.0f, -3.0f));
            }});
            put(SectionController.Side.RIGHT, new ArrayList<BoundingBox>() {{
                add(new BoundingBox(0.5f, 3.5f, -1.0f, 3.0f));
                add(new BoundingBox(1.5f, 2.5f, 3.0f, 4.0f));
            }});
        }
    };
    private int stepsRemove = 0;

    public Foguete(Logic logic, Floor floor, float speedX, SectionController.Side side) {
        super(logic, floor, side == SectionController.Side.RIGHT ? -WIDTH : (100 + WIDTH));
        this.speedX = side == SectionController.Side.RIGHT ? speedX : -speedX;
        this.baseY = floor.getHeight() + 2f;
        position.y = baseY + yDiff;
        this.side = side;

        HashMap<Integer, Integer> leftMap = new HashMap<>();
        leftMap.put(0, R.raw.foguete);
        leftMap.put(1, R.raw.foguete2);
        leftMap.put(2, R.raw.foguete3);
        bitmapsMap.put(SectionController.Side.LEFT, leftMap);

        HashMap<Integer, Integer> rightMap = new HashMap<>();
        rightMap.put(0, R.raw.foguete);
        rightMap.put(1, R.raw.foguete2);
        rightMap.put(2, R.raw.foguete3);
        bitmapsMap.put(SectionController.Side.RIGHT, rightMap);
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
        if (position.x > 100f + WIDTH || position.x < -WIDTH) {
            return false;
        }
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {

    }

    @Override
    public Bitmap getBitmap() {
        int variationIndex = (step % 15) / 5;
        HashMap<Integer, Integer> integerBitmapHashMap = bitmapsMap.get(side);
        Bitmap bitmap;
        if(side == SectionController.Side.RIGHT) {
            bitmap = getInvertedBitmap(integerBitmapHashMap.get(variationIndex));
        } else {
            bitmap = getBitmap(integerBitmapHashMap.get(variationIndex));
        }
        return bitmap;
    }

    @Override
    public List<BoundingBox> getBoxes() {
        return BOXES.get(side);
    }

    @Override
    public int getDrawableId() {
        return R.raw.foguete;
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
        if(stepsRemove == 0) {
            return 1;
        } else {
            float v = stepsRemove / (float) Moro.STEPS_TO_BLOCK;
            return v;
        }
    }

    @Override
    public int getPenaltySteps() {
        return 100;
    }
}
