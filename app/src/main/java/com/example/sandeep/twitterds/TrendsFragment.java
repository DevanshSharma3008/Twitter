package com.example.sandeep.twitterds;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandeep on 31/07/17.
 */

public class TrendsFragment extends Fragment {

    ArrayList<TrendsResponse.Trends> trend;
    CustomAdapter2 adapter2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.trending_tweets, container, false);

        TextView textView=(TextView)v.findViewById(R.id.textView);
        textView.setTypeface(null, Typeface.BOLD);

        EditText editText=(EditText)v.findViewById(R.id.editText);
        editText.setTypeface(null,Typeface.BOLD);

        trend = new ArrayList<>();
        adapter2 = new CustomAdapter2(getContext(), trend);
        final ListView listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(adapter2);

        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        TwitterAuthToken token = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        OAuthSigning authSigning = new OAuthSigning(authConfig, authToken);

        HashMap<String, String> params = new HashMap<>();
        params.put("id", "23424848");

        String header = authSigning.getAuthorizationHeader("GET", "https://api.twitter.com/1.1/trends/place.json", params);
        Log.i("a", header);


//        Log.i("header",header);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ArrayList<TrendsResponse>> call = apiInterface.getTrends(header, 23424848);
        call.enqueue(new Callback<ArrayList<TrendsResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<TrendsResponse>> call, Response<ArrayList<TrendsResponse>> response) {
                ArrayList<TrendsResponse> t = response.body();
                TrendsResponse trendsResponse = t.get(0);
                ArrayList<TrendsResponse.Trends> arrayList = trendsResponse.trends;
                //Log.i("ada",""+response.code());
                for (int i = 0; i < arrayList.size(); i++) {
                    TrendsResponse.Trends e = new TrendsResponse.Trends();
                    e.name = arrayList.get(i).name;
                    e.query = arrayList.get(i).query;
                    e.tweet_volume = arrayList.get(i).tweet_volume;
                    //Log.i("abde",e.name);
                    trend.add(e);
                    adapter2.notifyDataSetChanged();

                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String query=trend.get(position).query;
                        Intent i=new Intent(getContext(),TrendClickActivity.class);
                        i.putExtra("query",query);
                        startActivity(i);

                    }
                });


            }

            @Override
            public void onFailure(Call<ArrayList<TrendsResponse>> call, Throwable t) {

            }
        });


        return v;
    }
}

