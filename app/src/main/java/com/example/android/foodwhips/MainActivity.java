package com.example.android.foodwhips;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.foodwhips.activities.RecipeDetailsActivity;
import com.example.android.foodwhips.adapters.RecipeResultsAdapter;
import com.example.android.foodwhips.models.SearchRecipe;
import com.example.android.foodwhips.utilities.NetworkUtils;
import com.example.android.foodwhips.utilities.SearchRecipeJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mEditView;
    private TextView mTab1;
    private TextView mTab2;
    private TextView mTab3;
    private boolean mTabInt1 = false;
    private boolean mTabInt2 = false;
    private boolean mTabInt3 = false;
    private Button mButtonSearch;
    private URL foodsUrl;
    private String search_query;

    private RecyclerView recyclerView;
    private RecipeResultsAdapter startAdapter;
    static final String TAG = "mainactivity";

    private ProgressBar bar;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup toolbar on the activity
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup navigation drawer in activity
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(startAdapter);

        //Progress Bar
        bar = (ProgressBar) findViewById(R.id.progressBar);

        //Button setup

        mButtonSearch = (Button)findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String[] included = {"cognac", "nuts"};
                        String[] excluded = {"orange"};

                        search_query = mEditView.getText().toString();
                        foodsUrl = NetworkUtils.buildUrl(search_query, 1);
                       // foodsUrl = NetworkUtils.buildFilteredUrl(search_query, 1, included, excluded);
                        loadFoodData();
                    }
                });

        //EditText setup
        mEditView   = (EditText)findViewById(R.id.search_text);

        //Tab setup
        mTab1 = (TextView)findViewById(R.id.popular_tab);
        mTab2 = (TextView)findViewById(R.id.new_tab);
        mTab3 = (TextView)findViewById(R.id.favorites_tab);
    }

    private void loadFoodData() {
        new FetchFoodTask().execute();
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

                        Intent switchAct = new Intent(MainActivity.this, RecipeDetailsActivity.class);
                        switchAct.putExtra("recipe_id", recipeId);
                        startActivity(switchAct);
                    }
                });
                recyclerView.setAdapter(adapter);

            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_ellipsis_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.information:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickTab1(View v)
    {
        if(mTabInt1){
        }else{
            mTab1.setBackgroundResource(R.drawable.press);
            mTab2.setBackgroundResource(R.drawable.back);
            mTab3.setBackgroundResource(R.drawable.back);
        }
    }

    public void onClickTab2(View v)
    {
        if(mTabInt2){
        }else{
            mTab2.setBackgroundResource(R.drawable.press);
            mTab1.setBackgroundResource(R.drawable.back);
            mTab3.setBackgroundResource(R.drawable.back);
        }
    }

    public void onClickTab3(View v)
    {
        if(mTabInt3){
        }else{
            mTab3.setBackgroundResource(R.drawable.press);
            mTab1.setBackgroundResource(R.drawable.back);
            mTab2.setBackgroundResource(R.drawable.back);
        }
    }

}