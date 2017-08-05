package com.example.android.foodwhips;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.foodwhips.activities.BaseActivity;
import com.example.android.foodwhips.activities.SearchResultsActivity;
import com.example.android.foodwhips.adapters.HomeSwipeAdapter;
import com.example.android.foodwhips.adapters.TabsPagerAdapter;
import com.example.android.foodwhips.database.DBHelper;
import com.example.android.foodwhips.models.GetRecipe;
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
    private FragmentPagerAdapter fragmentPagerAdapter;

    private Context ctx;

    private String[] imagesFromURL;
    private String[] recipeIds;
    private int currentSlide = 0;
    static final String TAG = "mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

       // placeholder only
        imagesFromURL = new String[]{
          "http://www.tivix.com/uploads/blog_pics/Android-logo.png",
                "http://www.tivix.com/uploads/blog_pics/Android-logo.png",
                "http://www.tivix.com/uploads/blog_pics/Android-logo.png"
        };

        recipeIds = new String[]{
                "French-Lentil-Soup-1096886",
                "Magic-Custard-Cake-2113595",
                "Scalloped-Potatoes-2057149"
        };

//        new FetchCarouselView().execute(
//                "French-Lentil-Soup-1096886",
//                "Magic-Custard-Cake-2113595",
//                "Scalloped-Potatoes-2057149"
//        );

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
                if(currentSlide == imagesFromURL.length){ currentSlide = 0; }
                viewPager.setCurrentItem(currentSlide++, true);
            }
        };

        Timer swipeTimer = new Timer();
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


//        ViewPager vpPager = (ViewPager) findViewById(R.id.mainVP);
//        fragmentPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
//        vpPager.setAdapter(fragmentPagerAdapter);



    }

    @Override
    protected void onStart(){
        super.onStart();
        helper = new DBHelper(this);
        helper.getWritableDatabase();
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
            }
        }
    }
}