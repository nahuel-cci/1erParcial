package com.example.nahuel.a1erparcial;

/*import android.app.Fragment;*/
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by nahuel on 05/10/2017.
 */


public class PersonaFragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private TextView tvNombre;
    private TextView tvTelefono;


    public static PersonaFragment newInstance (int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PersonaFragment fragment = new PersonaFragment();
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
        View view = inflater.inflate(R.layout.persona_fragment, container, false);
        return view;
    }


    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        tvTelefono = (TextView) view.findViewById(R.id.tvTelefono);
        tvNombre.setText("Nombre #"+mPage);
        tvTelefono.setText("Telefono #"+mPage);
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //Toolbar toolbar = (Toolbar) getView().findViewById(R.id.appbar);
        //((MainActivity)getActivity()).setSupportActionBar(toolbar);

    }


/*    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.
    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }*/


    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        ((MainActivity)getActivity()).getMenuInflater().inflate(R.menu.context_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
///*        switch (item.getItemId())
//        {
////            case R.id.action_nuevo:
////
////                Intent NuevoProductoIntent = new Intent( ((MainActivity)getActivity()),IngresoClienteActivity.class);
////                startAct:
////
////
////                default:
//                  return super.onOptionsItemSelected(item);
//        }*/
//    return true;
//    }



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

    // nombre
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        tvNombre.setText(event.message);
    }

    // telefono
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent2(MessageEvent2 event){
        tvTelefono.setText(event.message);
    }
}
