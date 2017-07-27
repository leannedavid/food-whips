package com.example.android.foodwhips.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodwhips.R;


public class IngredientsInfo extends Fragment {
    private TextView mIngredientsView;
    static final String TAG = "ingredientsinfofragment";
    private static final String INGREDIENTS_VALUE = "ingredients";

    public IngredientsInfo(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients_info, container, false);

        //String recipe_id = getArguments().getString("recipe_id");
        //Log.v(TAG, "SUCCESSFULLY PASSED ID TO INGREDIENT FRAGMENT: " + recipe_id);

        mIngredientsView = (TextView) view.findViewById(R.id.detail_ingredients);

        String ingredients = getArguments().getString(INGREDIENTS_VALUE);
        mIngredientsView.setText(ingredients);
        //mIngredientsView.setText(ingredients != null ? ingredients : "lmao empty");

        return view;
    }
}
