package com.example.bonscan;


import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    private String[] Ingredients = {"Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua","Carne","Lapte","Oua"};
    private Button Next;
    private Button Plus;
    private Button Minus;
    private LinearLayout Layout;
    private ArrayList<String> WantedIngredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Layout = findViewById(R.id.Layout);
        for(String i : Ingredients){
            CheckBox cb = new CheckBox(this);
            cb.setText(i);
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
    }

    private void deleteIngredientFromList(String i) {
        WantedIngredients.remove(WantedIngredients.indexOf(i));
    }

    private void addIngredientToList(String i) {
        if(!WantedIngredients.contains(i))
            WantedIngredients.add(i);
    }
}