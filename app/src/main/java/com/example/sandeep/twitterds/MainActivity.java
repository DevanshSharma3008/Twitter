package com.example.sandeep.twitterds;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    BottomNavigationView prevMenuItem;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText("Home");
                    //Intent i=new Intent(MainActivity.this,HomeActivity.class);
                    //startActivity(i);
                    mViewPager.setCurrentItem(0);

                    //HomeActivityFragment fragment=new HomeActivityFragment();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();
                    return true; //HomeActivityFragment fragment=new HomeActivityFragment();
                //getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).com
                case R.id.navigation_search:

                    //mTextMessage.setText("SearchUsersActivity");
                    //Intent i=new Intent(MainActivity.this,HomeActivityFragment.class);
                    //startActivity(i);

                    //SearchFragment fragment2=new SearchFragment();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment2).commit();
                    mViewPager.setCurrentItem(1);

                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText("Notifications");
                    //PlaceholderFragment placeholderFragment = new PlaceholderFragment();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.content,placeholderFragment).commit();
                    mViewPager.setCurrentItem(2);

                    return true;
            }
            return false;
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton tweetComposer = (FloatingActionButton) findViewById(R.id.fab);
        tweetComposer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                Uri imageUri = FileProvider.getUriForFile(MainActivity.this,
//                        BuildConfig.APPLICATION_ID + ".fileprovider",
//                        new File("content://com.example.sandeep.twitterds.fileprovider/myimages/default_image.jpg"));
//
//                TweetComposer.Builder builder = new TweetComposer.Builder(getApplicationContext())
//                        .text("just setting up my Twitter Kit.")
//                        .image(imageUri);
//                builder.show();


                try {

                    new TweetComposer.Builder(MainActivity.this)

                            .text("Tweet from TwitterKit!")

                            .url(new URL("http://www.twitter.com"))

                            .show();


                } catch (MalformedURLException e) {

                    // Log.e(TAG, "error creating tweet intent", e);

                }

            }

        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //mTextMessage = (TextView) findViewById(R.id.message);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //HomeActivityFragment fragment = new HomeActivityFragment();
        //getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();

//        TwitterSession session= TwitterCore.getInstance().getSessionManager().getActiveSession();
//        String userName=session.getUserName();
//        Log.i("adad",userName);
//        Call<User>user=Twitter.getApiClient()






//        final Button tweetComposer = (Button) findViewById(R.id.tweet_composer);
//
//        tweetComposer.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//
//            public void onClick(View view) {
//
//                try {
//
//                    new TweetComposer.Builder(TweetComposerMainActivity.this)
//
//                            .text("Tweet from TwitterKit!")
//
//                            .url(new URL("http://www.twitter.com"))
//
//                            .show();
//
//
//                } catch (MalformedURLException e) {
//
//                    Log.e(TAG, "error creating tweet intent", e);
//
//                }
//
//            }
//
//        });


//        final Button organicComposer = (Button) findViewById(R.id.organic_composer);
//
//        organicComposer.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//
//            public void onClick(View view) {
//
//                launchPicker();
//
//            }
//
//        });
//
//    }
//
//
//
//    void launchPicker() {
//
//        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//
//        intent.setType(IMAGE_TYPES);
//
//        startActivityForResult(Intent.createChooser(intent, "Pick an Image"), IMAGE_PICKER_CODE);
//
//    }
//
//
//    @Override
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == IMAGE_PICKER_CODE && resultCode == Activity.RESULT_OK) {
//
//            launchComposer(data.getData());
//
//        }
//
//    }
//
//
//    void launchComposer(Uri uri) {
//
//        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
//
//                .getActiveSession();
//
//        final Intent intent = new ComposerActivity.Builder(TweetComposerMainActivity.this)
//
//                .session(session)
//
//                .image(uri)
//
//                .text("Tweet from TwitterKit!")
//
//                .hashtags("#twitter")
//
//                .createIntent();
//
//        startActivity(intent);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                    navigation.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        TwitterAuthToken token = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();

        OAuthSigning authSigning = new OAuthSigning(authConfig, authToken);

        TwitterSession session=TwitterCore.getInstance().getSessionManager().getActiveSession();

        String header = authSigning.getAuthorizationHeader("GET", "https://api.twitter.com/1.1/account/verify_credentials.json",null);
        Log.i("a",header);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<AccountCredentialNavDrawer> call = apiInterface.getDetails(header);
        call.enqueue(new Callback<AccountCredentialNavDrawer>() {
            @Override
            public void onResponse(Call<AccountCredentialNavDrawer> call, Response<AccountCredentialNavDrawer> response) {
                    AccountCredentialNavDrawer accountCredentialNavDrawer = response.body();
                    final CircleImageView circleImageView2=(CircleImageView)findViewById(R.id.imageViewDP);
                    final TextView textView=(TextView)findViewById(R.id.profilename);
                    final TextView textView2=(TextView)findViewById(R.id.textViewScreenName);


//                String url2=accountCredentialNavDrawer.profile_image_url;
//                String arr2[]=new String[10];
//                arr2=url2.split("normal");
//                String url_dp=arr2[0]+"bigger.jpg";
//


                Picasso.with(MainActivity.this)
                                .load(accountCredentialNavDrawer.profile_image_url)
                                .into(circleImageView2);


                        textView.setText(accountCredentialNavDrawer.name);

                        textView2.setText("@"+accountCredentialNavDrawer.screen_name);





            }

            @Override
            public void onFailure(Call<AccountCredentialNavDrawer> call, Throwable t) {

            }

                });



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }


        if(id==R.id.help){
            Intent i=new Intent();
            i.setAction(Intent.ACTION_VIEW);
            Uri uri=Uri.parse("https://support.twitter.com/");
            i.setData(uri);
            startActivity(i);
        }

        if(id==R.id.logout){
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
                SharedPreferences sp = getSharedPreferences(StartupLaunch.SP_NAME, MODE_PRIVATE);
                sp.edit().clear().commit();
                startActivity(new Intent(MainActivity.this, StartupLaunch.class));
                finish();
        }


        if(id==R.id.mytweets){
           Intent i=new Intent(MainActivity.this,MyTweetsActivity.class);
            startActivity(i);

        }

        if(id==R.id.profile){
            Intent i2=new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(i2);
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}