package com.example.nahuel.a1erparcial;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amitshekhar.DebugDB;

public class AddUserActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    public Button butAccept;
    public EditText etAddName;
    public EditText etAddPhone;
    public Spinner spSexo;
    public final static String insertionIntoDb = "com.example.myfirstapp.MESSAGE";
    private boolean editUser;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etAddName = (EditText) findViewById(R.id.etAddName);
        etAddPhone = (EditText) findViewById(R.id.etAddPhone);
        butAccept = (Button) findViewById(R.id.butAccept);
        spSexo = (Spinner) findViewById(R.id.spSexo);

        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(this, R.array.sexo_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spSexo.setAdapter(spAdapter);


        final Intent intent = getIntent();
        editUser = intent.getBooleanExtra("editUser", false);
        if (editUser) {
            id = intent.getIntExtra("_id", 0);
            UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
            db = usdbh.getWritableDatabase();
            Cursor rowCursor = db.rawQuery("SELECT nombre, telefono, sexo FROM Usuarios WHERE _id=" + id, null);

            // Me aseguro que haya algo en el cursor
            if (rowCursor.moveToFirst()) {
                do {
                    etAddName.setText(rowCursor.getString(0));
                    etAddPhone.setText(rowCursor.getString(1));

                    String sexoQuery = rowCursor.getString(2);
                    if (!sexoQuery.equals(null)) {
                        int spinnerPosition = spAdapter.getPosition(sexoQuery);
                        spSexo.setSelection(spinnerPosition);
                    }

                } while (rowCursor.moveToNext());
            }
        }



        butAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(AddUserActivity.this, "DBUsuarios", null, 1);
                DebugDB.getAddressLog();
                SQLiteDatabase db = usdbh.getWritableDatabase();

//                String sqlQuery = "INSERT INTO Usuarios (nombre, telefono) VALUES "+
//                                            "('"+ etAddName.getText().toString()+"','"
//                                                + etAddPhone.getText().toString()+"')";
//                db.execSQL(sqlQuery);

                Intent intent = new Intent(AddUserActivity.this, MenuActivity.class);
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("nombre", etAddName.getText().toString());
                nuevoRegistro.put("telefono",etAddPhone.getText().toString());
                nuevoRegistro.put("sexo", spSexo.getSelectedItem().toString());
                intent.putExtra("previousActivity", "AddUserActivity");


                String where = "_id=?";
                String [] idString = new String[] {String.valueOf(id)};
                if (editUser) {
                    if (db.update("Usuarios", nuevoRegistro, where, idString) != -1) {
                        intent.putExtra(insertionIntoDb, "Contacto modificado correctamente");
                    } else {
                        intent.putExtra(insertionIntoDb, "ERROR: Intente nuevamente");
                    }
                }
                else {
                    if (db.insert("Usuarios", null, nuevoRegistro) != -1) {
                        intent.putExtra(insertionIntoDb, "Contacto añadido correctamente");
                    } else {
                        intent.putExtra(insertionIntoDb, "ERROR: Intente nuevamente");
                    }
                }
                startActivity(intent);

            }
        });

    }
}
