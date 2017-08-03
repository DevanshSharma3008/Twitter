package com.example.sandeep.twitterds;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ImageView imageView=(ImageView)findViewById(R.id.profileBackground);
        final CircleImageView circleImageView=(CircleImageView)findViewById(R.id.circleImageView3);
        final TextView profileName=(TextView)findViewById(R.id.textView6);
        final TextView screen_name=(TextView)findViewById(R.id.textView5);
        final TextView following=(TextView)findViewById(R.id.textView14);
        final TextView followers=(TextView)findViewById(R.id.textView7);
        final TextView desc=(TextView)findViewById(R.id.textView13);
        final TextView mytweets=(TextView)findViewById(R.id.textView8);

        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        TwitterAuthToken token = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        OAuthSigning authSigning = new OAuthSigning(authConfig, authToken);

        TwitterSession session=TwitterCore.getInstance().getSessionManager().getActiveSession();

        final HashMap<String, String> params = new HashMap<>();
        //params.put("id", "23424848");
        params.put("screen_name",session.getUserName());

        String header = authSigning.getAuthorizationHeader("GET", "https://api.twitter.com/1.1/users/show.json",params);
        Log.i("a",header);



//        Log.i("header",header);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Call<Profile>call =apiInterface.getProfile(header,session.getUserName());
        Log.i("hello","hello");
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                //Log.i("hello1",String.valueOf(response.body()));
                Profile e=response.body();
                //Log.i("dev",e.name);


                String url1=e.profile_banner_url;
                String arr[]=new String[10];
                arr=url1.split("normal");
                //Log.i("devd",arr[0]+"bigger");
                String url_banner=arr[0];


                String url2=e.profile_image_url;
                String arr2[]=new String[10];
                arr2=url2.split("normal");
                String url_dp=arr2[0]+"bigger.jpg";

               // Log.i("test",url_banner+" "+url_dp);


                Picasso.with(ProfileActivity.this)
                        .load(url_banner)
                        .into(imageView);

                Picasso.with(ProfileActivity.this)
                        .load(url_dp)
                        .into(circleImageView);

               profileName.setText(e.name);
                screen_name.setText("@"+e.screen_name);
                following.setText(e.friends_count+"");
                followers.setText(e.followers_count+"");
                desc.setText(e.description);

                following.setTypeface(null, Typeface.BOLD);
                following.setText("Following   "+e.friends_count);
                following.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(ProfileActivity.this,FollowingActivity.class);
                        startActivity(i);
                    }
                });




                followers.setTypeface(null,Typeface.BOLD);
                followers.setText("Followers   "+e.followers_count);
                mytweets.setTypeface(null,Typeface.BOLD);
                mytweets.setText("Tweets   "+e.statuses_count);
                mytweets.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(ProfileActivity.this,MyTweetsActivity.class);
                        startActivity(i);
                    }
                });



            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });


    }
}
