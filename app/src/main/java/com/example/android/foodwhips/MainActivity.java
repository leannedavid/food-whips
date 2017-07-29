package com.example.android.foodwhips;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.foodwhips.activities.BaseActivity;

public class MainActivity extends BaseActivity {
    private TextView mTab1;
    private TextView mTab2;
    private TextView mTab3;
    private boolean mTabInt1 = false;
    private boolean mTabInt2 = false;
    private boolean mTabInt3 = false;

    static final String TAG = "mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tab setup
        mTab1 = (TextView)findViewById(R.id.popular_tab);
        mTab2 = (TextView)findViewById(R.id.new_tab);
        mTab3 = (TextView)findViewById(R.id.favorites_tab);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
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
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        if(mTabInt2){
        }else{
            mTab2.setBackgroundResource(R.drawable.press);
            mTab1.setBackgroundResource(R.drawable.back);
            mTab3.setBackgroundResource(R.drawable.back);
        }
    }

    public void onClickTab3(View v)
    {
        if(mTabInt3){
        }else{
            mTab3.setBackgroundResource(R.drawable.press);
            mTab1.setBackgroundResource(R.drawable.back);
            mTab2.setBackgroundResource(R.drawable.back);
        }
    }

}