package com.example.nahuel.share;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    private String TAG = "TaskHolder";


    public TaskHolder (View itemView){
        super (itemView);
        mNameField = itemView.findViewById(R.id.task_name);
        mIconState = itemView.findViewById(R.id.rectangle_view);
        mCreationtimeField = itemView.findViewById(R.id.creation_time);
    }

    public void bind (Task task){
        setName(task.getName());
        setCreationtime(task.getCreationtime());
        setIconstate(task.getState());

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

    private void setIconstate (String state){
        switch(state){
            case "completed":
                mIconState.setBackgroundResource(R.drawable.rectangle_completed);
                break;
            case "scheduled":
                mIconState.setBackgroundResource(R.drawable.rectangle_scheduled);
                break;
            case "running":
                mIconState.setBackgroundResource(R.drawable.rectangle_running);
                break;
        }

    }

}


