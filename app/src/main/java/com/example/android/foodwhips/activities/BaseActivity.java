package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.foodwhips.AboutActivity;
import com.example.android.foodwhips.R;

/* Responsible of keeping the same base layout (Toolbar/Navigation Drawer/Search Bar/Ellipses Menu)
   throughout all activities when navigating through the app.
*/
public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private String search_query;
    private EditText mEditView;
    private Button mButtonSearch;

    @Override
    public void setContentView(int layoutResID){
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        LinearLayout activityContainer = (LinearLayout) fullView.findViewById(R.id.content_frame);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        //Setup toolbar on the activity
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup navigation drawer in activity
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //EditText setup
        mEditView   = (EditText)findViewById(R.id.search_text);

        //Button setup
        mButtonSearch = (Button)findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                search_query = mEditView.getText().toString();
                Intent switchAct = new Intent(BaseActivity.this, SearchResultsActivity.class);
                switchAct.putExtra("searchQuery", search_query);
                startActivity(switchAct);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_ellipsis_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.information:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
