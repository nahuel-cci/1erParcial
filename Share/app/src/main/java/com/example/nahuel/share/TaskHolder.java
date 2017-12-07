package com.example.nahuel.share;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
    private String TAG = "TaskHolder";
    private ProgressBar mRunningTaskProgess;
    private MiTareaAsincrona mTareaAsc;


    public TaskHolder (View itemView){
        super (itemView);
        mNameField = itemView.findViewById(R.id.task_name);
        mIconState = itemView.findViewById(R.id.rectangle_view);
        mCreationtimeField = itemView.findViewById(R.id.creation_time);
        mRunningTaskProgess = itemView.findViewById(R.id.progress_running_task);
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
                mTareaAsc.execute();
                break;
        }

    }

    private void tareaLarga(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }

    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {


        /*** Le paso la cantidad de tiempo que se tiene que ejcutar en segundos***/
        @Override
        protected Boolean doInBackground(Void... duration) {

            for(int i=1; i<=10; i++) {
                tareaLarga(); /*** Tarea que tarda un segundo en ejcutarse ***/
                publishProgress(i*10);
                if(isCancelled())
                    break;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
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


