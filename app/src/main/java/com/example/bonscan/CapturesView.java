package com.example.bonscan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
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
            addToImageList();
        } else {
            images = savedInstanceState.getStringArrayList("images_array");
            addToImageList();

        }
        gallery.setAdapter(new PhotoViewAdapter(this));

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("images_array", images);
    }

    private void addToImageList() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
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
    }

    public class PhotoViewAdapter extends BaseAdapter {

        private Activity context;

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
                        .setLayoutParams(new GridView.LayoutParams(350, 500));

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