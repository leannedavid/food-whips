package com.example.android.foodwhips.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.android.foodwhips.R;

public class GeneralInfo extends Fragment{

    private TextView mTimeTaken;
    private TextView mRecipeServings;
    private TextView mSourceName;
    private TextView mSourceUrl;

    static final String TAG = "generalinfofragment";

    private static final String RECIPE_TIME_VALUE = "recipe_time";
    private static final String RECIPE_SERVINGS_VALUE = "recipe_servings";
    private static final String RECIPE_SOURCE_NAME = "recipe_source_name";
    private static final String RECIPE_SOURCE_URL = "recipe_source_url";

    public GeneralInfo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_info, container, false);

        //String recipe_id = getArguments().getString("recipe_id");
        //Log.v(TAG, "SUCCESSFULLY PASSED ID: " + recipe_id);

        mTimeTaken = (TextView) view.findViewById(R.id.detail_time_taken);
        mRecipeServings = (TextView) view.findViewById(R.id.detail_servings);
        mSourceName = (TextView) view.findViewById(R.id.detail_source_name);
        mSourceUrl = (TextView) view.findViewById(R.id.detail_source_link);

        mTimeTaken.setText("Time: " + getArguments().getString(RECIPE_TIME_VALUE));
        mRecipeServings.setText("Serving(s): " + getArguments().getString(RECIPE_SERVINGS_VALUE));
        mSourceName.setText("Source: " + getArguments().getString(RECIPE_SOURCE_NAME));
        mSourceUrl.setText("Source Link: " + getArguments().getString(RECIPE_SOURCE_URL));

        return view;
    }
}