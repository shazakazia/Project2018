package com.example.shaza.episenseversion20;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;

public class MessageSystem extends AppCompatActivity {

    EditText phone ;
//    EditText message ;
//    Button send ;

    private String phonenum;
    private String txt;
    private String pid;
    private String name;
    private RequestQueue mQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.phone) ;
//        message = findViewById(R.id.txtmessage) ;
//        send = findViewById(R.id.sendbtn);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            pid = extras.getString("Patient ID");
            name = extras.getString("Patient name");
        }

        txt = "EPISENSE: SEIZURE ALERT! \n" + " Patient name : " + name  ;
        phonenum = "+971561487886;";
        sendMessage(phonenum,txt);
        finish();

    }

    public void sendMessage(String phonenum , String mymessage)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenum,null,mymessage,null,null);
            Toast.makeText(this, "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Message fail", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
