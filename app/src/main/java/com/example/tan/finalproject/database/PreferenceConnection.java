package com.example.tan.finalproject.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tan on 5/10/2017.
 */

public class PreferenceConnection {
    SharedPreferences mPreferences;
    Context context;
    public PreferenceConnection(Context context) {
        this.context = context;
        mPreferences = context.getSharedPreferences("my_data",Context.MODE_PRIVATE);
    }
    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences("my_data",Context.MODE_PRIVATE);
        return sp;
    }
    public String getUserID() {
        return mPreferences.getString("userid","");
    }
    public String getUserName() {
        return mPreferences.getString("name","");
    }
}
