package com.example.shaza.episenseversion20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_Contacts extends AppCompatActivity {
    private EditText ecFname ;
    private EditText ecLname ;
    private EditText ecNumber;
    private Button done ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__contacts);

        ecFname = findViewById(R.id.ecFirstname) ;
        ecLname = findViewById(R.id.ecLastname) ;
        ecNumber = findViewById(R.id.ecNumber) ;
        done = findViewById(R.id.done) ;

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = ecFname.getText().toString() ;
                String lname = ecLname.getText().toString() ;
                String number = ecNumber.getText().toString() ;
                finish();
            }
        });

    }
}
