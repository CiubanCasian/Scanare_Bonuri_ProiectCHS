package com.example.bonscan;


import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

public class IngredientsActivity extends AppCompatActivity {

    private String[] Ingredients = {};
    private Button Next;
    private Button Plus;
    private LinearLayout Layout;
    private ArrayList<String> WantedIngredients = new ArrayList<String>();
    private EditText Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Layout = findViewById(R.id.Layout);
        for(String i : Ingredients){
            addNewIngredient(i);
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

        Next = findViewById(R.id.button3);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLinks();
            }
        });
    }

    private void addNewIngredient(String text) {
        if (text.equals(" ") || text.isEmpty() || Pattern.matches(" *", text)){
            Toast failToast = Toast.makeText(getApplicationContext(), "Enter the name for the ingredient", Toast.LENGTH_SHORT);
            failToast.show();
            return;
        }
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
        if(!WantedIngredients.contains(i)){
            WantedIngredients.add(i);
        }
    }

    private void getLinks(){
        //Do a google search with the words: reteta + (WantedIngredients[0] or WantedIngredients[1] or WantedIngredients[2] ... or (WantedIngredients[0] and WantedIngredients[1 and ...]))
        String regex = "(reteta) (";
        String aux = "(";
        for(String i:WantedIngredients){
            regex = regex + i + "|";
            aux = aux + i + "&";
        }
        regex = regex + aux;
        regex = regex.substring(0,regex.length()-1);
        regex = regex + "))";
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, regex);
        startActivity(intent);
    }
}