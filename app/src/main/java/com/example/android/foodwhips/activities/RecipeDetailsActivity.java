package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.app.LoaderManager;
import android.content.Loader;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Log;


import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.foodwhips.MainActivity;
import com.example.android.foodwhips.R;
import com.example.android.foodwhips.database.Contract;
import com.example.android.foodwhips.database.DBHelper;
import com.example.android.foodwhips.database.DatabaseUtils;
import com.example.android.foodwhips.fragments.GeneralInfo;
import com.example.android.foodwhips.fragments.IngredientsInfo;
import com.example.android.foodwhips.models.GetRecipe;

import com.example.android.foodwhips.utilities.ConversionUtils;
import com.example.android.foodwhips.utilities.RecipeLoader;


public class RecipeDetailsActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<GetRecipe>{
    private FragmentTabHost mTabHost;
    private ImageView mRecipeImage;
    private TextView mRecipeName;
    private TextView mSourceName;
    private TextView mRecipeRate;

    private ImageButton instructionsButton;
    private ImageButton faveButton;
    private ImageButton picButton;

    private Cursor cursor;
    private DBHelper helper;
    private SQLiteDatabase db;

    private Bundle bundle;
    private boolean check;

    private String recipeId;

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

        check = true;

        mRecipeImage = (ImageView) findViewById(R.id.detail_image);
        mRecipeName = (TextView) findViewById(R.id.detail_name);
        mSourceName = (TextView) findViewById(R.id.detail_source_name);
        mRecipeRate = (TextView) findViewById(R.id.detail_rating);

        instructionsButton = (ImageButton) findViewById(R.id.instructions_button);
        faveButton = (ImageButton) findViewById(R.id.favorite_button);
        picButton = (ImageButton) findViewById(R.id.pic_button);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public void onStart(){
        super.onStart();
        helper = new DBHelper(this);
        db = helper.getReadableDatabase();

        bundle = this.getIntent().getExtras();
        recipeId = bundle.getString("recipe_id");
        cursor = DatabaseUtils.returnById(db, recipeId);

        if (cursor.getCount() != 0) {
            faveButton.setImageResource(R.drawable.ic_fave);
        } else {
            faveButton.setImageResource(R.drawable.ic_unfaved);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        FragmentManager fm = getSupportFragmentManager();
        Log.v(TAG, "GETTING BACK STACK ENTRY COUNT: " + fm.getBackStackEntryCount());
        Log.v(TAG, "GETTING CURRENT TAB INDEX: " + mTabHost.getChildCount());

        if(check){ check = false; }
        else{
            Log.v(TAG, "WENT INSIDE ELSE STATEMENT IN ONRESUME(): ");
//            Fragment frg = getSupportFragmentManager().findFragmentByTag(GENERAL_INFO);
//            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.setAllowOptimization(false);
//            ft.detach(frg);
//            ft.attach(frg);
//            ft.commit();
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
//        FragmentManager fm = getSupportFragmentManager();
//
//        Log.v(TAG, "PRINT NUMBER OF BACKSTACK ENTRIES ON CHILD COUNT: " + mTabHost.getChildCount());
//
//        if(fm.getBackStackEntryCount() == 0){
//           // this.finish();
//            super.onBackPressed();
//        }
//        else{
//            getFragmentManager().popBackStack();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }


    @Override
    public Loader<GetRecipe> onCreateLoader(int id, final Bundle args){
        Bundle bundle = this.getIntent().getExtras();
        String recipeId = bundle.getString("recipe_id");

        Log.v(TAG, "THE VALUE OF INSIDE LOADER IS: " + recipeId);
        return new RecipeLoader(this, recipeId);
    }

    @Override
    public void onLoadFinished(Loader<GetRecipe> loader, final GetRecipe data){
        if(data != null) {
            Bundle bundle = new Bundle();
            bundle.putString(INGREDIENTS_VALUE, data.printIngredients());

            Log.v(TAG, "TOTAL TIME IN THE LOADER FOR THE RECIPE IS: " + data.getTotalTime());
            Log.v(TAG, "LENGTH OF TIME data.getTotalTime() IS " + data.getTotalTime().length());
            Log.v(TAG, "COURSES IS: " + data.getCourses().length + " AND CUISINES IS: " + data.getCuisines().length);

            if (data.getTotalTime().length() != 0 && data.getTotalTime() != null ) {
                bundle.putString(RECIPE_TIME_VALUE, data.getTotalTime());
            }
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

            faveButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    cursor = DatabaseUtils.returnById(db, data.getId());
                    Log.v(TAG, "PRINTING THE AMOUNT OF ROWS CURSOR HAS: " + cursor.getCount());

                    //Must be in the database already for it to mark the column to '0'
                    if(cursor != null && (cursor.getCount() > 0)){
                        //Two cases when trying to mark a stored recipe as a favorite or un-favorite
                        if(cursor.moveToFirst()) {
                            Log.v(TAG, "WENT INTO HERE SINCE CURSOR IS NOT NULL" + cursor.getCount());
                            //Recipe is already in the database, so just update the favorite column to '1'
                            String faveStatus = cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_FAVORITE));
                            Log.v(TAG, "PRINTING THE FAVESTATUS CURRENTLY: " + faveStatus);

                            if (faveStatus.equals("0")) {
                                Log.v(TAG, "WENT IN HERE CUZ SETTING UNFAVE TO FAVE");
                                DatabaseUtils.updateRecipeFaveStatus(db, data.getId(), 1);
                                faveButton.setImageResource(R.drawable.ic_fave);

                            } else if (faveStatus.equals("1")) {
                                Log.v(TAG, "WENT IN HERE CUZ SETTING FAVE TO UNFAVE");
                                DatabaseUtils.updateRecipeFaveStatus(db, data.getId(), 0);
                                faveButton.setImageResource(R.drawable.ic_unfaved);
                            }
                        }
                    } else{
                        Log.v(TAG, "WENT INTO HERE SINCE CURSOR IS NULL.");
                        //Recipe is not in the database, so add it AND mark favorite column as '1'
                        DatabaseUtils.addRecipeToDatabase(db, data.getId(), data.getRecipeName(), data.getSourceName(),
                                data.getSourceRecipeUrl(), data.getImgUrl(), data.getRating(), data.getTotalTime(),
                                data.getServings(), data.printCourses(), data.printCuisines(),
                                data.printFlavors(), data.printIngredients(), 1, null);
                        faveButton.setImageResource(R.drawable.ic_fave);
                    }
                }
            });

            picButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = DatabaseUtils.returnById(db, data.getId());
                    String photo_uri = "nothing";
                    if(cursor != null && (cursor.getCount() > 0)) {
                        //Two cases when trying to mark a stored recipe as a favorite or un-favorite
                        if (cursor.moveToFirst()) {
                            photo_uri = cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_PHOTO));
                        }
                    }
                    FragmentManager fm = getSupportFragmentManager();
                    Camera_Fragment cm = Camera_Fragment.newInstance(recipeId, photo_uri);
                    cm.show(fm, "cameraFragment");
                }
            });

            mTabHost.clearAllTabs();

            mTabHost.addTab(mTabHost.newTabSpec(GENERAL_INFO)
                    .setIndicator(GENERAL_INFO), GeneralInfo.class, bundle);

            mTabHost.addTab(mTabHost.newTabSpec(INGREDIENT_INFO)
                    .setIndicator(INGREDIENT_INFO), IngredientsInfo.class, bundle);
        }
    }

    @Override
    public void onLoaderReset(Loader<GetRecipe> loader){}
}