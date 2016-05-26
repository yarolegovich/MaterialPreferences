package com.yarolegovich.mp.io;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by yarolegovich on 05.05.2016.
 */
public class MaterialPreferences {

    private static final MaterialPreferences instance = new MaterialPreferences();

    private UserInputModule.Factory userInputModuleFactory;
    private StorageModule.Factory storageModuleFactory;

    private MaterialPreferences() {
        userInputModuleFactory = new StandardUserInputFactory();
        storageModuleFactory = new SharedPrefsStorageFactory(null);
    }

    public static MaterialPreferences instance() {
        return instance;
    }

    public static UserInputModule getUserInputModule(Context context) {
        return instance.userInputModuleFactory.create(context);
    }

    public static StorageModule getStorageModule(Context context) {
        return instance.storageModuleFactory.create(context);
    }

    public MaterialPreferences setUserInputModule(UserInputModule.Factory factory) {
        userInputModuleFactory = factory;
        return this;
    }

    public MaterialPreferences setStorageModule(StorageModule.Factory factory) {
        storageModuleFactory = factory;
        return this;
    }

    public void setDefault() {
        userInputModuleFactory = new StandardUserInputFactory();
        storageModuleFactory = new SharedPrefsStorageFactory(null);
    }


    private static class StandardUserInputFactory implements UserInputModule.Factory {
        @Override
        public UserInputModule create(Context context) {
            return new StandardUserInputModule(context);
        }
    }
}
