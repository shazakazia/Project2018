package com.example.shaza.episenseversion20;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.example.shaza.episenseversion20.loginScreen.IP;

//testing .. integration
public class AppStatus extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nav_user;
    private TextView nav_mail;
    public static String pid;
    private String did;
    private String name;
    private String pemail;
    private String itemfname;
    private String itemlname;
    private String itemnumber;
    public static int records ;
    public static ProfileTemplate myProfile ;
    private RequestQueue mQueue ;
    private Context context;
    public static List<ContactTemplate> contactlist ;
    private Handler mHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstatus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationIcon(null);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.nav_name);
        nav_mail = (TextView)hView.findViewById(R.id.nav_email);
        navigationView.setNavigationItemSelectedListener(this);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        //getLocalIpAddress()
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            pid = extras.getString("Patient ID");

        mQueue = Volley.newRequestQueue(this);
        getUser();
        contactlist = new ArrayList<ContactTemplate>() ;
        getContacts();
        this.context = this ;
        Intent alarm = new Intent(this.context, AlarmReceiver.class);
        System.out.println("before bool");
        boolean alarmRunning = (PendingIntent.getBroadcast(this.context, 1, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        System.out.println(alarmRunning);

//        if(!alarmRunning) {
            Log.d("heree","HERE2");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 1, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
//        mHandler.postDelayed(new Runnable() {
//            public void run() {
//                Intent h= new Intent(AppStatus.this,Profile.class);
//                h.putExtra("Doctor ID", did);
//                h.putExtra("Patient ID",pid);
//                h.putExtra("Patient name", name);
//                h.putExtra("Patient email", pemail);
//                startActivity(h);
//            }
//        }, 5000);
//        }

        mHandler.postDelayed(new Runnable() {
            public void run() {
                Intent h= new Intent(AppStatus.this,Profile.class);
                h.putExtra("Doctor ID", did);
                h.putExtra("Patient ID",pid);
                h.putExtra("Patient name", name);
                h.putExtra("Patient email", pemail);
                startActivity(h);
            }
        }, 5000);
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
        getMenuInflater().inflate(R.menu.mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        item.setEnabled(false);
//        switch (id){
//
//            case R.id.nav_profile:
//                Intent h= new Intent(AppStatus.this,Profile.class);
//                h.putExtra("Doctor ID", did);
//                h.putExtra("Patient ID",pid);
//                h.putExtra("Patient name", name);
//                h.putExtra("Patient email", pemail);
//                startActivity(h);
//                break;
//            case R.id.nav_records:
//                Intent i= new Intent(AppStatus.this,MedicalRec.class);
//                i.putExtra("Doctor ID", did);
//                i.putExtra("Patient ID",pid);
//                i.putExtra("Patient name", name);
//                i.putExtra("Patient email", pemail);
//                startActivity(i);
//                break;
//            case R.id.nav_consultant:
//                Intent g= new Intent(AppStatus.this,ConsultantInfo.class);
//                g.putExtra("Patient ID",pid);
//                g.putExtra("Doctor ID", did);
//                g.putExtra("Patient name", name);
//                g.putExtra("Patient email", pemail);
//                startActivity(g);
//                break;
//            case R.id.nav_contacts:
//                Intent s= new Intent(AppStatus.this,EContacts.class);
//                s.putExtra("Doctor ID", did);
//                s.putExtra("Patient ID",pid);
//                s.putExtra("Patient name", name);
//                s.putExtra("Patient email", pemail);
//                startActivity(s);
//                break;
//            case R.id.nav_logout:
//                Intent l = getBaseContext().getPackageManager()
//                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
//                l.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                finishAffinity();
//                startActivity(l);
//                break;
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getUser() {

        String url = "http://"+IP+"/patients/" + pid;

        //String url = "http://192.168.1.187:3001/patients/" + pid;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            JSONArray jsonArray = response.getJSONArray("Patients");
                            JSONObject patient = jsonArray.getJSONObject(0);
                            String fname = patient.getString("first_name");
                            String lname = patient.getString("last_name");
                            String fullname = fname+" "+lname ;
                            String id = patient.getString("patient_id");
                            String email = patient.getString("email");
                            String address = patient.getString("address");
                            String dob = patient.getString("date_of_birth");
                            String contactnum = patient.getString("contact_number");
                            String docname = "Dr. " + patient.getString("doctor_name");
                            records = Integer.parseInt(patient.getString("number_of_seizures"));
                            Log.d("records", patient.getString("number_of_seizures")) ;
                            did = patient.getString("doctor_id");

                            myProfile = new ProfileTemplate(fname,lname,fullname,id,email,address,dob,contactnum,docname) ;
                            name = fullname ;
                            Toast.makeText(AppStatus.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                            pemail= email ;
                            nav_user.setText(name);
                            nav_mail.setText(pemail);

                            Log.d("done","DONEEEE");


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


    public void getContacts() {
        String url = "http://"+IP+"/contacts/" + pid;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        try {

                            JSONArray jsonArray = response.getJSONArray("Contacts");
                            // Toast.makeText(MedicalRec.this, jsonArray.length(), Toast.LENGTH_LONG).show();

                            JSONObject record;
                            for(int i =0 ; i<jsonArray.length(); i++) {
                                record = jsonArray.getJSONObject(i);
                                itemfname = record.getString("first_name");
                                itemlname = record.getString("last_name") ;
                                itemnumber = record.getString("contact_number");

                                ContactTemplate contact = new ContactTemplate(itemfname, itemlname, itemnumber);
                                // Toast.makeText(EContacts.this, item, Toast.LENGTH_LONG).show();
                                // myContacts.add(itemname);
                                //System.out.println("here");
                                contactlist.add(contact);
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
    }

    public void logoutUser() {
        Intent alarm = new Intent(this.context, AlarmReceiver.class);
        System.out.println("at logout bool");
        boolean alarmRunning = (PendingIntent.getBroadcast(this.context, 1, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        System.out.println(alarmRunning);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 1, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        System.out.println("after cancel");
        alarmRunning = (PendingIntent.getBroadcast(this.context, 1, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        System.out.println(alarmRunning);
    }
    // public void send message()
//   {
//
//       if(check.toString().equals("1"))
//       {
//           Intent x = new Intent(Profile.this, MessageSystem.class);
//           x.putExtra("name",fullname);
//           x.putExtra("pid", id);
//           startActivity(x); }
//   }

//    public String getLocalIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces(); en.hasMoreElements();) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf
//                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    System.out.println("ip1--:" + inetAddress);
//                    System.out.println("ip2--:" + inetAddress.getHostAddress());
//
//                    // for getting IPV4 format
//                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address){
//
//                        String ip = inetAddress.getHostAddress().toString();
//                        System.out.println("ip---::" + ip);
//                        Toast.makeText(AppStatus.this, ip, Toast.LENGTH_LONG).show();
////                        TextView mytxtTextView = findViewById(R.id.mytext);
////                        mytxtTextView.setText(ip);
//                        // return inetAddress.getHostAddress().toString();
//                        return ip;
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Log.e("IP Address", ex.toString());
//        }
//        return null;
//    }

}

