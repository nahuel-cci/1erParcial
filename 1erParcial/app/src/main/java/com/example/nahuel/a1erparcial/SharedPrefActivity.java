package com.example.nahuel.a1erparcial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

public class SharedPrefActivity extends AppCompatActivity {
    private Switch swCambiarColor;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

//        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = mSettings.edit();

        swCambiarColor = (Switch) findViewById(R.id.swCambiarColor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar_sharedpref);

        swCambiarColor.setChecked(mSettings.getBoolean("cambiarColor", false));
        setSupportActionBar(toolbar);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_sharedpref, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.check_icon:
                editor.putBoolean("cambiarColor",swCambiarColor.isChecked() );
                editor.apply();
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
