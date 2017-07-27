package com.example.android.foodwhips.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodwhips.R;

/**
 * Created by li-en on 7/26/17.
 */

public class PhotoInfo extends Fragment {
    private TextView test;

    public PhotoInfo(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_picture_info, container, false);

        //test = (TextView) view.findViewById(R.id.)
        return view;

    }
}
