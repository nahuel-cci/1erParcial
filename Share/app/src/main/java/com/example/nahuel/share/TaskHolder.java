package com.example.nahuel.share;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.provider.CalendarContract;
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
    private MiTareaAsincrona mTareaAsc;


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
        setIconstate(task.getState(), task.getDuration());

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

    private void setIconstate (String state, Long duration){
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
                mTareaAsc = new MiTareaAsincrona();
                Log.d(TAG, "duration:"+duration+duration.toString());
                mTareaAsc.execute(duration);
                break;
        }

    }

    private void tareaLarga(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }

    private class MiTareaAsincrona extends AsyncTask<Long, Long, Boolean> {


        /*** Le paso la cantidad de tiempo que se tiene que ejcutar en segundos***/
        @Override
        protected Boolean doInBackground(Long... duration) {
            Log.d(TAG, "ACAAA duration:"+duration+duration.toString());
            for(int i=1; i<=duration[0]; i++) {
                tareaLarga(); /*** Tarea que tarda un segundo en ejcutarse ***/
                publishProgress(i*10*10/duration[0]);
                if(isCancelled())
                    break;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            int progreso = values[0].intValue();
            mRunningTaskProgess.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            mRunningTaskProgess.setMax(100);
            mRunningTaskProgess.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            mRelativeLayout.setBackgroundColor(Color.parseColor("#FFB8B8B8"));
//            if(result)
//                Toast.makeText(TaskHolder.this, "Tarea finalizada!",
//                        Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
//            Toast.makeText(TaskHolder.this, "Tarea cancelada!",
//                    Toast.LENGTH_SHORT).show();
        }


    }


}


