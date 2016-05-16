package com.yarolegovich.materialprefsample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.yarolegovich.mp.MaterialPreferenceScreen;

/**
 * Created by yarolegovich on 15.05.2016.
 */
public class FillTheFormActivity extends ToolbarActivity {

    private Form form = new Form();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        setupToolbar();

        MaterialPreferenceScreen screen = (MaterialPreferenceScreen) findViewById(R.id.preference_screen);
        FormInitializer formInitializer = new FormInitializer(form);
        formInitializer.onRestoreInstanceState(savedInstanceState);
        screen.setStorageModule(formInitializer);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        new FormInitializer(form).onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public void submitForm(View v) {
        Toast.makeText(this,
                "Form submitted!\n" + form.toString(),
                Toast.LENGTH_SHORT)
                .show();
    }
}
