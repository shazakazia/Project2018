package com.example.shaza.episenseversion20;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class loginScreen extends Activity {
    private Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        signin=(Button)findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent i=new Intent(
                        loginScreen.this,
                        Profile.class);
                startActivity(i);
            }
        });
    }
}
//second commit