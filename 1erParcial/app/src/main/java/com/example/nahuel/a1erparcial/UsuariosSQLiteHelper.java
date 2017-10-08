package com.example.nahuel.a1erparcial; /**
 * Created by nahuel on 27/09/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate =  "CREATE TABLE Usuarios " +
                            "(_id       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " nombre    TEXT," +
                            " telefono  TEXT" +
                            " sexo      TEXT)";



    public UsuariosSQLiteHelper(Context contexto, String nombre,
                                CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
        db.execSQL("INSERT INTO Usuarios (nombre, telefono, sexo) VALUES ('Diego', '40005000', 'Masculino')");
        db.execSQL("INSERT INTO Usuarios (nombre, telefono, sexo) VALUES ('Laura', '45675000', 'Femenino')");
        db.execSQL("INSERT INTO Usuarios (nombre, telefono, sexo) VALUES ('Pablo', '40001234', 'Otro')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}