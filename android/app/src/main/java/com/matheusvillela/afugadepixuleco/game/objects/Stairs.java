package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.Position;
import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.SectionController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Stairs extends MovingThing {
    private final Floor topFloor;
    private static final float SPEED = 0.1f;
    private int[] sprites = new int[]{R.raw.stairs1, R.raw.stairs2, R.raw.stairs3,
            R.raw.stairs4};

    private final static HashMap<SectionController.Side, List<BoundingBox>> boxes = new HashMap<SectionController.Side, List<BoundingBox>>() {
        private static final float diff = 10f;

        {
            put(SectionController.Side.RIGHT, new ArrayList<BoundingBox>() {
                {
                    for (int i = 0; i < 8; i++) {
                        add(new BoundingBox(2 * i, 2 * i + 2, -1 + diff - i * 2, -1 + diff + 2 - i * 2));
                    }
                }
            });

            put(SectionController.Side.LEFT, new ArrayList<BoundingBox>() {
                {
                    for (int i = 0; i < 8; i++) {
                        add(new BoundingBox(2 * i, 2 * i + 2, -1 - diff + i * 2, -1 - diff + 2 + i * 2));
                    }
                }
            });
        }
    };

    private final SectionController.Side side;
    private int variation = 0;

    public Stairs(Logic logic, Floor bottomFloor, Floor topFloor, float x, SectionController.Side side) {
        super(logic, bottomFloor, x);
        this.topFloor = topFloor;
        this.side = side;
    }

    @Override
    public boolean step() {
        variation++;
        if (variation >= 60) {
            variation = 0;
        }
        return true;
    }

    @Override
    public Bitmap getBitmap() {
        int variationIndex = variation / 15;
        Bitmap bitmap;
        if (side == SectionController.Side.LEFT) {
            bitmap = getBitmap(sprites[variationIndex]);
        } else {
            bitmap = getInvertedBitmap(sprites[variationIndex]);
        }
        return bitmap;
    }

    @Override
    public void onCollision(MovingThing obj) {
        // nothing
    }

    public float getSpeedX() {
        return side == SectionController.Side.RIGHT ? -SPEED : SPEED;
    }

    public float getSpeedY() {
        return SPEED;
    }

    public float getDestinyX() {
        Position position = getPosition();
        //float yDiff = topFloor.getHeight() - position.y;
        float x = position.x + (side == SectionController.Side.LEFT ? 5.6f : -5.6f);
        return x;
    }

    public Floor getTopFloor() {
        return topFloor;
    }

    public float getDestinyY() {
        return topFloor.getHeight();
    }

    @Override
    public int getDrawableId() {
        return R.raw.pillar;
    }

    @Override
    public List<BoundingBox> getBoxes() {
        return boxes.get(side);
    }

    @Override
    public float getHeight() {
        return 23;
    }

    @Override
    public float getWidth() {
        return 28;
    }
}
