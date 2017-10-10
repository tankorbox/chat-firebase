package com.example.tan.finalproject.notifications;


import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Tan on 5/10/2017.
 */

public class VibrateCreator {
    Vibrator v;
    public VibrateCreator (Context context) {
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    public void vibrate() {
        v.vibrate(500);
    }
}
