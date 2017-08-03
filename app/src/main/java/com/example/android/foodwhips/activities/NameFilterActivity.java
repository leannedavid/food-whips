package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.utilities.NetworkUtils;

import java.net.URL;

/**
 * Created by Vincent on 8/2/2017.
 */

public class NameFilterActivity extends BaseActivity {
    private String TAG = "NAME ACTIVITY: ";

    private EditText searchView;
    private Spinner diet_spinner;
    private Spinner time_spinner;
    private Spinner holiday_spinner;
    private Spinner course_spinner;

    private Button search_button;

    private URL foodsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_name);

        // Initialize Edit Text from XML
        searchView = (EditText) findViewById(R.id.search_name_filter_text);

        // Initialize Spinners from XML
        time_spinner = (Spinner) findViewById(R.id.time_options);
        diet_spinner = (Spinner) findViewById(R.id.diet_options);
        course_spinner = (Spinner) findViewById(R.id.course_options);
        holiday_spinner = (Spinner) findViewById(R.id.holiday_options);

        // Initialize Search button from XML
        search_button = (Button) findViewById(R.id.name_filter_search_button);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "ENTERING THIS");

                foodsUrl = NetworkUtils.buildNameUrl(searchView.getText().toString(), 1, time_spinner.getSelectedItem().toString(),
                        course_spinner.getSelectedItem().toString(), diet_spinner.getSelectedItem().toString(), holiday_spinner.getSelectedItem().toString());

                Log.v(TAG, "URL CREATED: " + foodsUrl);

                Intent switchAct = new Intent(NameFilterActivity.this, SearchResultsActivity.class);
                switchAct.putExtra("nameFilter", foodsUrl.toString());
                startActivity(switchAct);

            }
        });
    }
}
