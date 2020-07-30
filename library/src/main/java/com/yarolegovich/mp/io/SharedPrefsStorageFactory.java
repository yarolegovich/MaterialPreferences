package com.yarolegovich.mp.io;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;

/**
 * Created by yarolegovich on 16.05.2016.
 */
public class SharedPrefsStorageFactory implements StorageModule.Factory {
    private String preferencesName;

    public SharedPrefsStorageFactory(@Nullable String preferencesName) {
        this.preferencesName = preferencesName;
    }

    @Override
    public StorageModule create(Context context) {
        SharedPreferences prefs;
        if (preferencesName != null) {
            prefs = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        } else {
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return new SharedPreferenceStorageModule(prefs);
    }
}
