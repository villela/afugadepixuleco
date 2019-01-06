package com.matheusvillela.afugadepixuleco.game.objects;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.Position;

import java.util.List;

public abstract class MovingThing extends Thing {

    public MovingThing(Logic logic, Floor floor, float x) {
        super(floor, x);
        this.logic = logic;
    }

    protected final Logic logic;

    public interface RemovableThing {
        void remove();
        float getAlpha();

        int getPenaltySteps();
    }

    public MovingThing(Logic logic, Floor floor, float x, float y) {
        super(floor, x, y);
        this.logic = logic;
    }

    public abstract boolean step();

    public abstract void onCollision(MovingThing obj);

    public abstract List<BoundingBox> getBoxes();

    public boolean collidesWith(MovingThing otherThing) {
        if(getFloor() != otherThing.getFloor()) {
            return false;
        }
        List<BoundingBox> boxes = this.getBoxes();
        for (BoundingBox boundingBox : boxes) {
            List<BoundingBox> otherBoxes = otherThing.getBoxes();
            for (BoundingBox otherBoundingBox : otherBoxes) {
                Position otherPosition = otherThing.getPosition();

                float thisLeft = position.x + boundingBox.left;
                float thisRight = position.x + boundingBox.right;
                float thisBottom = position.y + boundingBox.bottom;
                float thisTop = position.y + boundingBox.top;

                float otherLeft = otherPosition.x + otherBoundingBox.left;
                float otherRight = otherPosition.x + otherBoundingBox.right;
                float otherBottom = otherPosition.y + otherBoundingBox.bottom;
                float otherTop = otherPosition.y + otherBoundingBox.top;

                /*if(thisLeft <= otherLeft && otherLeft <= thisRight) {
                    if(thisTop >= otherTop && otherTop >= thisBottom) {
                        return true;
                    }
                    if(otherTop >= thisTop && thisTop >= otherBottom) {
                        return true;
                    }
                }
                if(otherLeft <= thisLeft && thisLeft <= otherRight) {
                    if(thisTop >= otherTop && otherTop >= thisBottom) {
                        return true;
                    }
                    if(otherTop >= thisTop && thisTop >= otherBottom) {
                        return true;
                    }
                }*/
                if (thisLeft < otherRight &&
                        thisRight > otherLeft &&
                        thisBottom < otherTop &&
                        thisTop > otherBottom) {
                    return true;
                }
            }
        }
        return false;
    }

}
