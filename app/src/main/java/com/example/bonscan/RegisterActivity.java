package com.example.bonscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        clearErrors();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void registerButtonOnClick(View view) {
        TextInputLayout emailInputLayout = findViewById(R.id.emailInput);
        String email = String.valueOf(emailInputLayout.getEditText().getText());
        emailInputLayout.setError(null);

        if (!isEmailValid(email)) {
            emailInputLayout.setError("Invalid email format");
        }

        TextInputLayout passwordInputLayout = findViewById(R.id.passwordInput);
        String password = String.valueOf(passwordInputLayout.getEditText().getText());
        passwordInputLayout.setError(null);

        if (password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters long");
        }

        TextInputLayout password2InputLayout = findViewById(R.id.password2Input);
        String password2 = String.valueOf(password2InputLayout.getEditText().getText());
        password2InputLayout.setError(null);

        if (password.equals(password2)) {
            createAccount(email, password);
        } else {
            password2InputLayout.setError("Passwords do not match.");
        }

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast successfullToast = Toast.makeText(getApplicationContext(), "Register successfull", Toast.LENGTH_SHORT);
                            successfullToast.show();
                        } else {
                            Log.w("createUser:failure", task.getException());
                            Toast failToast = Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_SHORT);
                            failToast.show();
                        }

                    }
                });
    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void clearErrors() {
        TextInputLayout emailInputLayout = findViewById(R.id.emailInput);
        emailInputLayout.setError(null);
        TextInputLayout passwordInputLayout = findViewById(R.id.passwordInput);
        passwordInputLayout.setError(null);
        TextInputLayout password2InputLayout = findViewById(R.id.password2Input);
        password2InputLayout.setError(null);
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //TODO: check account already exists for email check
}