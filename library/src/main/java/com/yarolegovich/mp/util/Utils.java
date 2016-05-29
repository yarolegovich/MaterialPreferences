package com.yarolegovich.mp.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public class Utils {

    private Utils() {}

    public static int dpToPixels(Context context, int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return Math.round(dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int clickableBackground(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        return outValue.resourceId;
    }
}
