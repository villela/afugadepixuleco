package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.R;

public class Pillar extends Thing {

    private final float height = 16f;
    private final float width = 3f;

    public Pillar(Floor floor, float x) {
        super(floor, x);
    }

    @Override
    public int getDrawableId() {
        return R.raw.pillar;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getWidth() {
        return width;
    }

}
