package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.net.URL;
import java.util.ArrayList;

import static android.widget.LinearLayout.HORIZONTAL;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.utilities.NetworkUtils;

/**
 * Created by Vincent on 7/29/2017.
 */

public class CuisineFilterActivity extends BaseActivity {
    private String TAG = "CUISINE ACTIVITY: ";

    private EditText mSearchQuery;

    private static ArrayList<String> allowedCuisines = new ArrayList<>();
    public static ArrayList<String> excludedCuisines = new ArrayList<>();

    private String search_query;

    private URL foodsUrl;

    // This linear layout holds the first layout container
    private LinearLayout baseLayoutContainer;

    // ArrayList setup of all Spinners & Radio Buttons created
    private ArrayList<Spinner> allSpinners = new ArrayList<>();
    private ArrayList<RadioButton> allRadioButtons = new ArrayList<>();

    // Makes an array of value strings into an ArrayList
    private String[] spinnerArray;

    // Spinner & Radio Button setup
    private Spinner spinner;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private ArrayAdapter spinnerAdapter;

    // Boolean to check for back
    private Boolean check;

    // Beginning of activity: loads filter xml page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_cuisines);

        check = true;

        //EditText setup
        mSearchQuery = (EditText)findViewById(R.id.search_text);

        // Initialize original spinner created from xml page
        spinner = (Spinner) findViewById(R.id.cuisine_options);

        // Retrieves adapter for that spinner
        spinnerAdapter = (ArrayAdapter) spinner.getAdapter();

        // And Add to list of spinners
        allSpinners.add(spinner);

        // Initialize original radio buttons created from xml page
        radioButton1 = (RadioButton) findViewById(R.id.filter_cuisine_1);
        radioButton2 = (RadioButton) findViewById(R.id.filter_cuisine_2);
        // And add to list of radio buttons
        allRadioButtons.add(radioButton1);
        allRadioButtons.add(radioButton2);

        // Create the base layout form (the outer part)
        baseLayoutContainer = (LinearLayout) findViewById(R.id.base_layout);

        Button button = (Button) findViewById(R.id.add_button);

        // When ADD button is pressed
        // Adds new layout with Spinner & Radio Buttons
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "ENTERING THIS");

                // Get reference of the base layout form
                LinearLayout container = baseLayoutContainer;

                // Initialize the new layouts to be created
                LinearLayout ll = new LinearLayout(CuisineFilterActivity.this);
                ll.setOrientation(HORIZONTAL);
                LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                llParams.setMargins(0, 100, 0, 0);

                ll.setLayoutParams(llParams);

                // Add Spinner to layout
                addSpinners(ll);

                // Add Radio Buttons to layout
                addRadioButtons(ll);

                // Last step, add the new linear layout to the base layout
                container.addView(ll);
            }
        });

        // Button setup for when search button is pressed
        Button searchButton = (Button) findViewById(R.id.cuisines_search_button);

        // Add everything from search query and direct to URL
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "TESTING ON CREATE BEFORE SEARCH BUTTON");

                Log.v(TAG, "ENTERING THE SEARCH METHOD");
                search_query = mSearchQuery.getText().toString();
                Log.v(TAG, "QUERY ENTERED: " + mSearchQuery.getText().toString());
                Log.v(TAG, "RADIO BUTTON SIZE = " + allRadioButtons.size());
                for(int i = 0; i < allSpinners.size(); i++) {
                    Log.v(TAG, "ARRAY SIZE IS: " + allSpinners.size() + "; i = " + i);
                    if(allRadioButtons.get(i*2).isChecked()) {
                        allowedCuisines.add(allSpinners.get(i).getSelectedItem().toString());
                    }
                    else if(allRadioButtons.get(i*2+1).isChecked()) {
                        excludedCuisines.add(allSpinners.get(i).getSelectedItem().toString());
                    }
                }
                foodsUrl = NetworkUtils.buildCuisineUrl(search_query, 1, allowedCuisines, excludedCuisines);

                Intent switchAct = new Intent(CuisineFilterActivity.this, SearchResultsActivity.class);
                switchAct.putExtra("cuisinesFilter", foodsUrl.toString());
                startActivity(switchAct);

                Log.v(TAG, "INCLUDED CUISINES: " + allowedCuisines);
                Log.v(TAG, "EXCLUDED CUISINES: " + excludedCuisines);
            }
        });

        Log.v(TAG, "ON CREATE FINISHED");

    }

    // Clears search queries from previous search
    @Override
    protected void onResume() {
        super.onResume();

        if(check) {
            check = false;
        } else {
            allowedCuisines.clear();
            excludedCuisines.clear();
        }
    }

    // Add Spinner to the newly added linear layout
    public void addSpinners(LinearLayout ll) {
        Spinner sp = new Spinner(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sp.setLayoutParams(p);

        sp.setAdapter(spinnerAdapter);

        allSpinners.add(sp);
        ll.addView(sp);
    }

    // Add Radio Buttons to the newly added linear layout
    public void addRadioButtons(LinearLayout ll) {
        RadioGroup rbg = new RadioGroup(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        p.weight = 0.3f;
        rbg.setLayoutParams(p);
        rbg.setOrientation(HORIZONTAL);

        RadioButton rb1 = new RadioButton(this);
        RadioButton rb2 = new RadioButton(this);
        LinearLayout.LayoutParams b = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        rb1.setLayoutParams(b);
        rb2.setLayoutParams(b);
        rb1.setText("Allow");
        rb2.setText("Exclude");

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
