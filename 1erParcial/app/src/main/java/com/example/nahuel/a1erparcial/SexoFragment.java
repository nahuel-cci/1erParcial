package com.example.nahuel.a1erparcial;

/*import android.app.Fragment;*/

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by nahuel on 05/10/2017.
 */


public class SexoFragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private TextView tvSexo;


    public static SexoFragment newInstance (int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SexoFragment fragment = new SexoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        //setContentView(R.layout.activity_clientes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sexo_fragment, container, false);
        return view;
    }


    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvSexo= (TextView) view.findViewById(R.id.tvSexo);
        tvSexo.setText("Telefono #"+mPage);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // telefono
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent3(MessageEvent3 event){
        tvSexo.setText(event.message);
    }
}
