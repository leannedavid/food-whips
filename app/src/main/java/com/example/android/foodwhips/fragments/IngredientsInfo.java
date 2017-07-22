package com.example.android.foodwhips.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.foodwhips.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IngredientsInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngredientsInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsInfo extends Fragment {
    public IngredientsInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredients_info, container, false);
    }


}
