package com.nirk_lirana.ex2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
public class MainActivity extends AppCompatActivity {

    //for battery checker
    private BroadcastReceiver batteryReceiver;
    private IntentFilter filter;

    GameView gameView;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //notificationsSetup();
        broadcastSetup();

        //--------------------FULL SCREEN--------------------
        // Hide the Activity Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the Activity Action Bar
        getSupportActionBar().hide();
        //---------------------------------------------------


        // set Activity(screen) Orientation to LANDSCAPE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        gameView = (GameView) findViewById(R.id.viewID);

        mediaPlayer = new MediaPlayer().create(this, R.raw.bip);


    }

    private void broadcastSetup()
    {
        // 2. Create BatteryReceiver object
        batteryReceiver = new BatteryReceiver();

        // 3. Create IntentFilter for BATTERY_CHANGED & AIRPLANE_MODE_CHANGED broadcast
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // 4. Register the receiver to start listening for battery change messages
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.threadIsStopped = false;
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onPause()
    {

        super.onPause();

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        gameView.threadIsStopped = true;
        // 5. Un-Register the receiver to stop listening for battery change messages
        unregisterReceiver(batteryReceiver);
    }

    @Override
    protected void onDestroy() {
        gameView.gameThread.interrupt();
        super.onDestroy();
    }
    
}
