package com.example.android.foodwhips.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodwhips.R;

/**
 * Created by li-en on 8/5/17.
 */

public class FirstFragment extends Fragment {
    private String title;
    private int page;

    public static FirstFragment newInstance(int page, String title){
        FirstFragment first = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        return first;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        page = this.getArguments().getInt("someInt", 0);
        title = this.getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_main, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.recipe_name);
        tvLabel.setText(page + " -- " + title);
        return view;
    }
}
