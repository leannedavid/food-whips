package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.adapters.OnSwipeTouchListener;
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

    private TextView mTotalCount;

    private URL searchUrl;
    private int search_quantity;
    private TextView search_header;
    private LinearLayout recyclerLayout;
    private int search_start;
    private int search_page;
    private RecipeResultsAdapter adapter;
    private ArrayList<SearchRecipe> displayList;
    private ArrayList<SearchRecipe> recipeList;

    private int count;

    private static final String TAG = "SearchResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        search_header = (TextView) findViewById(R.id.search_header);
        search_quantity = 10;
        search_start = 0;
        search_page = 1;
        recyclerLayout = (LinearLayout) findViewById(R.id.recycler_layout);

        Bundle bundle = this.getIntent().getExtras();
        final String searchQuery = bundle.getString("searchQuery");
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

        // Creates new URL from existing URL
        try {
            searchUrl = new URL(foodsUrl.toString() + "&maxResult=30" + "&start=0");
        } catch (MalformedURLException e) {
            Log.v(TAG, "ERROR CREATING NEW URL " + e.getMessage());
        }

        search_header.setOnTouchListener(new OnSwipeTouchListener(SearchResultsActivity.this) {
            public void onSwipeRight() {
                if(count != 0) {
                search_quantity -= 10;
                search_start -= 10;
                search_page -= 1;
                if (search_quantity < 10) {
                    search_quantity = 30;
                    search_start = 20;
                    search_page = 3;
                }
                displayList.clear();

                for(int i = search_start; i < search_quantity; i++){
                    displayList.add(recipeList.get(i));
                }

                String hereasshole = search_page + "/3";
                search_header.setVisibility(View.VISIBLE);
                search_header.setText(hereasshole);
                adapter.notifyDataSetChanged();
                }
            }
            public void onSwipeLeft() {
                if(count != 0) {
                search_quantity += 10;
                search_start += 10;
                search_page += 1;
                if (search_quantity > 30) {
                    search_quantity = 10;
                    search_start = 0;
                    search_page = 1;
                }

                displayList.clear();

                for(int i = search_start; i < search_quantity; i++){
                    displayList.add(recipeList.get(i));
                }

                String hereasshole = search_page + "/3";
                search_header.setVisibility(View.VISIBLE);
                search_header.setText(hereasshole);
                adapter.notifyDataSetChanged();
                }
            }

        });

        //Progress Bar
        bar = (ProgressBar) findViewById(R.id.progressBar);

        //RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(startAdapter);

        mTotalCount = (TextView) findViewById(R.id.maxResults);
       // mTotalCount.setText("INITIALIZING FIRST");

        new FetchFoodTask().execute();

        Log.v(TAG, "HITTING THE SEARCHRESULTS ACTIVITY WITH QUERY: " + searchQuery);
        Log.v(TAG, "WHAT IS FOODS URL?: " + foodsUrl.toString());
    }

    private class FetchFoodTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            recipeList = new ArrayList<>();
            displayList = new ArrayList<>();
            try {
                String jsonRecipeDataResponse = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                recipeList = SearchRecipeJsonUtils.parseJSON(jsonRecipeDataResponse);

                count = recipeList.size();
                Log.v(TAG, "TOTAL RECIPES FOUND: " + count);
                if(count != 0) {
                    for(int i = search_start; i < search_quantity; i++) {
                        displayList.add(recipeList.get(i));
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String useless) {
            super.onPostExecute(useless);

            bar.setVisibility(View.GONE);

            if (displayList != null) {
                adapter = new RecipeResultsAdapter(displayList, new RecipeResultsAdapter.RecipeClickListener(){
                    @Override
                    public void onRecipeClick(int clickedItemIndex){
                        String recipeId = displayList.get(clickedItemIndex).getId();
                        Log.v(TAG, "Le name: " + recipeId);

                        Intent switchAct = new Intent(SearchResultsActivity.this, RecipeDetailsActivity.class);
                        switchAct.putExtra("recipe_id", recipeId);
                        startActivity(switchAct);
                    }
                });

                if(count != 0) {
                    search_header.setVisibility(View.VISIBLE);
                    search_header.setText("1/3");
                } else {
                    search_header.setVisibility(View.VISIBLE);
                    search_header.setText("No Results Found");
                }

                mRecyclerView.setAdapter(adapter);
            }
        }
    }
}





