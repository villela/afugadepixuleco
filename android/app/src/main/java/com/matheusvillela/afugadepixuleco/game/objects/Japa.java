package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.R;
import com.matheusvillela.afugadepixuleco.game.SectionController;

import java.util.ArrayList;
import java.util.List;

public class Japa extends MovingThing {
    private final Moro moro;
    private final Pixuleco pixuleco;
    private List<BoundingBox> boxes = new ArrayList<>();
    private static final float TOTAL_STEPS = 240.0f;
    private float steps = (int) TOTAL_STEPS;
    private Bitmap invertedBitmap;

    public Japa(Logic logic, Moro moro, Pixuleco pixuleco) {
        super(logic, Floor.ZERO, pixuleco.getPosition().x, pixuleco.getPosition().y);
        float diff = -0.7f - (getWidth() / 2.f);
        this.moro = moro;
        this.pixuleco = pixuleco;
        SectionController.Side pixulecoSide = pixuleco.getRunningSide();
        diff = pixulecoSide == SectionController.Side.LEFT ? diff : -diff;
        position.x = position.x + diff;
    }

    @Override
    public boolean step() {
        if (steps > 0) {
            steps--;
            moro.setAlpha(steps / TOTAL_STEPS);
            return true;
        }
        return false;
    }

    @Override
    public void onCollision(MovingThing obj) {
        // nothing
    }

    public float getAlpha() {
        float v = (TOTAL_STEPS - steps) / TOTAL_STEPS;
        return v;
    }

    @Override
    public Bitmap getBitmap() {
        SectionController.Side pixulecoSide = pixuleco.getRunningSide();
        if (pixulecoSide == SectionController.Side.LEFT) {
            return super.getBitmap();
        } else {
            if(this.invertedBitmap == null) {
                this.invertedBitmap = getInvertedBitmap(R.raw.japa);
            }
            return this.invertedBitmap;
        }
    }

    @Override
    public int getDrawableId() {
        return R.raw.japa;
    }

    @Override
    public List<BoundingBox> getBoxes() {
        return boxes;
    }

    @Override
    public float getHeight() {
        return 14.0f;
    }

    @Override
    public float getWidth() {
        return 6.0f;
    }
}
