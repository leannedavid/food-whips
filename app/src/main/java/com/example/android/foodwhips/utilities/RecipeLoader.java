package com.example.android.foodwhips.utilities;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.example.android.foodwhips.models.GetRecipe;

public class RecipeLoader extends AsyncTaskLoader<GetRecipe> {
    private String recipeId;

    public RecipeLoader(Context context, String recipeId){
        super(context);
        this.recipeId = recipeId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public GetRecipe loadInBackground(){
        return ConversionUtils.FetchRecipeTask(recipeId);
    }

    @Override
    public void onStopLoading(){
        cancelLoad();
    }


}
