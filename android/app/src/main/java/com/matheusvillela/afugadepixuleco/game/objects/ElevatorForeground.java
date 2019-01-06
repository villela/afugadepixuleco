package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.R;

import java.util.ArrayList;
import java.util.List;

public class ElevatorForeground extends MovingThing {

    private final float originalX;
    private final StateListener listener;
    private final Floor floor;
    private static final float WIDTH = Elevator.WIDTH + 0.4f;

    public ElevatorForeground(Logic logic, Floor floor, StateListener listener) {
        super(logic, floor, 50, Elevator.YDIFF);
        originalX = getPosition().x;
        this.listener = listener;
        this.floor = floor;
    }

    private static final List<BoundingBox> EMPTY_BOXES = new ArrayList<BoundingBox>();

    private static final List<BoundingBox> BOXES = new ArrayList<BoundingBox>() {{
        add(new BoundingBox(0f, Elevator.HEIGHT, (Elevator.WIDTH / 2f) - 0.1f, (Elevator.WIDTH / 2f) + 0.1f));
    }};

    public Floor getFloor() {
        return floor;
    }

    public interface StateListener {
        void onClosed(ElevatorForeground foreground);
        void onClosing(ElevatorForeground foreground);
        void onOpen(ElevatorForeground foreground);
        void onOpening(ElevatorForeground foreground);
    }

    public enum ElevatorState {
        CLOSED,
        OPENING,
        CLOSING,
        OPEN
    }

    private static final int MOVEMENT_STEPS = 100;
    private static final int OPENED_STEPS = 200;
    private int changeSteps = 0;
    private ElevatorState currentState = ElevatorState.CLOSED;

    @Override
    public int getDrawableId() {
        return R.raw.elevator_closed;
    }

    @Override
    public float getHeight() {
        return Elevator.HEIGHT;
    }

    @Override
    public float getWidth() {
        switch (currentState) {
            case CLOSED:
                return WIDTH;
            case OPEN:
                return 0;
            case OPENING:
                float v = (changeSteps / (float) MOVEMENT_STEPS) * WIDTH;
                position.x = originalX - (WIDTH - v) / 2f;
                return v;
            case CLOSING:
                float v1 = ((MOVEMENT_STEPS - changeSteps) / (float) MOVEMENT_STEPS) * WIDTH;
                position.x = originalX - (WIDTH - v1) / 2f;
                return v1;
        }
        return WIDTH;
    }

    public void open() {
        currentState = ElevatorState.OPENING;
        changeSteps = MOVEMENT_STEPS;
        listener.onOpening(this);
    }

    @Override
    public boolean step() {
        changeSteps--;
        if (changeSteps == 0) {
            if (currentState == ElevatorState.OPENING) {
                currentState = ElevatorState.OPEN;
                changeSteps = OPENED_STEPS;
                listener.onOpen(this);
            } else if (currentState == ElevatorState.CLOSING) {
                currentState = ElevatorState.CLOSED;
                position.x = originalX;
                listener.onClosed(this);
            } else if (currentState == ElevatorState.OPEN) {
                currentState = ElevatorState.CLOSING;
                changeSteps = MOVEMENT_STEPS;
                listener.onClosing(this);
            }
        }
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {

    }

    @Override
    public List<BoundingBox> getBoxes() {
        return currentState == ElevatorState.OPEN ? BOXES : EMPTY_BOXES;
    }
}
