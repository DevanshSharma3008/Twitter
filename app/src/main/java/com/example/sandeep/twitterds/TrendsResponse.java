package com.example.sandeep.twitterds;

import java.util.ArrayList;

/**
 * Created by sandeep on 31/07/17.
 */

public class TrendsResponse {


        ArrayList<Trends>trends;


        public static class Trends {
                String name;
                String query;
                String tweet_volume;
        }


}
