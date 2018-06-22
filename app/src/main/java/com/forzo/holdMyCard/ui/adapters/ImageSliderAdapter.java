package com.forzo.holdMyCard.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.forzo.holdMyCard.GlideApp;
import com.forzo.holdMyCard.R;
import com.jackandphantom.circularimageview.RoundedImage;

import java.util.ArrayList;

public class ImageSliderAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;

    public  ImageSliderAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.fragment_slider, view, false);
        RoundedImage myImage = (RoundedImage) myImageLayout.findViewById(R.id.image_view);
        myImage.setRoundedRadius(10);
//        myImage.setImageResource(images.get(position));

        GlideApp.with(context)
                .load(images)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(myImage);


        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}