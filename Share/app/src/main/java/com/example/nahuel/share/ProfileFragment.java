package com.example.nahuel.share;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String TAG = "ProfileFragment";
    private int mPage;
    private TextView mId;
    private ProgressBar spinner;
    private MiTareaAsincrona mTareaAsc;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public static ProfileFragment newInstance (int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }


    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mId = (TextView) view.findViewById(R.id.tvId);
        spinner=(ProgressBar)view.findViewById(R.id.progress_running_task);
        spinner.setVisibility(View.VISIBLE);
        Log.d("ProfileFragment:", currentUser.getEmail());
//        mId.setText(currentUser.getEmail());
        mTareaAsc = new MiTareaAsincrona();
        mTareaAsc.execute();
    }





    private void tareaLarga(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }

    private class MiTareaAsincrona extends AsyncTask<Void, Integer, Boolean> {
        private int i;

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
            spinner.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            spinner.setMax(100);
            spinner.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
//            mRelativeLayout.setBackgroundColor(Color.parseColor("#FFB8B8B8"));
//            if(result)
//                Toast.makeText(TaskHolder.this, "Tarea finalizada!",
//                        Toast.LENGTH_SHORT).show();
            mId.setText(currentUser.getEmail());
            spinner.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getContext(), "Tarea cancelada!", Toast.LENGTH_SHORT).show();
        }


    }

}
