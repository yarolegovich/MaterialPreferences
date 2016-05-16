package com.yarolegovich.mp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.yarolegovich.mp.io.UserInputModule;
import com.yarolegovich.mp.io.StorageModule;

import java.util.Collection;
import java.util.List;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public class MaterialPreferenceScreen extends ScrollView {

    private LinearLayout container;

    private UserInputModule userInputModule;
    private StorageModule storageModule;

    public MaterialPreferenceScreen(Context context) {
        super(context);
    }

    public MaterialPreferenceScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialPreferenceScreen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialPreferenceScreen(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        setFillViewport(true);
        LinearLayout container = new LinearLayout(getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.VERTICAL);
        addView(container);
        this.container = container;
    }

    public void changeViewsVisibility(List<Integer> viewIds, boolean visible) {
        int visibility = visible ? VISIBLE : GONE;
        changeViewsVisibility(container, viewIds, visibility);
    }

    public void setVisibilityController(int controllerId, List<Integer> controlledIds, boolean showWhenChecked) {
        setVisibilityController(
                (AbsMaterialCheckablePreference) findViewById(controllerId),
                controlledIds, showWhenChecked
        );
    }

    public void setVisibilityController(
            final AbsMaterialCheckablePreference controller,
            final List<Integer> controlledIds,
            final boolean showWhenChecked) {
        boolean shouldShow = showWhenChecked ? controller.getValue() : !controller.getValue();
        int initialVisibility = shouldShow ? View.VISIBLE : View.GONE;
        changeViewsVisibility(this, controlledIds, initialVisibility);
        controller.addPreferenceClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shouldShow = showWhenChecked ? controller.getValue() : !controller.getValue();
                int visibility = shouldShow ? View.VISIBLE : View.GONE;
                changeViewsVisibility(MaterialPreferenceScreen.this,
                        controlledIds,
                        visibility);
            }
        });
    }

    private void changeViewsVisibility(ViewGroup container, Collection<Integer> viewIds, int visibility) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof ViewGroup) {
                if (viewIds.contains(child.getId())) {
                    child.setVisibility(visibility);
                } else if (!(child instanceof AbsMaterialPreference)) {
                    changeViewsVisibility((ViewGroup) child, viewIds, visibility);
                }
            }
            if (viewIds.contains(child.getId())) {
                child.setVisibility(visibility);
            }
        }
    }

    UserInputModule getUserInputModule() {
        return userInputModule;
    }

    StorageModule getStorageModule() {
        return storageModule;
    }

    public void setUserInputModule(UserInputModule userInputModule) {
        this.userInputModule = userInputModule;
        setUserInputModuleRecursively(container, userInputModule);
    }

    public void setStorageModule(StorageModule storageModule) {
        this.storageModule = storageModule;
        setStorageModuleRecursively(container, storageModule);
    }

    private void setUserInputModuleRecursively(ViewGroup container, UserInputModule module) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof AbsMaterialPreference) {
                ((AbsMaterialPreference) child).setUserInputModule(module);
            } else if (child instanceof ViewGroup) {
                setUserInputModuleRecursively((ViewGroup) child, module);
            }
        }
    }

    private void setStorageModuleRecursively(ViewGroup container, StorageModule module) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof AbsMaterialPreference) {
                ((AbsMaterialPreference) child).setStorageModule(module);
            } else if (child instanceof ViewGroup) {
                setStorageModuleRecursively((ViewGroup) child, module);
            }
        }
    }

    @Override
    public void addView(View child) {
        if (container != null) {
            container.addView(child);
        } else {
            super.addView(child);
        }
    }

    @Override
    public void addView(View child, int index) {
        if (container != null) {
            container.addView(child, index);
        } else {
            super.addView(child, index);
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (container != null) {
            container.addView(child, params);
        } else {
            super.addView(child, params);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (container != null) {
            container.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

}
