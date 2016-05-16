package com.yarolegovich.mp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.yarolegovich.mp.io.StorageModule;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public class MaterialEditTextPreference extends AbsMaterialTextValuePreference<String> {

    public MaterialEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public String getValue() {
        return storageModule.getString(key, defaultValue);
    }

    @Override
    public void setValue(String value) {
        storageModule.saveString(key, value);
        showNewValueIfNeeded(value);
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        showNewValueIfNeeded(toRepresentation(getValue()));
    }

    @Override
    public void onClick(View v) {
        userInputModule.showEditTextInput(key, getTitle(), defaultValue, this);
    }

    @Override
    protected CharSequence toRepresentation(String value) {
        return value;
    }


}
