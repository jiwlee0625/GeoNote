package com.jiwlee0625.geonote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
    public void onClickRegisterBttn(View v) {
        Toast.makeText(this, "Clicked on Register Bttn", Toast.LENGTH_LONG).show();
    }*/
    public void logInAndRegister(String emailInput, String passInput) {

    }
    public void onClickLogInBttn(View v) {
        EditText emailView = (EditText)findViewById(R.id.authEmail);
        String emailInput = emailView.getText().toString();
        EditText passView = (EditText) findViewById(R.id.authPass);
        String passInput = passView.getText().toString();
        logInAndRegister(emailInput, passInput);
        Toast.makeText(this, "Clicked on Login Bttn with\nemail: " + emailInput + "\n" + "passInput: " + passInput, Toast.LENGTH_LONG).show();
    }
}
