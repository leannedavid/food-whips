package com.example.android.foodwhips.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.android.foodwhips.database.Contract.FOODWHIPS_TABLE.*;

/* Holds all the queries needed for the RecipeDetailsActivity */
public class DatabaseUtils {
    /* Return all the items in the database */
    public static Cursor getAll(SQLiteDatabase db){
        Cursor cursor = db.query(
                TABLE_NAME, //table
                null, //columns
                null, //selection
                null, //selectionArgs
                null, //groupBy
                null, //having
                Contract.FOODWHIPS_TABLE._ID //orderBy
        );
        return cursor;
    }

    /* Return the query by the id specified. */
    public static Cursor returnById(SQLiteDatabase db, String recipeId){
        String selection = Contract.FOODWHIPS_TABLE.COLUMN_NAME_RECIPE_ID + "=?";
        String[] selectionArgs = { recipeId };

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                Contract.FOODWHIPS_TABLE._ID
        );
        return cursor;
    }

    /* Return the query that have been marked as favorites */
    public static Cursor returnFavorites(SQLiteDatabase db, int fave){
        String selection = Contract.FOODWHIPS_TABLE.COLUMN_NAME_FAVORITE + "=?";
        String[] selectionArgs = { Integer.toString(fave) };

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                Contract.FOODWHIPS_TABLE._ID
        );
        return cursor;
    }

    /* Responsible for adding a specific recipe into the database */
    public static long addRecipeToDatabase(SQLiteDatabase db, String recipeId, String title,
                                     String sourceName, String sourceUrl, String imgUrl, String rating,
                                     String timeTaken, String servings, String courses,
                                     String cuisines, String flavors, String ingredients,
                                     int favorite, String photo){

        ContentValues cv = new ContentValues();

        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_RECIPE_ID, recipeId);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_TITLE, title);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_SOURCE_NAME, sourceName);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_SOURCE_URL, sourceUrl);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_IMG_URL, imgUrl);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_RATING, rating);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_TIME, timeTaken);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_SERVINGS, servings);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_COURSES, courses);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_CUISINES, cuisines);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_FLAVORS, flavors);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_INGREDIENTS, ingredients);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_FAVORITE, favorite);
        cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_PHOTO, photo);

        return db.insert(Contract.FOODWHIPS_TABLE.TABLE_NAME, null, cv);
    }

    /* Responsible for removing a specific recipe from the database */
    public static boolean updateRecipeFaveStatus(SQLiteDatabase db, String recipeId, int fave){
        ContentValues cv = new ContentValues();

        if(fave == 0){
            cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_FAVORITE, 0);
        }
        else if(fave == 1){
            cv.put(Contract.FOODWHIPS_TABLE.COLUMN_NAME_FAVORITE, 1);
        }

        return db.update(Contract.FOODWHIPS_TABLE.TABLE_NAME, cv,
                Contract.FOODWHIPS_TABLE.COLUMN_NAME_RECIPE_ID + " = '" + recipeId + "'",
                null) > 0;
    }
}
