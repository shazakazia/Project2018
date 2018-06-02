package com.example.shaza.episenseversion20;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundService.class);
           // background.putExtra("status", 7);
        Log.d("heree","HERE5");

//        Intent alarm = new Intent(context, AlarmReceiver.class);
//        boolean alarmRunning = (PendingIntent.getBroadcast(context, 1, alarm, PendingIntent.FLAG_NO_CREATE) != null);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmRunning = (PendingIntent.getBroadcast(context, 1, alarm, PendingIntent.FLAG_NO_CREATE) != null);
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
        context.startService(background);


    }

}