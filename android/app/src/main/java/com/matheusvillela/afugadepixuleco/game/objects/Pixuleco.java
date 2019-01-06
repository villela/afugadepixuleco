package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.SectionController;

import java.util.ArrayList;
import java.util.List;

public class Pixuleco extends MovingThing implements Moro.OnMoroFloorChangedListener {
    private final Logic logic;
    private Floor floor;
    private int currentSection;
    private int variation = 32;
    private float speed = 0.0f;
    private boolean goingUp = true;
    private int[] sprites = new int[]{R.drawable.ic_pixuleco0, R.drawable.ic_pixuleco2,
            R.drawable.ic_pixuleco3, R.drawable.ic_pixuleco4, R.drawable.ic_pixuleco1};

    private static final List<BoundingBox> NORMAL_BOXES = new ArrayList<BoundingBox>() {{
        add(new BoundingBox(0f, 6f, -2f, 2f));
        add(new BoundingBox(6f, 10f, -1.2f, 1.2f));
    }};

    public Pixuleco(Floor floor, float x, int currentSection, float speed, Logic logic, Moro moro) {
        super(logic, floor, x);
        this.floor = floor;
        this.currentSection = currentSection;
        this.speed = speed;

        this.logic = logic;
        moro.setOnMoroFloorChangedListener(this);
    }

    @Override
    public boolean step() {
        position.x += this.speed;
        updateVariation();

        if (position.x > 100) {
            if (currentSection == 0) {
                Floor nextFloor = Floor.getFloorById(floor.getId() + (goingUp ? 1 : -1));
                if (nextFloor != null) {
                    speed = -speed;
                    floor = nextFloor;
                    position.y = floor.getHeight();
                } else {
                    logic.onPixulecoEscaped();
                }
            } else {
                currentSection--;
                position.x = 0;
            }
        } else if (position.x < 0) {
            if (currentSection == logic.getTotalSections() - 1) {
                Floor nextFloor = Floor.getFloorById(floor.getId() + (goingUp ? 1 : -1));
                if (nextFloor != null) {
                    speed = -speed;
                    floor = nextFloor;
                    position.y = floor.getHeight();
                } else {
                    logic.onPixulecoEscaped();
                }
            } else {
                currentSection++;
                position.x = 100;
            }
        }
        return true;
    }

    public Floor getFloor() {
        return floor;
    }

    @Override
    public void onCollision(MovingThing obj) {

    }

    @Override
    public List<BoundingBox> getBoxes() {
        return NORMAL_BOXES;
    }

    @Override
    public int getDrawableId() {
        return R.drawable.ic_pixuleco2;
    }

    private void updateVariation() {
        variation++;
        if (variation >= 5 * 16) {
            variation = 0;
        }
    }

    @Override
    public Bitmap getBitmap() {
        int variationIndex = variation / 16;
        SectionController.Side side = speed <= 0 ? SectionController.Side.LEFT : SectionController.Side.RIGHT;
        Bitmap bitmap;
        if(side == SectionController.Side.LEFT) {
            bitmap = getBitmap(sprites[variationIndex]);
        } else {
            bitmap = getInvertedBitmap(sprites[variationIndex]);
        }
        return bitmap;
    }

    public SectionController.Side getRunningSide() {
        SectionController.Side side = speed <= 0 ? SectionController.Side.LEFT : SectionController.Side.RIGHT;
        return side;
    }

    @Override
    public float getHeight() {
        return 12;
    }

    @Override
    public float getWidth() {
        return 7;
    }

    public int getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(int currentSection) {
        this.currentSection = currentSection;
    }

    @Override
    public void OnMoroFloorChangedListener(Floor moroFloor, float moroX) {
        if(moroFloor.getId() > this.floor.getId()) {
            if(goingUp) {
                invertRun();
            }
        } else if(this.floor.getId() > moroFloor.getId()) {
            if(!goingUp) {
                invertRun();
            }
        } else {
            if(currentSection > logic.getCurrentSection()) {
                if(speed > 0) {
                    invertRun();
                }
            } else if(currentSection < logic.getCurrentSection()) {
                if(speed < 0) {
                    invertRun();
                }
            } else {
                if(position.x > moroX) {
                    if(speed < 0 ) {
                        invertRun();
                    }
                }
            }
        }
    }

    private void invertRun() {
        goingUp = !goingUp;
        speed = -speed;
    }

    public int getStringId() {
        return R.string.pixuleco;
    }
}
