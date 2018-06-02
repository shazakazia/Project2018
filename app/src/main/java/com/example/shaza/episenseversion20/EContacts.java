package com.example.shaza.episenseversion20;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
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

import static com.example.shaza.episenseversion20.AppStatus.contactlist;
import static com.example.shaza.episenseversion20.loginScreen.IP;

public class EContacts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //public static ArrayList<String> myContacts;
    FloatingActionButton Addbutton ;
    private RequestQueue mQueue ;
    private String pid;
    private String itemname;
    private String itemnumber;
    private String did;
    private String name;
    private String pemail;
    private TextView nav_user;
    private TextView nav_mail;
    private Button del;
    private Button edit;
    public static CustomAdapter adapter;




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
        View hView =  navigationView.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.nav_name);
        nav_mail = (TextView)hView.findViewById(R.id.nav_email);
        navigationView.setNavigationItemSelectedListener(this);

        final ListView list = (ListView) findViewById(R.id.contact_list) ;
        Addbutton = findViewById(R.id.addbutton) ;


        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if(extras != null)
        { pid = extras.getString("Patient ID");
            did = extras.getString("Doctor ID");
            name = extras.getString("Patient name");
            pemail = extras.getString("Patient email");}

        Toast.makeText(EContacts.this, did, Toast.LENGTH_LONG).show();

        nav_user.setText(name);
        nav_mail.setText(pemail);
        mQueue = Volley.newRequestQueue(this);

//
//       // myContacts= new ArrayList<String>();
//        contactlist = new ArrayList<ContactTemplate>() ;
//
//       // String url = "http://192.168.1.187:3001/contacts/" + pid;
//      String url = "http://"+IP+"/contacts/" + pid;
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>(){
//                    @Override
//                    public void onResponse(JSONObject response){
//                        try {
//
//                            JSONArray jsonArray = response.getJSONArray("Contacts");
//                            // Toast.makeText(MedicalRec.this, jsonArray.length(), Toast.LENGTH_LONG).show();
//
//                            JSONObject record;
//                            for(int i =0 ; i<jsonArray.length(); i++)
//                            {
//                                record = jsonArray.getJSONObject(i);
//                                itemname = record.getString("first_name")+"  "+record.getString("last_name") ;
//                                itemnumber = record.getString("contact_number");
//
//                                ContactTemplate contact = new ContactTemplate(itemname,itemnumber) ;
//                               // Toast.makeText(EContacts.this, item, Toast.LENGTH_LONG).show();
//                               // myContacts.add(itemname);
//                                //System.out.println("here");
//                                contactlist.add(contact) ;
//
//
                              populate(contactlist,list);
                            Addbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent x= new Intent(EContacts.this,Add_Contacts.class);
                                    x.putExtra("Patient ID",pid);
                                    startActivity(x);

                                }
                            });

    }


    public void populate(List<ContactTemplate> contactlist, ListView list)
    {
        adapter = new CustomAdapter(this,contactlist);
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
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_profile:
                Intent h= new Intent(EContacts.this,Profile.class);
                h.putExtra("Doctor ID", did);
                h.putExtra("Patient ID",pid);
                h.putExtra("Patient name", name);
                h.putExtra("Patient email", pemail);
                startActivity(h);
                break;
            case R.id.nav_records:
                Intent i= new Intent(EContacts.this,MedicalRec.class);
                i.putExtra("Doctor ID", did);
                i.putExtra("Patient ID",pid);
                i.putExtra("Patient name", name);
                i.putExtra("Patient email", pemail);
                startActivity(i);
                break;
            case R.id.nav_consultant:
                Intent g= new Intent(EContacts.this,ConsultantInfo.class);
                g.putExtra("Patient ID",pid);
                g.putExtra("Doctor ID", did);
                g.putExtra("Patient name", name);
                g.putExtra("Patient email", pemail);
                startActivity(g);
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


//