package com.example.nahuel.share;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

//public class AllTasksFragment extends Fragment {
public class AllTasksFragment extends Fragment implements FirebaseAuth.AuthStateListener{
    public static final String TAG = "AllTasksFragment";
    public static final String ARG_PAGE = "ARG_PAGE";
    private FloatingActionButton mAddTask;
    private RecyclerView mRecyclerView;
    private int mPage;

    /*** Consulta a la base de datos ***/
    protected static final Query mTaskQuery = FirebaseDatabase.getInstance().
            getReference().
            child("tasks").
            limitToLast(3);
    /***********************************/

    public static AllTasksFragment newInstance (int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        AllTasksFragment fragment = new AllTasksFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_tasks_fragment, container, false);
        return view;
    }


    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mAddTask = view.findViewById(R.id.fab_add_task);
        mRecyclerView = view.findViewById(R.id.rvTasks);

        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getActivity().getApplicationContext(), AddTaskActivity.class);
                startActivity(newIntent);
            }
        });

        /** RecyclerView **/
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        /******************/
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
    }

    @Override
    public void onDestroy()   {
        super.onDestroy();
    }
//
//
//
    @Override
    public void onStart() {
        super.onStart();
        if (isSignedIn()) { attachRecyclerViewAdapter(); }
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
//        mSendButton.setEnabled(isSignedIn());
//        mMessageEdit.setEnabled(isSignedIn());

        if (isSignedIn()) {
            attachRecyclerViewAdapter();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.signing_in, Toast.LENGTH_SHORT).show();
//            auth.signInAnonymously().addOnCompleteListener(new SignInResultNotifier(this));
        }
    }
//
//
    private boolean isSignedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }
//
    private void attachRecyclerViewAdapter() {
        final RecyclerView.Adapter adapter = newAdapter();

        // Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });

        mRecyclerView.setAdapter(adapter);
    }


    /*** Defino al adapter ***/
    protected RecyclerView.Adapter newAdapter() {
        FirebaseRecyclerOptions<Task> options =
                new FirebaseRecyclerOptions.Builder<Task>()
                        .setQuery(mTaskQuery, Task.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirebaseRecyclerAdapter<Task, TaskHolder>(options) {
            /*** Lo inflo ***/
            @Override
            public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new TaskHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.task, parent, false));
            }

            @Override
            protected void onBindViewHolder(TaskHolder holder, int position, Task model) {
                holder.bind(model);
            }

//            @Override
//            public void onDataChanged() {
//                // If there are no chat messages, show a view that invites the user to add a message.
//                mEmptyListMessage.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
//            }
        };
    }
}
