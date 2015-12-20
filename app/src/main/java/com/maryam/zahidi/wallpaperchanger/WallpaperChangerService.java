package com.maryam.zahidi.wallpaperchanger;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Maryam Zahidi & Lorenzo Anelli on 12/12/2015.
 * With Android Studio 1.5.1
 * Using android developer documentation
 */
//This is main service, that will change phone background every time, for ex: 1 minute
    //After one minute it will change background...
public class WallpaperChangerService extends Service {
    public static boolean STARTED = false;
    private String[] availableWallpapers;
    private int currentWallpaperIndex;
    private Timer timer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        STARTED = true;
        AssetManager assetManager = getAssets();
        try {
            //Setting directory for scan
            availableWallpapers = assetManager.list("wallpapers");
        } catch (Exception ex) {
            Log.e("WCS", "Impossible to scan all available wallpapers", ex);
        }
        currentWallpaperIndex = -1;
        //Checking for availability wallpapers and his length
        if (availableWallpapers != null && availableWallpapers.length > 0) {
            //Creating background task
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        nextWallpaper();
                    } catch (Exception ex) {
                        Log.e("WCS", "Impossible to change wallpaper", ex);
                    }
                }
            };
            timer = new Timer();
            //Each minute
            timer.schedule(timerTask, 0, 10000);
        }
    }

    private void nextWallpaper() throws Exception {
        currentWallpaperIndex++;
        if (currentWallpaperIndex == availableWallpapers.length) {
            currentWallpaperIndex = 0;
        }
        String currentWallpaper = "wallpapers/" + availableWallpapers[currentWallpaperIndex];
        Bitmap bitmap = null;
        InputStream inputStream = null;

        try {
            AssetManager assetManager = getAssets();
            inputStream = assetManager.open(currentWallpaper);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception ex) {
            Log.e("WCS", "Impossible to load the wallpaper", ex);
        } finally {
            //Checking if input stream not null
            if (inputStream != null) {
                inputStream.close();
            }
        }
        //Checking if bitmap not null
        if (bitmap != null) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            wallpaperManager.setBitmap(bitmap);
        }
    }
    //On destroying service, the service will stop
    @Override
    public void onDestroy() {
        super.onDestroy();
        STARTED = false;
        timer.cancel();
        timer = null;
    }
}
