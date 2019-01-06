package com.matheusvillela.afugadepixuleco;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class GameWonActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LEVEL_EXTRA = "level";
    public static final String THIEF_EXTRA = "thief";
    public static final String TIME_EXTRA = "time";
    private int level;
    private String thief;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_game_won);

        PixulecoApplication application = (PixulecoApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(MainActivity.class.getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Intent intent = getIntent();
        this.level = intent.getIntExtra(LEVEL_EXTRA, -1);
        this.thief = intent.getStringExtra(THIEF_EXTRA);
        this.time = intent.getStringExtra(TIME_EXTRA);

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Status")
                .setAction("Level Won")
                .setLabel("Level finished")
                .setValue(this.level)
                .build());

        TextView textView = (TextView) findViewById(R.id.gamewon_text);
        textView.setText(getString(R.string.gamewontext, level, thief, time));

        View view = findViewById(R.id.gamewon_sharefacebook);
        view.setOnClickListener(this);
        view = findViewById(R.id.gamewon_nextlevel);
        view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gamewon_sharefacebook:
                shareViaDialog(GameWonActivity.this, "Mandei Pixuleco pra cadeia, prenda ele você também em A Fuga de Pixuleco!", "",
                        "http://matheusvillela.com/pixuleco/levelwon.png",
                        "https://play.google.com/store/apps/details?id=com.matheusvillela.afugadepixuleco");
                break;
            case R.id.gamewon_nextlevel:
                finish();
                break;
        }


    }

    /**
     * Shares a story using ShareDialog. Story's name, description, picture and link can be specified.
     * Activity hosting the dialog is also required to show the dialog.
     * See https://developers.facebook.com/docs/reference/android/current/class/ShareDialog/
     */
    public static void shareViaDialog (Activity activity, String name, String description,
                                       String picture, String link) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareDialog shareDialog = new ShareDialog(activity);
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(link))
                    .setContentTitle(name)
                    .setContentDescription(description)
                    .setImageUrl(Uri.parse(picture))
                    .build();

            shareDialog.show(content);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
