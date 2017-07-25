package com.example.android.foodwhips.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.models.GetRecipe;
import com.example.android.foodwhips.utilities.ConversionUtils;
import com.example.android.foodwhips.utilities.NetworkUtils;

public class GeneralInfo extends Fragment {
    private ImageView mRecipeImage;
    private TextView mRecipeName;
    private TextView mRecipeRate;
    private TextView mTimeTaken;
    private TextView mRecipeServings;
    private TextView mSourceName;
    private TextView mSourceUrl;

    public GeneralInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_info, container, false);
        mRecipeImage = (ImageView) view.findViewById(R.id.detail_image);
        mRecipeName = (TextView) view.findViewById(R.id.detail_name);
        mRecipeRate = (TextView) view.findViewById(R.id.detail_rating);
        mTimeTaken = (TextView) view.findViewById(R.id.detail_time_taken);
        mRecipeServings = (TextView) view.findViewById(R.id.detail_servings);
        mSourceName = (TextView) view.findViewById(R.id.detail_source_name);
        mSourceUrl = (TextView) view.findViewById(R.id.detail_source_link);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        //Bundle bundle = getActivity().getIntent().getExtras();
//        GetRecipe recipe = ConversionUtils.FetchRecipeTask(bundle);
//
//        new ConversionUtils.FetchImageTask(mRecipeImage).execute(recipe.getImgUrl());
//        mRecipeName.setText(recipe.getRecipeName().toUpperCase());
//        mRecipeRate.setText("Rating: " + recipe.getRating() + "/5");
//        mTimeTaken.setText("Time: " + recipe.getTotalTime());
//        mRecipeServings.setText("Serving(s): " + recipe.getServings());
//        mSourceName.setText("Source: " + recipe.getSourceName());
//        mSourceUrl.setText("Source Link: " + recipe.getSourceRecipeUrl());
    }

    @Override
    public void onResume(){
        super.onResume();

    }
}
