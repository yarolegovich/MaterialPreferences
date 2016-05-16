package com.yarolegovich.mp.io;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public class SharedPreferenceStorageModule implements StorageModule {

    private SharedPreferences prefs;

    public SharedPreferenceStorageModule(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    @Override
    public void saveString(String key, String value) {
        prefs.edit().putString(key, value).apply();;
    }

    @Override
    public void saveInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    @Override
    public void saveStringSet(String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key, boolean defaultVal) {
        return prefs.getBoolean(key, defaultVal);
    }

    @Override
    public String getString(String key, String defaultVal) {
        return prefs.getString(key, defaultVal);
    }

    @Override
    public int getInt(String key, int defaultVal) {
        return prefs.getInt(key, defaultVal);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultVal) {
        return prefs.getStringSet(key, defaultVal);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {

    }
}
