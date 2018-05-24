package com.dominik.nestapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FlatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);

        Bundle extras = getIntent().getExtras();

        TextView tv1=(TextView) findViewById(R.id.textView);
        tv1.setText(extras.getString("name"));

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
        String maxUrl = extras.getString("maxUrl");

        Glide
                .with(this)
                .load(maxUrl)
                .into(imageView1);
    }

}
