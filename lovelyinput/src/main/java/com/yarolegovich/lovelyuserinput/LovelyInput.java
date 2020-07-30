package com.yarolegovich.lovelyuserinput;

import android.content.Context;
import androidx.annotation.ColorInt;

import com.yarolegovich.mp.io.UserInputModule;

import java.util.Collections;
import java.util.Map;

/**
 * Created by yarolegovich on 16.05.2016.
 */
public class LovelyInput implements UserInputModule.Factory {

    private Map<String, Integer> keyIconMappings;
    private Map<String, CharSequence> keyTitleMappings;
    private Map<String, CharSequence> keyMessageMappings;

    private int color;

    private LovelyInput() {
        keyIconMappings = Collections.emptyMap();
        keyTitleMappings = Collections.emptyMap();
        keyMessageMappings = Collections.emptyMap();
    }

    @Override
    public UserInputModule create(Context context) {
        return new LovelyInputModule(context)
                .setKeyMessageMapping(keyMessageMappings)
                .setKeyTitleMapping(keyTitleMappings)
                .setKeyIconMappings(keyIconMappings)
                .setTopColor(color);
    }

    public static class Builder {

        private LovelyInput factory;

        public Builder() {
            factory = new LovelyInput();
        }

        public Builder setTopColor(@ColorInt int color) {
            factory.color = color;
            return this;
        }

        public Builder setKeyTitleMappings(Map<String, CharSequence> mappings) {
            factory.keyTitleMappings = mappings;
            return this;
        }

        public Builder setKeyMessageMappings(Map<String, CharSequence> mappings) {
            factory.keyMessageMappings = mappings;
            return this;
        }

        public Builder setKeyIconMappings(Map<String, Integer> mappings) {
            factory.keyIconMappings = mappings;
            return this;
        }

        public LovelyInput build() {
            return factory;
        }

    }
}
