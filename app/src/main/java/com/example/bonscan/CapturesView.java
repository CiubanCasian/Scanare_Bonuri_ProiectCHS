package com.example.bonscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bonscan.models.PhotoViewAdapter;

public class CapturesView extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captures_view);
    }

    public void onClickAnother(View view) {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }

    public void onClickAnalyze(View view) {
    }
}