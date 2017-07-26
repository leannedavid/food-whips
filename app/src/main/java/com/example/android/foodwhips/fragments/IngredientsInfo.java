package com.example.android.foodwhips.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.models.GetRecipe;
import com.example.android.foodwhips.utilities.RecipeLoader;


public class IngredientsInfo extends Fragment implements LoaderManager.LoaderCallbacks<GetRecipe>{
    private TextView mIngredientsView;
    static final String TAG = "ingredientsinfofragment";

    public IngredientsInfo(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients_info, container, false);

        String recipe_id = getArguments().getString("recipe_id");
        Log.v(TAG, "SUCCESSFULLY PASSED ID TO INGREDIENT FRAGMENT: " + recipe_id);

        mIngredientsView = (TextView) view.findViewById(R.id.detail_ingredients);

        getLoaderManager().initLoader(0, null, this).forceLoad();

        return view;
    }


    @Override
    public Loader<GetRecipe> onCreateLoader(int id, final Bundle args){
        Log.v(TAG, "THE VALUE OF INSIDE LOADER IS: " + getArguments().getString("recipe_id"));
        return new RecipeLoader(getContext(), getArguments().getString("recipe_id"));
    }

    @Override
    public void onLoadFinished(Loader<GetRecipe> loader, GetRecipe data){
        if(data != null) {
            mIngredientsView.setText(data.printIngredients());
        }
        else{
            mIngredientsView.setText(R.string.empty_data);
        }
    }

    @Override
    public void onLoaderReset(Loader<GetRecipe> loader){}
}
