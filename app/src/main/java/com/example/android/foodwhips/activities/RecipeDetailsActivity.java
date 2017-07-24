package com.example.android.foodwhips.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.models.GetRecipe;
import com.example.android.foodwhips.models.SearchRecipe;
import com.example.android.foodwhips.utilities.ConversionUtils;
import com.example.android.foodwhips.utilities.GetRecipeJsonUtils;
import com.example.android.foodwhips.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {
    private ImageView mRecipeImage;
    private TextView mRecipeName;
    private TextView mRecipeRate;
    private TextView mTimeTaken;
    private TextView mRecipeServings;
    private TextView mSourceName;
    private TextView mSourceUrl;

    private TextView mIngredientsView;

    static final String TAG = "recipedetailsactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        mRecipeImage = (ImageView) findViewById(R.id.detail_image);
        mRecipeName = (TextView) findViewById(R.id.detail_name);
        mRecipeRate = (TextView) findViewById(R.id.detail_rating);
        mTimeTaken = (TextView) findViewById(R.id.detail_time_taken);
        mRecipeServings = (TextView) findViewById(R.id.detail_servings);
        mSourceName = (TextView) findViewById(R.id.detail_source_name);
        mSourceUrl = (TextView) findViewById(R.id.detail_source_link);

        mIngredientsView = (TextView) findViewById(R.id.detail_ingredients);

        new FetchRecipeTask().execute();
    }

    private class FetchRecipeTask extends AsyncTask<String, Void, GetRecipe> {
        @Override
        protected GetRecipe doInBackground(String... params) {

            Bundle bundle = getIntent().getExtras();
            String recipeId = bundle.getString("recipe_id");
            URL recipeUrl = NetworkUtils.buildUrl(recipeId, 2);
            GetRecipe specificRecipe = null;

            try {
                String JsonRecipeDataResponse = NetworkUtils.getResponseFromHttpUrl(recipeUrl);
                specificRecipe = GetRecipeJsonUtils.parseJSON(JsonRecipeDataResponse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.v(TAG, "THE RECIPE ID: " + recipeId);
            return specificRecipe;
        }

        @Override
        protected void onPostExecute(final GetRecipe recipe) {
            super.onPostExecute(recipe);
            new ConversionUtils.FetchImageTask(mRecipeImage).execute(recipe.getImgUrl());
            mRecipeName.setText(recipe.getRecipeName().toUpperCase());
            mRecipeRate.setText("Rating: " + recipe.getRating() + "/5");
            mTimeTaken.setText("Time: " + recipe.getTotalTime());
            mRecipeServings.setText("Serving(s): " + recipe.getServings());
            mSourceName.setText("Source: " + recipe.getSourceName());
            mSourceUrl.setText("Source Link: " + recipe.getSourceRecipeUrl());

            mIngredientsView.setText(recipe.printIngredients());
        }
    }
}
