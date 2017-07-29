package com.example.android.foodwhips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.foodwhips.activities.BaseActivity;
import com.example.android.foodwhips.activities.SearchResultsActivity;

public class MainActivity extends BaseActivity{
    private String search_query;
    private EditText mEditView;
    private Button mButtonSearch;

    static final String TAG = "mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditText setup
        mEditView   = (EditText)findViewById(R.id.search_text);

        //Button setup
        mButtonSearch = (Button)findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                search_query = mEditView.getText().toString();
                Intent switchAct = new Intent(MainActivity.this, SearchResultsActivity.class);
                switchAct.putExtra("searchQuery", search_query);
                startActivity(switchAct);
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
    }


}