package com.example.android.foodwhips.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.database.Contract;
import com.example.android.foodwhips.utilities.ConversionUtils;

public class FavoriteRecipesAdapter extends RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteHolder> {
    private Context context;
    private Cursor cursor;
    private RecipeClickListener listener;
    private String TAG = "FavoriteRecipesAdapter";

    public FavoriteRecipesAdapter(Cursor cursor, RecipeClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface RecipeClickListener {
        void onRecipeClick(Cursor cursor, int clickedRecipeIndex);
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType){
       context = parent.getContext();
       LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.search_results, parent, false);
        FavoriteHolder holder = new FavoriteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class FavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mFoodImage;
        TextView mRecipeNameText;
        TextView mRatingText;
        TextView mTimeTakenText;
        TextView mCoursesText;
        TextView mCuisinesText;

        public FavoriteHolder(View view) {
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
            cursor.moveToPosition(position);

            String rating = ConversionUtils.starRating(cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_RATING)));
            String timeTaken = cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_TIME));
            String courses = cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_COURSES));
            String cuisines = cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_CUISINES));

            new ConversionUtils.FetchImageTask(mFoodImage).execute(cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_IMG_URL)));
            mRecipeNameText.setText(cursor.getString(cursor.getColumnIndex(Contract.FOODWHIPS_TABLE.COLUMN_NAME_TITLE)).toUpperCase());

            if(rating.length() != 0){
                mRatingText.setVisibility(View.VISIBLE);
                mRatingText.setText("Rating: " + rating);
            }

            if(timeTaken.length() > 0 && timeTaken != null) {
                mTimeTakenText.setVisibility(View.VISIBLE);
                mTimeTakenText.setText("Time: " + timeTaken);
            }

            if(courses.length() != 0) {
                mCoursesText.setVisibility(View.VISIBLE);
                mCoursesText.setText("Courses: " + courses);
            }
            if(cuisines.length() != 0) {
                mCuisinesText.setVisibility(View.VISIBLE);
                mCuisinesText.setText("Cuisines: " + cuisines);
            }
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            listener.onRecipeClick(cursor, pos);
        }
    }
}
