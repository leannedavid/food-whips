package com.example.android.foodwhips;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.foodwhips.adapters.RecipeResultsAdapter;
import com.example.android.foodwhips.models.Recipe;
import com.example.android.foodwhips.utilities.NetworkUtils;
import com.example.android.foodwhips.utilities.RecipeJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.foodwhips.R.drawable.back;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(startAdapter);

        bar = (ProgressBar) findViewById(R.id.progressBar);

        mButtonSearch = (Button)findViewById(R.id.search_button);
        mEditView   = (EditText)findViewById(R.id.search_text);
        mTab1 = (TextView)findViewById(R.id.popular_tab);
        mTab2 = (TextView)findViewById(R.id.new_tab);
        mTab3 = (TextView)findViewById(R.id.favorites_tab);

        mButtonSearch.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        search_query = mEditView.getText().toString();
                        foodsUrl = NetworkUtils.buildUrl(search_query);

                        loadFoodData();
                    }
                });
    }

    private void loadFoodData() {
        new FetchFoodTask().execute();
    }

    private class FetchFoodTask extends AsyncTask<String, Void, ArrayList<Recipe>> {
        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Recipe> doInBackground(String... params) {
            ArrayList<Recipe> recipeList = null;

            try {
                String jsonRecipeDataResponse = NetworkUtils.getResponseFromHttpUrl(foodsUrl);
                recipeList = RecipeJsonUtils.parseJSON(jsonRecipeDataResponse);

            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }
            return recipeList;

        }

        @Override
        protected void onPostExecute(final ArrayList<Recipe> recipeList) {
            super.onPostExecute(recipeList);

            bar.setVisibility(View.GONE);

            if (recipeList != null) {
                RecipeResultsAdapter adapter = new RecipeResultsAdapter(recipeList, new RecipeResultsAdapter.RecipeClickListener(){
                    @Override
                    public void onRecipeClick(int clickedItemIndex){
                        String name = recipeList.get(clickedItemIndex).getRecipeName();
                    }
                });
                recyclerView.setAdapter(adapter);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh) {
            loadFoodData();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if(mTabInt1){
        }else{
            mTab2.setBackgroundResource(R.drawable.press);
            mTab1.setBackgroundResource(R.drawable.back);
            mTab3.setBackgroundResource(R.drawable.back);
        }
    }

    public void onClickTab3(View v)
    {
        if(mTabInt1){
        }else{
            mTab3.setBackgroundResource(R.drawable.press);
            mTab1.setBackgroundResource(R.drawable.back);
            mTab2.setBackgroundResource(R.drawable.back);
        }
    }

}