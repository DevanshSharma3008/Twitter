package com.example.sandeep.twitterds;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sandeep on 28/07/17.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).


        if(position==0) {
            //HomeActivityFragment fragment = new HomeActivityFragment();
            //return fragment;
            return PlaceholderFragment.newInstance(position+1);
        }
        else if(position==1){
           SearchFragment fragment2=new SearchFragment();
            return fragment2;
        }
        else {
            //return PlaceholderFragment.newInstance(position+1);
            TrendsFragment fragment3 = new TrendsFragment();
            return fragment3;

        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}
