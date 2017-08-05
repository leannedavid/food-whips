package com.example.android.foodwhips.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by Vincent on 6/27/2017.
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String FOOD_BASE_URL = "http://api.yummly.com/v1/api/recipes?";
    private static final String RECIPE_BASE_URL = "http://api.yummly.com/";
    private static final String GET_RECIPE_PATH = "v1/api/recipe/";

    /* Query parameters here */
    private final static String QUERY_PARAM_APP_ID = "_app_id";
    private final static String appid = "ad09ae93";

    private final static String QUERY_PARAM_APPKEY = "_app_key";
    private final static String appkey = "ca4a3cb230d72671a6fc66eeaddc9238";

    private final static String QUERY_PARAM_NAME = "q";
    private final static String QUERY_MAX_RESULTS = "maxResult";

    private final static String QUERY_PARAM_TIME = "maxTotalTimeInSeconds";

    private final static int MAX_RESULT_NUM = 5;

    // Basic / General URL method
    public static URL buildUrl(String search, int type) {
        search = search.replaceAll("\\s+","+");

        Uri builtUri = null;

        if (type == 1) {
            builtUri = Uri.parse(FOOD_BASE_URL).buildUpon().
                    appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                    appendQueryParameter(QUERY_PARAM_APPKEY, appkey).
                    appendQueryParameter(QUERY_PARAM_NAME, search).
                    appendQueryParameter(QUERY_MAX_RESULTS, Integer.toString(MAX_RESULT_NUM)).
                    build();
        }

        else if(type == 2){
            builtUri = Uri.parse(RECIPE_BASE_URL).buildUpon().
                    path(GET_RECIPE_PATH + search).
                    appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                    appendQueryParameter(QUERY_PARAM_APPKEY, appkey).

                    build();
        }

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI: " + url);

        return url;
    }


    // buildURL method for including / excluding ingredients
    public static URL buildIngredientUrl(String search, int type, ArrayList<String> include, ArrayList<String> exclude) {
        search = search.replaceAll("\\s+","+");
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https").authority("api.yummly.com").appendPath("v1").appendPath("api").
                appendPath("recipes").appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                appendQueryParameter(QUERY_PARAM_APPKEY, appkey).
                appendQueryParameter(QUERY_PARAM_NAME, search);

        for(String s: include){
            if(s.length() > 0){
                builder.appendQueryParameter("allowedIngredient[]", s.replaceAll("\\s","%20"));
            }
        }

        for(String s2: exclude) {
            if(s2.length() > 0){
                builder.appendQueryParameter("excludedIngredient[]", s2.replaceAll("\\s","%20"));
            }
        }
        Uri builtUri = null;


        if (type == 1) {
            builtUri = builder.build();
        }

        else if(type == 2){
            builtUri = Uri.parse(RECIPE_BASE_URL).buildUpon().
                    path(GET_RECIPE_PATH + search).
                    appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                    appendQueryParameter(QUERY_PARAM_APPKEY, appkey).

                    build();
        }

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Ingredient Filtered URI: " + url);

        return url;
    }

    // buildURL method for including / excluding cuisines
    public static URL buildCuisineUrl(String search, int type, ArrayList<String> allowed, ArrayList<String> exclude) {
        search = search.replaceAll("\\s+","+");
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https").authority("api.yummly.com").appendPath("v1").appendPath("api").
                appendPath("recipes").appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                appendQueryParameter(QUERY_PARAM_APPKEY, appkey).
                appendQueryParameter(QUERY_PARAM_NAME, search);

        for(String s: allowed){
            if(s.length() > 0){
                builder.appendQueryParameter("allowedCuisine[]", "cuisine^cuisine-" + s.toLowerCase());
            }
        }

        for(String s2: exclude) {
            if(s2.length() > 0){
                builder.appendQueryParameter("excludedCuisine[]", "cuisine^cuisine-" + s2.toLowerCase());
            }
        }
        Uri builtUri = null;


        if (type == 1) {
            builtUri = builder.build();
        }

        else if(type == 2){
            builtUri = Uri.parse(RECIPE_BASE_URL).buildUpon().
                    path(GET_RECIPE_PATH + search).
                    appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                    appendQueryParameter(QUERY_PARAM_APPKEY, appkey).

                    build();
        }

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Cuisine Filtered URI: " + url);

        return url;
    }

    //A random generated URL
    public static URL randomURLBuilder(String search, String cuisine, String course){
        search = search.replaceAll("\\s+","+");
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("api.yummly.com").appendPath("v1").appendPath("api").
                appendPath("recipes").appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                appendQueryParameter(QUERY_PARAM_APPKEY, appkey).
                appendQueryParameter(QUERY_PARAM_NAME, search);
        builder.appendQueryParameter("allowedCuisine[]", "cuisine^cuisine-" + cuisine.toLowerCase());
        builder.appendQueryParameter("allowedCourse[]", "course^course-" + course.toLowerCase());
        Uri builtUri = builder.build();
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    // buildURL method for all other filters
    public static URL buildNameUrl(String search, int type, String timeInSeconds,
           String allowedCourse, String allowedDiet, String allowedHoliday) {
        search = search.replaceAll("\\s+","+");
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https").authority("api.yummly.com").appendPath("v1").appendPath("api").
                appendPath("recipes").appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                appendQueryParameter(QUERY_PARAM_APPKEY, appkey).
                appendQueryParameter(QUERY_PARAM_NAME, search);

        if(allowedCourse.length() != 0){
            if(allowedCourse != "Choose Course") {
                builder.appendQueryParameter("allowedCourse[]", "course^course-" + allowedCourse.toLowerCase());
            }
        }

        if(allowedDiet.length() != 0){
            if(allowedDiet != "Choose Diet") {
                builder.appendQueryParameter("allowedDiet[]", allowedDiet);
            }
        }

        if(allowedHoliday.length() != 0){
            if(allowedHoliday != "Choose Holiday") {
                builder.appendQueryParameter("allowedHoliday[]", "holiday^holiday-" + allowedHoliday.replaceAll("\\s", "-").toLowerCase());
            }
        }

        if(timeInSeconds.length() != 0){
            if(timeInSeconds == "50+") {
                builder.appendQueryParameter(QUERY_PARAM_TIME, "10000");
            }
            else {
                int timeInteger = Integer.parseInt(timeInSeconds);
                timeInteger = timeInteger * 60;
                builder.appendQueryParameter(QUERY_PARAM_TIME, Integer.toString(timeInteger));
            }
        }

        Uri builtUri = null;


        if (type == 1) {
            builtUri = builder.build();
        }

        else if(type == 2){
            builtUri = Uri.parse(RECIPE_BASE_URL).buildUpon().
                    path(GET_RECIPE_PATH + search).
                    appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                    appendQueryParameter(QUERY_PARAM_APPKEY, appkey).

                    build();
        }

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Name Filtered URI: " + url);

        return url;
    }

    /* This method goes to the API and grabs the JSON string */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
