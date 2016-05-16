package com.yarolegovich.materialprefsample;

import android.content.Context;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public class Prefs {

    private static Prefs instance;

    public static void init(Context context) {
        instance = new Prefs(context);
    }

    public static Prefs keys() {
        return instance;
    }

    public final String KEY_USE_LOVELY;

    public final String KEY_YEARS_OF_EXP;
    public final String KEY_IS_ADEQUATE;
    public final String KEY_TECHNOLOGIES;
    public final String KEY_FAV_COLOR;

    public final String KEY_UPDATE_INTERVAL;
    public final String KEY_AUTO_LOCATION;
    public final String KEY_LOCATION;
    public final String KEY_TEMPERATURE;
    public final String KEY_DATE_FORMAT;
    public final String KEY_TIME_FORMAT;

    private Prefs(Context context) {
        KEY_USE_LOVELY = context.getString(R.string.pkey_use_lovely);

        KEY_YEARS_OF_EXP = context.getString(R.string.pkey_experience);
        KEY_IS_ADEQUATE = context.getString(R.string.pkey_adequate);
        KEY_TECHNOLOGIES = context.getString(R.string.pkey_techs);
        KEY_FAV_COLOR = context.getString(R.string.pkey_color);

        KEY_UPDATE_INTERVAL = context.getString(R.string.pkey_update_interval);
        KEY_AUTO_LOCATION = context.getString(R.string.pkey_auto_location);
        KEY_LOCATION = context.getString(R.string.pkey_location);
        KEY_TEMPERATURE = context.getString(R.string.pkey_temperature);
        KEY_DATE_FORMAT = context.getString(R.string.pkey_date_format);
        KEY_TIME_FORMAT = context.getString(R.string.pkey_time_format);
    }
}
