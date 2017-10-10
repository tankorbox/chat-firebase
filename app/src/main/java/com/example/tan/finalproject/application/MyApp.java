package com.example.tan.finalproject.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tan on 5/5/2017.
 */

public class MyApp extends Application {
    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Context getContext() {
        return mInstance.getApplicationContext();
    }
}
