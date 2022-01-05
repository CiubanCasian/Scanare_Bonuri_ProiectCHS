package com.example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bonscan.R;
import com.example.bonscan.models.PhotoViewAdapter;

public class GalleryFragment extends Fragment {

    private final int[] imageid = {
            R.drawable.imag_1,
            R.drawable.imag_2,
            R.drawable.imag_3,
            R.drawable.imag_4
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView photoRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_gallery, container, false);
        PhotoViewAdapter adapter = new PhotoViewAdapter(imageid);
        photoRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        photoRecycler.setLayoutManager(layoutManager);
        return photoRecycler;
    }
}
