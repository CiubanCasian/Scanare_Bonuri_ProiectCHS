package com.example.bonscan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private String photoUrl;
    private static final int PICK_IMAGE = 100;
    private ImageView profileImage;
    private ActivityResultLauncher<String> mGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_user_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference("ProfilePic/cat3.png");
        ImageView profileImage = findViewById(R.id.ProfileImage);

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                profileImage.setImageURI(result);
                // Save it to user.uid/Images/ProfilePicture/profile_picture.png
                String path = user.getUid() + "/Images/ProfilePicture/";


                Uri file = result;
                StorageReference storageRef = storage.getReference();
                StorageReference riversRef = storageRef.child(path + "profile_picture.png");
                UploadTask uploadTask = riversRef.putFile(file);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast successfullToast = Toast.makeText(getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT);
                        successfullToast.show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast successfullToast = Toast.makeText(getApplicationContext(), "Upload Succesufull!", Toast.LENGTH_SHORT);
                        successfullToast.show();
                    }
                });
            }
        });

        if (user != null) {
            // User is signed in
            storage.getReference(user.getUid() + "/Images/ProfilePicture/profile_picture.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Glide.with(UserProfileActivity.this /* context */)
                            .load(uri)
                            .into(profileImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Log.w("PhotoUrl","NO Photo!");
                    profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user_profile));
                }
            });
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
    public void onClickProfilePic(View view){
        // Select a picture from phone
        mGetContent.launch("image/*");
    }
}