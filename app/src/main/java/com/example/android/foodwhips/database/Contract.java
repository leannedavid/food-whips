package com.example.android.foodwhips.database;

import android.provider.BaseColumns;

/**
 * Created by li-en on 7/29/17.
 */

public class Contract {
    public static class FOODWHIPS_TABLE implements BaseColumns{
        //name of the table for the database
        public static final String TABLE_NAME = "foodwhips";

        //setting up the columns/attributes
        public static final String COLUMN_NAME_RECIPE_ID = "recipeId";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SOURCE_NAME = "sourceName";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_TIME = "timeTaken";
        public static final String COLUMN_NAME_SERVINGS = "servings";
        public static final String COLUMN_NAME_COURSES = "courses";
        public static final String COLUMN_NAME_CUISINES = "cuisines";
        public static final String COLUMN_NAME_FLAVORS = "flavors";

        public static final String COLUMN_NAME_INGREDIENTS = "ingredients";

        public static final String COLUMN_NAME_FAVORITE = "favorite";
        public static final String COLUMN_NAME_PHOTO = "photo";
    }
}
