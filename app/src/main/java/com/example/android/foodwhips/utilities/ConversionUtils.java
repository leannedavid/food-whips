package com.example.android.foodwhips.utilities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.android.foodwhips.MainActivity;
import com.example.android.foodwhips.activities.RecipeDetailsActivity;
import com.example.android.foodwhips.adapters.RecipeResultsAdapter;
import com.example.android.foodwhips.models.GetRecipe;
import com.example.android.foodwhips.models.SearchRecipe;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.ArrayList;

public class ConversionUtils {
    static final String TAG = "conversionutils";

    public static String secondsToHrsMins(String timeInSecs){
        String finalTime = "";
        int time = Integer.parseInt(timeInSecs);
        time = Math.round(time/60);
        finalTime = Integer.toString(time) + " minutes";

        if(time > 60){
            int hr = time / 60;
            int min = (time % 60);

            finalTime = Integer.toString(hr) +
                    (hr > 1 ? " hours" : " hour");

            if(min >= 1) {
                 finalTime += " and " + Integer.toString(min) +
                 (min > 1 ? " minutes" : " minute");
            }
        }
        return finalTime;
    }

    public static String starRating(String rating){
        String stars = "";
        for(int i = 0; i < Integer.parseInt(rating); i++){
            stars += "★";
        }
        return stars;
    }

    public static String decimalsToPercentage(String decimal){
        String percentage = "";
        double decimalValue = Math.round(Double.parseDouble(decimal) * 100);

        percentage = Integer.toString((int) decimalValue) + "%";

        return percentage;
    }
    public static class FetchImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public FetchImageTask(ImageView mImage) {
            this.image = mImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls){
            String img_url = urls[0];
            Bitmap icon = null;
            try{
                InputStream in = new URL(img_url).openStream();
                icon = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                e.printStackTrace();
            }
            return icon;
        }

        @Override
        protected void onPostExecute(Bitmap img){
            image.setImageBitmap(img);
        }
    }

    public static GetRecipe FetchRecipeTask(String recipeId){
        //Bundle bundle = getIntent().getExtras();

       // String recipeId = stuff.getString("recipe_id");
        URL recipeUrl = NetworkUtils.buildUrl(recipeId, 2);
        GetRecipe specificRecipe = null;

        try {
            String JsonRecipeDataResponse = NetworkUtils.getResponseFromHttpUrl(recipeUrl);
            specificRecipe = GetRecipeJsonUtils.parseJSON(JsonRecipeDataResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "THE RECIPE ID: " + recipeId);
        return specificRecipe;
    }

}
