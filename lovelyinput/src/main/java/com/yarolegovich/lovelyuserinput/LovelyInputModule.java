package com.yarolegovich.lovelyuserinput;

import android.content.Context;
import android.text.TextUtils;

import com.yarolegovich.lovelydialog.AbsLovelyDialog;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;
import com.yarolegovich.mp.io.StandardUserInputModule;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yarolegovich on 16.05.2016.
 */
public class LovelyInputModule extends StandardUserInputModule {

    private Map<String, Integer> keyIconMappings;
    private Map<String, CharSequence> keyTitleMapping;
    private Map<String, CharSequence> keyMessageMapping;

    private int topColor;

    public LovelyInputModule(Context context) {
        super(context);
    }

    @Override
    public void showEditTextInput(
            String key,
            CharSequence title,
            CharSequence defaultValue,
            final Listener<String> listener) {
        standardInit(new LovelyTextInputDialog(context)
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String text) {
                        listener.onInput(text);
                    }
                }), key, title)
                .show();
    }

    @Override
    public void showSingleChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            final Listener<String> listener) {
        standardInit(new LovelyChoiceDialog(context)
                .setItems(displayItems, new LovelyChoiceDialog.OnItemSelectedListener<CharSequence>() {
                    @Override
                    public void onItemSelected(int position, CharSequence item) {
                        listener.onInput(values[position].toString());
                    }
                }), key, title)
                .show();
    }

    @Override
    public void showMultiChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            boolean[] itemStates,
            final Listener<Set<String>> listener) {
        standardInit(new LovelyChoiceDialog(context)
                .setItemsMultiChoice(displayItems, itemStates, new LovelyChoiceDialog.OnItemsSelectedListener<CharSequence>() {
                    @Override
                    public void onItemsSelected(List<Integer> positions, List<CharSequence> items) {
                        Set<String> selected = new HashSet<>();
                        for (int position : positions) {
                            selected.add(values[position].toString());
                        }
                        listener.onInput(selected);
                    }
                }), key, title)
                .show();
    }

    private AbsLovelyDialog standardInit(AbsLovelyDialog dialog, String key, CharSequence prefTitle) {
        CharSequence title = getTitle(key, prefTitle);
        CharSequence message = keyMessageMapping.get(key);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            dialog.setMessage(message);
        }
        return dialog.setTopColor(topColor).setIcon(keyIconMappings.get(key));
    }

    private CharSequence getTitle(String key, CharSequence prefTitle) {
        return keyTitleMapping.keySet().contains(key) ?
                keyTitleMapping.get(key) :
                prefTitle;
    }

    public LovelyInputModule setKeyIconMappings(Map<String, Integer> keyIconMappings) {
        this.keyIconMappings = keyIconMappings;
        return this;
    }

    public LovelyInputModule setKeyTitleMapping(Map<String, CharSequence> keyTitleMapping) {
        this.keyTitleMapping = keyTitleMapping;
        return this;
    }

    public LovelyInputModule setKeyMessageMapping(Map<String, CharSequence> keyMessageMapping) {
        this.keyMessageMapping = keyMessageMapping;
        return this;
    }

    public LovelyInputModule setTopColor(int topColor) {
        this.topColor = topColor;
        return this;
    }
}
