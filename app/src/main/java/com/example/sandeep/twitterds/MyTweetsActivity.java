package com.example.sandeep.twitterds;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class MyTweetsActivity extends AppCompatActivity {


    UserTimeline userTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweets);


        getSupportActionBar().setTitle("My Tweets");

        TwitterSession session= TwitterCore.getInstance().getSessionManager().getActiveSession();

        userTimeline = new UserTimeline.Builder()
                .screenName(session.getUserName())
                .build();

       final TweetTimelineListAdapter defaultTwitterAdapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();

        // custom adapter made to handle onClick on each tweet view
        //customAdapter = new CustomAdapter(this, searchTimeline, this);

        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(defaultTwitterAdapter);

        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.twiiterNaveColor));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                defaultTwitterAdapter.refresh(new com.twitter.sdk.android.core.Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Toast or some other action
                    }
                });
            }
        });

    }
}
