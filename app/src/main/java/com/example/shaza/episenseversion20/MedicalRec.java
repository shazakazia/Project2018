package com.example.shaza.episenseversion20;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import java.util.List;

import static com.example.shaza.episenseversion20.loginScreen.IP;

public class MedicalRec extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   // public static ArrayList<String> myRec;
    private RequestQueue mQueue ;
    private TextView nav_user;
    private TextView nav_mail;
    private String pid;
    private String item;
    private String did;
    private String name;
    private String pemail;
    private String test;

    private String itemdate;
    private String itemtime;

    private List<RecordsTemplate> recordlist ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_rec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Seizure History");
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
        recordlist = new ArrayList<RecordsTemplate>() ;
        String url = "http://"+IP+"/patients/" + pid+ "/history";
        //String url = "http://172.28.16.92:3001/patients/" + pid+ "/history";

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

                                    itemtime = "Time: " + record.getString("timestamp");
                                    itemdate = "Date: " +record.getString("date");

                                    RecordsTemplate newrecord = new RecordsTemplate(itemdate,itemtime) ;

                                    //Toast.makeText(MedicalRec.this, item, Toast.LENGTH_LONG).show();

                                    recordlist.add(newrecord);
                                   // System.out.println("here");
                                    populate(recordlist,list);
                                   // System.out.println(myRec.size());
                                //}

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

    public void populate(List<RecordsTemplate> recordlist, ListView list)
    {
        CustomAdapter2 adapter = new CustomAdapter2(this,recordlist);
        list.setAdapter(adapter) ;
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
                logout();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logout()
    {
        Intent l = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        l.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        Intent alarm = new Intent(this, AlarmReceiver.class);
        System.out.println("at logout bool");
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 1, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        System.out.println(alarmRunning);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        System.out.println("after cancel");
        alarmRunning = (PendingIntent.getBroadcast(this, 1, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        System.out.println(alarmRunning);
        finishAffinity();
        startActivity(l);
    }
}
