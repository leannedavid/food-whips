package com.example.android.foodwhips.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.foodwhips.R;
import com.example.android.foodwhips.models.GetRecipe;
import com.example.android.foodwhips.utilities.ConversionUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ariel on 8/1/2017.
 */

public class HomeSwipeAdapter extends PagerAdapter {
    private Context ctx;
    private LayoutInflater layoutInflater;
    private String[] imagesFromURL;
    private ArrayList<GetRecipe> data;

    final static String TAG = "SWIPEADAPTER";

    public HomeSwipeAdapter(Context ctx, String[] imagesFromURL, ArrayList<GetRecipe> data){
        this.ctx = ctx;
        this.imagesFromURL = imagesFromURL;
        this.data = data;
    }

    @Override
    public int getCount() {
        if (imagesFromURL != null) {
            return imagesFromURL.length;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.carousel_image);
        TextView textView = (TextView) itemView.findViewById(R.id.carousel_title);

        //Log.v(TAG, "ENTERING SWIPE ADAPTER, VALUE OF POSITION " + position + " IS: " + data.get(position).getRecipeName());

        Picasso.with(ctx).load(imagesFromURL[position]).into(imageView);
        Log.v(TAG, "VALUE OF POSITION: " + position);

        //textView.setText(data.get(position).getRecipeName());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
