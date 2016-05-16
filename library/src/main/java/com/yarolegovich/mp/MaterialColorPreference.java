package com.yarolegovich.mp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.yarolegovich.mp.io.StorageModule;
import com.yarolegovich.mp.io.UserInputModule;
import com.yarolegovich.mp.io.MaterialPreferences;
import com.yarolegovich.mp.view.ColorView;

import static com.yarolegovich.mp.R.styleable.*;
import static com.yarolegovich.mp.view.ColorView.SHAPE_CIRCLE;

/**
 * Created by yarolegovich on 06.05.2016.
 */
public class MaterialColorPreference extends AbsMaterialPreference<Integer> implements
        UserInputModule.Listener<Integer>, android.view.View.OnClickListener {

    private ColorView colorView;

    private ColorIndicatorConfig config;
    private int initialColor;

    public MaterialColorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialColorPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialColorPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onCollectAttributes(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, MaterialColorPreference);
        try {
            config = new ColorIndicatorConfig();
            config.setBorderColor(ta.getColor(MaterialColorPreference_mp_border_color, Color.BLACK));
            config.setBorderWidth(ta.getDimensionPixelSize(MaterialColorPreference_mp_border_width, 1));
            config.setShape(ta.getInt(MaterialColorPreference_mp_indicator_shape, SHAPE_CIRCLE));

            initialColor = ta.getColor(MaterialColorPreference_mp_initial_color, Color.WHITE);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onViewCreated() {
        colorView = (ColorView) findViewById(R.id.mp_color);
        colorView.setBorderColor(config.getBorderColor());
        colorView.setShape(config.getShape());
        colorView.setBorderWidth(config.getBorderWidth());
        colorView.setColor(getValue());

        addPreferenceClickListener(this);
    }

    @Override
    public Integer getValue() {
        return storageModule.getInt(key, initialColor);
    }

    @Override
    public void setValue(Integer value) {
        storageModule.saveInt(key, value);
        colorView.setColor(value);
    }

    @Override
    public void onClick(View v) {
        userInputModule.showColorSelectionInput(key, getTitle(), getValue(), this);
    }

    @Override
    public void onInput(Integer value) {
        setValue(value);
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        colorView.setColor(getValue());
    }

    @Override
    protected int getLayout() {
        return R.layout.view_color_preference;
    }

    private static class ColorIndicatorConfig {
        private int borderColor;
        private int borderWidth;
        private int shape;

        public void setBorderColor(int borderColor) {
            this.borderColor = borderColor;
        }

        public void setBorderWidth(int borderWidth) {
            this.borderWidth = borderWidth;
        }

        public void setShape(int shape) {
            this.shape = shape;
        }

        public int getBorderColor() {
            return borderColor;
        }

        public int getBorderWidth() {
            return borderWidth;
        }

        public int getShape() {
            return shape;
        }
    }
}
