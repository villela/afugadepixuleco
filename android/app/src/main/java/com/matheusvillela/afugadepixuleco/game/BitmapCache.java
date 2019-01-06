package com.matheusvillela.afugadepixuleco.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import com.matheusvillela.afugadepixuleco.PixulecoApplication;

import java.io.InputStream;
import java.util.HashMap;

public class BitmapCache {
    private static BitmapCache singleton;
    private final HashMap<Integer, Bitmap> hashMap;
    private final Resources resources;

    public static BitmapCache getInstance() {
        if (singleton == null) {
            synchronized (Logic.class) {
                if (singleton == null) {
                    singleton = new BitmapCache();
                }
            }
        }
        return singleton;
    }

    private BitmapCache() {
        this.hashMap = new HashMap<>();
        this.resources = PixulecoApplication.getInstance().getResources();
    }

    public Bitmap getBitmap(int resId) {
        Bitmap bitmap = hashMap.get(resId);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(resources, resId);
            if(bitmap == null) {
                InputStream is = resources.openRawResource(resId);
                bitmap = BitmapFactory.decodeStream(is);
            }
            hashMap.put(resId, bitmap);
        }
        return bitmap;
    }

    private Bitmap flipBitmap(Bitmap src) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }

    public Bitmap getFlippedBitmap(int resId) {
        Bitmap bitmap = hashMap.get(-resId);
        if (bitmap == null) {
            bitmap = getBitmap(resId);
            bitmap = flipBitmap(bitmap);
            hashMap.put(-resId, bitmap);
        }
        return bitmap;
    }
}
