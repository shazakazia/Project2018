package com.example.shaza.episenseversion20;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;

public class MedicalRec extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<String> myRec;
    private RequestQueue mQueue ;
    private TextView nav_user;
    private TextView nav_mail;
    private String pid;
    private String item;
    private String did;
    private String name;
    private String pemail;
    private String test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_rec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Previous Records");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.nav_name);
        nav_mail = (TextView)hView.findViewById(R.id.nav_email);
        navigationView.setNavigationItemSelectedListener(this);


        final ListView list = (ListView) findViewById(R.id.record_list) ;


        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            pid = extras.getString("Patient ID");
            did = extras.getString("Doctor ID");
            name = extras.getString("Patient name");
            pemail = extras.getString("Patient email");
            }
        //Toast.makeText(MedicalRec.this, did, Toast.LENGTH_LONG).show();

        nav_user.setText(name);
        nav_mail.setText(pemail);

        mQueue = Volley.newRequestQueue(this);

        myRec= new ArrayList<String>();
        //String url = "http://172.28.19.149:3001/patients/" + pid+ "/history";
       // String url = "http://10.0.2.2:3001/patients/" + pid+ "/history";
        String url = "http://192.168.1.208:3001/patients/" + pid+ "/history";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {

                            JSONArray jsonArray = response.getJSONArray("Seizures");
                           // Toast.makeText(MedicalRec.this, jsonArray.length(), Toast.LENGTH_LONG).show();

                            JSONObject record;
                            for(int i =0 ; i<jsonArray.length(); i++)
                            {
                                record = jsonArray.getJSONObject(i);
                                test=record.getString("isSeizure");
                                if(test.equals("1"))
                                {
                                    item = record.getString("timestamp")+" , "+record.getString("date");
                                    //Toast.makeText(MedicalRec.this, item, Toast.LENGTH_LONG).show();

                                    myRec.add(item);
                                   // System.out.println("here");
                                    populate(myRec,list);
                                   // System.out.println(myRec.size());
                                }

                               // Log.d(item,"OUTPUT_ITEM");
                                //Log.d(myRec.get(i), "OUTPUT_RECS");
//
                           }


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
       //populateListview();
    }

    public void populate(ArrayList<String> myRec, ListView list)
    {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, android.R.id.text1, myRec);

        list.setAdapter(myAdapter);
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
                Intent h= new Intent(MedicalRec.this,Profile.class);
                h.putExtra("Doctor ID", did);
                h.putExtra("Patient ID",pid);
                h.putExtra("Patient name", name);
                h.putExtra("Patient email", pemail);
                startActivity(h);
                break;
            case R.id.nav_records:
                Intent i= new Intent(MedicalRec.this,MedicalRec.class);
                i.putExtra("Doctor ID", did);
                i.putExtra("Patient ID",pid);
                i.putExtra("Patient name", name);
                i.putExtra("Patient email", pemail);
                startActivity(i);
                break;
            case R.id.nav_consultant:
                Intent g= new Intent(MedicalRec.this,ConsultantInfo.class);
                g.putExtra("Patient ID",pid);
                g.putExtra("Doctor ID", did);
                g.putExtra("Patient name", name);
                g.putExtra("Patient email", pemail);
                startActivity(g);
                break;
            case R.id.nav_contacts:
                Intent s= new Intent(MedicalRec.this,EContacts.class);
                s.putExtra("Doctor ID", did);
                s.putExtra("Patient ID",pid);
                s.putExtra("Patient name", name);
                s.putExtra("Patient email", pemail);
                startActivity(s);
                break;
            case R.id.nav_logout:
                Intent l = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                l.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(l);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
