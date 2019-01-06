package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.Logic;

public class BlinkBlue extends Blink {
    public BlinkBlue(Logic logic) {
        super(logic);
    }

    @Override
    public int getDrawableId() {
        return R.raw.blink_blue;
    }
}
