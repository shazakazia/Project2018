package com.example.shaza.episenseversion20;

import android.content.Intent;
import android.os.Bundle;
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

public class EContacts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<String> myContacts;
    private RequestQueue mQueue ;
    private String pid;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_econtacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final ListView list = (ListView) findViewById(R.id.contact_list) ;


        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if(extras != null)
            pid = extras.getString("Patient ID");

        Toast.makeText(EContacts.this, pid, Toast.LENGTH_LONG).show();


        mQueue = Volley.newRequestQueue(this);

        myContacts= new ArrayList<String>();

        String url = "http://10.0.2.2:3001/contacts/" + pid;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {

                            JSONArray jsonArray = response.getJSONArray("Contacts");
                            // Toast.makeText(MedicalRec.this, jsonArray.length(), Toast.LENGTH_LONG).show();

                            JSONObject record;
                            for(int i =0 ; i<jsonArray.length(); i++)
                            {
                                record = jsonArray.getJSONObject(i);
                                item = record.getString("first_name")+"  "+record.getString("last_name")+" \n "+record.getString("contact_number");
                                Toast.makeText(EContacts.this, item, Toast.LENGTH_LONG).show();

                                myContacts.add(item);
                                System.out.println("here");
                                populate(myContacts,list);
                               System.out.println(myContacts.size());
                                 Log.d(item,"OUTPUT_ITEM");
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
//
    public void populate(ArrayList<String> myContacts, ListView list)
    {
        ArrayAdapter<String> myAdapter = new CustomAdapter(this,myContacts);
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

    /*private void populateListview() {
        String[] myRecs = { "Shaza", "Rabia", "Uroosa", "Alex", "Beschier" };
        ListView list = (ListView) findViewById(R.id.record_list) ;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicalRec.this,android.R.layout.simple_list_item_1, android.R.id.text1, myRecs);
        list.setAdapter(adapter);
    }*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_profile:
                Intent h= new Intent(EContacts.this,Profile.class);
                h.putExtra("Patient ID",pid);
                startActivity(h);
                break;
            case R.id.nav_records:
                Intent i= new Intent(EContacts.this,MedicalRec.class);
                i.putExtra("Patient ID",pid);
                startActivity(i);
                break;
            case R.id.nav_consultant:
                Intent g= new Intent(EContacts.this,ConsultantInfo.class);
                g.putExtra("Patient ID",pid);
                //  g.putExtra("Doctor ID", did);
                startActivity(g);
                break;
            case R.id.nav_contacts:
                Intent s= new Intent(EContacts.this,EContacts.class);
                s.putExtra("Patient ID",pid);
                startActivity(s);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
