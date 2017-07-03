//package com.example.android.foodwhips.adapters;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.android.foodwhips.R;
//import com.example.android.foodwhips.model.Recipe;
//
//import java.util.ArrayList;
//
///**
// * Created by li-en on 7/2/17.
// */
//
//public class RecipeResultsAdapter extends RecyclerView.Adapter<RecipeResultsAdapter.RecipeHolder> {
//    private ArrayList<Recipe> recipeList;
//    RecipeClickListener listener;
//
//    //Constructor of RecipeResults Adapter
//    public RecipeResultsAdapter(ArrayList<Recipe> recipeList, RecipeClickListener listener){
//        this.recipeList = recipeList;
//        this.listener = listener;
//    }
//
//    public interface RecipeClickListener{
//        void onRecipeClick(int clickedRecipeIndex);
//    }
//
//
//    //Creates each of the ViewHolders to display on the screen
//    @Override
//    public RecipeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
//        //gets context of current activity on the screen
//        Context context = viewGroup.getContext();
//
//        //obtains views from the xml file
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        boolean attachToParentImmediately = false;
//
//        //used to instantiate layout xml file into actual View objects
//        View view = inflater.inflate(R.layout.search_results, viewGroup, attachToParentImmediately);
//
//        RecipeHolder holder = new RecipeHolder(view);
//
//        return holder;
//
//    }
//
//    //Displays the data info of an article at specified position given
//    @Override
//    public void onBindViewHolder(RecipeHolder recipeHolder, int position){
//        recipeHolder.bind(position);
//    }
//
//    //returns size of arrayList holding all the recipe matches
//    @Override
//    public int getItemCount(){
//        return recipeList.size();
//    }
//
//
//    public class RecipeHolder extends RecyclerView.ViewHolder implements View.onClickListener{
//        TextView mRecipeNameText;
//        TextView mRatingText;
//        TextView mSourceText;
//        TextView mTimeTakenText;
//        TextView mCoursesText;
//        TextView mCuisinesText;
//
//        public RecipeHolder(View view){
//            super(view);
//
//            //Getting references of the id's
//            mRecipeNameText = (TextView) view.findViewById(R.id.recipe_name);
//            mRatingText = (TextView) view.findViewById(R.id.recipe_rating);
//            mSourceText
//
//        }
//
//
//
//    }
//
//
////    [NAME OF THE THING]
////    rating: :star: :star: :star: :star:
////
////    source: lmao me
////    time taken: x mins
////    course: main meal
////    cuisine: asian
//
//
//
//
//
//}
