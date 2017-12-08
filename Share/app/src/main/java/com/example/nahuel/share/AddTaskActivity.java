package com.example.nahuel.share;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddTaskActivity extends AppCompatActivity {
    public Button butAccept;
    public EditText etDuration;
    public EditText etAddPhone;
    public Spinner spName;
    private boolean editUser;
    private int id;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        /*** base de datos ***/
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*** views ***/
        etDuration = (EditText) findViewById(R.id.etDuration);
        butAccept = (Button) findViewById(R.id.butAccept);
        spName = (Spinner) findViewById(R.id.spName);

        /*** adapter para el spinner ***/
        final ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(this, R.array.name_array, android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spName.setAdapter(spAdapter);



        butAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTaskActivity.this, UserActivity.class);
//                intent.putExtra("previousActivity", "AddTaskActivity");

                Task task = new Task(
                        spName.getSelectedItem().toString(),
                        (long) 1512306300,
                        "scheduled",
                        Long.parseLong(etDuration.getText().toString())
                );

                mDatabase.child("tasks").push().setValue(task);

                startActivity(intent);
            }
        });
    }
}