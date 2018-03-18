package com.example.shaza.episenseversion20;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shaza on 2/21/2018.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(@NonNull Context context, String[] myRecs) {
        super(context, R.layout.record_item, myRecs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.record_item,parent,false);

        String singleitem = getItem(position);
        TextView myText = (TextView) customView.findViewById(R.id.recordlabel);
        ImageView myImage = (ImageView) customView.findViewById(R.id.contactpic);

        myText.setText(singleitem);
       // myImage.setImageResource(R.drawable.ic_menu_camera);
        return customView ;
    }
}
