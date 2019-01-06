package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.Logic;

public class BlinkRed extends Blink {
    public BlinkRed(Logic logic) {
        super(logic);
    }

    @Override
    public int getDrawableId() {
        return R.raw.blink_red;
    }
}
