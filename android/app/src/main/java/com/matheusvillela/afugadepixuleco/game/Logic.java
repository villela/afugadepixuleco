package com.matheusvillela.afugadepixuleco.game;

import com.matheusvillela.afugadepixuleco.game.objects.Blink;
import com.matheusvillela.afugadepixuleco.game.objects.BlinkBlue;
import com.matheusvillela.afugadepixuleco.game.objects.BlinkRed;
import com.matheusvillela.afugadepixuleco.game.objects.Clock;
import com.matheusvillela.afugadepixuleco.game.objects.Elevator;
import com.matheusvillela.afugadepixuleco.game.objects.ElevatorForeground;
import com.matheusvillela.afugadepixuleco.game.objects.Japa;
import com.matheusvillela.afugadepixuleco.game.objects.Life;
import com.matheusvillela.afugadepixuleco.game.objects.MiniElevator;
import com.matheusvillela.afugadepixuleco.game.objects.MiniMoro;
import com.matheusvillela.afugadepixuleco.game.objects.MiniPixuleco;
import com.matheusvillela.afugadepixuleco.game.objects.Moro;
import com.matheusvillela.afugadepixuleco.game.objects.MovingThing;
import com.matheusvillela.afugadepixuleco.game.objects.Pixuleco;
import com.matheusvillela.afugadepixuleco.game.objects.Thing;
import com.matheusvillela.afugadepixuleco.game.view.BackgroundView;
import com.matheusvillela.afugadepixuleco.game.view.ForegroundView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Logic implements Moro.ReachSideListener {

    private static Logic singleton;
    private final Moro moro;
    private final Pixuleco pixuleco;
    private final Level level;
    private final MiniMoro miniMoro;
    private final MiniPixuleco miniPixuleco;
    private final Elevator elevator;
    private final MiniElevator miniElevator;
    private final LevelFinishedListener levelFinishedListener;
    private final int levelNum;
    private final int livesCount;
    private int currentSection;
    private boolean backgroundInvalid = true;
    private LinkedHashMap<LogicButton, LogicEvent> buttonUseMap = new LinkedHashMap<>();
    private SectionController.Side comingSide = SectionController.Side.RIGHT;
    private boolean gameWon = false;
    private Japa japa;
    private Clock clock;
    private List<Life> lives = new ArrayList<>();
    private Blink blink;

    public Logic(int levelNum, int livesLeft, BackgroundView backgroundView, ForegroundView foregroundView, LevelFinishedListener levelFinishedListener) {
        this.level = Level.createLevel(this, levelNum);
        this.levelNum = levelNum;
        this.livesCount = livesLeft;
        this.elevator = new Elevator(this, 4);
        this.miniElevator = new MiniElevator(this, elevator, 4, getTotalSections());
        this.moro = new Moro(this, Floor.FIRST, 50, this, elevator);
        this.miniMoro = new MiniMoro(moro, this);
        this.pixuleco = new Pixuleco(Floor.SECOND, 50, 4, 0.09f + (0.01f * levelNum), this, moro);
        this.miniPixuleco = new MiniPixuleco(pixuleco, this);
        this.currentSection = 0;
        this.clock = new Clock(this, 70);
        backgroundView.setLogic(this);
        foregroundView.setLogic(this);
        this.levelFinishedListener = levelFinishedListener;
        for(int i = 0 ; i < livesCount; i++) {
            Life life = new Life(this, 5 + 8 * i);
            lives.add(life);
        }
    }

    public SectionController.Side getComingSide() {
        return comingSide;
    }

    synchronized public void step() {
        if (gameWon) {
            if (!japa.step()) {
                levelFinishedListener.onLevelWon(levelNum, clock.getRunTimeMillis(), pixuleco.getStringId());
            }
            return;
        }
        boolean sameSection = pixuleco.getCurrentSection() == currentSection;
        if (sameSection) {
            boolean sameFloor = pixuleco.getFloor() == moro.getFloor();
            if (sameFloor) {
                if (pixuleco.collidesWith(moro)) {
                    gameWon = true;
                    japa = new Japa(this, moro, pixuleco);
                    return;
                }
            }
        }
        for (MovingThing thing : getForegroundThings()) {
            boolean b = thing.collidesWith(moro);
            if (b) {
                moro.onCollision(thing);
                thing.onCollision(moro);
                break;
            }
        }
        level.getSections().get(currentSection).step();
        if (elevator.getCurrentSection() == currentSection) {
            for (ElevatorForeground elevatorForeground : elevator.getElevators()) {
                if (elevatorForeground.collidesWith(moro)) {
                    moro.onCollision(elevatorForeground);
                }
            }
        }
        LogicButton lastCommand = getLastCommand();
        if (lastCommand == LogicButton.UP) {
            moro.jump();
            lastCommand = getLastCommand();
        }
        moro.setLastCommand(lastCommand);
        moro.step();
        miniMoro.step();
        pixuleco.step();
        miniPixuleco.step();
        elevator.step();
        for (MovingThing thing : getForegroundThings()) {
            thing.step();
        }
        if(this.blink != null) {
            if(!blink.step()) {
                blink = null;
            }
        }
        clock.step();
    }

    synchronized private LogicButton getLastCommand() {
        LogicButton button = null;
        for (Map.Entry<LogicButton, LogicEvent> entry : buttonUseMap.entrySet()) {
            if (entry.getValue() == LogicEvent.PRESSED) {
                button = entry.getKey();
            }
        }
        //Timber.d("button %s", button);
        if (button == LogicButton.UP) {
            buttonUseMap.remove(LogicButton.UP);
        }
        return button;
    }

    public boolean isBackgroundInvalid() {
        if (backgroundInvalid) {
            backgroundInvalid = false;
            return true;
        }
        return false;
    }

    public List<Thing> getBackgroundThings() {
        return level.getSections().get(currentSection).getBackgroundThings();
    }

    public List<MovingThing> getForegroundThings() {
        return level.getSections().get(currentSection).getForegroundThings();
    }

    public Moro getMoro() {
        return moro;
    }

    public Pixuleco getPixuleco() {
        if (pixuleco.getCurrentSection() == currentSection) {
            return pixuleco;
        }
        return null;
    }

    public Elevator getElevator() {
        if (elevator.getCurrentSection() == currentSection) {
            return elevator;
        }
        return null;
    }

    public MiniMoro getMinimoro() {
        return miniMoro;
    }

    public MiniPixuleco getMinipixuleco() {
        return miniPixuleco;
    }

    public MiniElevator getMiniElevator() {
        return miniElevator;
    }

    synchronized public void onButtonPressed(LogicButton button, LogicEvent event) {
        if (button == LogicButton.UP) {
            if (event == LogicEvent.PRESSED) {
                buttonUseMap.remove(button);
                buttonUseMap.put(button, event);
            }
        } else {
            buttonUseMap.remove(button);
            buttonUseMap.put(button, event);
        }
    }

    @Override
    public boolean onSideReached(SectionController.Side side) {
        int sectionsSize = level.getSections().size();
        if (side == SectionController.Side.RIGHT) {
            if (currentSection == 0) {
                return true;
            } else {
                comingSide = SectionController.Side.LEFT;
                currentSection--;
            }
        } else {
            if (currentSection >= sectionsSize - 1) {
                return true;
            } else {
                comingSide = SectionController.Side.RIGHT;
                currentSection++;
            }
        }
        backgroundInvalid = true;
        level.getSections().get(currentSection).reset();
        return false;
    }

    public int getTotalSections() {
        List<LevelSection> sections = level.getSections();
        return sections.size();
    }

    public int getCurrentSection() {
        return currentSection;
    }

    public Japa getJapa() {
        return this.japa;
    }

    public Clock getClock() {
        return clock;
    }

    public void resetFloor(Floor floor) {
        level.getSections().get(currentSection).resetFloor(floor);
    }

    public void onPixulecoEscaped() {
        levelFinishedListener.onEscaped(levelNum);
    }

    public List<Life> getLives() {
        return lives;
    }

    public void removeTime(int stepsToRemove) {
        clock.removeSteps(stepsToRemove);
    }

    public Blink getBlink() {
        return blink;
    }

    public void blink(boolean good) {
        this.blink = good ? new BlinkBlue(this) : new BlinkRed(this);
    }

    public enum LogicButton {
        MOVE_RIGHT,
        MOVE_LEFT,
        UP,
        DOWN
    }

    public enum LogicEvent {
        PRESSED,
        RELEASED
    }
}
