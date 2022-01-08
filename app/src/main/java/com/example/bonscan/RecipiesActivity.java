package com.example.bonscan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipiesActivity extends AppCompatActivity {

    private Button Finish;
    private LinearLayout Layout;
    private ArrayList<String> LinksList = new ArrayList<String>();
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

        Bundle b = getIntent().getExtras();
        if(b != null)
            LinksList = b.getStringArrayList("RecipesURLs");
        Layout = findViewById(R.id.Layout);
        for(String i: LinksList){
            TextView textView =(new TextView(this));
            textView.setClickable(true);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setTextSize(24);
            String text = i;
            textView.setText(Html.fromHtml(text));
            Layout.addView(textView);
        }
    }
}