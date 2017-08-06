package com.example.android.foodwhips.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "foods.db";
    private static final String TAG = "DATABASE HELPER";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String queryString = "CREATE TABLE " + Contract.FOODWHIPS_TABLE.TABLE_NAME + " (" +
                Contract.FOODWHIPS_TABLE._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_RECIPE_ID + " TEXT NOT NULL UNIQUE, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_SOURCE_NAME + " TEXT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_SOURCE_URL + " TEXT NOT NULL, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_IMG_URL + " TEXT NOT NULL, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_RATING + " TEXT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_TIME + " TEXT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_SERVINGS + " TEXT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_COURSES + " TEXT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_CUISINES + " TEXT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_FLAVORS + " TEXT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_INGREDIENTS + " TEXT, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_FAVORITE + " INTEGER DEFAULT 0, " +
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_PHOTO + " TEXT" + ");";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //db.execSQL("DROP TABLE " + Contract.TABLE_TODO.TABLE_NAME + " if exists;");
    }
}
