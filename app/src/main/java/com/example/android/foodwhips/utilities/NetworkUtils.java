package com.example.android.foodwhips.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Vincent on 6/27/2017.
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String NEWS_BASE_URL = "http://api.yummly.com/v1/api/recipes?";

    /* Query parameters here */
    private final static String QUERY_PARAM_APP_ID = "_app_id";
    private final static String appid = "";

    private final static String QUERY_PARAM_APPKEY = "_app_key";
    private final static String appkey = "";

    /* Need to add something to accept third / multiple parameters */
    private final static String QUERY_PARAM_NAME = "q";
   // private final static String param_query = "";

    public static URL buildUrl(String search) {
        search.replaceAll("\\s+","+");

        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon().
                appendQueryParameter(QUERY_PARAM_APP_ID, appid).
                appendQueryParameter(QUERY_PARAM_APPKEY, appkey).
                appendQueryParameter(QUERY_PARAM_NAME, search).
                build();

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
