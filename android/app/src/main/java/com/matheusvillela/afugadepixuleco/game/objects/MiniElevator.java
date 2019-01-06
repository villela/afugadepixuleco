package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.R;

import java.util.ArrayList;
import java.util.List;

public class MiniElevator extends MovingThing implements Elevator.NextFloorObserver {
    private final int section;
    private final int totalSections;
    private List<BoundingBox> boxes = new ArrayList<>();
    private static final float ZERO_POINT_X = Minimap.X + (Minimap.WIDTH / 2);
    private static final float ZERO_POINT_Y = Minimap.Y + 0.3f;
    private static final float HEIGHT = 2f;

    public MiniElevator(Logic logic,  Elevator elevator, int section, int totalSections) {
        super(logic, Floor.ZERO, ZERO_POINT_X, ZERO_POINT_Y);
        this.section = section;
        this.totalSections = totalSections;
        elevator.addObserver(this);
        onNextFloor(elevator.getCurrentFloor());
    }

    @Override
    public boolean step() {
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {
        // nothing
    }

    @Override
    public int getDrawableId() {
        return R.raw.mini_elevator;
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
        return 1.3f;
    }

    @Override
    public void onNextFloor(Floor floor) {
        float f = section / (float) totalSections;
        float v = Minimap.WIDTH / (float) totalSections;
        position.x = ZERO_POINT_X - f * Minimap.WIDTH + (-v + v * 50f / 100.f);
        position.y = ZERO_POINT_Y + ((floor.getHeight() - Floor.FIRST.getHeight()) /
                (Floor.FOURTH.getHeight() - Floor.FIRST.getHeight())) * Minimap.HEIGHT;
    }
}
