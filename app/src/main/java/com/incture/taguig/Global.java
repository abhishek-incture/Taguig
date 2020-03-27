package com.incture.taguig;

import android.content.Context;
import android.widget.Toast;

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
}
