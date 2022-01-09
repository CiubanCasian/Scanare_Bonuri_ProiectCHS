package com.example.bonscan.analyzer;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.google.mlkit.vision.text.Text;

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

    public ArrayList<String> analyzeText(Text text) {
        ArrayList<String> foundIngredients = new ArrayList<>();
        String resultText = text.getText();
        Log.i("ResultText", resultText);
        for (Text.TextBlock block : text.getTextBlocks()) {
            String blockText = block.getText();
            //Log.i("ResultBlockText", blockText);
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                //Log.i("ResultLineText", lineText);
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText().toUpperCase();
                    if (dbIngredients.contains(elementText)) {
                        foundIngredients.add(elementText.toLowerCase());
                    }
                    Log.i("ResultElementText", elementText);
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }

        Log.i("INGREDIENTS", foundIngredients.toString());
        return foundIngredients;
    }
}
