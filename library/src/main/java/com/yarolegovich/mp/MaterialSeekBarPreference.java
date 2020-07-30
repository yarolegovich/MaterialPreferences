package com.yarolegovich.mp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.yarolegovich.mp.io.StorageModule;
import com.yarolegovich.mp.util.Utils;

import static com.yarolegovich.mp.R.styleable.MaterialSeekBarPreference;
import static com.yarolegovich.mp.R.styleable.MaterialSeekBarPreference_mp_max_val;
import static com.yarolegovich.mp.R.styleable.MaterialSeekBarPreference_mp_min_val;
import static com.yarolegovich.mp.R.styleable.MaterialSeekBarPreference_mp_show_val;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public class MaterialSeekBarPreference extends AbsMaterialPreference<Integer> {

    private AppCompatSeekBar seekBar;
    private TextView value;

    private int minValue;
    private int maxValue;
    private boolean showValue;

    public MaterialSeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onConfigureSelf() {
        int padding = Utils.dpToPixels(getContext(), 16);
        setPadding(0, padding, 0, padding);
        setGravity(Gravity.CENTER_VERTICAL);
        setClickable(true);
        setBackgroundResource(Utils.clickableBackground(getContext()));
    }

    @Override
    protected void onCollectAttributes(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, MaterialSeekBarPreference);
        try {
            maxValue = ta.getInt(MaterialSeekBarPreference_mp_max_val, 10);
            minValue = ta.getInt(MaterialSeekBarPreference_mp_min_val, 0);
            showValue = ta.getBoolean(MaterialSeekBarPreference_mp_show_val, false);
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onViewCreated() {
        value = (TextView) findViewById(R.id.mp_value);
        if (showValue) {
            value.setVisibility(VISIBLE);
        }

        seekBar = (AppCompatSeekBar) findViewById(R.id.mp_seekbar);
        seekBar.setOnSeekBarChangeListener(new ProgressSaver());
        seekBar.setMax(maxValue - minValue);
        setSeekBarValue(getValue());
    }

    @Override
    public Integer getValue() {
        try {
            return storageModule.getInt(key, Integer.parseInt(defaultValue));
        } catch (NumberFormatException e) {
            throw new AssertionError(getContext().getString(R.string.exc_not_int_default));
        }
    }

    @Override
    public void setValue(Integer value) {
        storageModule.saveInt(key, value);
        setSeekBarValue(value);
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        setSeekBarValue(getValue());
    }

    private void setSeekBarValue(int value) {
        seekBar.setProgress(value - minValue);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_seekbar_preference;
    }

    private class ProgressSaver implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            value.setText(String.valueOf(progress + minValue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            setValue(seekBar.getProgress() + minValue);
        }
    }
}
