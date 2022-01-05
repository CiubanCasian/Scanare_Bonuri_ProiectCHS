package com.example.bonscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class Camera extends AppCompatActivity implements ImageAnalysis.Analyzer, View.OnClickListener {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    private ImageCapture imageCapture;
    private PreviewView previewView;
    private Button captureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = findViewById(R.id.previewView);
        captureButton = findViewById(R.id.camera_capture_button);

        captureButton.setOnClickListener(this);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Image capture use case
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        // Image analysis use case
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        //TODO:extract text from photo
        imageAnalysis.setAnalyzer(getExecutor(), this);

        //bind to lifecycle:
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        // image processing here for the current frame
        Log.d("TAG", "analyze: got the frame at: " + image.getImageInfo().getTimestamp());
        image.close();
    }

    @Override
    public void onClick(View view) {

        imageCapture.takePicture(
                getExecutor(),
                new ImageCapture.OnImageCapturedCallback() {
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy imageProxy) {
                        Log.i("SUCCESS", "Capture success");
                        sendImageToCaptureView(imageProxy);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.i("FAIL", "Capture failed with message:  " + exception.getMessage());
                    }
                }
        );
    }

    private void sendImageToCaptureView(@NonNull ImageProxy imageProxy) {
        Intent intent = new Intent(getApplicationContext(), CapturesView.class);
        @SuppressLint("UnsafeOptInUsageError") Image image = imageProxy.getImage();
        assert image != null;
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
        intent.putExtra("byteArray", bs.toByteArray());
        startActivity(intent);
    }

}