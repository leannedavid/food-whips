package com.example.android.foodwhips.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.foodwhips.fragments.GeneralInfo;
import com.example.android.foodwhips.fragments.IngredientsInfo;

/**
 * Created by li-en on 7/19/17.
 */

public class FragmentRecipeAdapter extends FragmentPagerAdapter {
    public FragmentRecipeAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        if(position == 0){
            fragment = new GeneralInfo();
        }
        else if(position == 1){
            fragment = new IngredientsInfo();
        }
        return fragment;
    }

    @Override
    public int getCount(){
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String title = "";
        if (position == 0){
            title = "General Info";
        }
        else if (position == 1){
            title = "Ingredients";
        }
        return title;
    }
}
