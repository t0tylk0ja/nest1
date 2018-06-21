package com.dominik.nestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    Flat flatToShow;
    Bundle extras;
    private List<Flat> mFlatList;
    private FlatDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        extras = getIntent().getExtras();

        ImageView planView = (ImageView) findViewById(R.id.planView);
        ImageView galleryView = (ImageView) findViewById(R.id.galleryView);
        ImageView galleryView2 = (ImageView) findViewById(R.id.galleryView2);
        ImageView galleryView3 = (ImageView) findViewById(R.id.galleryView3);

        dbHelper = FlatDBHelper.getInstanse(this);
        mFlatList=dbHelper.flatList("");

        String name =extras.getString("flatName");
        for(Flat x:mFlatList){
            if(x.getName().equals(name)){
                flatToShow=x;
            }
        }

        Glide
                .with(this)
                .load(flatToShow.getPlanUrl())
                .into(planView);
        Glide
                .with(this)
                .load(flatToShow.getGalleryUrl())
                .into(galleryView);

        Glide
                .with(this)
                .load(flatToShow.getGalleryUrl2())
                .into(galleryView2);
    }
}
