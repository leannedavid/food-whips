package com.example.android.foodwhips;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.foodwhips.activities.BaseActivity;
import com.example.android.foodwhips.activities.RecipeDetailsActivity;
import com.example.android.foodwhips.activities.SearchResultsActivity;
import com.example.android.foodwhips.adapters.HomeSwipeAdapter;
import com.example.android.foodwhips.database.DBHelper;
import com.example.android.foodwhips.models.GetRecipe;
import com.example.android.foodwhips.utilities.ConversionUtils;
import com.example.android.foodwhips.utilities.GetRecipeJsonUtils;
import com.example.android.foodwhips.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity{
    private String search_query;
    private EditText mEditView;
    private Button mButtonSearch;
    private DBHelper helper;
    private ViewPager viewPager;
    private HomeSwipeAdapter swipeAdapter;

    private Context ctx;

    private String[] imagesFromURL;
    private String[] recipeIds;
    private int currentSlide = 0;
    static final String TAG = "mainactivity";

    private ImageView mImageView;
    private TextView mRecipeNameText;
    private TextView mRatingText;
    private TextView mTimeTakenText;
    private TextView mCoursesText;
    private TextView mCuisinesText;

    private View includedSearchLayout;
    private Timer swipeTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

       // placeholder only
//        imagesFromURL = new String[]{
//          "http://www.tivix.com/uploads/blog_pics/Android-logo.png",
//                "http://www.tivix.com/uploads/blog_pics/Android-logo.png",
//                "http://www.tivix.com/uploads/blog_pics/Android-logo.png"
//        };

        recipeIds = new String[]{
                "Funeral-Potatoes-2119274",
                "Pizza-quesadillas-_aka-pizzadillas_-351493",
                "Grilled-Chicken-Teriyaki-With-Udon-Noodle-Salad-636811",
                "Pop_s-Asian-American-Grilling-Sauce-607592"
        };

        new FetchCarouselView().execute(recipeIds);
        ArrayList<GetRecipe> starter = new ArrayList<>();

        //EditText setup
        mEditView = (EditText)findViewById(R.id.search_text);

        //Get the View Pager
        viewPager = (ViewPager) findViewById(R.id.image_carousel);
        swipeAdapter = new HomeSwipeAdapter(this, imagesFromURL, starter, recipeIds);
        viewPager.setAdapter(swipeAdapter);

        //Automatically slides the View Pager
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentSlide == imagesFromURL.length - 1){ currentSlide = 0; }
                viewPager.setCurrentItem(currentSlide++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 4000);

        //Button setup
        mButtonSearch = (Button)findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                search_query = mEditView.getText().toString();
                Intent switchAct = new Intent(MainActivity.this, SearchResultsActivity.class);
                switchAct.putExtra("searchQuery", search_query);
                startActivity(switchAct);
            }
        });

        includedSearchLayout = findViewById(R.id.recommendedRecipes);

        mImageView = (ImageView) includedSearchLayout.findViewById(R.id.recipe_image);
        mRecipeNameText = (TextView) includedSearchLayout.findViewById(R.id.recipe_name);
        mRatingText = (TextView) includedSearchLayout.findViewById(R.id.recipe_rating);
        mTimeTakenText = (TextView) includedSearchLayout.findViewById(R.id.recipe_time);
        mCoursesText = (TextView) includedSearchLayout.findViewById(R.id.recipe_courses);
        mCuisinesText = (TextView) includedSearchLayout.findViewById(R.id.recipe_cuisines);
    }

    @Override
    protected void onStart(){
        super.onStart();
        helper = new DBHelper(this);
        helper.getWritableDatabase();
    }

    @Override
    protected void onPause(){
        super.onPause();
        swipeTimer.cancel();
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState){
//        super.onPostCreate(savedInstanceState);
//    }

    public class FetchCarouselView extends AsyncTask<String, Void, ArrayList<GetRecipe>> {
        @Override
        protected ArrayList<GetRecipe> doInBackground(String... recipeId) {
            ArrayList<GetRecipe> specificRecipe = new ArrayList<>();

            for (int i = 0; i < recipeId.length; i++) {
                URL recipeUrl = NetworkUtils.buildUrl(recipeId[i], 2);
                Log.v(TAG, "THIS IS THE " + recipeId[i] + " URL: " + recipeUrl.toString());

                try {
                    String jsonRecipeDataResponse = NetworkUtils.getResponseFromHttpUrl(recipeUrl);
                    GetRecipe recipePull = GetRecipeJsonUtils.parseJSON(jsonRecipeDataResponse);
                    specificRecipe.add(recipePull);
                }catch(IOException e){
                    e.printStackTrace();
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
            return specificRecipe;
        }

        @Override
        protected void onPostExecute(final ArrayList<GetRecipe> data){
            super.onPostExecute(data);
            imagesFromURL = new String[data.size()];

            if(data != null) {
                for(int i = 0; i < data.size(); i++){
                    imagesFromURL[i] =  data.get(i).getImgUrl();
                }

                HomeSwipeAdapter swipeAdapter = new HomeSwipeAdapter(ctx, imagesFromURL, data, recipeIds);
                viewPager.setAdapter(swipeAdapter);

                new ConversionUtils.FetchImageTask(mImageView).execute(data.get(3).getImgUrl());

                mRecipeNameText.setVisibility(View.VISIBLE);
                mRecipeNameText.setText(data.get(3).getRecipeName().toUpperCase());

                mRatingText.setVisibility(View.VISIBLE);
                mRatingText.setText("Rating: " + ConversionUtils.starRating(data.get(3).getRating()));

                mTimeTakenText.setVisibility(View.VISIBLE);
                mTimeTakenText.setText("Time Taken: " + data.get(3).getTotalTime());

                mCoursesText.setVisibility(View.VISIBLE);
                mCoursesText.setText("Courses: " + data.get(3).printCourses());

                mCuisinesText.setVisibility(View.VISIBLE);
                mCuisinesText.setText("Cuisines: " + data.get(3).printCuisines());


                includedSearchLayout.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view) {
                        Intent switchAct = new Intent(MainActivity.this, RecipeDetailsActivity.class);
                        switchAct.putExtra("recipe_id", recipeIds[3]);
                        startActivity(switchAct);
                    }
                });
            }
        }
    }
}