package com.incture.taguig;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Global {

    public static Context mContext;

    public Global(Context context) {
        mContext = context;
    }


    public static void showToast(Context context, String text) {

        try {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPicasa(String imageUrl, ImageView imageView) {
        Picasso.with(mContext)
                .load(imageUrl)
                .noPlaceholder()
                .into(imageView);
    }
}
