package com.example.android.foodwhips;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.LinearLayout;

import java.net.URL;
import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;
import static android.widget.LinearLayout.HORIZONTAL;

import com.example.android.foodwhips.activities.BaseActivity;
import com.example.android.foodwhips.utilities.NetworkUtils;

/**
 * Created by Vincent on 7/10/2017.
 */

public class IngredientFilterActivity extends BaseActivity {

    private String TAG = "INGREDIENT ACTIVITY: ";
    private EditText mSearchQuery;
    private EditText mEditView;
    public static ArrayList<String> includeSearchQueries = new ArrayList<>();
    public static ArrayList<String> excludeSearchQueries = new ArrayList<>();
    private String search_query;
    private URL foodsUrl;

    // This linear layout holds the first layout container
    private LinearLayout baseLayoutContainer;

    // ArrayList setup of all Edit Text & Radio Buttons created
    private ArrayList<EditText> allEditTexts = new ArrayList<>();
    private ArrayList<RadioButton> allRadioButtons = new ArrayList<>();

    // Radio Button setup
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    // Beginning of activity: loads filter xml page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_ingredients);

        //EditText setup
        mSearchQuery = (EditText)findViewById(R.id.search_ingredient_text);
        mEditView = (EditText)findViewById(R.id.ingredient_text);
        allEditTexts.add(mEditView);

        // Initialize original radio buttons created from xml page
        radioButton1 = (RadioButton) findViewById(R.id.filter_ingredient_1);
        radioButton2 = (RadioButton) findViewById(R.id.filter_ingredient_2);
        // And add to overall list of radio buttons
        allRadioButtons.add(radioButton1);
        allRadioButtons.add(radioButton2);

        // Create the base layout form (the outer part)
        baseLayoutContainer = (LinearLayout) findViewById(R.id.base_layout);

        Button button = (Button) findViewById(R.id.add_button);

        // When ADD button is pressed
        // Adds new layout with Edit Text & Radio Buttons
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "ENTERING THIS");

                // Get reference of the base layout form
                LinearLayout container = baseLayoutContainer;

                // Initialize the new layouts to be created
                LinearLayout ll = new LinearLayout(IngredientFilterActivity.this);
                ll.setOrientation(VERTICAL);
                LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

                ll.setLayoutParams(llParams);
                // Change to own desire:
                ll.setPadding(10, 10, 10, 10);

                // Add EditText to layout
                addEditText(ll);

                // Add Radio Buttons to layout
                addRadioButtons(ll);

                // Add new sub-layouts to the array
                //subLayouts.add(ll);

                // Last step, add the new linear layout to the base layout
                container.addView(ll);
            }
        });

        // Button setup for when search button is pressed
        Button searchButton = (Button) findViewById(R.id.ingredient_search_button);

        // Add everything from search query and direct to URL
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "TESTING ON CREATE BEFORE SEARCH BUTTON");


                Log.v(TAG, "ENTERING THE SEARCH METHOD");
                search_query = mSearchQuery.getText().toString();
                Log.v(TAG, "QUERY ENTERED: " + mSearchQuery.getText().toString());
                Log.v(TAG, "EDIT ARRAY SIZE = " + allEditTexts.size());
                Log.v(TAG, "RADIO BUTTON SIZE = " + allRadioButtons.size());
                for(int i = 0; i < allEditTexts.size(); i++) {
                    Log.v(TAG, "ARRAY SIZE IS: " + allEditTexts.size() + "; i = " + i);
                    if(allRadioButtons.get(i*2).isChecked() == true) {
                        includeSearchQueries.add(allEditTexts.get(i).getText().toString());
                    }
                    else if(allRadioButtons.get(i*2+1).isChecked() == true) {
                        excludeSearchQueries.add(allEditTexts.get(i).getText().toString());
                    }
                }
                foodsUrl = NetworkUtils.buildIngredientUrl(search_query, 1, includeSearchQueries, excludeSearchQueries);

                Log.v(TAG, "INCLUDED INGREDIENTS: " + includeSearchQueries);
                Log.v(TAG, "EXCLUDED INGREDIENTS: " + excludeSearchQueries);
            }
        });

        Log.v(TAG, "ON CREATE FINISHED");

    }

    // Adds Edit Text to the newly added linear layout
    public void addEditText(LinearLayout ll) {
        EditText et = new EditText(this);

        //LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        //p.weight = 1;
        //et.setLayoutParams(p);
        et.setHint(R.string.ingredient_hint);

        et.setTextSize(16);

        // Add new Edit Text to allEditText ArrayList
        allEditTexts.add(et);

        // Add new Edit Text to the new Linear Layout
        ll.addView(et);
    }

    // Add Radio Buttons to the newly added linear layout
    public void addRadioButtons(LinearLayout ll) {
        RadioGroup rbg = new RadioGroup(this);
        //LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
       // p.weight = 3;
        //rbg.setLayoutParams(p);
        rbg.setOrientation(HORIZONTAL);

        RadioButton rb1 = new RadioButton(this);
        RadioButton rb2 = new RadioButton(this);

        rb1.setText("Included");
        rb2.setText("Excluded");

        // Add new radio buttons to a radio group
        rbg.addView(rb1);
        rbg.addView(rb2);

        // Add new radio buttons to allRadioButtons ArrayList
        allRadioButtons.add(rb1);
        allRadioButtons.add(rb2);

        // Add new radio group (2 buttons) to the new Linear Layout
        ll.addView(rbg);

    }

}
