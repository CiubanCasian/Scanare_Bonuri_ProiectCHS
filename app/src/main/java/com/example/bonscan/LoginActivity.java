package com.example.bonscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        if (!isEmailValid(email)) {
            emailInputLayout.setError("Invalid email format");
            Log.i("Messages", emailInputLayout.getError().toString());
        } else {
            emailInputLayout.setError(null);
        }

        TextInputLayout passwordInputLayout = findViewById(R.id.passwordInput);
        String password = String.valueOf(passwordInputLayout.getEditText().getText());

        if (password.length() < 6) {
            passwordInputLayout.setError("Password is too short");
        } else {
            passwordInputLayout.setError(null);
        }

        signIn(email, password);

    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast failToast = Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT);
                        failToast.show();
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