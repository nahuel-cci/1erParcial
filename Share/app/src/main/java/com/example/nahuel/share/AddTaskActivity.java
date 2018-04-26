package com.example.nahuel.share;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {
    public Button butAccept;
    public EditText etDuration;
    public EditText etAddPhone;
    public Spinner spName;
    private boolean editUser;
    private int id;
    private DatabaseReference mDatabase;

    private static final int NOTIF_ALERTA_ID = 1;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;


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
                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
//                intent.putExtra("previousActivity", "AddTaskActivity");

                String key = mDatabase.child("tasks").push().getKey();
                Task task = new Task(
                        spName.getSelectedItem().toString().toLowerCase(),
                        (long) System.currentTimeMillis()/1000,
                        "scheduled",
                        Long.parseLong(etDuration.getText().toString()),
                        (long)0
                );
                Map<String, Object> postValues = task.toMap();

                //mDatabase.child("tasks").push().setValue(task);
                //mDatabase.child("scheduled_tasks").push().setValue(task);

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/tasks/" + key, postValues);
                childUpdates.put("/scheduled_tasks/" + key,postValues);
                mDatabase.updateChildren(childUpdates);


//                /** Notificacion **/
//                mBuilder = new NotificationCompat.Builder(AddTaskActivity.this)
//                        .setSmallIcon(android.R.drawable.stat_sys_warning)
//                        .setLargeIcon((((BitmapDrawable)getResources()
//                                .getDrawable(R.drawable.common_full_open_on_phone)).getBitmap()))
//                        .setContentTitle("Tarea completada")
//                        .setContentText("Toca aquí para visualizarla")
//                        .setContentInfo("")
//                        .setTicker("Alerta!");
//
//
//                Intent notIntent = new Intent(AddTaskActivity.this, MainActivity.class);
//                PendingIntent contIntent = PendingIntent.getActivity(AddTaskActivity.this, 0, notIntent, 0);
//                mBuilder.setContentIntent(contIntent);
//
//                NotificationManager mNotificationManager =
//                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//                mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
//                /******************/




                startActivity(intent);
            }
        });





    }
}