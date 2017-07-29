package com.example.android.foodwhips.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodwhips.R;

public class PhotoInfo extends Fragment {
    private TextView temp;

    public PhotoInfo(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_picture_info, container, false);

        temp = (TextView) view.findViewById(R.id.test_temp);
        return view;
    }
}
