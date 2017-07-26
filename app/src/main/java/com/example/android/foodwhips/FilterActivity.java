package com.example.android.foodwhips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class FilterActivity extends BaseActivity {

    private TextView mSearchQuery;
    private TextView mEditView;
    // private String[] excludeArray;
    public static ArrayList<String> includeSearchQueries = new ArrayList<>();
    public static ArrayList<String> excludeSearchQueries = new ArrayList<>();
    private String search_query;
    private String include_query;
    private String exclude_query;
    private URL foodsUrl;

    // This linear layout holds the first layout container
    private LinearLayout baseLayoutContainer;
    // This list holds the linear layouts that will be added when button is pressed
    private ArrayList<LinearLayout> subLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_results);

        //EditText setup
        mSearchQuery = (EditText)findViewById(R.id.search_text);
        mEditView = (EditText)findViewById(R.id.filter_text);

        // RadioGroup setup
        RadioGroup rg = (RadioGroup) findViewById(R.id.filter_button);

        // Create the base layout form (the outer part)
        baseLayoutContainer = (LinearLayout) findViewById(R.id.base_layout);
        subLayouts = new ArrayList<LinearLayout>();
        Button button = (Button) findViewById(R.id.add_button);

        // When ADD button is pressed
        // Adds new layout with Edit Text & Radio Buttons
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get reference of the base layout form
                LinearLayout container = baseLayoutContainer;
                if (!subLayouts.isEmpty()) {
                    // double checks to see if any new container exists
                    // if it does, use new container
                    container = subLayouts.get(subLayouts.size() - 1);
                }

                // Initialize the new layouts to be created
                LinearLayout ll = new LinearLayout(FilterActivity.this);
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
                subLayouts.add(ll);

                // Last step, add the new linear layout to the base layout
                container.addView(ll);
            }
        });

        // When radio buttons pressed; check if include ingredient or exclude and make URL
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            // Checks which radio button in the radio group has been checked
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.filter_ingredient_1:
                        include_query = mEditView.getText().toString();
                        includeSearchQueries.add(include_query);
                        break;

                    case R.id.filter_ingredient_2:
                        exclude_query = mEditView.getText().toString();
                        excludeSearchQueries.add(exclude_query);
                        break;


                }
            }
        });

        // Button setup for search
        Button searchButton = (Button) findViewById(R.id.search_button);

        // Add everything from search query and direct to URL
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_query = mSearchQuery.getText().toString();
                foodsUrl = NetworkUtils.buildFilteredUrl(search_query, 1, includeSearchQueries, excludeSearchQueries);
            }

        });

    }

    // Adds Edit Text to the newly added linear layout
    public void addEditText(LinearLayout ll) {
        EditText et = new EditText(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        p.weight = 1;
        et.setLayoutParams(p);
        et.setHint(R.string.filter_hint);
        et.setTextSize(16);
        ll.addView(et);
    }

    // Add Radio Buttons to the newly added linear layout
    public void addRadioButtons(LinearLayout ll) {
        RadioGroup rbg = new RadioGroup(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
        p.weight = 3;
        rbg.setLayoutParams(p);
        rbg.setOrientation(HORIZONTAL);

        RadioButton rb1 = new RadioButton(this);
        RadioButton rb2 = new RadioButton(this);

        rb1.setText("Included");
        rb2.setText("Excluded");

        rbg.addView(rb1);
        rbg.addView(rb2);

        ll.addView(rbg);

    }

}
