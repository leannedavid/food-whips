package com.example.android.foodwhips.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.android.foodwhips.R;

public class GeneralInfo extends Fragment{

    private TextView mTimeTaken;
    private TextView mRecipeServings;
    private TextView mCourses;
    private TextView mCuisines;
    private TextView mSourceUrl;

    static final String TAG = "generalinfofragment";

    private static final String RECIPE_TIME_VALUE = "recipe_time";
    private static final String RECIPE_SERVINGS_VALUE = "recipe_servings";
    private static final String RECIPE_COURSES = "recipe_courses";
    private static final String RECIPE_CUISINES = "recipe_cuisines";
    private static final String RECIPE_SOURCE_URL = "recipe_source_url";

    public GeneralInfo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_info, container, false);

        //String recipe_id = getArguments().getString("recipe_id");
        //Log.v(TAG, "SUCCESSFULLY PASSED ID: " + recipe_id);

        String recipe_courses = getArguments().getString(RECIPE_COURSES);
        String recipe_cuisines = getArguments().getString(RECIPE_CUISINES);

        Log.v(TAG, "DID COURSES COPY?: " + recipe_courses);

        mTimeTaken = (TextView) view.findViewById(R.id.detail_time_taken);
        mRecipeServings = (TextView) view.findViewById(R.id.detail_servings);
        mCourses = (TextView) view.findViewById(R.id.detail_courses);
        mCuisines = (TextView) view.findViewById(R.id.detail_cuisines);
        mSourceUrl = (TextView) view.findViewById(R.id.detail_source_link);

        mTimeTaken.setText("Time: " + getArguments().getString(RECIPE_TIME_VALUE));
        mRecipeServings.setText("Serving(s): " + getArguments().getString(RECIPE_SERVINGS_VALUE));

        if (recipe_courses != null) {
            mCourses.setVisibility(View.VISIBLE);
            mCourses.setText("Courses: " + recipe_courses);
        }

        if (recipe_cuisines != null) {
            mCourses.setVisibility(View.VISIBLE);
            mCuisines.setText("Cuisines: " + recipe_cuisines);
        }

        mSourceUrl.setText("Source Link: " + getArguments().getString(RECIPE_SOURCE_URL));

        return view;
    }
}