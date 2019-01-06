package com.matheusvillela.afugadepixuleco.game;

public class ServiceLoop extends Loop {
    public static final long UPDATE_INTERVAL = 10; // ms - nano does not offer significand improvement
    private final Logic logic;

    public ServiceLoop(Logic logic) {
        super(UPDATE_INTERVAL);
        this.logic = logic;
    }

    @Override
    public void step() {
        this.logic.step();
    }

}
