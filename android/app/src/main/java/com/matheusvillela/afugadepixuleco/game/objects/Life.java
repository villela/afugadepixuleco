package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;

import java.util.ArrayList;
import java.util.List;

public class Life extends MovingThing {
    private List<BoundingBox> boxes = new ArrayList<>();

    public Life(Logic logic, int positionX) {
        super(logic, Floor.ZERO, positionX, 90);
    }


    @Override
    public boolean step() {
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {

    }

    @Override
    public List<BoundingBox> getBoxes() {
        return boxes;
    }

    @Override
    public int getDrawableId() {
        return R.raw.life;
    }

    @Override
    public float getHeight() {
        return 5;
    }

    @Override
    public float getWidth() {
        return 7;
    }
}
