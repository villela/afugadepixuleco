package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Elevator implements ElevatorForeground.StateListener {
    public static final float YDIFF = 1;
    public static final float WIDTH = 8;
    public static final float HEIGHT = 15;
    private static final int INTERVAL_STEPS = 200;
    private final int currentSection;
    private int stepsLeft = 0;
    private List<ElevatorForeground> elevators = new ArrayList<>();
    private HashMap<Floor, ElevatorForeground> foregroundHashMap = new HashMap<>();
    private List<NextFloorObserver> nextFloorObservers = new ArrayList<>();
    private boolean down = true;
    private Floor currentFloor = Floor.SECOND;

    public interface NextFloorObserver {
        void onNextFloor(Floor floor);
    }

    public Elevator(Logic logic, int section) {
        this.currentSection = section;
        ElevatorForeground object = new ElevatorForeground(logic, Floor.FIRST, this);
        foregroundHashMap.put(Floor.FIRST, object);
        elevators.add(object);
        object = new ElevatorForeground(logic, Floor.SECOND, this);
        foregroundHashMap.put(Floor.SECOND, object);
        elevators.add(object);
        object = new ElevatorForeground(logic, Floor.THIRD, this);
        foregroundHashMap.put(Floor.THIRD, object);
        elevators.add(object);
    }

    void addObserver(NextFloorObserver observer) {
        if (!nextFloorObservers.contains(observer)) {
            nextFloorObservers.add(observer);
        }
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    void removeObserver(NextFloorObserver observer) {
        nextFloorObservers.remove(observer);
    }

    public List<ElevatorForeground> getElevators() {
        return elevators;
    }

    public int getCurrentSection() {
        return currentSection;
    }

    public void step() {
        if (stepsLeft == 0) {
            foregroundHashMap.get(currentFloor).open();
        } else if (stepsLeft < 0) {
            foregroundHashMap.get(currentFloor).step();
        }
        stepsLeft--;
    }


    @Override
    public void onClosed(ElevatorForeground foreground) {
        Floor floor = foreground.getFloor();
        if (down) {
            if (floor == Floor.SECOND) {
                currentFloor = Floor.FIRST;
            } else if (floor == Floor.FIRST) {
                currentFloor = Floor.SECOND;
                down = false;
            }
        } else {
            if (floor == Floor.SECOND) {
                currentFloor = Floor.THIRD;
            } else if (floor == Floor.THIRD) {
                currentFloor = Floor.SECOND;
                down = true;
            }
        }
        stepsLeft = INTERVAL_STEPS;
        for (NextFloorObserver observer : nextFloorObservers) {
            observer.onNextFloor(currentFloor);
        }
    }

    @Override
    public void onClosing(ElevatorForeground foreground) {

    }

    @Override
    public void onOpen(ElevatorForeground foreground) {

    }

    @Override
    public void onOpening(ElevatorForeground foreground) {

    }
}
