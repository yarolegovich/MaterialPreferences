package com.yarolegovich.mp;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public class MaterialCheckboxPreference extends AbsMaterialCheckablePreference {

    public MaterialCheckboxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialCheckboxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialCheckboxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_checkbox_preference;
    }
}
