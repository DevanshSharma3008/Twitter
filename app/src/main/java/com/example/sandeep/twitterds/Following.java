package com.example.sandeep.twitterds;

import java.util.ArrayList;

/**
 * Created by sandeep on 02/08/17.
 */

public class Following {

    ArrayList<UserFollowing>users;

    public static class UserFollowing{

        String name;
        String profile_image_url;
        String screen_name;
        long id;
    }

}
