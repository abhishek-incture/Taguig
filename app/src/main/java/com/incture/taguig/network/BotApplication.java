package com.incture.taguig.network;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class BotApplication extends Application {
    private static BotApplication mInstance;
    public static final String TAG = BotApplication.class.getSimpleName();

    private RequestQueue mRequestQueue;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }
    public static synchronized BotApplication getInstance(Context ctx) {

        mInstance=new BotApplication();
        context=ctx;

        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
