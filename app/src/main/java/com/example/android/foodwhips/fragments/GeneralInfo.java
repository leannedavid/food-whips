package com.example.android.foodwhips.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.models.GetRecipe;
import com.example.android.foodwhips.utilities.ConversionUtils;
import com.example.android.foodwhips.utilities.NetworkUtils;
import com.example.android.foodwhips.utilities.RecipeLoader;

public class GeneralInfo extends Fragment{
//public class GeneralInfo extends Fragment implements LoaderManager.LoaderCallbacks<GetRecipe> {
    private ImageView mRecipeImage;
    private TextView mRecipeName;
    private TextView mRecipeRate;
    private TextView mTimeTaken;
    private TextView mRecipeServings;
    private TextView mSourceName;
    private TextView mSourceUrl;

    static final String TAG = "generalinfofragment";

    public GeneralInfo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_info, container, false);

        //String recipe_id = getArguments().getString("recipe_id");
        //Log.v(TAG, "SUCCESSFULLY PASSED ID: " + recipe_id);

        mRecipeImage = (ImageView) view.findViewById(R.id.detail_image);
        mRecipeName = (TextView) view.findViewById(R.id.detail_name);
        mRecipeRate = (TextView) view.findViewById(R.id.detail_rating);
        mTimeTaken = (TextView) view.findViewById(R.id.detail_time_taken);
        mRecipeServings = (TextView) view.findViewById(R.id.detail_servings);
        mSourceName = (TextView) view.findViewById(R.id.detail_source_name);
        mSourceUrl = (TextView) view.findViewById(R.id.detail_source_link);

       // getLoaderManager().initLoader(0, null, this).forceLoad();

        return view;
    }

    /*
    @Override
    public Loader<GetRecipe> onCreateLoader(int id, final Bundle args){
        Log.v(TAG, "THE VALUE OF INSIDE LOADER FOR GENERAL IS: " + getArguments().getString("recipe_id"));
        return new RecipeLoader(getContext(), getArguments().getString("recipe_id"));
    }

    @Override
    public void onLoadFinished(Loader<GetRecipe> loader, GetRecipe data){
        if(data != null) {
            new ConversionUtils.FetchImageTask(mRecipeImage).execute(data.getImgUrl());
            mRecipeName.setText(data.getRecipeName().toUpperCase());
            mRecipeRate.setText("Rating: " + data.getRating() + "/5");
            mTimeTaken.setText("Time: " + data.getTotalTime());
            mRecipeServings.setText("Serving(s): " + data.getServings());
            mSourceName.setText("Source: " + data.getSourceName());
            mSourceUrl.setText("Source Link: " + data.getSourceRecipeUrl());
        }
        else{
            mTimeTaken.setText(R.string.empty_data);
        }
    }

    @Override
    public void onLoaderReset(Loader<GetRecipe> loader){}
    */
}


