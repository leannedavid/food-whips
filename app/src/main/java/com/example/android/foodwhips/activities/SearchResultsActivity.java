package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.foodwhips.MainActivity;
import com.example.android.foodwhips.R;
import com.example.android.foodwhips.adapters.RecipeResultsAdapter;
import com.example.android.foodwhips.models.SearchRecipe;
import com.example.android.foodwhips.utilities.NetworkUtils;
import com.example.android.foodwhips.utilities.SearchRecipeJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultsActivity extends BaseActivity {
    private ProgressBar bar;
    private RecyclerView mRecyclerView;
    private RecipeResultsAdapter startAdapter;
    private URL foodsUrl;


    private static final String TAG = "SearchResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        Bundle bundle = this.getIntent().getExtras();
        String searchQuery = bundle.getString("searchQuery");
        String ingredientsQuery = bundle.getString("ingredientsFilter");
        String cuisineQuery = bundle.getString("cuisinesFilter");
        String randomQuery = bundle.getString("randomFilter");
        String nameQuery = bundle.getString("nameFilter");

        Log.v(TAG, "IS SEARCH QUERY NULL ? " + searchQuery);
        Log.v(TAG, "IS INGREDIENTS FILTER NULL ? " + ingredientsQuery);
        Log.v(TAG, "IS CUISINE FILTER NULL ? " + cuisineQuery);
        Log.v(TAG, "IS RANDOM NULL ? " + randomQuery);
        Log.v(TAG, "IS NAME FILTER NULL ? " + nameQuery);

        URL ingredientsFilter = null;
        try {
            ingredientsFilter = new URL(ingredientsQuery);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        URL cuisineFilter = null;
        try {
            cuisineFilter = new URL(cuisineQuery);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        URL randomFilter = null;
        try{
            randomFilter = new URL(randomQuery);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        URL nameFilter = null;
        try {
            nameFilter = new URL(nameQuery);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }

        if(searchQuery != null) {
            foodsUrl = NetworkUtils.buildUrl(searchQuery, 1);
        } else if(ingredientsFilter != null) {
            foodsUrl =  ingredientsFilter;
        } else if(cuisineFilter != null) {
            foodsUrl = cuisineFilter;
        } else if(randomFilter != null){
            foodsUrl = randomFilter;
        } else if(nameFilter != null) {
            foodsUrl = nameFilter;
        }


        //Progress Bar
        bar = (ProgressBar) findViewById(R.id.progressBar);

        //RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(startAdapter);

        new FetchFoodTask().execute();

        Log.v(TAG, "HITTING THE SEARCHRESULTS ACTIVITY WITH QUERY: " + searchQuery);
        Log.v(TAG, "WHAT IS FOODS URL?: " + foodsUrl.toString());
    }


    private class FetchFoodTask extends AsyncTask<String, Void, ArrayList<SearchRecipe>> {
        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<SearchRecipe> doInBackground(String... params) {
            ArrayList<SearchRecipe> recipeList = null;

            try {
                String jsonRecipeDataResponse = NetworkUtils.getResponseFromHttpUrl(foodsUrl);
                recipeList = SearchRecipeJsonUtils.parseJSON(jsonRecipeDataResponse);
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }
            return recipeList;
        }

        @Override
        protected void onPostExecute(final ArrayList<SearchRecipe> recipeList) {
            super.onPostExecute(recipeList);

            bar.setVisibility(View.GONE);

            if (recipeList != null) {
                RecipeResultsAdapter adapter = new RecipeResultsAdapter(recipeList, new RecipeResultsAdapter.RecipeClickListener(){
                    @Override
                    public void onRecipeClick(int clickedItemIndex){
                        String recipeId = recipeList.get(clickedItemIndex).getId();
                        Log.v(TAG, "Le name: " + recipeId);

                        Intent switchAct = new Intent(SearchResultsActivity.this, RecipeDetailsActivity.class);
                        switchAct.putExtra("recipe_id", recipeId);
                        startActivity(switchAct);
                    }
                });
                mRecyclerView.setAdapter(adapter);
            }
        }
    }

}
