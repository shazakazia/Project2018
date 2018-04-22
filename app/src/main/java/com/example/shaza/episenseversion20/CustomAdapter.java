package com.example.shaza.episenseversion20;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View_Holder holder = null ;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) ;
        holder = new View_Holder() ;


        if(convertView==null)
        {convertView = mInflater.inflate(R.layout.record_item,null);
            holder.name = convertView.findViewById(R.id.namelabel ) ;
            holder.number = convertView.findViewById(R.id.numberlabel ) ;
            convertView.setTag(holder); }
        else
        {
            holder = (View_Holder) convertView.getTag() ;
        }

        ContactTemplate row_pos = myList.get(position) ;
        holder.name.setText(row_pos.getName());
        holder.number.setText(row_pos.getNumber()) ;

        return convertView;

    }

    private class View_Holder
    {
        private TextView name ;
        private TextView number ;
    }
}
//