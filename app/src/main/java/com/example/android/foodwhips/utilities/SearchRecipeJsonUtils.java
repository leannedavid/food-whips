package com.example.android.foodwhips.utilities;

import com.example.android.foodwhips.models.SearchRecipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchRecipeJsonUtils {
    private static final String RECIPE_MATCHES = "matches";

    private static final String RECIPE_IMG = "imageUrlsBySize";
    private static final String RECIPE_IMG_SIZE_90 = "90";

    private static final String RECIPE_SOURCE = "sourceDisplayName";
    private static final String RECIPE_INGREDIENTS = "ingredients";
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "recipeName";
    private static final String RECIPE_TIME_TAKEN = "totalTimeInSeconds";
    private static final String RECIPE_RATING = "rating";

    private static final String RECIPE_ATTRIBUTES = "attributes";
    private static final String RECIPE_COURSES = "course";
    private static final String RECIPE_CUISINES = "cuisine";

    private static final String RECIPE_TOTAL_RESULTS = "totalMatchCount";


    public static ArrayList<SearchRecipe> parseJSON(String json) throws JSONException{
        ArrayList<SearchRecipe> recipeResults = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray matches = main.getJSONArray(RECIPE_MATCHES);
        String totalResults = main.getString(RECIPE_TOTAL_RESULTS);

        for(int i = 0; i < matches.length(); i++){
            JSONObject match = matches.getJSONObject(i);

            //IMAGE URL
            String img;
            try{
                JSONObject imgObj = match.getJSONObject(RECIPE_IMG);
                img = "";
                if(imgObj != null && imgObj.has(RECIPE_IMG_SIZE_90)) {
                    img = imgObj.getString(RECIPE_IMG_SIZE_90);
                }
            }catch(JSONException e){
                img = "http://lh6.ggpht.com/96qipIieKT3rMwc3cwSh_nsQ4eP2UUXpUbb40hEgAKVLZbkyAn6QCvzBip26D_JRkIQkthSRwdnafSlU-TD_ug=s90-c";
            }


            //RECIPE SOURCE
            String source = match.getString(RECIPE_SOURCE);

            //INGREDIENTS
            JSONArray ingredientArrayList = match.getJSONArray(RECIPE_INGREDIENTS);
            ArrayList<String> ingredients = new ArrayList<>();
            for(int j = 0; j < ingredientArrayList.length(); j++){
                ingredients.add(ingredientArrayList.getString(j));
            }
            String[] ingredientsList = ingredients.toArray(new String[ingredientArrayList.length()]);

            //RECIPE ID
            String id = match.getString(RECIPE_ID);

            //RECIPE NAME
            String name = match.getString(RECIPE_NAME);

            //TIME TAKEN TO MAKE RECIPE
            String timeTaken = "";
            if (!match.isNull(RECIPE_TIME_TAKEN)) {
                timeTaken = match.getString(RECIPE_TIME_TAKEN);
            }

            //RATING OF THE RECIPE
            String rating = match.getString(RECIPE_RATING);

            //JSON ATTRIBUTES OF RECIPE (COURSES & CUISINES)
            JSONObject attributes = match.getJSONObject(RECIPE_ATTRIBUTES);

            //RECIPE COURSES
            ArrayList<String> coursesArrayList = new ArrayList<>();

            if(attributes.has(RECIPE_COURSES)) {
                JSONArray courses = attributes.getJSONArray(RECIPE_COURSES);
                for (int j = 0; j < courses.length(); j++) {
                    coursesArrayList.add(courses.optString(j));
                }
            }
            String[] coursesList = coursesArrayList.toArray(new String[coursesArrayList.size()]);

            //RECIPE CUISINES
            ArrayList<String> cuisineArrayList = new ArrayList<>();

            if(attributes.has(RECIPE_CUISINES)) {
                JSONArray cuisines = attributes.getJSONArray(RECIPE_CUISINES);
                for (int j = 0; j < cuisines.length(); j++) {
                    cuisineArrayList.add(cuisines.optString(j));
                }
            }
            String[] cuisineList = cuisineArrayList.toArray(new String[cuisineArrayList.size()]);

            SearchRecipe recipe = new SearchRecipe(img, source, ingredientsList, id, name, timeTaken, rating,
                    coursesList, cuisineList, totalResults);

            recipeResults.add(recipe);
        }

        return recipeResults;
    }
}
