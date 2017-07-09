package com.example.android.foodwhips.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.models.Recipe;
import com.example.android.foodwhips.utilities.ConversionUtils;

import java.io.InputStream;
import java.net.URL;
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

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mFoodImage;
        TextView mRecipeNameText;
        TextView mRatingText;
        TextView mTimeTakenText;
        TextView mCoursesText;
        TextView mCuisinesText;

        public RecipeHolder(View view) {
            super(view);

            //Getting references of the id's
            mFoodImage = (ImageView) view.findViewById(R.id.recipe_image);
            mRecipeNameText = (TextView) view.findViewById(R.id.recipe_name);
            mRatingText = (TextView) view.findViewById(R.id.recipe_rating);
            mTimeTakenText = (TextView) view.findViewById(R.id.recipe_time);
            mCoursesText = (TextView) view.findViewById(R.id.recipe_courses);
            mCuisinesText = (TextView) view.findViewById(R.id.recipe_cuisines);

            view.setOnClickListener(this);
        }

        public void bind(int position) {
            Recipe recipe = recipeList.get(position);

            new FetchImageTask(mFoodImage).execute(recipe.getImg());
            mRecipeNameText.setText(recipe.getRecipeName().toUpperCase());
            mRatingText.setText("Rating: " + ConversionUtils.starRating(recipe.getRating()));
            mTimeTakenText.setText("Time: " + ConversionUtils.secondsToHrsMins(recipe.getTimeInSecs()));

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

        private class FetchImageTask extends AsyncTask<String, Void, Bitmap> {
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
    }
}