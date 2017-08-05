package com.example.android.foodwhips.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.foodwhips.fragments.FirstFragment;

/**
 * Created by li-en on 8/5/17.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;

    public TabsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount(){
        return NUM_ITEMS;
    }

    //Return fragment to display
    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                return FirstFragment.newInstance(0, "Page 1");
            case 1:
                return FirstFragment.newInstance(1, "Page 2");
            case 2:
                return FirstFragment.newInstance(2, "Page 3");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "Page " + position;
    }
}
