package com.example.shaza.episenseversion20;

import android.app.Activity;
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

public class loginScreen extends Activity {
    private Button signin;
    private Button signupbtn;
    private EditText emailedit;
    private EditText passwordedit;

    private final String key_email="email";
    private final String key_password="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        signin=(Button)findViewById(R.id.signin);
        emailedit = (EditText)findViewById(R.id.email);
        passwordedit = (EditText)findViewById(R.id.password);
        signupbtn = (Button)findViewById(R.id.go_to_signup);


          signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent i=new Intent(
                        loginScreen.this,
                        signUp.class);
                i.putExtra("heyThere","hello!");
                startActivity(i);

            }
        });
    }
//hello
    private void userLogin(){

        final String email = emailedit.getText().toString().trim();
        final String password = passwordedit.getText().toString().trim();
         final String url = "http://10.0.2.2:3000/patients/login?email=" + email + "&patient_password=" + password;
        final String id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(loginScreen.this, response, Toast.LENGTH_LONG).show();
                       if(!response.equals("Forbidden"))
                       {
                           Toast.makeText(loginScreen.this, "User Authenticated", Toast.LENGTH_LONG).show();
                           Intent i=new Intent(
                                   loginScreen.this,
                                   Profile.class);
                           i.putExtra( "Patient ID", response);
                           startActivity(i);
                       }

                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginScreen.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put(key_password,password);
                params.put(key_email,email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
//