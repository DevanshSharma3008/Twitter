package com.example.sandeep.twitterds;

import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.HashMap;

/**
 * Created by sandeep on 26/07/17.
 */

public class HeaderCreation {

    String url;
    HashMap<String,String>map;

    public HeaderCreation(String url, HashMap<String, String> map) {
        this.url = url;
        this.map = map;
    }


    public String getHeader(){
        TwitterSession session= TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthConfig authConfig=TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken=session.getAuthToken();
        OAuthSigning authSigning=new OAuthSigning(authConfig,authToken);
        String header=authSigning.getAuthorizationHeader("GET",url,map);
        return header;
    }


}
