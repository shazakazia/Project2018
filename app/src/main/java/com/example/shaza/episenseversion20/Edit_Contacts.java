package com.example.shaza.episenseversion20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.shaza.episenseversion20.loginScreen.IP;
import static com.example.shaza.episenseversion20.AppStatus.contactlist;


public class Edit_Contacts extends AppCompatActivity {
    private EditText ecFname;
    private EditText ecLname;
    private EditText ecNumber;
    private String pid;
    private Button done;
    private String cnumber;
    private String foname;
    private String loname;
    private String position;

    private final String key_firstname = "firstname";
    private final String key_lastname = "lastname";
    private final String key_nnumber = "nnumber";
    private final String key_patientid = "patientid" ;
    private final String key_cnumber = "cnumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__contacts);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            pid = extras.getString("Patient ID");
            cnumber = extras.getString("Old Number");
            position = extras.getString("Postion");
            foname = extras.getString("Old fName");
            loname = extras.getString("Old lName");
        }

        ecFname = findViewById(R.id.ecFirstnameedit);
        ecLname = findViewById(R.id.ecLastnameedit);
        ecNumber = findViewById(R.id.ecNumberedit);
        done = findViewById(R.id.doneedit);

        ecFname.setText(foname);
        ecLname.setText(loname);
        ecNumber.setText(cnumber);



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(cnumber);
            }
        });
    }

    public void edit(String ccnumber) {

        final String fname = ecFname.getText().toString().trim();
        final String lname = ecLname.getText().toString().trim();
        final String nnumber = ecNumber.getText().toString().trim();
        final String cnumber = ccnumber;

        final String url = "http://"+IP+"/contacts?patient_id=" + pid + "?first_name="+fname+"&last_name="+lname+"&contact_number="+cnumber+"&new_number="+nnumber;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Toast.makeText(Edit_Contacts.this, response, Toast.LENGTH_LONG).show();
                       if (response.equals("OK") ) {
                           ContactTemplate editedc = new ContactTemplate(fname,lname,nnumber);
                            contactlist.set(Integer.parseInt(position),editedc);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit_Contacts.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(key_firstname, fname);
                params.put(key_lastname, lname);
                params.put(key_nnumber, nnumber);
                params.put(key_cnumber, cnumber);
                params.put(key_patientid, pid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}



