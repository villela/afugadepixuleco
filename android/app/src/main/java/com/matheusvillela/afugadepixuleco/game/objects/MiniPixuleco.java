package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.Position;
import com.matheusvillela.afugadepixuleco.R;

import java.util.ArrayList;
import java.util.List;

public class MiniPixuleco extends MovingThing {
    private final Pixuleco pixuleco;
    private final Logic logic;
    private List<BoundingBox> boxes = new ArrayList<>();
    private static final float ZERO_POINT_X = Minimap.X + (Minimap.WIDTH / 2);
    private static final float ZERO_POINT_Y = Minimap.Y;
    private static final float HEIGHT = 2f;

    public MiniPixuleco(Pixuleco pixuleco, Logic logic) {
        super(logic, Floor.ZERO, ZERO_POINT_X, ZERO_POINT_Y);
        this.pixuleco = pixuleco;
        this.logic = logic;
        step();
    }

    @Override
    public boolean step() {
        Position pixulecoPosition = pixuleco.getPosition();
        int currentSection = pixuleco.getCurrentSection();
        int totalSections = logic.getTotalSections();
        float f = currentSection / (float) totalSections;
        float v = Minimap.WIDTH / (float) totalSections;
        position.x = ZERO_POINT_X - f * Minimap.WIDTH + (-v + v * pixulecoPosition.x / 100.f);
        position.y = ZERO_POINT_Y + ((pixulecoPosition.y - Floor.FIRST.getHeight()) /
                (Floor.FOURTH.getHeight() - Floor.FIRST.getHeight())) * Minimap.HEIGHT;
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {
        // nothing
    }

    @Override
    public int getDrawableId() {
        return R.raw.mini_pixuleco;
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
