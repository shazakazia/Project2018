package com.example.shaza.episenseversion20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by Shaza on 4/19/2018.
 */

public class RecordItem extends AppCompatActivity {

    private Button del;
    private Button edit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_econtacts);

        del = findViewById(R.id.deletebtn);
        edit = findViewById(R.id.editbtn);



    }

}
