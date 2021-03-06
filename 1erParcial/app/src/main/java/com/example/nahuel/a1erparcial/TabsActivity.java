package com.example.nahuel.a1erparcial;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



public class TabsActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private String nombre;
    private String telefono;
    private String sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        final Intent intent = getIntent();
        int id = intent.getIntExtra("_id", 0);
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        db = usdbh.getWritableDatabase();
        Cursor rowCursor = db.rawQuery("SELECT nombre, telefono, sexo FROM Usuarios WHERE _id="+id, null);

        // Me aseguro que haya algo en el cursor
        if (rowCursor.moveToFirst()) {
            do {
                nombre= rowCursor.getString(0);
                telefono = rowCursor.getString(1);
                sexo = rowCursor.getString(2);
            } while(rowCursor.moveToNext());
        }
//        EventBus.getDefault().post(new MessageEvent("Hello"));
        EventBus.getDefault().postSticky(new MessageEvent(nombre));
        EventBus.getDefault().postSticky(new MessageEvent2(telefono));
        EventBus.getDefault().postSticky(new MessageEvent3(sexo));


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), TabsActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }
}
