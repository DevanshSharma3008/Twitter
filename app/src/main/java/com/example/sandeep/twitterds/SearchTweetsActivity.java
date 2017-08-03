package com.example.sandeep.twitterds;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

public class SearchTweetsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tweets);

        getSupportActionBar().setTitle("Search Tweets");


        SearchView s = (SearchView) findViewById(R.id.searchvi);
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


    public void filter(String text)
    { //text=text.toLowerCase();
        SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query(text)
                .build();

        TweetTimelineListAdapter defaultTwitterAdapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(searchTimeline)
                .build();

        // custom adapter made to handle onClick on each tweet view
        //customAdapter = new CustomAdapter(this, searchTimeline, this);

       ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(defaultTwitterAdapter);


    }


}
