package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.adapters.FavoriteRecipesAdapter;
import com.example.android.foodwhips.database.Contract;
import com.example.android.foodwhips.database.DBHelper;
import com.example.android.foodwhips.database.DatabaseUtils;

public class FavoritesActivity extends BaseActivity
    implements FavoriteRecipesAdapter.RecipeClickListener{
    private RecyclerView recyclerView;
    private SQLiteDatabase db;
    private DBHelper helper;
    private FavoriteRecipesAdapter adapter;
    private Cursor cursor;

    static final String TAG = "FavoritesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart(){
        super.onStart();
        helper = new DBHelper(this);
        db = helper.getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        adapter = new FavoriteRecipesAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        db.close();
        cursor.close();
    }

    @Override
    public void onRecipeClick(Cursor cursor, int clickedItem){
        //moves the cursor to the clicked item
        cursor.moveToPosition(clickedItem);

        String recipeId = cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_RECIPE_ID));
        Log.d(TAG, String.format("GETTING THE RECIPE ID: %s", recipeId));

        Intent switchAct = new Intent(FavoritesActivity.this, RecipeDetailsActivity.class);
        switchAct.putExtra("recipe_id", recipeId);
        startActivity(switchAct);
    }

}
