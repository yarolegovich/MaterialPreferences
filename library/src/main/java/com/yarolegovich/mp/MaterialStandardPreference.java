package com.yarolegovich.mp;

import android.content.Context;
import android.util.AttributeSet;

import com.yarolegovich.mp.io.StorageModule;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public class MaterialStandardPreference extends AbsMaterialPreference<Void> {

    public MaterialStandardPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialStandardPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialStandardPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public void setValue(Void value) {

    }


    @Override
    protected int getLayout() {
        return R.layout.view_standard_preference;
    }
}
