package com.matheusvillela.afugadepixuleco.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.objects.Thing;

public class BackgroundView extends View {

    public BackgroundView(Context context) {
        super(context);
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Logic logic;

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Timber.d("onDraw");
        super.onDraw(canvas);
        if(logic != null) {
            for (Thing thing : logic.getBackgroundThings()) {
                com.matheusvillela.afugadepixuleco.game.view.ViewHelper.drawThing(thing, canvas);
            }
        }
    }
}
