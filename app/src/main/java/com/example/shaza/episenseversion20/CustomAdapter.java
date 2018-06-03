package com.example.shaza.episenseversion20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import static com.example.shaza.episenseversion20.loginScreen.IP;
import static com.example.shaza.episenseversion20.AppStatus.pid;


/**
 * Created by Shaza on 2/21/2018.
 */

public class CustomAdapter extends BaseAdapter {

    Context context ;
    List<ContactTemplate> myList;

    public CustomAdapter(Context context, List<ContactTemplate> myContacts) {

        this.context = context ;
        this.myList = myContacts ;
    }

    @Override
    public int getCount() {
        return myList.size() ;
    }

    @Override
    public Object getItem(int i) {
        return myList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return myList.indexOf(getItem(i)) ;   }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View_Holder holder = null ;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) ;
        holder = new View_Holder() ;


        if(convertView==null)
        {convertView = mInflater.inflate(R.layout.record_item,null);
            holder.name = convertView.findViewById(R.id.namelabel ) ;
            holder.number = convertView.findViewById(R.id.numberlabel ) ;
            holder.edit = convertView.findViewById(R.id.editbtn);
            holder.delete = convertView.findViewById(R.id.deletebtn);

            convertView.setTag(holder); }
        else
        {
            holder = (View_Holder) convertView.getTag() ;
        }

        ContactTemplate row_pos = myList.get(position) ;
        String name = row_pos.getFname()+" " +row_pos.getLname();
        String numberset = "+"+row_pos.getNumber();
        holder.name.setText(name);
        holder.number.setText(numberset) ;


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                ContactTemplate editcontact = myList.get(position) ;
                //System.out.println(position);
                Intent e= new Intent(v2.getContext(),Edit_Contacts.class);
                e.putExtra("Patient ID","2");
                e.putExtra("Old Number",editcontact.getNumber() );
                e.putExtra("Old fName",editcontact.getFname() );
                e.putExtra("Old lName",editcontact.getLname() );
                e.putExtra("Position",position);
                v2.getContext().startActivity(e);

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v3) {
                System.out.println("here in set on click");
                ContactTemplate deletecontact = myList.get(position) ;
                String dnumber = deletecontact.getNumber();
               // Toast.makeText(context, dnumber, Toast.LENGTH_SHORT).show();
                final String url ="http://"+IP+"/contacts/"+pid+"?contact_number="+dnumber ;
                System.out.println(url);
                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(v3.getContext(), response, Toast.LENGTH_LONG).show();
                                if (response.equals("OK") ) {
                                    myList.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(v3.getContext(), "Contact Deleted", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(v3.getContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {

                };
                RequestQueue requestQueue = Volley.newRequestQueue(v3.getContext());
                requestQueue.add(stringRequest);

            }
        });
        return convertView;

    }

    private class View_Holder
    {
        private TextView name ;
        private TextView number ;
        private Button edit ;
        private Button delete ;
    }
}
//