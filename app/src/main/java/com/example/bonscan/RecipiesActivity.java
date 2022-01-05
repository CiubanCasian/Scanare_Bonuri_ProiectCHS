package com.example.bonscan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class RecipiesActivity extends AppCompatActivity {

    private Button Finish;
    private LinearLayout Layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        Finish = findViewById(R.id.Finish);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipiesActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}