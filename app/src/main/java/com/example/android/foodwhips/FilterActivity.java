package com.example.android.foodwhips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import com.example.android.foodwhips.utilities.NetworkUtils;


/**
 * Created by Vincent on 7/10/2017.
 */

public class FilterActivity extends AppCompatActivity {

    private TextView mEditView;
    private static ArrayList<String> editTextValue = new ArrayList<String>();
    private RadioGroup rg;
    private String include_query;
    private String exclude_query;
    private URL foodsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_results);

        //EditText setup
        mEditView = (EditText)findViewById(R.id.filter_text);

        // RadioGroup setup
        RadioGroup rg = (RadioGroup) findViewById(R.id.filter_button);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int selectedId) {
                switch(selectedId) {
                    case R.id.filter_ingredient_1:
                        // if include; do something
                        include_query = mEditView.getText().toString();
                        editTextValue.add(include_query);
                        foodsUrl = NetworkUtils.buildUrl(include_query, 1);
                        break;

                    case R.id.filter_ingredient_2:
                        // if exclude; do something
                        exclude_query = mEditView.getText().toString();
                        foodsUrl = NetworkUtils.buildUrl(exclude_query, 1);

                        break;

                }
            }
        });

    }
}
