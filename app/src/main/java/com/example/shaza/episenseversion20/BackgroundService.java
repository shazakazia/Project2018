package com.example.shaza.episenseversion20;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.shaza.episenseversion20.AppStatus.myProfile;
import static com.example.shaza.episenseversion20.AppStatus.pid;
import static com.example.shaza.episenseversion20.AppStatus.records;
import static com.example.shaza.episenseversion20.loginScreen.IP;

public class BackgroundService extends Service {

    public static MediaPlayer mediaPlayer ;
    private RequestQueue mQueue ;
   // public static boolean checklogout =false;
    private boolean isRunning;
    private Context context;
    public Thread backgroundThread;
    private static final int myid=9090 ;
    public View v ;

    private int currentrecords ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
       public void run() {

            //Log.d("still here", "in run");
               System.out.println("---------------------------------------------\nSERVICE IS RUNNING\n---------------------------------------------");

               mQueue = Volley.newRequestQueue(BackgroundService.this);
               final String url = "http://" + IP + "/patients/"+pid+"/numberofseizures";
               //   final String url = "http://192.168.1.187:3001/patients/2/numberofseizures";

               JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                       new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {
                               try {
                                   JSONArray jsonArray = response.getJSONArray("Seizures");
                                   JSONObject patient = jsonArray.getJSONObject(0);
                                   currentrecords = Integer.parseInt(patient.getString("numberOfSeizures"));

                                   System.out.println("---------------------------------------------\n"+patient.getString("numberOfSeizures")
                                           +"\n---------------------------------------------");
                                   if (records < currentrecords) {
                                       System.out.println("!!!!!!!!!!!!!!!\nPROCESSING NOTIFICATION ALARM, TEXTMESSAGE\n!!!!!!!!!!!!!!!");
                                       AppStatus.records = currentrecords;
                                       showNotification(v);
                                       sendmessage();
                                       mediaPlayer = MediaPlayer.create(context, R.raw.helptone);
                                       mediaPlayer.setLooping(true);
                                       mediaPlayer.start();
                                   }

                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       }, new Response.ErrorListener() {
                   public void onErrorResponse(VolleyError error) {
                       error.printStackTrace();
                   }
               });

               mQueue.add(request);
              // Log.d("below queue", "going to destroy");
               stopSelf();
              // Log.d("wentttt", "wennttt");
       }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
       // Log.d("destroy", "hereee");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(checklogout)
//        {
//            Log.d("called check log out","true check") ;
//            backgroundThread.interrupt();
//        }
        if(!this.isRunning) {
            this.isRunning = true;
            Log.d("heree","HERE3");
            this.backgroundThread.start();
            Log.d("heree","HERE4");
        }

        return START_STICKY;
    }

    public void showNotification(View v)
    {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_error_outline_black_24dp);
        builder.setAutoCancel(true);
        builder.setContentTitle("Alarm ALert");
        builder.setContentText("Seizure Alarm alert. Touch OK to stop alarm");
        builder.setTicker("Alarm Alert") ;

        Intent intent = new Intent(this, AlarmStop.class) ;
        intent.putExtra("Patient ID", "2");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent) ;

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(myid,builder.build());
    }


     public void sendmessage() {
           Intent x = new Intent(this, MessageSystem.class);
           x.putExtra("Patient name",myProfile.getFullname());
           x.putExtra("Patient ID", myProfile.getId());
           startActivity(x); }
}
