package com.incture.taguig.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.incture.taguig.R;


public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slideImages={
            R.drawable.card1,
            R.drawable.card2,
            R.drawable.card3
    };

    /*public String[] slide_heading={
            "EAT",
            "Sleep",
            "Code"
    };

    public String[] slide_description={
            "vvffvfmmvvgfvvfvffgvvgfvgfvvgfgfvfdvfdvfdvfdvfdvfgbvbvgf",
            "vvffvfmmvvgfvvfvffgvvgfvgfvvgfgfvfdvfdvfdvfdvfdvfgbvbvgf",
            "vvffvfmmvvgfvvfvffgvvgfvgfvvgfgfvfdvfdvfdvfdvfdvfgbvbvgf"
    };*/

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView imageView=view.findViewById(R.id.imgView);


        imageView.setImageResource(slideImages[position]);

        container.addView(view);


        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
