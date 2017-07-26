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
    private final static String appid = "";

    private final static String QUERY_PARAM_APPKEY = "_app_key";
    private final static String appkey = "";

    private final static String QUERY_PARAM_NAME = "q";
    // private final static String param_query = "";

    private final static String QUERY_PARAM_INCLUDE = "allowedIngredient[]";
    private final static String QUERY_PARAM_EXCLUDE = "excludedIngredient[]";

    public static URL buildUrl(String search, int type) {
        search.replaceAll("\\s+","+");

        Uri builtUri = null;

        if (type == 1) {
            builtUri = Uri.parse(FOOD_BASE_URL).buildUpon().
                    appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                    appendQueryParameter(QUERY_PARAM_APPKEY, appkey).
                    appendQueryParameter(QUERY_PARAM_NAME, search).
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


    // New buildURL for each ingredients filter
//    public static URL buildFilteredUrl(String search, int type, String [] include, String [] exclude) {
      public static URL buildFilteredUrl(String search, int type, ArrayList<String> include, ArrayList<String> exclude) {
        search.replaceAll("\\s+","+");
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https").authority("api.yummly.com").appendPath("v1").appendPath("api").
                appendPath("recipes").appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                appendQueryParameter(QUERY_PARAM_APPKEY, appkey);

        for(String s: include){
            builder.appendQueryParameter("allowedIngredient[]", s);
        }

        for(String s2: exclude) {
            builder.appendQueryParameter("excludedIngredient[]", s2);
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

        Log.v(TAG, "Built Filtered URI: " + url);

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
