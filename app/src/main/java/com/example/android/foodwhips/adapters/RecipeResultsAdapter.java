package com.example.android.foodwhips.adapters;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.models.Recipe;

import java.util.ArrayList;

/**
 * Created by li-en on 7/2/17.
 */

public class RecipeResultsAdapter extends RecyclerView.Adapter<RecipeResultsAdapter.RecipeHolder> {
    private ArrayList<Recipe> recipeList;
    RecipeClickListener listener;

    //Constructor of RecipeResults Adapter
    public RecipeResultsAdapter(ArrayList<Recipe> recipeList, RecipeClickListener listener) {
        this.recipeList = recipeList;
        this.listener = listener;
    }

    public interface RecipeClickListener {
        void onRecipeClick(int clickedRecipeIndex);
    }

    //Creates each of the ViewHolders to display on the screen
    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //gets context of current activity on the screen
        Context context = viewGroup.getContext();

        //obtains views from the xml file
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean attachToParentImmediately = false;

        //used to instantiate layout xml file into actual View objects
        View view = inflater.inflate(R.layout.search_results, viewGroup, attachToParentImmediately);

        RecipeHolder holder = new RecipeHolder(view);

        return holder;

    }

    //Displays the data info of an article at specified position given
    @Override
    public void onBindViewHolder(RecipeHolder recipeHolder, int position) {
        recipeHolder.bind(position);
    }

    //returns size of arrayList holding all the recipe matches
    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public String secondstoMins(String timeinSecs){
        int time = Integer.parseInt(timeinSecs);
        time = Math.round(time/60);

        return Integer.toString(time);
    }

    public String starRating(String rating){
        String stars = "";
        for(int i = 0; i < Integer.parseInt(rating); i++){
            stars += "★";
        }

        return stars;
    }


    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mRecipeNameText;
        TextView mRatingText;
        TextView mTimeTakenText;
        TextView mCoursesText;
        TextView mCuisinesText;

        public RecipeHolder(View view) {
            super(view);

            //Getting references of the id's
            mRecipeNameText = (TextView) view.findViewById(R.id.recipe_name);
            mRatingText = (TextView) view.findViewById(R.id.recipe_rating);
            mTimeTakenText = (TextView) view.findViewById(R.id.recipe_time);
            mCoursesText = (TextView) view.findViewById(R.id.recipe_courses);
            mCuisinesText = (TextView) view.findViewById(R.id.recipe_cuisines);

            view.setOnClickListener(this);

        }

        public void bind(int position) {
            Recipe recipe = recipeList.get(position);

            mRecipeNameText.setText(recipe.getRecipeName().toUpperCase());
            mRatingText.setText("Rating: " + starRating(recipe.getRating()));
            mTimeTakenText.setText("Time: " + secondstoMins(recipe.getTimeInSecs()) + " minutes");

            if (recipe.isCoursesEmpty() == false) {
                mCoursesText.setText("Course(s): " + recipe.printCourses());
            }

            if(recipe.isCuisinesEmpty() == false) {
                mCuisinesText.setText("Cuisine(s): " + recipe.printCuisines());
            }
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            listener.onRecipeClick(pos);
        }
    }
}