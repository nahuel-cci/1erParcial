package com.example.nahuel.a1erparcial;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by nahuel on 29/09/2017.
 */

public class UsuariosCursorAdapter extends CursorAdapter {
    public UsuariosCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.item_usuarios, parent, false);

        return retView;
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvCodigo = (TextView) view.findViewById(R.id.tvCodigo);
        TextView tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        TextView tvTelefono = (TextView) view.findViewById(R.id.tvTelefono);
        // Extract properties from cursor
        int codigo = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));
        // Populate fields with extracted properties
        tvCodigo.setText(String.valueOf(codigo));
        tvNombre.setText(nombre);
        tvTelefono.setText(telefono);
    }
}