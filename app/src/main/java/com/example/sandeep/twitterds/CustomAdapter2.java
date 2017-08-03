package com.example.sandeep.twitterds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sandeep on 31/07/17.
 */

public class CustomAdapter2 extends ArrayAdapter<TrendsResponse.Trends> {

    ArrayList<TrendsResponse.Trends> arrayList;
    Context context;


    public CustomAdapter2(@NonNull Context context, ArrayList<TrendsResponse.Trends> arrayList) {
        super(context, 0);

        this.context = context;
        this.arrayList = arrayList;

    }


    @Override
    public int getCount() {
        return arrayList.size();
    }


    static class Viewholder {
        TextView name;
        //TextView screen_name;

        Viewholder(TextView name) {
            this.name = name;
            //this.screen_name = screen_name;

        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_2, null);
            TextView name = (TextView) convertView.findViewById(R.id.trendingtopics);
            //TextView screen_name = (TextView) convertView.findViewById(R.id.trendingtweetvolume);
            Viewholder v = new Viewholder(name);
            convertView.setTag(v);

        }

        TrendsResponse.Trends e = arrayList.get(position);
        Viewholder v = (Viewholder) convertView.getTag();
        v.name.setText(e.name);
        //v.screen_name.setText("" + e.tweet_volume);

        return convertView;

    }
}

