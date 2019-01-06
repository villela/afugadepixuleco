package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.Position;
import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.SectionController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

public class Moro extends MovingThing implements Elevator.NextFloorObserver {

    private final Elevator elevator;
    private Floor floor;
    private Logic.LogicButton lastCommand;
    private SectionController.Side facingSide = SectionController.Side.LEFT;
    private int[] sprites = new int[]{R.drawable.ic_moro0_f, R.drawable.ic_moro6_f, R.drawable.ic_moro3_f,
            R.drawable.ic_moro7_f, R.drawable.ic_moro1_f, R.drawable.ic_moro6_f,
            R.drawable.ic_moro3_f, R.drawable.ic_moro7_f, R.drawable.ic_moro2_f,
            R.drawable.ic_moro5_f};
    private boolean jump = false;
    private boolean jumping = false;
    private int variation = 4;
    private float speedx = 0.0f;
    private float speedy = 0.0f;
    private float baseYPosition;
    public static final int STEPS_TO_BLOCK = 100;
    private static final float SPEED = 0.4f;
    public static final int STEPS_SPEED_BOOST = 100;
    public static final float BOOST_MODIFIER = 1.3f;
    public int stepsBoost = 0;
    private final ReachSideListener reachSideListener;
    private static final List<BoundingBox> NORMAL_BOXES = new ArrayList<BoundingBox>() {{
        add(new BoundingBox(0f, 6f, -2f, 2f));
        add(new BoundingBox(6f, 10f, -1.2f, 1.2f));
    }};
    private static final List<BoundingBox> JUMP_BOXES = new ArrayList<BoundingBox>() {{
        add(new BoundingBox(3f, 6f, -2f, 2f));
        add(new BoundingBox(6f, 10f, -1.2f, 1.2f));
    }};
    private final static HashMap<SectionController.Side, List<BoundingBox>> CROUCH_BOXES = new HashMap<SectionController.Side, List<BoundingBox>>() {
        {
            put(SectionController.Side.RIGHT, new ArrayList<BoundingBox>() {{
                add(new BoundingBox(0f, 4f, -2.5f, 1.5f));
                add(new BoundingBox(4f, 7f, -0.7f, 1.8f));
            }});
            put(SectionController.Side.LEFT, new ArrayList<BoundingBox>() {{
                add(new BoundingBox(0f, 4f, -1.5f, 2.5f));
                add(new BoundingBox(4f, 7f, -1.8f, 0.7f));
            }});
        }
    };
    private List<BoundingBox> boxes = NORMAL_BOXES;
    private boolean onStairs = false;
    private Position stairsDestiny;
    private boolean canEnterElevator = false;
    private boolean onElevator = false;
    private Floor stairsFloorDestiny;
    private OnMoroFloorChangedListener onMoroFloorChangedListener;
    private Float alpha;
    private int stepsBlocked = 0;

