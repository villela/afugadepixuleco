package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.R;

public class Background extends Thing {

    public Background() {
        super(Floor.ZERO, 50);
    }

    @Override
    public int getDrawableId() {
        return R.raw.bg;
    }

    @Override
    public float getHeight() {
        return 100;
    }

    @Override
    public float getWidth() {
        return 100;
    }

}
