package com.example.android.foodwhips.utilities;

import android.util.Log;

import com.example.android.foodwhips.models.GetRecipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private static final String GET_SERVINGS = "numberOfServings";

    private static final String GET_ATTRIBUTES = "attributes";
    private static final String GET_CUISINES = "cuisine";
    private static final String GET_COURSES = "course";

    private static final String GET_FLAVORS = "flavors";
    private static final String GET_PIQUANT = "Piquant";
    private static final String GET_MEATY = "Meaty";
    private static final String GET_BITTER = "Bitter";
    private static final String GET_SWEET = "Sweet";
    private static final String GET_SOUR = "Sour";
    private static final String GET_SALTY = "Salty";

    private static final String GET_RATING = "rating";

    static final String TAG = "GETRECIPEJOSNUTILS";

    public static GetRecipe parseJSON(String json) throws JSONException{
        JSONObject main = new JSONObject(json);

        //GET RECIPE TIME
        String time = "";
        if(!main.isNull(GET_TOTAL_TIME)){
            time = main.getString(GET_TOTAL_TIME);
        }

        //GET RECIPE IMAGE ARRAY
        JSONArray imagesArray = main.getJSONArray(GET_IMAGES);
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
        JSONObject flavors = main.getJSONObject(GET_FLAVORS);
        ArrayList<String> flavorArrayList = new ArrayList<>();

        if(flavors.length() != 0) {
            flavorArrayList.add(flavors.getString(GET_PIQUANT));
            flavorArrayList.add(flavors.getString(GET_MEATY));
            flavorArrayList.add(flavors.getString(GET_BITTER));
            flavorArrayList.add(flavors.getString(GET_SWEET));
            flavorArrayList.add(flavors.getString(GET_SOUR));
            flavorArrayList.add(flavors.getString(GET_SALTY));
        }

        String[] flavorsList = flavorArrayList.toArray(new String[flavorArrayList.size()]);

        String rating = main.getString(GET_RATING);

        GetRecipe recipe = new GetRecipe(time, img, recipeName,
                sourceName, sourceUrl, id, ingredientsList,
                servings, cuisineList, coursesList, flavorsList, rating);
        return recipe;
    }
}