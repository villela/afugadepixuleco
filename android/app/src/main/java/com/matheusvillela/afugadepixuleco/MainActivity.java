package com.matheusvillela.afugadepixuleco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.matheusvillela.afugadepixuleco.game.LevelFinishedListener;
import com.matheusvillela.afugadepixuleco.game.Logic;
import com.matheusvillela.afugadepixuleco.game.ServiceLoop;
import com.matheusvillela.afugadepixuleco.game.UiLoop;
import com.matheusvillela.afugadepixuleco.game.view.BackgroundView;
import com.matheusvillela.afugadepixuleco.game.view.ForegroundView;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, LevelFinishedListener {

    private ThreadPoolExecutor mThreadPool;
    private ServiceLoop serviceLoop;
    private UiLoop uiLoop;
    private Logic logic;
    private static final int MAXIMUM_LIVES = 3;
    private int lives = MAXIMUM_LIVES;
    private int nextLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PixulecoApplication application = (PixulecoApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(MainActivity.class.getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        this.setContentView(R.layout.activity_main);
        mThreadPool = new ThreadPoolExecutor(
                2,       // Initial pool size
                9999,       // Max pool size
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>());
        findViewById(R.id.button_left_crouch).setOnTouchListener(this);
        findViewById(R.id.button_left_jump).setOnTouchListener(this);
        findViewById(R.id.button_left_move).setOnTouchListener(this);
        findViewById(R.id.button_right_crouch).setOnTouchListener(this);
        findViewById(R.id.button_right_jump).setOnTouchListener(this);
        findViewById(R.id.button_right_move).setOnTouchListener(this);
        BackgroundView backgroundView = (BackgroundView) findViewById(R.id.background_view);
        ForegroundView foregroundView = (ForegroundView) findViewById(R.id.foreground_view);
        this.logic = new Logic(1, lives, backgroundView, foregroundView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BackgroundView backgroundView = (BackgroundView) findViewById(R.id.background_view);
        ForegroundView foregroundView = (ForegroundView) findViewById(R.id.foreground_view);
        this.uiLoop = new UiLoop(logic, backgroundView, foregroundView);
        mThreadPool.execute(uiLoop);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }

    private void stop() {
        if(serviceLoop != null) {
            serviceLoop.close();
        }
        if(uiLoop != null) {
            uiLoop.close();
        }
        this.serviceLoop = null;
        this.uiLoop = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            if(this.serviceLoop == null) {
                this.serviceLoop = new ServiceLoop(logic);
                mThreadPool.execute(serviceLoop);
            }
            Logic.LogicEvent logicEvent = action == MotionEvent.ACTION_UP ? Logic.LogicEvent.RELEASED : Logic.LogicEvent.PRESSED;
            switch (v.getId()) {
                case R.id.button_left_jump:
                case R.id.button_right_jump:
                    logic.onButtonPressed(Logic.LogicButton.UP, logicEvent);
                    break;
                case R.id.button_left_crouch:
                case R.id.button_right_crouch:
                    logic.onButtonPressed(Logic.LogicButton.DOWN, logicEvent);
                    break;
                case R.id.button_left_move:
                    logic.onButtonPressed(Logic.LogicButton.MOVE_LEFT, logicEvent);
                    break;
                case R.id.button_right_move:
                    logic.onButtonPressed(Logic.LogicButton.MOVE_RIGHT, logicEvent);
                    break;
            }
        }
        return true;
    }

    @Override
    public void onLevelWon(int level, long totalTimeMillis, int pixulecoStringId) {
        Intent intent = new Intent(this, GameWonActivity.class);
        intent.putExtra(GameWonActivity.LEVEL_EXTRA, level);
        intent.putExtra(GameWonActivity.THIEF_EXTRA, getString(pixulecoStringId));
        String time = totalTimeMillis / 1000 + "." + totalTimeMillis % 1000;
        intent.putExtra(GameWonActivity.TIME_EXTRA, time);
        startActivityForResult(intent, 1);
        stop();
        this.nextLevel = level + 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BackgroundView backgroundView = (BackgroundView) findViewById(R.id.background_view);
        ForegroundView foregroundView = (ForegroundView) findViewById(R.id.foreground_view);
        if(lives < MAXIMUM_LIVES) {
            lives++;
        }
        this.logic = new Logic(this.nextLevel, lives, backgroundView, foregroundView, this);
        startUi();
    }

    private void startUi() {
        if(this.uiLoop == null) {
            BackgroundView backgroundView = (BackgroundView) findViewById(R.id.background_view);
            ForegroundView foregroundView = (ForegroundView) findViewById(R.id.foreground_view);
            this.uiLoop = new UiLoop(logic, backgroundView, foregroundView);
            mThreadPool.execute(uiLoop);
        }
    }

    @Override
    public void onEscaped(int level) {
        stop();
        if(lives > 0) {
            lives--;
            BackgroundView backgroundView = (BackgroundView) findViewById(R.id.background_view);
            ForegroundView foregroundView = (ForegroundView) findViewById(R.id.foreground_view);
            this.logic = new Logic(level, lives, backgroundView, foregroundView, this);
            startUi();
        } else {
            lives = MAXIMUM_LIVES;
            BackgroundView backgroundView = (BackgroundView) findViewById(R.id.background_view);
            ForegroundView foregroundView = (ForegroundView) findViewById(R.id.foreground_view);
            this.logic = new Logic(1, lives, backgroundView, foregroundView, this);
            startUi();
        }
    }
}
