package com.matheusvillela.afugadepixuleco.game;

import com.matheusvillela.afugadepixuleco.game.view.BackgroundView;
import com.matheusvillela.afugadepixuleco.game.view.ForegroundView;

public class UiLoop extends Loop {
    private static final long UPDATE_INTERVAL = 33; // ms - nano does not offer significand improvement
    private final Logic logic;
    private final BackgroundView backgroundView;
    private final ForegroundView foregroundView;

    public UiLoop(Logic logic, BackgroundView backgroundView, ForegroundView foregroundView) {
        super(UPDATE_INTERVAL);
        this.logic = logic;
        this.backgroundView = backgroundView;
        this.foregroundView = foregroundView;
    }

    @Override
    public void step() {
        if(logic.isBackgroundInvalid()) {
            backgroundView.postInvalidate();
        }
        foregroundView.postInvalidate();
    }

}
