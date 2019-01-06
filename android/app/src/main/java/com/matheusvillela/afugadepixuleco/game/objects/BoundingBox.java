package com.matheusvillela.afugadepixuleco.game.objects;

public class BoundingBox {
    public BoundingBox(float bottom, float top, float left, float right) {
        this.bottom = bottom;
        this.top = top;
        this.left = left;
        this.right = right;

    }
    public float bottom;
    public float top;
    public float left;
    public float right;
}
