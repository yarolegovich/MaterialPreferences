package com.yarolegovich.materialprefsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yarolegovich.mp.MaterialPreferenceScreen;

import java.util.Arrays;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public class AppConfigsActivity extends ToolbarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        setupToolbar();

        MaterialPreferenceScreen screen = (MaterialPreferenceScreen) findViewById(R.id.preference_screen);
        screen.setVisibilityController(R.id.pref_auto_loc, Arrays.asList(R.id.pref_location), false);
    }
}
