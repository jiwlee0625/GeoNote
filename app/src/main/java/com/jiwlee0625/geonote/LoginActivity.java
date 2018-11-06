package com.jiwlee0625.geonote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }

    /**
    public void onClickRegisterBttn(View v) {
        Toast.makeText(this, "Clicked on Register Bttn", Toast.LENGTH_LONG).show();
    }*/
    public void logInAndRegister(String emailInput, String passInput) {
        Toast.makeText(LoginActivity.this, "login and register called", Toast.LENGTH_LONG).show();
        mAuth.signInWithEmailAndPassword(emailInput, passInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent goToMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(goToMainIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(LoginActivity.this, "line 48" + errorCode, Toast.LENGTH_LONG).show();
                            switch (errorCode) {
                                case "auth/invalid-email":
                                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                                    break;
                                case "auth/user-disabled":
                                    Toast.makeText(LoginActivity.this, "User disabled", Toast.LENGTH_LONG).show();
                                    break;
                                case "auth/user-not-found":
                                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_LONG).show();
                                    //Ask to register here
                                    registerProcess();
                                    break;
                                case "auth/wrong-password":
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    }
                });
    }
    public void registerProcess() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //REGISTER
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //NOT REGISTER
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("The email you entered isn't registered. Want to register with entered email and password?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
    public void onClickLogInBttn(View v) {
        EditText emailView = (EditText)findViewById(R.id.authEmail);
        String emailInput = emailView.getText().toString();
        EditText passView = (EditText) findViewById(R.id.authPass);
        String passInput = passView.getText().toString();
        logInAndRegister(emailInput, passInput);
    }
}
