package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.Position;
import com.matheusvillela.afugadepixuleco.R;

import java.util.ArrayList;
import java.util.List;

public class MiniMoro extends MovingThing {
    private final Moro moro;
    private final Logic logic;
    private List<BoundingBox> boxes = new ArrayList<>();
    private static final float ZERO_POINT_X = Minimap.X + (Minimap.WIDTH / 2);
    private static final float ZERO_POINT_Y = Minimap.Y;
    private static final float HEIGHT = 2f;

    public MiniMoro(Moro moro, Logic logic) {
        super(logic, Floor.ZERO, ZERO_POINT_X, ZERO_POINT_Y);
        this.moro = moro;
        this.logic = logic;
        step();
    }

    @Override
    public boolean step() {
        Position moroPosition = moro.getPosition();
        int currentSection = logic.getCurrentSection();
        int totalSections = logic.getTotalSections();
        float f = currentSection / (float) totalSections;
        float v = Minimap.WIDTH / (float) totalSections;
        position.x = ZERO_POINT_X - f * Minimap.WIDTH + (-v + v * moroPosition.x / 100.f);
        position.y = ZERO_POINT_Y + ((moroPosition.y - Floor.FIRST.getHeight()) /
                (Floor.FOURTH.getHeight() - Floor.FIRST.getHeight())) * Minimap.HEIGHT;
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {
        // nothing
    }

    @Override
    public int getDrawableId() {
        return R.raw.mini_moro;
    }

    @Override
    public List<BoundingBox> getBoxes() {
        return boxes;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public float getWidth() {
        return 1;
    }
}
