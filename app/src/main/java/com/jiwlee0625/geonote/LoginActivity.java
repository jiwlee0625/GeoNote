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
    private EditText emailView;
    private EditText passView;
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
    public void logIn(String emailInput, String passInput) {
        mAuth.signInWithEmailAndPassword(emailInput, passInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            switch (errorCode) {
                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                    emailView.setText("");
                                    passView.setText("");
                                    break;
                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(LoginActivity.this, "User disabled", Toast.LENGTH_SHORT).show();
                                    emailView.setText("");
                                    passView.setText("");
                                    break;
                                case "ERROR_USER_NOT_FOUND":
                                    //Ask to register here
                                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                    emailView.setText("");
                                    passView.setText("");
                                    break;
                                case "ERROR_WRONG_PASSWORD":
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    passView.setText("");
                                    break;
                            }
                        }
                    }
                });
    }
    public void register(String emailInput, String passInput) {
        mAuth.createUserWithEmailAndPassword(emailInput, passInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Created user account, logging in now", Toast.LENGTH_SHORT).show();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            FirebaseAuthException taskError = ((FirebaseAuthException) task.getException());
                            String errorCode = taskError.getErrorCode();
                            switch (errorCode) {
                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(LoginActivity.this, "Email already in use", Toast.LENGTH_SHORT).show();
                                    emailView.setText("");
                                    passView.setText("");
                                    break;
                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                    emailView.setText("");
                                    passView.setText("");
                                    break;
                                case "ERROR_WEAK_PASSWORD":
                                    Toast.makeText(LoginActivity.this, "Weak password", Toast.LENGTH_SHORT).show();
                                    passView.setText("");
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, taskError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    public void onClickLogInBttn(View v) {
        emailView = (EditText)findViewById(R.id.authEmail);
        String emailInput = emailView.getText().toString();
        if(emailInput.length() == 0) {
            Toast.makeText(LoginActivity.this, "Please input email", Toast.LENGTH_SHORT).show();
            return;
        }
        passView = (EditText) findViewById(R.id.authPass);
        String passInput = passView.getText().toString();
        if(passInput.length() == 0) {
            Toast.makeText(LoginActivity.this, "Please input password", Toast.LENGTH_SHORT).show();
            return;
        }
        logIn(emailInput, passInput);
    }
    public void onClickRegisterBttn(View v) {
        emailView = (EditText)findViewById(R.id.authEmail);
        String emailInput = emailView.getText().toString();
        if(emailInput.length() == 0) {
            Toast.makeText(LoginActivity.this, "Please input email", Toast.LENGTH_SHORT).show();
            return;
        }
        passView = (EditText) findViewById(R.id.authPass);
        String passInput = passView.getText().toString();
        if(passInput.length() == 0) {
            Toast.makeText(LoginActivity.this, "Please input password", Toast.LENGTH_SHORT).show();
            return;
        }
        register(emailInput, passInput);
    }
    public void goToMain() {
        Intent goToMainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goToMainIntent);
    }
}
