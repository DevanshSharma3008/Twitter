package com.example.sandeep.twitterds;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by sandeep on 26/07/17.
 */

public interface ApiInterface {

    @GET("statuses/home_timeline.json")
    Call<ArrayList<Home>> getTweets(@Header("Authorization")String header,@Query("count")int count);


    @GET("users/search.json")
    Call<ArrayList<UsersProfile>>getUsers(@Header("Authorization")String header,@Query("q")String searchquery);


    @GET("trends/place.json")
    Call<ArrayList<TrendsResponse>>getTrends(@Header("Authorization")String header, @Query("id")int id);

    @GET("users/show.json")
    Call<Profile>getProfile(@Header("Authorization")String header,@Query("screen_name")String name);

    @GET("account/verify_credentials.json")
    Call<AccountCredentialNavDrawer>getDetails(@Header("Authorization")String header);

    @GET("friends/list.json")
    Call<Following>getFollowing(@Header("Authorization")String header);

    @GET("friendships/destroy.json")
    Call<Unfollowing>unfollow(@Header("Authorization")String header,@Query("user_id")long id);

}
