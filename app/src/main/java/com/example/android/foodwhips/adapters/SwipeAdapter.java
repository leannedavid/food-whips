package com.example.android.foodwhips.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.foodwhips.R;

/**
 * Created by Ariel on 8/1/2017.
 */

public class SwipeAdapter extends PagerAdapter {
    private int[] images = {R.drawable.french_lentil_soup, R.drawable.baked_summer_squash, R.drawable.magic_custard_cake};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public SwipeAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.carousel_image);
        TextView textView = (TextView) itemView.findViewById(R.id.carousel_text);
        imageView.setImageResource(images[position]);
        if (position == 0){
            textView.setText("French Lentil Soup");
        }else if(position == 1){
            textView.setText("Baked Summer Squash");
        }else{
            textView.setText("Magic Custard Cake");
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
