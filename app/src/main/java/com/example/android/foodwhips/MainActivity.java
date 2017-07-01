package com.example.android.foodwhips;
/*

Tasked with creating basic layout for homepage


*/
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.foodwhips.utilities.NetworkUtils;

import java.net.URL;

import static com.example.android.foodwhips.R.drawable.back;

public class MainActivity extends AppCompatActivity {

    private TextView mFoodTextView;
    private TextView mEditView;
    private TextView mTab1;
    private TextView mTab2;
    private TextView mTab3;
    private boolean mTabInt1 = false;
    private boolean mTabInt2 = false;
    private boolean mTabInt3 = false;
    private Button mButtonSearch;
    private URL foodsUrl;
    private String search_query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSearch = (Button)findViewById(R.id.search_button);
        mEditView   = (EditText)findViewById(R.id.search_text);
        mTab1 = (TextView)findViewById(R.id.popular_tab);
        mTab2 = (TextView)findViewById(R.id.new_tab);
        mTab3 = (TextView)findViewById(R.id.favorites_tab);
        mFoodTextView = (TextView) findViewById(R.id.food_data);
        mButtonSearch.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        search_query = mEditView.getText().toString();
                        foodsUrl = NetworkUtils.buildUrl(search_query);

                        loadFoodData();
                    }
                });
    }

    private void loadFoodData() {
        new FetchFoodTask().execute();
    }

    private class FetchFoodTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {

            String result = null;

            try {
                result = NetworkUtils.getResponseFromHttpUrl(foodsUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String foodData) {
            if (foodData != null) {
                mFoodTextView.setText(foodData);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh) {
            mFoodTextView.setText("");
            loadFoodData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickTab1(View v)
    {
        if(mTabInt1){
        }else{
            mTab1.setBackgroundResource(R.drawable.press);
            mTab2.setBackgroundResource(R.drawable.back);
            mTab3.setBackgroundResource(R.drawable.back);
        }
    }

    public void onClickTab2(View v)
    {
        if(mTabInt1){
        }else{
            mTab2.setBackgroundResource(R.drawable.press);
            mTab1.setBackgroundResource(R.drawable.back);
            mTab3.setBackgroundResource(R.drawable.back);
        }
    }

    public void onClickTab3(View v)
    {
        if(mTabInt1){
        }else{
            mTab3.setBackgroundResource(R.drawable.press);
            mTab1.setBackgroundResource(R.drawable.back);
            mTab2.setBackgroundResource(R.drawable.back);
        }
    }

}
