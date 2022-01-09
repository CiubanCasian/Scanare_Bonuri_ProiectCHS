package com.example.bonscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseStorage storage;
    private ImageView profileImage;

    private Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        profileImage = findViewById(R.id.ProfileImage);
    }

    @Override
    protected void onResume() {

        super.onResume();
        findPhoto();
    }

    private void findPhoto() {
        if (user != null) {
            // User is signed in
            storage.getReference(user.getUid() + "/Images/ProfilePicture/profile_picture.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Glide.with(HomeActivity.this /* context */)
                            .load(uri)
                            .into(profileImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Log.w("PhotoUrl", "NO Photo!");
                    profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user_profile));
                }
            });
        } else {
            // No user is signed in
            Toast successfulToast = Toast.makeText(getApplicationContext(), "Unsuccessful user", Toast.LENGTH_SHORT);
            successfulToast.show();
        }
    }

    public void goToUserProfile(View view) {
        if (user != null) {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }

    }

    public void onClickScan(View view) {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //Disable back button
    }
}