package com.example.android.foodwhips.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodwhips.R;


public class IngredientsInfo extends Fragment {
    private TextView mIngredientsView;
    public IngredientsInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients_info, container, false);

        mIngredientsView = (TextView) view.findViewById(R.id.detail_ingredients);
        return view;
    }
}
