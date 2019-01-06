package com.matheusvillela.afugadepixuleco.game;

import com.matheusvillela.afugadepixuleco.game.objects.Background;
import com.matheusvillela.afugadepixuleco.game.objects.ElevatorBackground;
import com.matheusvillela.afugadepixuleco.game.objects.Minimap;
import com.matheusvillela.afugadepixuleco.game.objects.MovingThing;
import com.matheusvillela.afugadepixuleco.game.objects.Pillar;
import com.matheusvillela.afugadepixuleco.game.objects.Table;
import com.matheusvillela.afugadepixuleco.game.objects.Thing;

import java.util.ArrayList;
import java.util.List;

public class LevelSection {
    private final SectionController controller;
    private List<Thing> backgroundThings = new ArrayList<>();

    private static final float TABLE_HEIGHT = 6f;

    public static final List<Thing> FIRST_SECTION_THINGS = new ArrayList<Thing>() {
        {
            add(new Background());
            add(new Minimap());
            add(new Table(Floor.FIRST, 33, TABLE_HEIGHT, 15));
            add(new Table(Floor.FIRST, 67, TABLE_HEIGHT, 15));
        }
    };

    public static final List<Thing> SECOND_SECTION_THINGS = new ArrayList<Thing>() {
        {
            add(new Background());
            add(new Minimap());
            add(new Table(Floor.SECOND, 33, TABLE_HEIGHT, 15));
            add(new Table(Floor.SECOND, 67, TABLE_HEIGHT, 15));

            add(new Pillar(Floor.FIRST, 20));
            add(new Pillar(Floor.FIRST, 40));
            add(new Pillar(Floor.FIRST, 60));
            add(new Pillar(Floor.FIRST, 80));

            add(new Pillar(Floor.THIRD, 20));
            add(new Pillar(Floor.THIRD, 40));
            add(new Pillar(Floor.THIRD, 60));
            add(new Pillar(Floor.THIRD, 80));
        }
    };

    public static final List<Thing> THIRD_SECTION_THINGS = new ArrayList<Thing>() {
        {
            add(new Background());
            add(new Minimap());
            add(new Table(Floor.FIRST, 20, TABLE_HEIGHT, 8));
            add(new Table(Floor.FIRST, 80, TABLE_HEIGHT, 8));
            add(new Table(Floor.SECOND, 20, TABLE_HEIGHT, 8));
            add(new Table(Floor.SECOND, 80, TABLE_HEIGHT, 8));
            add(new Table(Floor.SECOND, 40, TABLE_HEIGHT, 8));
            add(new Table(Floor.SECOND, 60, TABLE_HEIGHT, 8));
            add(new Table(Floor.THIRD, 20, TABLE_HEIGHT, 8));
            add(new Table(Floor.THIRD, 80, TABLE_HEIGHT, 8));
        }
    };

    public static final List<Thing> FOURTH_SECTION_THINGS = new ArrayList<Thing>() {
        {
            add(new Background());
            add(new Minimap());
            add(new Table(Floor.FIRST, 33, TABLE_HEIGHT, 15));
            add(new Table(Floor.FIRST, 67, TABLE_HEIGHT, 15));
            add(new Table(Floor.THIRD, 33, TABLE_HEIGHT, 15));
            add(new Table(Floor.THIRD, 67, TABLE_HEIGHT, 15));

            add(new Pillar(Floor.SECOND, 20));
            add(new Pillar(Floor.SECOND, 40));
            add(new Pillar(Floor.SECOND, 60));
            add(new Pillar(Floor.SECOND, 80));
        }
    };

    public static final List<Thing> FIFTH_SECTION_THINGS = new ArrayList<Thing>() {
        {
            add(new Background());
            add(new ElevatorBackground(Floor.FIRST));
            add(new ElevatorBackground(Floor.SECOND));
            add(new ElevatorBackground(Floor.THIRD));
            add(new Minimap());
        }
    };

    public static final List<Thing> SIXTH_SECTION_THINGS = SECOND_SECTION_THINGS;
    public static final List<Thing> SEVENTH_SECTION_THINGS = THIRD_SECTION_THINGS;

    public static final List<Thing> EIGHT_SECTION_THINGS = new ArrayList<Thing>() {
        {
            add(new Background());
            add(new Minimap());
        }
    };

    public LevelSection(SectionController controller, List<Thing> backgroundThings) {
        this.controller = controller;
        this.backgroundThings.addAll(backgroundThings);
    }

    public List<Thing> getBackgroundThings() {
        return backgroundThings;
    }

    public List<MovingThing> getForegroundThings() {
        return controller.getForegroundThings();
    }

    public void step() {
        controller.step();
    }

    public void reset() {
        controller.reset();
    }

    public void resetFloor(Floor floor) {
        controller.resetFloor(floor);
    }
}
