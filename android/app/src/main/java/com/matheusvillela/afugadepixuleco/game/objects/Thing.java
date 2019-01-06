package com.matheusvillela.afugadepixuleco.game.objects;

import android.graphics.Bitmap;

import com.matheusvillela.afugadepixuleco.game.BitmapCache;
import com.matheusvillela.afugadepixuleco.game.Floor;
import com.matheusvillela.afugadepixuleco.game.Position;

public abstract class Thing {

    private final Bitmap bitmap;
    protected Position position;
    private Floor floor;

    public Thing(Floor floor, float x) {
        this.bitmap = getBitmap(getDrawableId());
        Position position = new Position(x, floor.getHeight());
        setPosition(position);
        this.floor = floor;
    }

    public Thing(Floor floor, float x, float y) {
        this.bitmap = getBitmap(getDrawableId());
        Position position = new Position(x, floor.getHeight() + y);
        setPosition(position);
    }

    protected Bitmap getBitmap(int resId) {
        BitmapCache cache = BitmapCache.getInstance();
        return cache.getBitmap(resId);
    }

    protected Bitmap getInvertedBitmap(int resId) {
        BitmapCache cache = BitmapCache.getInstance();
        return cache.getFlippedBitmap(resId);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public abstract int getDrawableId();

    public Bitmap getBitmap() {
        return bitmap;
    }

    public abstract float getHeight();

    public abstract float getWidth();

    public Floor getFloor() {
        return floor;
    }
}
