package com.maryam.zahidi.wallpaperchanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class WallpaperChangerActivity extends Activity {
    @SuppressWarnings("FieldCanBeLocal")
    private Button buttonStartService /**Starting service*/,
            buttonStopService /**Stopping service*/,
            buttonFinish /**Hiding activity/app*/;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_changer);

        //Declaring linear layout object and associating with his Id
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);

        //setting programmatically background of main screen app
        linearLayout.setBackgroundResource(R.drawable.img1);

        //Associating our declared buttons to their ids
        buttonStartService = (Button) findViewById(R.id.avvia);
        buttonStopService = (Button) findViewById(R.id.arresta);
        buttonFinish = (Button) findViewById(R.id.esci);

        //Setting parameters to buttons
        buttonStartService.setEnabled(!WallpaperChangerService.STARTED);
        buttonStopService.setEnabled(WallpaperChangerService.STARTED);
        buttonStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWallpaperChangerService();
            }
        });
        buttonStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopWallpaperChangerService();
            }
        });
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //Stopping service
    private void stopWallpaperChangerService() {
        buttonStartService.setEnabled(true);
        buttonStopService.setEnabled(false);
        stopService(new Intent(getApplicationContext(), WallpaperChangerService.class));
    }
    //Starts service
    private void startWallpaperChangerService() {
        buttonStartService.setEnabled(false);
        buttonStopService.setEnabled(true);
        startService(new Intent(getApplicationContext(), WallpaperChangerService.class));
    }
}
