package com.example.android.foodwhips.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.activities.RecipeDetailsActivity;
import com.example.android.foodwhips.activities.SearchResultsActivity;

import org.w3c.dom.Text;

/**
 * Created by li-en on 8/5/17.
 */

public class FirstFragment extends Fragment {
    private String title;
    private int page;
    private TextView mTvLabel;

    private RecyclerView rv;

    public static FirstFragment newInstance(int page, String title, String searchQuery){
        FirstFragment first = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("soup", searchQuery);
        first.setArguments(args);
        return first;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.main_viewpager, container, false);

//        Intent switchAct = new Intent(view.getContext(), SearchResultsActivity.class);
//        switchAct.putExtra("recipe_id", "onion");
//        startActivity(switchAct);

        //rv = (RecyclerView)
        mTvLabel = (TextView) view.findViewById(R.id.txtPage);
        //tvLabel.setText("lalllal");
        mTvLabel.setText(page + " -- " + title);
        return view;
    }
}
