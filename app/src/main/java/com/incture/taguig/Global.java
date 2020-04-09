package com.incture.taguig;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Global {

    public static Context mContext;

    public Global(Context context) {
        mContext = context;
    }


    public static View progressOverlayImage;


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


    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        try {
            boolean show = toVisibility == View.VISIBLE;
            if (show) {
                view.setAlpha(0);
            }
            view.setVisibility(View.VISIBLE);
            view.animate()
                    .setDuration(duration)
                    .alpha(show ? toAlpha : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(toVisibility);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
