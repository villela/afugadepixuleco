package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.R;

public class Table extends Thing {

    private final float height;
    private final float width;

    public Table(Floor floor, float x, float height, float width) {
        super(floor, x);
        this.height = height;
        this.width = width;
    }

    @Override
    public int getDrawableId() {
        return R.raw.table;
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
