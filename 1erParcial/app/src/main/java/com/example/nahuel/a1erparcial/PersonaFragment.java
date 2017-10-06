package com.example.nahuel.a1erparcial;

/*import android.app.Fragment;*/
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;


/**
 * Created by nahuel on 05/10/2017.
 */


public class PersonaFragment extends Fragment
{

    public PersonaFragment()
    {}


    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_clientes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_tabs, container, false);
    }


    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView lv = (ListView) view.findViewById(R.id.lvSome);
        lv.setAdapter(adapter);
    }
*/


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

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        ((MainActivity)getActivity()).getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
/*        switch (item.getItemId())
        {
//            case R.id.action_nuevo:
//
//                Intent NuevoProductoIntent = new Intent( ((MainActivity)getActivity()),IngresoClienteActivity.class);
//                startAct:
//
//
//                default:
                  return super.onOptionsItemSelected(item);
        }*/
    return true;
    }
}
