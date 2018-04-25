package com.example.shaza.episenseversion20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlarmStop extends AppCompatActivity {


    private Button mystop ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_stop);
        mystop = findViewById(R.id.stopb) ;

        mystop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundService.mediaPlayer.stop();
            }
        });    }
}
