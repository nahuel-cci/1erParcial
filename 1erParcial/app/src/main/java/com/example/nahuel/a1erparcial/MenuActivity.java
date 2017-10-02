package com.example.nahuel.a1erparcial;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String previousActivity = intent.getStringExtra("previousActivity");
        String insertionResult = intent.getStringExtra(AddUserActivity.insertionIntoDb);

//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(message);
//
//        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_menu);
//        layout.addView(textView);

        if (previousActivity.matches("AddUserActivity")) {
            Toast toast = Toast.makeText(   getApplicationContext(),
                                            insertionResult,
                                            Toast.LENGTH_SHORT);
            toast.show();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddUser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MenuActivity.this, AddUserActivity.class);
                startActivity(newIntent);
            }
        });


        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);

        DebugDB.getAddressLog();

        SQLiteDatabase db2 = usdbh.getWritableDatabase();
        Cursor usuariosCursor = db2.rawQuery("SELECT * FROM Usuarios", null);


        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.lvItems);
//        // Setup cursor adapter using cursor from last step
        UsuariosCursorAdapter usuariosAdapter = new UsuariosCursorAdapter(this, usuariosCursor);
//        // Attach cursor adapter to the ListView
        lvItems.setAdapter(usuariosAdapter);
//      db2.close();



    }
}


