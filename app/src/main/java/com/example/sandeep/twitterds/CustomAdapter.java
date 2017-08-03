package com.example.sandeep.twitterds;

/**
 * Created by sandeep on 24/07/17.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.exoplayer.C;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomAdapter extends ArrayAdapter<UsersProfile>{

    ArrayList<UsersProfile>arrayList;
    Context context;


    public CustomAdapter(@NonNull Context context, ArrayList<UsersProfile>arrayList) {
        super(context,0);

        this.context=context;
        this.arrayList=arrayList;

    }


    @Override
    public int getCount() {
        return arrayList.size();
    }


    static class Viewholder{
        TextView name;
        TextView screen_name;
        CircleImageView circleImageView;

        Viewholder(TextView name, TextView screen_name, CircleImageView circleImageView){
            this.name=name;
            this.screen_name=screen_name;
            this.circleImageView=circleImageView;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item,null);
            TextView name=(TextView)convertView.findViewById(R.id.textView);
            TextView screen_name=(TextView)convertView.findViewById(R.id.textView2);
            CircleImageView circleImageView=(CircleImageView)convertView.findViewById(R.id.circleImageView);
            Viewholder v=new Viewholder(name,screen_name,circleImageView);
            convertView.setTag(v);

        }

        UsersProfile e=arrayList.get(position);
        Viewholder v=(Viewholder)convertView.getTag();
        v.name.setText(e.name);
        v.screen_name.setText("@"+e.screen_name);
        String url=e.profile_image_url;
        Picasso.with(context)
                .load(url)
                .into(v.circleImageView);

        return  convertView;

    }


}