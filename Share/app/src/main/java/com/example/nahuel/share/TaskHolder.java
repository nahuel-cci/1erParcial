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

import org.greenrobot.eventbus.EventBus;

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

    private static final int NOTIF_ALERTA_ID = 1;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private Context mContext;

    private int i;
    private Long creationtime;


    public TaskHolder (View itemView){
        super (itemView);
        mNameField = itemView.findViewById(R.id.task_name);
        mIconState = itemView.findViewById(R.id.rectangle_view);
        mCreationtimeField = itemView.findViewById(R.id.creation_time);
        mRunningTaskProgess = itemView.findViewById(R.id.progress_running_task);
        mRelativeLayout = itemView.findViewById(R.id.relative_layout);
    }

    public void bind (Task task){
        setName(task.getName());
        setCreationtime(task.getCreationtime());
        setIconstate(task.getState(), task.getDuration(), task.getProgress());

        if (task.getProgress() == 100){
            EventBus.getDefault().postSticky(new MessageEvent());
        }


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void setName(String name) {
        mNameField.setText(name);
    }

    private void setCreationtime(Long creationtime) {
        this.creationtime = creationtime;
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




//                /** Notificacion **/
//                mBuilder = new NotificationCompat.Builder(mContext)
//                        .setSmallIcon(android.R.drawable.stat_sys_warning)
//                        .setLargeIcon((((BitmapDrawable)itemView.getContext().getApplicationContext().getResources()
//                                .getDrawable(R.drawable.common_full_open_on_phone)).getBitmap()))
//                        .setContentTitle("Tarea completada")
//                        .setContentText("Toca aquí para visualizarla")
//                        .setContentInfo("")
//                        .setTicker("Alerta!");
//
//
//                Intent notIntent = new Intent(mContext, MainActivity.class);
//                PendingIntent contIntent = PendingIntent.getActivity(mContext, 0, notIntent, 0);
//                mBuilder.setContentIntent(contIntent);
//
//                NotificationManager mNotificationManager =
//                        (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//                mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
//                /******************/
                }
                break;
        }
    }


//    private void mInitNotification(){


//    }


}


