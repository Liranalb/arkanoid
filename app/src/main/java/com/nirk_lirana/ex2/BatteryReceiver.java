package com.nirk_lirana.ex2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import static android.content.Context.NOTIFICATION_SERVICE;


public class BatteryReceiver extends BroadcastReceiver
{
    private static final String CHANNEL_ID = "cannal_1";
    private static final CharSequence CHANNEL_NAME = "batteryChannel";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {

            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            int percent = (level * 100) / scale;

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;


            if ((percent == 10) && !isCharging) {
                Log.d("mylog", "low battery");

                Log.d("mylog", "inside notify");
                String title = "Warning! low battery";
                String text = "Please plug your mobile device to a charger";

                NotificationManager notificationManager =
                        (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    NotificationChannel notificationChannel = new NotificationChannel(
                            CHANNEL_ID,
                            CHANNEL_NAME,

                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_battery_alert_24)
                        .setContentTitle(title)
                        .setContentText(text)
                        .build();
                notificationManager.notify(1, notification);
            }
        }

    }
}