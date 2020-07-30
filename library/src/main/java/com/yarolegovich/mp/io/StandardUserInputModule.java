package com.yarolegovich.mp.io;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.kunzisoft.androidclearchroma.ChromaDialog;
import com.kunzisoft.androidclearchroma.IndicatorMode;
import com.kunzisoft.androidclearchroma.colormode.ColorMode;
import com.kunzisoft.androidclearchroma.listener.OnColorSelectedListener;
import com.yarolegovich.mp.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yarolegovich on 06.05.2016.
 */
public class StandardUserInputModule implements UserInputModule {

    protected Context context;

    public StandardUserInputModule(Context context) {
        this.context = context;
    }

    @Override
    public void showEditTextInput(
            String key,
            CharSequence title,
            CharSequence defaultValue,
            final Listener<String> listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
        final EditText inputField = (EditText) view.findViewById(R.id.mp_text_input);

        if (defaultValue != null) {
            inputField.setText(defaultValue);
            inputField.setSelection(defaultValue.length());
        }

        final Dialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .show();
        view.findViewById(R.id.mp_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInput(inputField.getText().toString());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void showSingleChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            int selected,
            final Listener<String> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(displayItems, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = values[which].toString();
                        listener.onInput(selected);
                        dialog.dismiss();
                    }
                })
                /*.setItems(displayItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = values[which].toString();
                        listener.onInput(selected);
                    }
                })*/
                .show();
    }

    @Override
    public void showMultiChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            final boolean[] itemStates,
            final Listener<Set<String>> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMultiChoiceItems(displayItems, itemStates, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        itemStates[which] = isChecked;
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Set<String> result = new HashSet<>();
                        for (int i = 0; i < values.length; i++) {
                            if (itemStates[i]) {
                                result.add(values[i].toString());
                            }
                        }
                        listener.onInput(result);
                    }
                })
                .show();
    }

    @Override
    public void showColorSelectionInput(
            String key,
            CharSequence title,
            int defaultColor,
            final Listener<Integer> colorListener) {
        FragmentActivity activity;
        try {
            activity = (FragmentActivity) context;
        } catch (ClassCastException exc) {
            throw new AssertionError(context.getString(R.string.exc_not_frag_activity_subclass));
        }
        final String tag = colorListener.getClass().getSimpleName();
        ChromaDialog dialog = new ChromaDialog.Builder()
                .initialColor(defaultColor)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .create();
        dialog.setOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onPositiveButtonClick(int color) {
                colorListener.onInput(color);
            }

            @Override
            public void onNegativeButtonClick(int color) {

            }
        });
        dialog.show(activity.getSupportFragmentManager(), tag);
    }
}
