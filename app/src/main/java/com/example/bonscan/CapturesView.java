package com.example.bonscan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.bonscan.analyzer.Analyzer;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CapturesView extends AppCompatActivity {

    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captures_view);
        GridView gallery = findViewById(R.id.galleryGridView);
        if (savedInstanceState == null) {
            images = new ArrayList<>();
        } else {
            images = savedInstanceState.getStringArrayList("images_array");

        }
        addToImageList();
        gallery.setAdapter(new PhotoViewAdapter(this));

    }

    private void addToImageList() {
        Bitmap bitmap = getImageFromCamera();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        images.add(result);
    }

    public void onClickAnother(View view) {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }

    public void onClickAnalyze(View view) {
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        InputImage image = InputImage.fromBitmap(getImageFromCamera(), 0);
        String ingredients = extractIngredients();
        Log.i("INGREDIENTS", ingredients);
        Analyzer analyzer = new Analyzer(ingredients);
        //analyzer.analyzeText(result_text);
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(this::recognizeText)
                        .addOnFailureListener(
                                e -> {
                                    // Task failed with an exception
                                    // ...
                                });

    }

    private String extractIngredients() {
        String ingredients = "";
        try {
            InputStream inputStream = getAssets().open("ingredients.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            ingredients = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    private void recognizeText(Text result) {

        String resultText = result.getText();
        Log.i("ResultText", resultText);
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Log.i("ResultBlockText", blockText);
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Log.i("ResultLineText", lineText);
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    Log.i("ResultElementText", elementText);
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }
    }

    private Bitmap getImageFromCamera() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
        return bitmap;
    }

    public class PhotoViewAdapter extends BaseAdapter {

        private final Activity context;

        public PhotoViewAdapter(Activity localContext) {
            context = localContext;
        }

        public int getCount() {
            return images.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_XY);
                picturesView
                        .setLayoutParams(new GridView.LayoutParams(700, 1000));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(Base64.decode(images.get(position), Base64.DEFAULT))
                    .centerCrop()
                    .into(picturesView);

            return picturesView;
        }

    }

}