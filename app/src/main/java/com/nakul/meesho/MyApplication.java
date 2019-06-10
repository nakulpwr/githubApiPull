package com.nakul.meesho;

import android.app.Application;
import android.content.Context;

import com.nakul.meesho.network.VolleyHelper;

public class MyApplication extends Application {
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        VolleyHelper.getInstance(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
