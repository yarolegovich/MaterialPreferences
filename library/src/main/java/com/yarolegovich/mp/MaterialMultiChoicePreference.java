package com.yarolegovich.mp;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.yarolegovich.mp.io.StorageModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.yarolegovich.mp.R.styleable.*;

/**
 * Created by yarolegovich on 06.05.2016.
 */
public class MaterialMultiChoicePreference extends AbsMaterialListPreference<Set<String>> {

    private Set<String> defaultSelected;

    public MaterialMultiChoicePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialMultiChoicePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialMultiChoicePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onCollectAttributes(AttributeSet attrs) {
        super.onCollectAttributes(attrs);
        defaultSelected = new HashSet<>();
        TypedArray ta = getContext().obtainStyledAttributes(MaterialMultiChoicePreference);
        try {
            if (ta.hasValue(MaterialMultiChoicePreference_mp_default_selected)) {
                CharSequence[] defSelected = ta.getTextArray(MaterialMultiChoicePreference_mp_default_selected);
                for (CharSequence cs : defSelected) {
                    defaultSelected.add(cs.toString());
                }
            }
        } finally {
            ta.recycle();
        }
    }

    @Override
    public Set<String> getValue() {
        return storageModule.getStringSet(key, defaultSelected);
    }

    @Override
    public void setValue(Set<String> value) {
        storageModule.saveStringSet(key, value);
        showNewValueIfNeeded(toRepresentation(value));
    }

    @Override
    public void onClick(View v) {
        userInputModule.showMultiChoiceInput(
                key, getTitle(), entries, entryValues,
                itemStates(getValue()),
                this);
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        showNewValueIfNeeded(toRepresentation(getValue()));
    }

    @Override
    protected CharSequence toRepresentation(Set<String> value) {
        return TextUtils.join(", ", entryValuesToEntries(value));
    }

    private boolean[] itemStates(Set<String> selected) {
        boolean[] result = new boolean[entryValues.length];
        for (int i = 0; i < entryValues.length; i++) {
            result[i] = selected.contains(entryValues[i].toString());
        }
        return result;
    }

    private List<CharSequence> entryValuesToEntries(Set<String> neededValues) {
        List<CharSequence> result = new ArrayList<>();
        for (int i = 0; i < entryValues.length; i++) {
            if (neededValues.contains(entryValues[i].toString())) {
                result.add(entries[i].toString());
            }
        }
        return result;
    }
}
