package com.matheusvillela.afugadepixuleco.game;

import com.matheusvillela.afugadepixuleco.game.objects.Aviao;
import com.matheusvillela.afugadepixuleco.game.objects.Foguete;
import com.matheusvillela.afugadepixuleco.game.objects.Mortadela;
import com.matheusvillela.afugadepixuleco.game.objects.MovingThing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SectionController {

    private final Logic logic;
    private Side side;
    private List<MovingThing> foregroundThings = new ArrayList<>();
    private List<MovingThing> builtForegroundThings = new ArrayList<>();
    private List<MovingThing> removableThings = new ArrayList<>();
    private List<ToBuildThing> thingsToBuild = new ArrayList<>();

    public List<MovingThing> getForegroundThings() {
        ArrayList<MovingThing> result = new ArrayList<>();
        result.addAll(foregroundThings);
        result.addAll(builtForegroundThings);
        return result;
    }

    public void resetFloor(Floor floor) {
        for (MovingThing movingThing : builtForegroundThings) {
            if (floor == movingThing.getFloor()) {
                if (movingThing instanceof MovingThing.RemovableThing) {
                    ((MovingThing.RemovableThing) movingThing).remove();
                    for (ToBuildThing toBuildThing : thingsToBuild) {
                        if (toBuildThing.floor == floor) {
                            toBuildThing.enabled = false;
                        }
                    }
                }
            }
        }
    }

    public enum Side {
        RIGHT,
        LEFT
    }

    public SectionController(Logic logic) {
        this.logic = logic;
        this.side = logic.getComingSide();
    }

    public void addFixedForegroundThing(MovingThing thing) {
        foregroundThings.add(thing);
    }

    public void addMovingForegroundThing(Class<?> clazz, Floor floor, int level, float speed) {
        ToBuildThing thingToBuild = new ToBuildThing(clazz, floor, level, speed);
        thingsToBuild.add(thingToBuild);
    }

    public void addMortadela(Floor floor, int levelNum) {
        addMovingForegroundThing(Mortadela.class, floor, levelNum, 0.02f);
    }

    public void addFoguete(Floor floor, int levelNum) {
        addMovingForegroundThing(Foguete.class, floor, levelNum, 0.045f);
    }

    public void addAviao(Floor floor, int levelNum) {
        addMovingForegroundThing(Aviao.class, floor, levelNum, 0.05f);
    }



    public void addRemovableForegroundThing(MovingThing thing) {
        removableThings.add(thing);
    }


    private int step = 0;

    public void step() {
        for (ToBuildThing toBuildThing : thingsToBuild) {
            if (toBuildThing.enabled && ((step - toBuildThing.start) % toBuildThing.interval == 0)) {
                Constructor[] allConstructors = toBuildThing.clazz.getDeclaredConstructors();
                try {
                    Object object = allConstructors[0].newInstance(logic, toBuildThing.floor, toBuildThing.speedX, side);
                    builtForegroundThings.add((MovingThing) object);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        List<MovingThing> removeList = null;
        for (MovingThing thing : builtForegroundThings) {
            if (!thing.step()) {
                if (removeList == null) {
                    removeList = new ArrayList<>();
                }
                removeList.add(thing);
            }
        }
        if (removeList != null) {
            for (MovingThing thing : removeList) {
                builtForegroundThings.remove(thing);
            }
        }
        step++;
    }

    public void reset() {
        step = 0;
        this.side = logic.getComingSide();
        builtForegroundThings.clear();
        builtForegroundThings.addAll(removableThings);
        for (ToBuildThing toBuildThing : thingsToBuild) {
            toBuildThing.enabled = true;
        }
    }

    private class ToBuildThing {
        final Class<?> clazz;
        final int start;
        final Floor floor;
        private final float speedX;
        boolean enabled = true;
        public int interval;

        public ToBuildThing(Class<?> clazz, Floor floor, int level, float speed) {
            this.clazz = clazz;
            this.start = 10;
            this.floor = floor;
            this.speedX = speed + level * (clazz == Mortadela.class ? 0.013f : 0.04f);
            this.interval = 500 - (int)(this.speedX * this.speedX * 2200);
        }
    }
}
