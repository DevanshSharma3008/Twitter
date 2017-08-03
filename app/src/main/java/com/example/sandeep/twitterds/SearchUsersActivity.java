package com.example.sandeep.twitterds;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchUsersActivity extends AppCompatActivity {

    ArrayList<UsersProfile> arrayList;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("Search Users");


        arrayList = new ArrayList<>();
        adapter=new CustomAdapter(this,arrayList);
        ListView list=(ListView)findViewById(R.id.list2);
        list.setAdapter(adapter);


        android.support.v7.widget.SearchView s = (android.support.v7.widget.SearchView) findViewById(R.id.searchview2);
        s.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

    }


    public void filter(String text) {
        //Log.i("ad",text);
        arrayList.clear();
        if (text.length() != 0) {
            TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
            TwitterAuthToken authToken = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

            TwitterAuthToken token = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

            OAuthSigning authSigning = new OAuthSigning(authConfig, authToken);

            HashMap<String, String> params = new HashMap<>();
            //params.put("id", "23424848");
            params.put("q", text);

            String header = authSigning.getAuthorizationHeader("GET", "https://api.twitter.com/1.1/users/search.json", params);
            Log.i("a", header);


//        Log.i("header",header);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.twitter.com/1.1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            Call<ArrayList<UsersProfile>> call = apiInterface.getUsers(header, text);
            call.enqueue(new Callback<ArrayList<UsersProfile>>() {
                @Override
                public void onResponse(Call<ArrayList<UsersProfile>> call, Response<ArrayList<UsersProfile>> response) {
                    ArrayList<UsersProfile> user = response.body();
                    //Log.i("TAG", String.valueOf(response.code()));
                    //Log.i("TAG", String.valueOf(response.body()));
                    for (int i = 0; i < user.size(); i++) {
                        UsersProfile e = new UsersProfile();
                        e.name = user.get(i).name;
                        e.screen_name = user.get(i).screen_name;
                        e.profile_image_url = user.get(i).profile_image_url;
//                        //Log.i("TAG", "url " + e.profile_image_url);
                        arrayList.add(e);

                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ArrayList<UsersProfile>> call, Throwable t) {

                }
            });
        }

    }
}