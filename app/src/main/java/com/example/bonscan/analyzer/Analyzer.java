package com.example.bonscan.analyzer;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class Analyzer {

    private ArrayList<String> dbIngredients;

    public Analyzer(String ingredients) {
        dbIngredients = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(ingredients);
            for (int i = 0; i < jsonArray.length(); i++) {
                dbIngredients.add((String) jsonArray.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "Analyzer{" +
                "dbIngredients=" + dbIngredients +
                '}';
    }

    public void analyzeText() {

    }
}
