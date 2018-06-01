package com.example.shaza.episenseversion20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundService.class);
           // background.putExtra("status", 7);
        Log.d("heree","HERE5");
        context.startService(background);


    }

}