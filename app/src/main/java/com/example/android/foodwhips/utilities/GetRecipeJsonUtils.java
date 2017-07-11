package com.example.android.foodwhips.utilities;

import com.example.android.foodwhips.models.GetRecipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by li-en on 7/10/17.
 */

public class GetRecipeJsonUtils {
    private static final String GET_TOTAL_TIME = "totalTime";

    private static final String GET_IMAGES = "images";
    private static final String GET_LARGE_IMAGE_URL = "hostedLargeUrl";

    private static final String GET_NAME = "name";

    private static final String GET_SOURCES = "source";
    private static final String GET_SOURCE_NAME = "sourceDisplayName";
    private static final String GET_SOURCE_URL = "sourceRecipeUrl";

    private static final String GET_ID = "id";
    private static final String GET_INGREDIENTS_LIST = "ingredientLines";
    private static final String GET_SERVINGS = "yield";

    private static final String GET_ATTRIBUTES = "attributes";
    private static final String GET_CUISINES = "cuisine";
    private static final String GET_COURSES = "course";

    private static final String GET_RATING = "rating";
    //do flavors later

    public static GetRecipe parseJSON(String json) throws JSONException{
        JSONObject main = new JSONObject(json);

        //GET RECIPE TIME
        String time = main.getString(GET_TOTAL_TIME);

        //GET RECIPE IMAGE ARRAY
        JSONArray imagesArray = new JSONArray(GET_IMAGES);
        String img = "";
        for (int i = 0; i < imagesArray.length(); i++){
            JSONObject imageObj = imagesArray.getJSONObject(i);
            //GET LARGE IMAGE URL
            img = imageObj.getString(GET_LARGE_IMAGE_URL);
        }

        //GET RECIPE NAME
        String recipeName = main.getString(GET_NAME);

        //GET RECIPE SOURCES
        JSONObject sourceObj = main.getJSONObject(GET_SOURCES);
        String sourceName = "";
        String sourceUrl = "";

        for(int i = 0; i < sourceObj.length(); i++){
            //GET SOURCE NAME
            sourceName = sourceObj.getString(GET_SOURCE_NAME);

            //GET SOURCE URL
            sourceUrl = sourceObj.getString(GET_SOURCE_URL);
        }

        //GET RECIPE ID
        String id = main.getString(GET_ID);

        // GET INGREDIENTS
        JSONArray ingredientArrayList = main.getJSONArray(GET_INGREDIENTS_LIST);
        ArrayList<String> ingredients = new ArrayList<>();
        for(int j = 0; j < ingredientArrayList.length(); j++){
            ingredients.add(ingredientArrayList.getString(j));
        }
        String[] ingredientsList = ingredients.toArray(new String[ingredientArrayList.length()]);

        //GET NUMBER OF SERVINGS
        String servings = main.getString(GET_SERVINGS);

        //GET RECIPE ATTRIBUTES
        JSONObject attributes = main.getJSONObject(GET_ATTRIBUTES);
        String cuisines = "";
        String courses = "";

        //RECIPE COURSES
        ArrayList<String> coursesArrayList = new ArrayList<>();

        if(attributes.has(GET_COURSES)) {
            JSONArray coursesArray = attributes.getJSONArray(GET_COURSES);
            for (int j = 0; j < coursesArray.length(); j++) {
                coursesArrayList.add(coursesArray.getString(j));
            }
        }
        String[] coursesList = coursesArrayList.toArray(new String[coursesArrayList.size()]);

        //RECIPE CUISINES
        ArrayList<String> cuisineArrayList = new ArrayList<>();

        if(attributes.has(GET_CUISINES)) {
            JSONArray cuisinesArray = attributes.getJSONArray(GET_CUISINES);
            for (int j = 0; j < cuisinesArray.length(); j++) {
                cuisineArrayList.add(cuisinesArray.getString(j));
            }
        }
        String[] cuisineList = cuisineArrayList.toArray(new String[cuisineArrayList.size()]);

        //GET RECIPE FLAVORS
        String flavors[] = null;

        String rating = main.getString(GET_RATING);

        GetRecipe recipe = new GetRecipe(time, img, recipeName,
                sourceName, sourceUrl, id, ingredientsList,
                servings, cuisineList, coursesList, flavors, rating);
        return recipe;
    }
}