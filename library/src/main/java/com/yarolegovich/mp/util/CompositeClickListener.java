package com.yarolegovich.mp.util;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarolegovich on 04.05.2016.
 */
public class CompositeClickListener implements View.OnClickListener {

    private List<View.OnClickListener> listeners;

    public CompositeClickListener() {
        listeners = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        for (int i = listeners.size() - 1; i >= 0; i--) {
            listeners.get(i).onClick(v);
        }
    }

    public int addListener(View.OnClickListener listener) {
        listeners.add(listener);
        return listeners.size() - 1;
    }

    public void removeListener(View.OnClickListener listener) {
        listeners.remove(listener);
    }

    public void removeListener(int index) {
        listeners.remove(index);
    }
}
