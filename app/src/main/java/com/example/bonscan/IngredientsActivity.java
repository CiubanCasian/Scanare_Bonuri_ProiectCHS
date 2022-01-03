package com.example.bonscan;


import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    private String[] Ingredients = {};
    private Button Next;
    private Button Plus;
    private Button Minus;
    private LinearLayout Layout;
    private ArrayList<String> WantedIngredients = new ArrayList<String>();
    private EditText Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Layout = findViewById(R.id.Layout);
        for(String i : Ingredients){
            CheckBox cb = new CheckBox(this);
            cb.setText(i);
            cb.setTextSize(24);
            cb.setChecked(true);
            addIngredientToList(i);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cb.isChecked())
                        addIngredientToList(i);
                    else
                        deleteIngredientFromList(i);
                }
            });
            Layout.addView(cb);
        }
        
        Plus = findViewById(R.id.Plus);
        Name = findViewById(R.id.Name);
        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = Name.getText().toString();
                if(!text.isEmpty())
                    addNewIngredient(text);
                else {
                    Toast failToast = Toast.makeText(getApplicationContext(), "Enter the name for the ingredient", Toast.LENGTH_SHORT);
                    failToast.show();
                }
            }
        });
    }

    private void addNewIngredient(String text) {
        CheckBox cb = new CheckBox(this);
        cb.setText(text);
        cb.setChecked(true);
        cb.setTextSize(24);
        addIngredientToList(text);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb.isChecked())
                    addIngredientToList(text);
                else
                    deleteIngredientFromList(text);
            }
        });
        Layout.addView(cb);
    }

    private void deleteIngredientFromList(String i) {
        WantedIngredients.remove(WantedIngredients.indexOf(i));
    }

    private void addIngredientToList(String i) {
        if(!WantedIngredients.contains(i))
            WantedIngredients.add(i);
    }
}