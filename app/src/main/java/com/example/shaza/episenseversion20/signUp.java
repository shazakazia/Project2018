package com.example.shaza.episenseversion20;
import android.app.Activity;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
        import java.util.Map;

public class signUp extends Activity {
    private Button signUpbtn;
    private EditText passwordedit;
    private EditText emailedit;
    private EditText firstnameedit;
    private EditText lastnameedit;
    private EditText patientidedit;
    private EditText doctoremailedit;

    private final String key_password="password";
    private final String key_email="email";
    private final String key_firstname="firstname";
    private final String key_lastname="lastname";
    private final String key_patientid="patientid";
    private final String key_doctoremail="doctoremail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpbtn=(Button)findViewById(R.id.signUpbtn);
        passwordedit = (EditText)findViewById(R.id.passwordinput);
        emailedit = (EditText)findViewById(R.id.emailinput);
        firstnameedit = (EditText)findViewById(R.id.first);
        lastnameedit = (EditText)findViewById(R.id.last);
        patientidedit = (EditText)findViewById(R.id.pidinput);
        doctoremailedit = (EditText)findViewById(R.id.doctoremailinput);


        Intent intent = getIntent();


        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }
        });
    }

    private void userSignUp(){

        final String password = passwordedit.getText().toString().trim();
        final String email = emailedit.getText().toString().trim();
        final String fname = firstnameedit.getText().toString().trim();
        final String lname = lastnameedit.getText().toString().trim();
        final String pid = patientidedit.getText().toString().trim();
        final String dname = doctoremailedit.getText().toString().trim();
        final String url = "http://10.0.2.2:3001/patients?patient_id=" + pid + "&patient_password=" + password + "&email=" + email + "&first_name=" + fname + "&last_name=" + lname + "&doctor_email" + dname;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(signUp.this, response, Toast.LENGTH_LONG).show();
                        if(response.equals("OK"))
                        {
                            finish() ;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(signUp.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put(key_password,password);
                params.put(key_email,email);
                params.put(key_firstname,fname);
                params.put(key_lastname,lname);
                params.put(key_patientid,pid);
                params.put(key_doctoremail,dname);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
//second commit