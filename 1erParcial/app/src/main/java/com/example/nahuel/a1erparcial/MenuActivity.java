package com.example.nahuel.a1erparcial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;

public class MenuActivity extends AppCompatActivity {
    private TextView lblMensaje;
    private ListView lvItems;
    private UsuariosCursorAdapter usuariosAdapter;
    private SQLiteDatabase db;
    private Cursor contextMenuCursor;
    private SharedPreferences mSettings;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String previousActivity = intent.getStringExtra("previousActivity");
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

//      SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);



        if (previousActivity.matches("AddUserActivity")) {
            String insertionResult = intent.getStringExtra(AddUserActivity.insertionIntoDb);
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



        /***** Bases de datos ****/
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        DebugDB.getAddressLog();

        db = usdbh.getWritableDatabase();
        Cursor usuariosCursor = db.rawQuery("SELECT * FROM Usuarios", null);

        // Find ListView to populate
        lvItems = (ListView) findViewById(R.id.lvItems);

        // Setup cursor adapter using cursor from last step
        usuariosAdapter = new UsuariosCursorAdapter(this, usuariosCursor);

        // Attach cursor adapter to the ListView
        lvItems.setAdapter(usuariosAdapter);
//      db.close();

        //Asociamos los menús contextuales a los controles
        registerForContextMenu(lvItems);
        /************************/


        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newIntent = new Intent(MenuActivity.this, TabsActivity.class);
                newIntent.putExtra("_id", (int)id);
                startActivity(newIntent);
            }
        });

    }

    // La posta de los toolbars
    // https://guides.codepath.com/android/Using-the-App-Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent intent = new Intent(this, SharedPrefActivity.class);
                intent.putExtra("previousActivity", "MenuActivity");
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)   {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        contextMenuCursor = (Cursor) lvItems.getItemAtPosition(info.position);
        String title = contextMenuCursor.getString(contextMenuCursor.getColumnIndex("nombre"));
        menu.setHeaderTitle(title);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.CtxLblOpc1:
                Intent newIntent = new Intent(MenuActivity.this, AddUserActivity.class);
                newIntent.putExtra("editUser", true);
                newIntent.putExtra("_id", contextMenuCursor.getInt(contextMenuCursor.getColumnIndex("_id")));
                startActivity(newIntent);
                return true;
            case R.id.CtxLblOpc2:
                db.execSQL("DELETE FROM Usuarios WHERE _id="+contextMenuCursor.getString(contextMenuCursor.getColumnIndex("_id")));
                Cursor usuariosCursorAux = db.rawQuery("SELECT * FROM Usuarios", null);
                usuariosAdapter.changeCursor(usuariosCursorAux);
//                usuariosAdapter.notifyDataSetChanged(); // No hace falta
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mSettings.getBoolean("cambiarColor", false)){
            toolbar.setTitleTextColor(Color.RED);
        }
        else{
            toolbar.setTitleTextColor(Color.BLACK);
        }
    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//
//    }

}


