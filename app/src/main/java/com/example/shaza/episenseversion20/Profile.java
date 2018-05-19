package com.example.shaza.episenseversion20;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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

import java.util.HashMap;
import java.util.Map;

import static com.example.shaza.episenseversion20.AppStatus.myProfile;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView showname;
    private TextView showid ;
    private TextView nav_user;
    private TextView nav_mail;
    private EditText showemail ;
    private EditText showcontact;
    private EditText showaddress;
    private EditText showdob;
    private TextView showdocname;
    private Button updatebtn;
    private boolean updateable = false ;
    private RequestQueue mQueue ;
    private String pid;
    private String did;
    private String name;
    private String pemail;
    private String check = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        updatebtn = findViewById(R.id.update);
        showemail = findViewById(R.id.email123);
        showid = findViewById(R.id.pid);
        showname =  findViewById(R.id.name);
        showcontact = findViewById(R.id.contactnum);
        showaddress = findViewById(R.id.address);
        showdob = findViewById(R.id.dob);
        showdocname = findViewById(R.id.dname);

        showdob.setEnabled(false);
        showaddress.setEnabled(false);
        showcontact.setEnabled(false);
        showdob.setFocusable(false);
        showaddress.setFocusable(false);
        showcontact.setFocusable(false);
        showemail.setEnabled(false);
        showemail.setFocusable(false);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if(extras != null)
            pid = extras.getString("Patient ID");
            did = extras.getString("Doctor ID");
        Toast.makeText(Profile.this, did, Toast.LENGTH_LONG).show();

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(view);
            }
        });

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);



                            showname.setText(myProfile.getFullname());
                            showid.setText(myProfile.getId());
                            showemail.setText(myProfile.getEmail());
                            showcontact.setText(myProfile.getContactnum());
                            showaddress.setText(myProfile.getAddress());
                            showdob.setText(myProfile.getDob());
                            showdocname.setText(myProfile.getDocname());

                            name = myProfile.getFullname();
                            pemail= myProfile.getEmail() ;
                            nav_user.setText(name);
                            nav_mail.setText(pemail);

                            Log.d("done","DONEEEE");


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
        switch (id){
            case R.id.nav_profile:
                Intent h= new Intent(Profile.this,Profile.class);
                h.putExtra("Doctor ID", did);
                h.putExtra("Patient ID",pid);
                h.putExtra("Patient name", name);
                h.putExtra("Patient email", pemail);
                startActivity(h);
                break;
            case R.id.nav_records:
                Intent i= new Intent(Profile.this,MedicalRec.class);
                i.putExtra("Doctor ID", did);
                i.putExtra("Patient ID",pid);
                i.putExtra("Patient name", name);
                i.putExtra("Patient email", pemail);
                startActivity(i);
                break;
            case R.id.nav_consultant:
                Intent g= new Intent(Profile.this,ConsultantInfo.class);
                g.putExtra("Patient ID",pid);
                g.putExtra("Doctor ID", did);
                g.putExtra("Patient name", name);
                g.putExtra("Patient email", pemail);
                startActivity(g);
                break;
            case R.id.nav_contacts:
                Intent s= new Intent(Profile.this,EContacts.class);
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

    public void update(View v)
    {
        if(!updateable){
            showdob.setEnabled(true);
            showaddress.setEnabled(true);
            showcontact.setEnabled(true);
            showdob.setFocusable(true);
            showaddress.setFocusable(true);
            showcontact.setFocusable(true);
            showdob.setFocusableInTouchMode(true);
            showaddress.setFocusableInTouchMode(true);
            showcontact.setFocusableInTouchMode(true);
            Toast.makeText(this,"You can now edit your profile details",Toast.LENGTH_SHORT).show();
            updateable=true;

        } else {
            showdob.setEnabled(false);
            showaddress.setEnabled(false);
            showcontact.setEnabled(false);
            showdob.setFocusable(false);
            showaddress.setFocusable(false);
            showcontact.setFocusable(false);


            final String key_address="address";
            final String key_number="number";
            final String key_dob="dob";
            final String dob = showdob.getText().toString().trim();
            final String address = showaddress.getText().toString().trim();
            final String contact = showcontact.getText().toString().trim();

            final String url = "http://10.0.2.2:3001/patients/" +pid+"?" + "&address=" + address + "&date_of_birth=" + dob + "&contact_number=" + contact;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(signUp.this, response, Toast.LENGTH_LONG).show();
                            if(response.equals("OK"))
                            {
                                myProfile.setAddress(address);
                                myProfile.setContactnum(contact);
                                myProfile.setDob(dob);
                                finish() ;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Profile.this,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                    params.put(key_address,address);
                    params.put(key_number,contact);
                    params.put(key_dob,dob);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            updateable=false;
        }
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
}

