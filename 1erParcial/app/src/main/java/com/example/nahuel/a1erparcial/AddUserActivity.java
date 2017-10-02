package com.example.nahuel.a1erparcial;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amitshekhar.DebugDB;

public class AddUserActivity extends AppCompatActivity {
    public Button butAccept;
    public EditText etAddName;
    public EditText etAddPhone;
    public final static String insertionIntoDb = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);



        etAddName = (EditText) findViewById(R.id.etAddName);
        etAddPhone = (EditText) findViewById(R.id.etAddPhone);
        butAccept = (Button) findViewById(R.id.butAccept);

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
                intent.putExtra("previousActivity", "AddUserActivity");

                if (db.insert("Usuarios", null, nuevoRegistro) != -1) {
                    intent.putExtra(insertionIntoDb, "Contacto añadido correctamente");
                }
                else{
                    intent.putExtra(insertionIntoDb, "ERROR: Intente nuevamente");
                }
                startActivity(intent);

            }
        });

    }
}
