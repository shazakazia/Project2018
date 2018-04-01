package com.example.shaza.episenseversion20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultantInfo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView showname;
    private TextView showemail ;
    private TextView showcontact;
    private TextView showaddress;
    private RequestQueue mQueue ;
    private String pid;
    private String did;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showemail = findViewById(R.id.emaildoc);
        showname = findViewById(R.id.docname);
        showcontact = findViewById(R.id.doccon);
        showaddress = findViewById(R.id.docadd);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if(extras != null)
        { pid = extras.getString("Patient ID");
            did = extras.getString("Doctor ID");}
        Toast.makeText(ConsultantInfo.this, did, Toast.LENGTH_LONG).show();



        mQueue = Volley.newRequestQueue(this);


        String url = "http://10.0.2.2:3000/doctors/" + did;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                                JSONArray jsonArray = response.getJSONArray("Doctors");
                            JSONObject patient = jsonArray.getJSONObject(0);
                            String fname = patient.getString("first_name");
                            String lname = patient.getString("last_name");
                            String fullname = "Dr."+ fname+" "+lname ;
                            String email = patient.getString("email");
                            String address = patient.getString("address");
                            String contactnum = patient.getString("contact_number");

                            showname.setText(fullname);
                            showemail.setText(email);
                            showcontact.setText(contactnum);
                            showaddress.setText(address);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_profile:
                Intent h= new Intent(ConsultantInfo.this,Profile.class);
                startActivity(h);
                break;
            case R.id.nav_records:
                Intent i= new Intent(ConsultantInfo.this,MedicalRec.class);
                startActivity(i);
                break;
            case R.id.nav_consultant:
                Intent g= new Intent(ConsultantInfo.this,ConsultantInfo.class);
                startActivity(g);
                break;
            case R.id.nav_contacts:
                Intent s= new Intent(ConsultantInfo.this,EContacts.class);
                startActivity(s);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
