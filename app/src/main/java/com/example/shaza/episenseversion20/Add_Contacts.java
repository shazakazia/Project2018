package com.example.shaza.episenseversion20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static com.example.shaza.episenseversion20.AppStatus.contactlist;
import static com.example.shaza.episenseversion20.loginScreen.IP;
import static com.example.shaza.episenseversion20.EContacts.adapter;


public class Add_Contacts extends AppCompatActivity {
    private EditText ecFname;
    private EditText ecLname;
    private EditText ecNumber;
    private String pid;
    private Button done;

    private final String key_firstname = "firstname";
    private final String key_lastname = "lastname";
    private final String key_number = "number";
    private final String key_patientid = "patientid" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__contacts);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            pid = extras.getString("Patient ID");
        }

        ecFname = findViewById(R.id.ecFirstname);
        ecLname = findViewById(R.id.ecLastname);
        ecNumber = findViewById(R.id.ecNumber);
        done = findViewById(R.id.done);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    public void add() {
        if(ecFname.getText().toString().trim().isEmpty()||ecLname.getText().toString().trim().isEmpty()||ecNumber.getText().toString().trim().isEmpty())
        {
            Toast.makeText(Add_Contacts.this, "Details Incomplete!", Toast.LENGTH_LONG).show();
            return;
        }

        final String fname = ecFname.getText().toString().trim();
        final String lname = ecLname.getText().toString().trim();
        String checknumber = ecNumber.getText().toString().trim();

        if(checknumber.substring(0, 2).equals("05"))
        {
            checknumber = checknumber.replaceFirst("05","9715");
        }
        final String number = checknumber ;

        final String url = "http://"+IP+"/contacts?patient_id=" + pid + "&first_name=" + fname + "&last_name=" + lname + "&contact_number=" + number;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      // Toast.makeText(Add_Contacts.this, response, Toast.LENGTH_LONG).show();
                       if (response.equals("OK") ) {
                           ContactTemplate contact = new ContactTemplate(fname,lname, number);
                           contactlist.add(contact);
                           adapter.notifyDataSetChanged();
                           Toast.makeText(Add_Contacts.this, "Contact Created", Toast.LENGTH_LONG).show();
                           finish();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_Contacts.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(key_firstname, fname);
                params.put(key_lastname, lname);
                params.put(key_number, number);
                params.put(key_patientid, pid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}



