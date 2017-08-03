package com.example.sandeep.twitterds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandeep on 02/08/17.
 */

public class CustomAdapter3 extends ArrayAdapter<Following.UserFollowing> {

    ArrayList<Following.UserFollowing> arrayList;
    Context context;


    public CustomAdapter3(@NonNull Context context, ArrayList<Following.UserFollowing> arrayList) {
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
        TextView screen_name;
        CircleImageView circleImageView;
        Button button;

        public Viewholder(TextView name, TextView screen_name, CircleImageView circleImageView, Button button) {
            this.name = name;
            this.screen_name = screen_name;
            this.circleImageView = circleImageView;
            this.button = button;
        }
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_3, null);
            TextView name = (TextView) convertView.findViewById(R.id.textView);
            TextView screen_name = (TextView) convertView.findViewById(R.id.textView2);
            CircleImageView circleImageView = (CircleImageView) convertView.findViewById(R.id.circleImageView);
            Button button = (Button) convertView.findViewById(R.id.button);
            Viewholder v = new Viewholder(name, screen_name, circleImageView, button);
            convertView.setTag(v);

        }

        Following.UserFollowing e = arrayList.get(position);
        Viewholder v = (Viewholder) convertView.getTag();
        v.name.setText(e.name);
        v.screen_name.setText("@" + e.screen_name);
        Picasso.with(context)
                .load(e.profile_image_url)
                .into(v.circleImageView);

        final long id2 = e.id;

        v.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context,"asdasda",Toast.LENGTH_SHORT).show();

                TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
                TwitterAuthToken authToken = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

                TwitterAuthToken token = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

                OAuthSigning authSigning = new OAuthSigning(authConfig, authToken);

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("user_id", "" + id2);

                String header = authSigning.getAuthorizationHeader("POST", "https://api.twitter.com/1.1/friendships/destroy.json",map);
                Log.i("a", header);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.twitter.com/1.1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                Call<Unfollowing> call = apiInterface.unfollow(header, id2);
                call.enqueue(new Callback<Unfollowing>() {
                    @Override
                    public void onResponse(Call<Unfollowing> call, Response<Unfollowing> response) {
                        Log.i("testing",response.code()+"");
                        if(response.isSuccessful())
                        Toast.makeText(getContext(),"Unfollowed",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Unfollowing> call, Throwable t) {

                    }
                });

            }


        });

        return convertView;
    }
}