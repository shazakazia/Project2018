package com.example.shaza.episenseversion20;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shaza on 2/21/2018.
 */

public class CustomAdapter2 extends BaseAdapter {

    Context context ;
    List<RecordsTemplate> myList;

    public CustomAdapter2(Context context, List<RecordsTemplate> recordslist) {

        this.context = context ;
        this.myList = recordslist ;
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
        {convertView = mInflater.inflate(R.layout.record_item2,null);
            holder.date = convertView.findViewById(R.id.datelabel ) ;
            holder.time = convertView.findViewById(R.id.timelabel ) ;
            convertView.setTag(holder); }
        else
        {
            holder = (View_Holder) convertView.getTag() ;
        }

        RecordsTemplate row_pos = myList.get(position) ;
        holder.date.setText(row_pos.getDate());
        holder.time.setText(row_pos.getTime()) ;

        return convertView;

    }

    private class View_Holder
    {
        private TextView date ;
        private TextView time ;
    }
}
//