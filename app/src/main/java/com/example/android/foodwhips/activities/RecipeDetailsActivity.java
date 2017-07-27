package com.example.android.foodwhips.activities;


import android.support.v4.app.FragmentTabHost;
import android.app.LoaderManager;
import android.content.Loader;

import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.fragments.GeneralInfo;
import com.example.android.foodwhips.fragments.IngredientsInfo;
import com.example.android.foodwhips.fragments.PhotoInfo;
import com.example.android.foodwhips.models.GetRecipe;

import com.example.android.foodwhips.utilities.ConversionUtils;
import com.example.android.foodwhips.utilities.RecipeLoader;


public class RecipeDetailsActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<GetRecipe>{
    private FragmentTabHost mTabHost;
    private ImageView mRecipeImage;
    private TextView mRecipeName;
    private TextView mRecipeRate;

    private Button faveButton;
    private Button picButton;

    static final String TAG = "recipedetailsactivity";

    private static final String GENERAL_INFO = "General";
    private static final String INGREDIENT_INFO = "Ingredients";
    private static final String PHOTO_INFO = "Photos";

    private static final String INGREDIENTS_VALUE = "ingredients";
    private static final String RECIPE_TIME_VALUE = "recipe_time";
    private static final String RECIPE_SERVINGS_VALUE = "recipe_servings";
    private static final String RECIPE_SOURCE_NAME = "recipe_source_name";
    private static final String RECIPE_SOURCE_URL = "recipe_source_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        Bundle bundle = this.getIntent().getExtras();
        String recipeId = bundle.getString("recipe_id");
        Log.v(TAG, "check if this is sending");
        Log.v(TAG, "ID IS " + recipeId);

        mRecipeImage = (ImageView) findViewById(R.id.detail_image);
        mRecipeName = (TextView) findViewById(R.id.detail_name);
        mRecipeRate = (TextView) findViewById(R.id.detail_rating);

        faveButton = (Button) findViewById(R.id.favorite_button);
        picButton = (Button) findViewById(R.id.pic_button);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        getLoaderManager().initLoader(0, null, this).forceLoad();

    }

    @Override
    public Loader<GetRecipe> onCreateLoader(int id, final Bundle args){
        Bundle bundle = this.getIntent().getExtras();
        String recipeId = bundle.getString("recipe_id");

        Log.v(TAG, "THE VALUE OF INSIDE LOADER IS: " + recipeId);
        return new RecipeLoader(this, recipeId);
    }

    @Override
    public void onLoadFinished(Loader<GetRecipe> loader, GetRecipe data){
        if(data != null) {
            Bundle bundle = new Bundle();
            bundle.putString(INGREDIENTS_VALUE, data.printIngredients());
            bundle.putString(RECIPE_TIME_VALUE, data.getTotalTime());
            bundle.putString(RECIPE_SERVINGS_VALUE, data.getServings());
            bundle.putString(RECIPE_SOURCE_NAME, data.getSourceName());
            bundle.putString(RECIPE_SOURCE_URL, data.getSourceRecipeUrl());

            new ConversionUtils.FetchImageTask(mRecipeImage).execute(data.getImgUrl());
            mRecipeName.setText(data.getRecipeName().toUpperCase());
            mRecipeRate.setText("Rating: " + data.getRating() + "/5");

            mTabHost.addTab(mTabHost.newTabSpec(GENERAL_INFO)
                    .setIndicator(GENERAL_INFO), GeneralInfo.class, bundle);

            mTabHost.addTab(mTabHost.newTabSpec(INGREDIENT_INFO)
                    .setIndicator(INGREDIENT_INFO), IngredientsInfo.class, bundle);

            mTabHost.addTab(mTabHost.newTabSpec(PHOTO_INFO)
                    .setIndicator(PHOTO_INFO), PhotoInfo.class, null);

            Log.v(TAG, "SUCCESSFULLY PUT INFO INTO A BUNDLE");
        }
    }

    @Override
    public void onLoaderReset(Loader<GetRecipe> loader){}

}

