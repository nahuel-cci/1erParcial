package com.example.nahuel.share;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nahuel on 06/12/2017.
 */

public class TaskHolder extends RecyclerView.ViewHolder {
    private final TextView mNameField;
    private final View mIconState;
    private final TextView mCreationtimeField;
    private final View mRelativeLayout;
    private String TAG = "TaskHolder";
    private ProgressBar mRunningTaskProgess;

    private CountDownTimer mCountDownTimer;
    private NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

    private int i;



    public TaskHolder (View itemView){
        super (itemView);
        mNameField = itemView.findViewById(R.id.task_name);
        mIconState = itemView.findViewById(R.id.rectangle_view);
        mCreationtimeField = itemView.findViewById(R.id.creation_time);
        mRunningTaskProgess = itemView.findViewById(R.id.progress_running_task);
        mRelativeLayout = itemView.findViewById(R.id.relative_layout);

        mInitNotification();
    }

    public void bind (Task task){
        setName(task.getName());
        setCreationtime(task.getCreationtime());
        setIconstate(task.getState(), task.getDuration(), task.getProgress());

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void setName(String name) {
        mNameField.setText(name);
    }

    private void setCreationtime(Long creationtime) {
//        mCreationtimeField.setText(creationtime.toString());
        Log.d(TAG, "creationtime:"+creationtime+creationtime.toString());
        String sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(creationtime*1000));
        mCreationtimeField.setText(sfd.toString().toString());
    }

    private void setIconstate (String state, Long duration, Long progress){
        switch(state){
            case "completed":
                mIconState.setBackgroundResource(R.drawable.rectangle_completed);
                mRunningTaskProgess.setVisibility(View.INVISIBLE);
                break;
            case "scheduled":
                mIconState.setBackgroundResource(R.drawable.rectangle_scheduled);
                mRunningTaskProgess.setVisibility(View.INVISIBLE);
                break;
            case "running":
                mIconState.setBackgroundResource(R.drawable.rectangle_running);
                mRunningTaskProgess.setVisibility(View.VISIBLE);
                mRunningTaskProgess.setProgress(progress.intValue());
                if (progress == 100){
                    mRelativeLayout.setBackgroundColor(Color.parseColor("#FFB8B8B8"));
                    mNotificationManager.notify(1, mBuilder.build());
                }

                /***********************************/
//                LocalBroadcastManager.getInstance(itemView.getContext()).
//                        registerReceiver(mMessageReceiver,new IntentFilter("setearElProgreso"));
                /***********************************/
//                mTareaAsc = new MiTareaAsincrona();
//                Log.d(TAG, "duration:"+duration+duration.toString());
//                mTareaAsc.execute(duration);
                /***********************************/
//                mRunningTaskProgess.setProgress(i);
//                mCountDownTimer=new CountDownTimer(20000,1000) {
//
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
//                        i++;
//                        mRunningTaskProgess.setProgress((int)i*100/(20000/1000));
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        //Do what you want
//                        i++;
//                        mRunningTaskProgess.setProgress(100);
//                    }
//                };
//                mCountDownTimer.start();
                /***********************************/


                break;
        }

    }




    private void mInitNotification(){
        mBuilder = new NotificationCompat.Builder(itemView.getContext().getApplicationContext())
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setLargeIcon((((BitmapDrawable)itemView.getContext().getApplicationContext().getResources()
                        .getDrawable(R.drawable.common_full_open_on_phone)).getBitmap()))
                .setContentTitle("Tarea completada")
                .setContentText("Toca aquí para visualizarla")
                .setContentInfo("")
                .setTicker("Alerta!");

        Intent notIntent = new Intent(itemView.getContext(), MainActivity.class);
        PendingIntent contIntent = PendingIntent.getActivity(itemView.getContext(), 0, notIntent, 0);
        mBuilder.setContentIntent(contIntent);
    }


}


