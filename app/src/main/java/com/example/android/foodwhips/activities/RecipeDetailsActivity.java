package com.example.android.foodwhips.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.adapters.FragmentRecipeAdapter;
import com.example.android.foodwhips.fragments.GeneralInfo;
import com.example.android.foodwhips.fragments.IngredientsInfo;
import com.example.android.foodwhips.models.GetRecipe;
import com.example.android.foodwhips.models.SearchRecipe;
import com.example.android.foodwhips.utilities.ConversionUtils;
import com.example.android.foodwhips.utilities.GetRecipeJsonUtils;
import com.example.android.foodwhips.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RecipeDetailsActivity extends BaseActivity {
    private ImageView mRecipeImage;
    private TextView mRecipeName;
    private TextView mRecipeRate;
    private TextView mTimeTaken;
    private TextView mRecipeServings;
    private TextView mSourceName;
    private TextView mSourceUrl;

    private TextView mIngredientsView;

    private FragmentTabHost mTabHost;
    private DrawerLayout drawerLayout;

    static final String TAG = "recipedetailsactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        Bundle bundle = this.getIntent().getExtras();
        String recipeId = bundle.getString("recipe_id");
        Log.v(TAG, "check if this is sending");
        Log.v(TAG, "ID IS " + recipeId);
        //bundle.putString("recipe_id", recipeId);
        //IngredientsInfo ingInfo = new IngredientsInfo();
       // ingInfo.setArguments(bundle);


        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);


        mTabHost.addTab(mTabHost.newTabSpec("General")
                .setIndicator("General"), GeneralInfo.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("Ingredients")
                .setIndicator("Ingredients"), IngredientsInfo.class, bundle);



//        mRecipeImage = (ImageView) findViewById(R.id.detail_image);
//        mRecipeName = (TextView) findViewById(R.id.detail_name);
//        mRecipeRate = (TextView) findViewById(R.id.detail_rating);
//        mTimeTaken = (TextView) findViewById(R.id.detail_time_taken);
//        mRecipeServings = (TextView) findViewById(R.id.detail_servings);
//        mSourceName = (TextView) findViewById(R.id.detail_source_name);
//        mSourceUrl = (TextView) findViewById(R.id.detail_source_link);

//        mIngredientsView = (TextView) findViewById(R.id.detail_ingredients);

//
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        FragmentRecipeAdapter adapter = new FragmentRecipeAdapter(getSupportFragmentManager());
//        pager.setAdapter(adapter);
//
//        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
//        tabs.setupWithViewPager(pager);


//        IngredientsInfo ingredients = new IngredientsInfo();
//        ingredients.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.detail_ingredients, ingredients).commit();
    }

}
