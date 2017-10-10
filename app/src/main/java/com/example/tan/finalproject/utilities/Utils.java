package com.example.tan.finalproject.utilities;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Tan on 5/5/2017.
 */

public class Utils {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
