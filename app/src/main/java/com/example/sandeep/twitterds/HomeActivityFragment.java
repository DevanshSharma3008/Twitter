package com.example.sandeep.twitterds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sandeep on 25/07/17.
 */

public class HomeActivityFragment extends ListFragment {

    ListView listView;
    private CustomAdapter customAdapter;
    private UserTimeline userTimeline;
    private TweetTimelineListAdapter defaultTwitterAdapter;
    private TweetTimelineListAdapter adapter2;

    ProgressBar mProgressBar;
    List<Long>ids;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.home_fragment, container, false);

        mProgressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        showProgress(true);
        ids = new ArrayList<>();

//        userTimeline = new UserTimeline.Builder()
//                .screenName("codingninjashq")
//                .build();
//
//        defaultTwitterAdapter = new TweetTimelineListAdapter.Builder(getContext())
//                .setTimeline(userTimeline)
//                .build();
        Log.i("ab", "asdas");

//        HashMap<String,String>map=new HashMap<>();
//        map.put("count",""+25);
//        HeaderCreation headerCreation=new HeaderCreation("https://api.twitter.com/1.1/statuses/home_timeline.json",map);
//        String header=headerCreation.getHeader();


        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        TwitterAuthToken token = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        OAuthSigning authSigning = new OAuthSigning(authConfig, authToken);

        HashMap<String, String> params = new HashMap<>();
        //params.put("id", "23424848");
        params.put("count", "" + 40);

        String header = authSigning.getAuthorizationHeader("GET", "https://api.twitter.com/1.1/statuses/home_timeline.json", params);
        Log.i("a", header);

        //final String header2=header;
        //retrofitConnection(header);


        //        Log.i("header",header);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ArrayList<Home>> call = apiInterface.getTweets(header, 40);
        call.enqueue(new retrofit2.Callback<ArrayList<Home>>() {
            @Override
            public void onResponse(Call<ArrayList<Home>> call, final Response<ArrayList<Home>> response) {
                ArrayList<Home> hometimeline = response.body();
                for (int i = 0; i < hometimeline.size(); i++) {
                    Long id = hometimeline.get(i).id;
                    ids.add(id);
                }
                TweetUtils.loadTweets(ids, new com.twitter.sdk.android.core.Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        final FixedTweetTimeline fixedTweetTimeline = new FixedTweetTimeline.Builder().setTweets(result.data).build();
                        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity()).setTimeline(fixedTweetTimeline).build();
                        setListAdapter(adapter);

                        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_layout);
                        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
                        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                swipeLayout.setRefreshing(true);
                                adapter.refresh(new com.twitter.sdk.android.core.Callback<TimelineResult<Tweet>>() {
                                    @Override
                                    public void success(Result<TimelineResult<Tweet>> result) {
                                        swipeLayout.setRefreshing(false);

                                    }

                                    @Override
                                    public void failure(TwitterException exception) {

                                    }


                                });
                            }
                        });
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
            }


            @Override
            public void onFailure(Call<ArrayList<Home>> call, Throwable t) {

            }
        });


        showProgress(false);
        return v;
    }





    private void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }



}
