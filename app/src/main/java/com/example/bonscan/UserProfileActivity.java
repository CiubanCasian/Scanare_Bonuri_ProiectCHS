package com.example.bonscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.ObjectInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_user_profile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        ImageView profileImage = findViewById(R.id.ProfileImage);
        if (user != null) {
            // User is signed in
            //Picasso.get().load(user.getPhotoUrl()).into(imageView);
            Uri photoUrl = user.getPhotoUrl();
            if (photoUrl != null) {
                Log.w("PhotoUrl",photoUrl.toString());
            }
            else{
                Log.w("PhotoUrl","NO Photo!");
                profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user_profile));
            }
        } else {
            // No user is signed in
            Toast successfullToast = Toast.makeText(getApplicationContext(), "UnSuccessfull user", Toast.LENGTH_SHORT);
            successfullToast.show();
        }
    }

    public void onClickLogout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Toast successfullToast = Toast.makeText(getApplicationContext(), "Loged out!", Toast.LENGTH_SHORT);
        successfullToast.show();
        finish();
    }
}