package com.example.sandeep.twitterds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowingActivity extends AppCompatActivity {

    ArrayList<Following.UserFollowing>foll;

    CustomAdapter3 adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);


        getSupportActionBar().setTitle("Following");

        foll=new ArrayList<>();
        adapter3=new CustomAdapter3(this,foll);

        ListView listView=(ListView)findViewById(R.id.listFollowing);
        listView.setAdapter(adapter3);


        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        TwitterAuthToken token = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        OAuthSigning authSigning = new OAuthSigning(authConfig, authToken);

        String header = authSigning.getAuthorizationHeader("GET", "https://api.twitter.com/1.1/friends/list.json",null);
        Log.i("a",header);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Following> call = apiInterface.getFollowing(header);
        call.enqueue(new Callback<Following>() {
            @Override
            public void onResponse(Call<Following> call, Response<Following> response) {
               Following e=response.body();
                ArrayList<Following.UserFollowing>arrayList=e.users;
                for(int i=0;i<arrayList.size();i++){
                    Following.UserFollowing u=new Following.UserFollowing();
                    u.id=arrayList.get(i).id;
                    u.name=arrayList.get(i).name;
                    u.profile_image_url=arrayList.get(i).profile_image_url;
                    u.screen_name=arrayList.get(i).screen_name;
                    foll.add(u);
                    Log.i("devad",u.name);
                    adapter3.notifyDataSetChanged();
                }
                //adapter3.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Following> call, Throwable t) {

            }
        });


    }
}
