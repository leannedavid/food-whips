package com.example.android.foodwhips.activities;


import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.app.LoaderManager;
import android.content.Loader;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
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
    private TextView mSourceName;
    private TextView mRecipeRate;

    private Button instructionsButton;
    private Button faveButton;
    private Button picButton;

    static final String TAG = "recipedetailsactivity";

    private static final String GENERAL_INFO = "General";
    private static final String INGREDIENT_INFO = "Ingredients";
    private static final String PHOTO_INFO = "Photo";

    private static final String INGREDIENTS_VALUE = "ingredients";
    private static final String RECIPE_TIME_VALUE = "recipe_time";
    private static final String RECIPE_SERVINGS_VALUE = "recipe_servings";
    private static final String RECIPE_COURSES = "recipe_courses";
    private static final String RECIPE_CUISINES = "recipe_cuisines";
    private static final String RECIPE_FLAVORS = "recipe_flavors";
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
        mSourceName = (TextView) findViewById(R.id.detail_source_name);
        mRecipeRate = (TextView) findViewById(R.id.detail_rating);

        instructionsButton = (Button) findViewById(R.id.instructions_button);
        faveButton = (Button) findViewById(R.id.favorite_button);
        picButton = (Button) findViewById(R.id.pic_button);

        faveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Button fave = (Button) view;
                if (fave.getText().toString().equals("Un-favorite")){
                    faveButton.setText("Favorite");
                }
                else{
                    faveButton.setText("Un-favorite");
                }
            }
        });

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public void onRestart(){
        super.onRestart();
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

            if(!data.isCoursesEmpty()) {
                bundle.putString(RECIPE_COURSES, data.printCourses());
            }

            if(!data.isCuisinesEmpty()) {
                bundle.putString(RECIPE_CUISINES, data.printCuisines());
            }

            if(!data.isFlavorsEmpty()){
                bundle.putString(RECIPE_FLAVORS, data.printFlavors());
            }

            bundle.putString(RECIPE_SOURCE_URL, data.getSourceRecipeUrl());

            new ConversionUtils.FetchImageTask(mRecipeImage).execute(data.getImgUrl());
            mRecipeName.setText(data.getRecipeName().toUpperCase());
            mSourceName.setText(data.getSourceName());
            mRecipeRate.setText("Rating: " + data.getRating() + "/5");

            final String sourceUrl = data.getSourceRecipeUrl();


            instructionsButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    mTabHost.clearAllTabs();
                    Intent switchAct = new Intent(RecipeDetailsActivity.this, WebActivity.class);
                    switchAct.putExtra("source_url", sourceUrl);
                    startActivity(switchAct);
                }
            });


            mTabHost.addTab(mTabHost.newTabSpec(GENERAL_INFO)
                    .setIndicator(GENERAL_INFO), GeneralInfo.class, bundle);

            mTabHost.addTab(mTabHost.newTabSpec(INGREDIENT_INFO)
                    .setIndicator(INGREDIENT_INFO), IngredientsInfo.class, bundle);

            mTabHost.addTab(mTabHost.newTabSpec(PHOTO_INFO)
                    .setIndicator(PHOTO_INFO), PhotoInfo.class, null);

            Log.v(TAG, "SUCCESSFULLY PUT INFO INTO A BUNDLE");
            Log.v(TAG, "VALUE OF COURSES: " + data.printCourses());
            Log.v(TAG, "VALUE OF CUISINES: " + data.printCuisines());
        }
    }

    @Override
    public void onLoaderReset(Loader<GetRecipe> loader){}
}

