package com.yarolegovich.materialprefsample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yarolegovich.lovelyuserinput.LovelyInput;
import com.yarolegovich.mp.io.MaterialPreferences;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Prefs.init(this);

        findViewById(R.id.pref_form).setOnClickListener(this);
        findViewById(R.id.pref_configs).setOnClickListener(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        setUserInputModule(prefs.getBoolean(Prefs.keys().KEY_USE_LOVELY, false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pref_form:
                startActivity(new Intent(this, FillTheFormActivity.class));
                break;
            case R.id.pref_configs:
                startActivity(new Intent(this, AppConfigsActivity.class));
                break;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Prefs.keys().KEY_USE_LOVELY)) {
            setUserInputModule(sharedPreferences.getBoolean(key, false));
        }
    }

    private void setUserInputModule(boolean shouldUseLovelyModule) {
        if (shouldUseLovelyModule) {
            MaterialPreferences.instance().setUserInputModule(createLovelyInputModule());
        } else {
            MaterialPreferences.instance().setDefault();
        }
    }

    private LovelyInput createLovelyInputModule() {
        Map<String, Integer> iconMappings = new HashMap<>();
        Prefs keys = Prefs.keys();
        iconMappings.put(keys.KEY_UPDATE_INTERVAL, R.drawable.ic_access_time_white_36dp);
        iconMappings.put(keys.KEY_LOCATION, R.drawable.ic_location_on_white_36dp);
        iconMappings.put(keys.KEY_TEMPERATURE, R.drawable.ic_thermometer_lines_white_36dp);
        iconMappings.put(keys.KEY_DATE_FORMAT, R.drawable.ic_date_range_white_36dp);
        iconMappings.put(keys.KEY_TIME_FORMAT, R.drawable.ic_access_time_white_36dp);
        iconMappings.put(keys.KEY_TECHNOLOGIES, R.drawable.ic_computer_white_36dp);
        int topColor = ContextCompat.getColor(this, R.color.lovelyDialogTop);
        return new LovelyInput.Builder()
                .setKeyIconMappings(iconMappings)
                .setTopColor(topColor)
                .build();
    }
}