    @Override
    public void onNextFloor(Floor floor) {
        Timber.d("onNextFloor");
        if (this.onElevator) {
            Timber.d("onNextFloor2");
            Position p = new Position(position.x, floor.getHeight());
            p.y += 2f;
            setPosition(p, floor);
        } else {
            elevator.removeObserver(this);
        }
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public Float getAlpha() {
        return alpha;
    }

    public boolean isOnElevator() {
        return onElevator;
    }

    public interface ReachSideListener {
        boolean onSideReached(SectionController.Side side);
    }

    public Moro(Logic logic, Floor floor, float x, ReachSideListener reachSideListener, Elevator elevator) {
        super(logic, floor, x);
        this.floor = floor;
        this.elevator = elevator;

        this.reachSideListener = reachSideListener;
    }

    @Override
    public int getDrawableId() {
        return R.drawable.ic_moro0_f;
    }

    @Override
    public List<BoundingBox> getBoxes() {
        return boxes;
    }

    @Override
    public float getHeight() {
        return 12;
    }

    private float getSpeed() {
        return SPEED * (stepsBoost > 0 ? BOOST_MODIFIER : 1);
    }

    @Override
    public float getWidth() {
        return 7;
    }

    @Override
    public boolean step() {
        if (stepsBoost > 0) {
            stepsBoost--;
        }
        if (stepsBlocked > 0) {
            stepsBlocked--;
            return true;
        }
        if (this.onElevator) {
            if (this.canEnterElevator && lastCommand == Logic.LogicButton.DOWN) {
                this.onElevator = false;
                this.position.y -= 2f;
                elevator.removeObserver(this);
            }
        } else {
            if (onStairs) {
                if (this.stairsDestiny.y < position.y) {
                    setPosition(stairsDestiny, stairsFloorDestiny);
                    onStairs = false;
                    jump = false;
                    this.speedy = 0f;
                }
            } else {
                if (jump) {
                    jump = false;
                    if (!jumping) {
                        if (canEnterElevator && lastCommand == null) {
                            Timber.d("ENTERING ELEVATOR");
                            this.onElevator = true;
                            this.position.y += 2f;
                            this.speedy = 0f;
                            this.speedx = 0f;
                            elevator.addObserver(this);
                        } else {  // jump
                            boxes = JUMP_BOXES;
                            this.speedy = 0.6f;
                            this.jumping = true;
                        }
                    }
                }
                if (jumping) {
                    variation = 25;
                    this.speedy -= 0.023f;
                    if (position.y < baseYPosition) {
                        position.y = baseYPosition;
                        jumping = false;
                        this.speedy = 0f;
                        this.speedx = 0f;
                    }
                }
                if (!jumping && !this.onElevator) {
                    if (lastCommand == Logic.LogicButton.MOVE_LEFT) {
                        boxes = NORMAL_BOXES;
                        facingSide = SectionController.Side.LEFT;
                        this.speedx = -getSpeed();
                        updateVariation();
                    } else if (lastCommand == Logic.LogicButton.MOVE_RIGHT) {
                        boxes = NORMAL_BOXES;
                        facingSide = SectionController.Side.RIGHT;
                        this.speedx = getSpeed();
                        updateVariation();
                    } else if (lastCommand == Logic.LogicButton.DOWN) {
                        List<BoundingBox> boundingBoxes = CROUCH_BOXES.get(facingSide);
                        boxes = boundingBoxes;
                        if (this.speedx > 0) {
                            this.speedx -= 0.018f;
                            if (this.speedx < 0) {
                                this.speedx = 0;
                            }
                        }
                        if (this.speedx < 0) {
                            this.speedx += 0.018f;
                            if (this.speedx > 0) {
                                this.speedx = 0;
                            }
                        }
                        variation = 95;
                    } else if (lastCommand == null) {
                        boxes = NORMAL_BOXES;
                        this.speedx = 0.0f;
                        variation = 3;
                    }
                }
            }
        }
        if (canEnterElevator) {
            canEnterElevator = false;
        }
        position.x += this.speedx;
        position.y += this.speedy;
        if (position.x > 100) {
            boolean blocked = reachSideListener.onSideReached(SectionController.Side.RIGHT);
            if (blocked) {
                position.x = 100;
            } else {
                position.x = 0;
            }
        } else if (position.x < 0) {
            boolean blocked = reachSideListener.onSideReached(SectionController.Side.LEFT);
            if (blocked) {
                position.x = 0;
            } else {
                position.x = 100;
            }
        }
        //Timber.d("speedx %s", this.speedx);
        return true;
    }

    @Override
    public void onCollision(MovingThing obj) {
        if (!onStairs && !onElevator) {
            if (obj instanceof Stairs) {
                jumping = false;
                onStairs = true;
                variation = 3;
                Stairs stairs = (Stairs) obj;
                stairsDestiny = new Position(stairs.getDestinyX(), stairs.getDestinyY());
                stairsFloorDestiny = stairs.getTopFloor();
                speedx = stairs.getSpeedX();
                speedy = stairs.getSpeedY();
            } else if (obj instanceof ElevatorForeground) {
                canEnterElevator = true;
            } else if (obj instanceof Mortadela || obj instanceof Pneu || obj instanceof Foguete || obj instanceof Aviao) {
                if (stepsBlocked == 0) {
                    stepsBlocked = STEPS_TO_BLOCK;
                    logic.resetFloor(floor);
                    logic.removeTime(((RemovableThing) obj).getPenaltySteps());
                    logic.blink(false);
                }
            } else if (obj instanceof Dinheiro) {
                ((RemovableThing) obj).remove();
                stepsBoost = STEPS_SPEED_BOOST;
                logic.blink(true);
            }
        }
    }

    private void updateVariation() {
        variation++;
        if (variation >= 90) {
            variation = 10;
        }
    }

    @Override
    public Bitmap getBitmap() {
        int variationIndex = variation / 10;
        Bitmap bitmap;
        if (facingSide == SectionController.Side.LEFT) {
            bitmap = getBitmap(sprites[variationIndex]);
        } else {
            bitmap = getInvertedBitmap(sprites[variationIndex]);
        }
        return bitmap;
    }

    public void setLastCommand(Logic.LogicButton lastCommand) {
        this.lastCommand = lastCommand;
    }

    public void jump() {
        if (stepsBlocked == 0) {
            this.jump = true;
        }
    }

    public interface OnMoroFloorChangedListener {
        void OnMoroFloorChangedListener(Floor floor, float moroX);
    }

    void setOnMoroFloorChangedListener(OnMoroFloorChangedListener listener) {
        this.onMoroFloorChangedListener = listener;
    }

    private void setPosition(Position stairsDestiny, Floor floor) {
        if (floor != this.floor) {
            this.floor = floor;
            if (this.onMoroFloorChangedListener != null) {
                this.onMoroFloorChangedListener.OnMoroFloorChangedListener(this.floor, position.x);
            }
        }
        setPosition(stairsDestiny);
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.baseYPosition = position.y;
    }

    public Floor getFloor() {
        return floor;
    }
}
