package com.matheusvillela.afugadepixuleco.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.objects.Blink;
import com.matheusvillela.afugadepixuleco.game.objects.Clock;
import com.matheusvillela.afugadepixuleco.game.objects.Elevator;
import com.matheusvillela.afugadepixuleco.game.objects.Japa;
import com.matheusvillela.afugadepixuleco.game.objects.Life;
import com.matheusvillela.afugadepixuleco.game.objects.Moro;
import com.matheusvillela.afugadepixuleco.game.objects.MovingThing;
import com.matheusvillela.afugadepixuleco.game.objects.Pixuleco;
import com.matheusvillela.afugadepixuleco.game.objects.Thing;

import java.util.List;

public class ForegroundView extends View {


    public ForegroundView(Context context) {
        super(context);
    }

    public ForegroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForegroundView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if(logic == null) {
            return;
        }
        Moro moro = logic.getMoro();
        if(moro.isOnElevator()) {
            ViewHelper.drawThing(moro, canvas, moro.getAlpha());
        }
        Elevator elevator = logic.getElevator();
        if(elevator != null) {
            for(Thing thing : elevator.getElevators()) {
                ViewHelper.drawThing(thing, canvas);
            }
        }
        for (Thing thing : logic.getForegroundThings()) {
            if(thing instanceof MovingThing.RemovableThing) {
                float alpha = ((MovingThing.RemovableThing)thing).getAlpha();
                if(alpha < 1) {
                    ViewHelper.drawThing(thing, canvas, alpha);
                    continue;
                }
            }
            ViewHelper.drawThing(thing, canvas);
        }

        Japa japa = logic.getJapa();
        if(japa != null) {
            ViewHelper.drawThing(japa, canvas, japa.getAlpha());
        }

        Pixuleco pixuleco = logic.getPixuleco();
        if(pixuleco != null) {
            ViewHelper.drawThing(pixuleco, canvas);
        }
        if(!moro.isOnElevator()) {
            ViewHelper.drawThing(moro, canvas, moro.getAlpha());
        }
        ViewHelper.drawThing(logic.getMiniElevator(), canvas);
        ViewHelper.drawThing(logic.getMinipixuleco(), canvas);
        ViewHelper.drawThing(logic.getMinimoro(), canvas);

        Clock clock = logic.getClock();
        Float clockAlpha = clock.getAlpha();
        ViewHelper.drawText(clock.getText(), clock.getPosition(), canvas, clockAlpha);

        List<Life> lives = logic.getLives();
        for(Life life : lives) {
            ViewHelper.drawThing(life, canvas, clockAlpha);
        }

        Blink blink = logic.getBlink();
        if(blink != null) {
            ViewHelper.drawThing(blink, canvas, blink.getAlpha());
        }

    }
}
