package com.yarolegovich.mp;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yarolegovich.mp.io.StorageModule;
import com.yarolegovich.mp.io.UserInputModule;
import com.yarolegovich.mp.io.MaterialPreferences;

import static com.yarolegovich.mp.R.styleable.*;

/**
 * Created by yarolegovich on 05.05.2016.
 */
@SuppressWarnings("ResourceType")
abstract class AbsMaterialTextValuePreference<T> extends AbsMaterialPreference<T> implements
        UserInputModule.Listener<T>, android.view.View.OnClickListener {

    private static final int NOT_SHOW_VALUE = 0;
    private static final int SHOW_ON_RIGHT = 1;
    private static final int SHOW_ON_BOTTOM = 2;

    private TextView rightValue;
    private int showValueMode;

    public AbsMaterialTextValuePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsMaterialTextValuePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbsMaterialTextValuePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onCollectAttributes(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, AbsMaterialTextValuePreference);
        try {
            showValueMode = ta.getInt(AbsMaterialTextValuePreference_mp_show_value, NOT_SHOW_VALUE);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onViewCreated() {
        rightValue = (TextView) findViewById(R.id.mp_right_value);
        showNewValueIfNeeded(toRepresentation(getValue()));
        addPreferenceClickListener(this);
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        showNewValueIfNeeded(toRepresentation(getValue()));
    }

    @Override
    public void onInput(T value) {
        setValue(value);
    }

    protected abstract CharSequence toRepresentation(T value);

    protected void setRightValue(CharSequence value) {
        rightValue.setVisibility(visibility(value));
        rightValue.setText(value);
    }

    protected void showNewValueIfNeeded(CharSequence value) {
        switch (showValueMode) {
            case SHOW_ON_RIGHT:
                setRightValue(value);
                break;
            case SHOW_ON_BOTTOM:
                setSummary(value);
                break;
        }
    }

    protected boolean hasSummary() {
        return showValueMode != SHOW_ON_BOTTOM && !TextUtils.isEmpty(getSummary());
    }

    @Override
    protected int getLayout() {
        return R.layout.view_text_input_preference;
    }
}
