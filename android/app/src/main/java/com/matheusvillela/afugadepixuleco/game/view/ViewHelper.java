package com.matheusvillela.afugadepixuleco.game.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.matheusvillela.afugadepixuleco.game.Position;
import com.matheusvillela.afugadepixuleco.game.objects.BoundingBox;
import com.matheusvillela.afugadepixuleco.game.objects.MovingThing;
import com.matheusvillela.afugadepixuleco.game.objects.Thing;

public class ViewHelper {
    private static final boolean DRAW_BOXES = false;

    public static void drawThing(Thing thing, Canvas canvas, Float alpha) {
        Position position = thing.getPosition();
        Bitmap bitmap = thing.getBitmap();
        int canvasWidth = canvas.getWidth();
        int x = (int) (canvasWidth * position.x / 100);
        int canvasHeight = canvas.getHeight();
        int y = (int) (canvasHeight * (100 - position.y) / 100);
        int height = (int) (thing.getHeight() * canvasHeight / 100);
        int width = (int) (thing.getWidth() * canvasWidth / 100);
        int halfWidth = width / 2;
        Rect dst = new Rect(x - halfWidth, y - height, x + halfWidth, y);
        if (alpha != null) {
            Paint alphaPaint = new Paint();
            alphaPaint.setAlpha((int) (alpha * 254));
            canvas.drawBitmap(bitmap, null, dst, alphaPaint);
        } else {
            canvas.drawBitmap(bitmap, null, dst, null);
        }

        if (DRAW_BOXES) {
            if (thing instanceof MovingThing) {
                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                for (BoundingBox box : ((MovingThing) thing).getBoxes()) {
                    int top = (int) (canvasHeight * -box.top / 100) + y;
                    int bottom = (int) (canvasHeight * -box.bottom / 100) + y;
                    int left = (int) (canvasWidth * box.left / 100) + x;
                    int right = (int) (canvasWidth * box.right / 100) + x;
                    canvas.drawLine(left, top, right, top, paint);
                    canvas.drawLine(right, top, right, bottom, paint);
                    canvas.drawLine(right, bottom, left, bottom, paint);
                    canvas.drawLine(left, bottom, left, top, paint);
                }
            }
        }
    }

    public static void drawThing(Thing thing, Canvas canvas) {
        drawThing(thing, canvas, null);
    }

    public static void drawText(String text, Position position, Canvas canvas, Float alpha) {
        int canvasWidth = canvas.getWidth();
        int x = (int) (canvasWidth * position.x / 100);
        int canvasHeight = canvas.getHeight();
        int y = (int) (canvasHeight * (100 - position.y) / 100);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setFakeBoldText(true);
        if(alpha != null) {
            paint.setAlpha((int) (alpha * 254));
        }
        canvas.drawText(text, x, y, paint);
    }
}
