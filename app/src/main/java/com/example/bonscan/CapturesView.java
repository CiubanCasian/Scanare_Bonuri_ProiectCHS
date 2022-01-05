package com.example.bonscan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CapturesView extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 124;

    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captures_view);
        GridView gallery = findViewById(R.id.galleryGridView);

        gallery.setAdapter(new PhotoViewAdapter(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().hasExtra("byteArray")) {
            //ImageView imv= new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            // imv.setImageBitmap(bitmap);
            Log.i("HAS_PHOTO", "The photo has been send to the next activity succesfully");
        }
    }

    public class PhotoViewAdapter extends BaseAdapter {

        private int[] image_id;
        private Activity context;

        public PhotoViewAdapter(Activity localContext) {
            context = localContext;
            images = getAllShownImagesPath(context);
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
                        .setLayoutParams(new GridView.LayoutParams(220, 250));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(images.get(position))
                    .centerCrop()
                    .into(picturesView);

            return picturesView;
        }

        private ArrayList<String> getAllShownImagesPath(Activity activity) {
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            Log.i("PATH", uri.getPath());

            String[] projection = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);

                listOfAllImages.add(absolutePathOfImage);
            }
            cursor.close();
            return listOfAllImages;
        }

    }

    public void onClickAnother(View view) {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }

    public void onClickAnalyze(View view) {
    }
}