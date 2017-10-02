package com.example.nahuel.a1erparcial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public EditText userName;
    public EditText userPass;
    public Button butLogIn;
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.userName);
        userPass = (EditText) findViewById(R.id.etAddPhone);
        butLogIn = (Button) findViewById(R.id.butLogIn);

        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange (View v, boolean hasFocus ) {
                if (hasFocus) {
                    if (userName.getText().toString().equals(getString(R.string.userDefaultText))) {
                        userName.getText().clear();
                    }
                } else {
                    if (userName.getText().toString().length() == 0) {
                        userName.setText(getString(R.string.userDefaultText));
                    }
                }
            }
        });

        userPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange (View v, boolean hasFocus ) {
                if (hasFocus) {
                    if (userPass.getText().toString().equals(getString(R.string.passDefaultText))) {
                        userPass.getText().clear();
                    }
                } else {
                    if (userPass.getText().toString().length() == 0) {
                        userPass.setText(getString(R.string.passDefaultText));
                    }
                }
            }
        });
    }

    /** Called when the user taps the Send button */
    public void actionLogIn(View view) {
        // Do something in response to button
        // Validar datos
//        if (    userName.getText().toString().matches("Nahuel") &&
//                userPass.getText().toString().matches("Nahuel")){

            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("previousActivity", "MainActivity");
            intent.putExtra(EXTRA_MESSAGE, userName.getText().toString());
            startActivity(intent);
//        }
    }


    /***
    @Override
    public void onClick(View v) {
        userName.getText().clear();
        userPass.getText().clear();
    }
    ***/

}
