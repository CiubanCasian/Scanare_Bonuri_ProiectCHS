package com.example.bonscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickLogin(View view) {
        TextInputLayout emailInputLayout = findViewById(R.id.emailInput);
        EditText emailText = emailInputLayout.getEditText();
        String email = emailText.getText().toString();

        TextInputLayout passwordInputLayout = findViewById(R.id.passwordInput);
        String password = String.valueOf(passwordInputLayout.getEditText().getText());

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast successfullToast = Toast.makeText(getApplicationContext(), "Login successfull", Toast.LENGTH_SHORT);
                            successfullToast.show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //goToUserProfile(user);
                            goToHome(user);
                        } else {
                            Log.w("singInUser:failure", task.getException());
                            Toast failToast = Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT);
                            failToast.show();
                        }

                    }
                });
    }

    private void goToUserProfile(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
            startActivity(intent);
        }

    }

    private void goToHome(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClickForgotPass(View view) {
    }

    @Override
    public void onBackPressed() {
        //Disable back button
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}