package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.R;

public class Minimap extends Thing {

    public static float WIDTH = 50f;
    public static float HEIGHT = 8f;
    public static float Y = 1.2f;
    public static float X = 50f;

    public Minimap() {
        super(Floor.ZERO, X, Y);
    }

    @Override
    public int getDrawableId() {
        return R.raw.minimap;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

}
